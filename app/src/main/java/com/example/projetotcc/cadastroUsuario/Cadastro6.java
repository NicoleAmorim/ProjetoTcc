package com.example.projetotcc.cadastroUsuario;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetotcc.LoadingDialog;
import com.example.projetotcc.R;
import com.example.projetotcc.androidMask.MaskEditTextChangedListener;
import com.example.projetotcc.controllers.ValidarCadastroUsuario;

import dominio.entidade.CEP;
import dominio.entidade.CEPException;
import dominio.entidade.Usuario;

import org.json.JSONException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cadastro6 extends AppCompatActivity implements Button.OnClickListener {
    private Button btnBuscar;

    private EditText txtCEP;
    private EditText txtRua;
    private EditText txtComplemento;
    private EditText txtBairro;
    private EditText txtEstado;
    private EditText txtCidade;
    private EditText txtNumero;
    public static Context context;
    public static LoadingDialog loadingDialog;
    public static Usuario usuario;
    protected Intent it = null;

    private CEP vCEP;
    private ValidarCadastroUsuario validarCadastroUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_6);

        this.btnBuscar = (Button) findViewById(R.id.btnBuscar);
        this.txtCEP = (EditText) findViewById(R.id.txtCEP);
        this.txtCidade = (EditText) findViewById(R.id.txtLogradouro);
        this.txtRua = (EditText) findViewById(R.id.txtRua);
        this.txtComplemento = (EditText) findViewById(R.id.txtComplemento);
        this.txtBairro = (EditText) findViewById(R.id.txtBairro);
        this.txtEstado = (EditText) findViewById(R.id.txtEstado);
        this.txtNumero = (EditText) findViewById(R.id.txtNumero);

        MaskEditTextChangedListener maskCEP = new MaskEditTextChangedListener("#####-###", this.txtCEP);

        this.txtCEP.addTextChangedListener(maskCEP);

        this.btnBuscar.setOnClickListener(this);
        loadingDialog = new LoadingDialog(this);
        validarCadastroUsuario = new ValidarCadastroUsuario();
        context = this;
    }

    @Override
    public void onClick(View view) {
        if (view == this.btnBuscar) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                this.txtBairro.setText("");
                this.txtComplemento.setText("");
                this.txtEstado.setText("");
                this.txtCidade.setText("");
                this.txtRua.setText("");

                String cep = this.txtCEP.getText().toString();

                Pattern pattern = Pattern.compile("^[0-9]{5}-[0-9]{3}$");
                Matcher matcher = pattern.matcher(cep);
                if (matcher.find()) {
                    new Cadastro6.DownloadCEPTask().execute(cep);
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("Aviso!")
                            .setMessage("Favor informar um CEP válido!")
                            .setPositiveButton(R.string.msgOk, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Sem Internet!")
                        .setMessage("Não tem nenhuma conexão de rede disponível!")
                        .setPositiveButton(R.string.msgOk, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    }
    public void Cadastrar(View view)
    {
        CEP cep = new CEP();
        Uri imagem = Cadastro5.filePath;
        loadingDialog.StartActivityLogin();
        cep.setNumero(txtNumero.getText().toString());
        cep.setCEP(txtCEP.getText().toString());
        cep.setBairro(txtBairro.getText().toString());
        cep.setComplemento(txtComplemento.getText().toString());
        cep.setCidade(txtCidade.getText().toString());
        cep.setEstado(txtEstado.getText().toString());
        cep.setRua(txtRua.getText().toString());
        validarCadastroUsuario.ValidarCadastro6FireBase(cep, imagem);
    }

    private class DownloadCEPTask extends AsyncTask<String, Void, CEP> {
        @Override
        protected CEP doInBackground(String ... ceps) {
            CEP vCep = null;

            try {
                vCep = new CEP(ceps[0]);
            } catch (CEPException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                return vCep;
            }

        }

        @Override
        protected void onPostExecute(CEP result) {
            if (result != null) {
                txtBairro.setText(result.getBairro());
                txtComplemento.setText(result.getComplemento());
                txtEstado.setText(result.getEstado());
                txtCidade.setText(result.getCidade());
                txtRua.setText(result.getRua());
            }
        }
    }
}
