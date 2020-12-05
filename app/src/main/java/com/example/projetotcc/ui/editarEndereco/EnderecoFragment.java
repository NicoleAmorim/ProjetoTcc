package com.example.projetotcc.ui.editarEndereco;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.example.projetotcc.androidMask.MaskEditTextChangedListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dominio.entidade.CEP;
import dominio.entidade.CEPException;
import dominio.entidade.Servico;
import dominio.entidade.Usuario;

public class EnderecoFragment extends Fragment {

    private EnderecoViewModel mViewModel;
    private Intent it;
    private Usuario usuario;
    private Button btnBuscar;
    private EditText editCEP, editCidade, editRua, editComplemento, editBairro, editEstado, editNumero;
    private CEP vCEP;
    public static Context context;

    public static EnderecoFragment newInstance() {
        return new EnderecoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_editar_endereco, container, false);
        usuario = PaginaUsuario.usuario;
        vCEP = PaginaUsuario.cep;
        this.btnBuscar = view.findViewById(R.id.btnBuscar);
        this.editCEP = view.findViewById(R.id.editCEP);
        this.editCidade = view.findViewById(R.id.editLogradouro);
        this.editRua = view.findViewById(R.id.editRua);
        this.editComplemento = view.findViewById(R.id.editComplemento);
        this.editBairro = view.findViewById(R.id.editBairro);
        this.editEstado = view.findViewById(R.id.editEstado);
        this.editNumero = view.findViewById(R.id.editNumero);
        TextView btnChat = view.findViewById(R.id.btnEditarLocal);

        MaskEditTextChangedListener maskCEP = new MaskEditTextChangedListener("#####-###", this.editCEP);

        this.editCEP.addTextChangedListener(maskCEP);

        this.btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar(v);
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editar();
            }
        });

        editRua.setText(vCEP.getRua());
        editCidade.setText(vCEP.getCidade());
        editEstado.setText(vCEP.getEstado());
        editBairro.setText(vCEP.getBairro());
        editNumero.setText(vCEP.getNumero());
        editComplemento.setText(vCEP.getComplemento());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EnderecoViewModel.class);
        // TODO: Use the ViewModel
    }
    public void Editar()
    {
        vCEP.setRua(editRua.getText().toString());
        vCEP.setCidade(editCidade.getText().toString());
        vCEP.setEstado(editEstado.getText().toString());
        vCEP.setComplemento(editComplemento.getText().toString());
        vCEP.setBairro(editBairro.getText().toString());
        vCEP.setNumero(editNumero.getText().toString());
        vCEP.setCEP(editCEP.getText().toString());
        FirebaseFirestore.getInstance().collection("endereco")
                .document(usuario.getId())
                .set(vCEP)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        it = new Intent(PaginaUsuario.context, PaginaUsuario.class);

                        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        PaginaUsuario.context.startActivity(it);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());
                    }
                });
    }
    public void validar(View view)
    {
        if (view == this.btnBuscar) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                this.editBairro.setText("");
                this.editComplemento.setText("");
                this.editEstado.setText("");
                this.editCidade.setText("");
                this.editRua.setText("");

                String cep = this.editCEP.getText().toString();

                Pattern pattern = Pattern.compile("^[0-9]{5}-[0-9]{3}$");
                Matcher matcher = pattern.matcher(cep);
                if (matcher.find()) {
                    new EnderecoFragment.DownloadCEPTask().execute(cep);
                } else {
                    new AlertDialog.Builder(PaginaUsuario.context)
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
                new AlertDialog.Builder(PaginaUsuario.context)
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
                editBairro.setText(result.getBairro());
                editComplemento.setText(result.getComplemento());
                editEstado.setText(result.getEstado());
                editCidade.setText(result.getCidade());
                editRua.setText(result.getRua());
            }
        }
    }

}