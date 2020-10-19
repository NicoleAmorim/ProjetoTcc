package com.example.projetotcc.CadastroUsuario;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetotcc.R;
import com.example.projetotcc.Usuario;

import java.util.ArrayList;
import java.util.Arrays;

public class Cadastro2 extends AppCompatActivity {
    private static String data;
    private static String sexo;
    public static Usuario usuario;
    private String ano, dia, mes;
    private Intent it = null;
    private Context context;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_2);

        context = this;
        usuario = Cadastro1.usuario;

        String[] StringSexo = new String[]{
                "SEXO",
                "Masculino",
                "Feminino"
        };

        String[] StringDia = new String[]{
                "Dia",
                "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                "31"
        };

        String[] StringMes = new String[]{
                "Mês",
                "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                "11", "12"
        };

        String[] StringAno = new String[2020];
        StringAno[0] = "Ano";
        int n = 1;
        for (int i = 2020; i > 1; --i) {
            StringAno[n] = String.valueOf(i);
            ++n;
        }



        //SEXO SPINNER
        Spinner spinnerSexo = (Spinner)this.findViewById(R.id.sexoUserCadastro);
        ArrayAdapter arrayAdapterSexo = new ArrayAdapter(this, R.layout.spinner_item, new ArrayList(Arrays.asList(StringSexo))) {
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView)view;
                if (position == 0) { tv.setTextColor(Color.GRAY);return view; } else { tv.setTextColor(Color.BLACK);return view; }
            }
            public boolean isEnabled(int position) { return position != 0; }
        };
        arrayAdapterSexo.setDropDownViewResource(R.layout.spinner_item);
        spinnerSexo.setAdapter(arrayAdapterSexo);
        spinnerSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                sexo = (String)parent.getItemAtPosition(position);
                if (position > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Sexo : ");
                    stringBuilder.append(Cadastro2.sexo);
                    Toast.makeText(context, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                }

            }
            public void onNothingSelected(AdapterView var1) { }
        });




        //DIA SPINNER
        Spinner spinnerDia = (Spinner)this.findViewById(R.id.spinnerDia);
        ArrayAdapter arrayAdapterDia = new ArrayAdapter(this, R.layout.spinner_item, new ArrayList(Arrays.asList(StringDia))) {
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView)view;
                if (position == 0) { tv.setTextColor(Color.GRAY);return view; } else { tv.setTextColor(Color.BLACK);return view; }
            }
            public boolean isEnabled(int position) { return position != 0; }
        };
        arrayAdapterDia.setDropDownViewResource(R.layout.spinner_item);
        spinnerDia.setAdapter(arrayAdapterDia);
        spinnerDia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                dia = (String)parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView var1) { }
        });




        //MÊS SPINNER
        Spinner spinnerMes = (Spinner)this.findViewById(R.id.spinnerMes);
        ArrayAdapter arrayAdapterMes = new ArrayAdapter(this, R.layout.spinner_item, new ArrayList(Arrays.asList(StringMes))) {
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView)view;
                if (position == 0) { tv.setTextColor(Color.GRAY);return view; } else { tv.setTextColor(Color.BLACK);return view; }
            }
            public boolean isEnabled(int position) { return position != 0; }
        };
        arrayAdapterMes.setDropDownViewResource(R.layout.spinner_item);
        spinnerMes.setAdapter(arrayAdapterMes);
        spinnerMes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                mes = (String)parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView var1) { }
        });





        //ANO SPINNER
        Spinner spinnerAno = (Spinner)this.findViewById(R.id.spinnerAno);
        ArrayAdapter arrayAdapterAno = new ArrayAdapter(this, R.layout.spinner_item, new ArrayList(Arrays.asList(StringAno))) {
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView)view;
                if (position == 0) { tv.setTextColor(Color.GRAY);return view; } else { tv.setTextColor(Color.BLACK);return view; }
            }
            public boolean isEnabled(int position) { return position != 0; }
        };
        arrayAdapterAno.setDropDownViewResource(R.layout.spinner_item);
        spinnerAno.setAdapter(arrayAdapterAno);
        spinnerAno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                ano = (String)parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView var1) { }
        });
    }
    public void Cadastrar(View view) {

        if (ano == "Ano" || mes == "Mês" || dia == "Dia") {
            Toast.makeText(this, "data está vazio", Toast.LENGTH_SHORT).show();
        } else {
            if (sexo == "SEXO") {
                Toast.makeText(this, "sexo está vazio", Toast.LENGTH_SHORT).show();
            } else {
                usuario.setSexo(sexo);
                data = dia + mes + ano;
                usuario.setIdade(Integer.parseInt(data));
                it = new Intent(this, Cadastro3.class);
                this.startActivity(it);
            }
        }
    }
}