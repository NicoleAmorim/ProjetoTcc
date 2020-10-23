package com.example.projetotcc;

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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import dominio.entidade.Usuario;

public class EditarEndereco extends AppCompatActivity {
    public static Usuario usuario;
    private String estado, cidade;
    private Intent it = null;
    public static Context context;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_editar_endereco);

        context = this;

        String[] StringEstado = new String[]{
                "Estado",
                "AC", "AL", "AP", "AM", "BA", "CE", "ES", "GO", "MA", "MT",
                "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS",
                "RO", "RR", "SC", "SP", "SE", "TO", "DF"
        };

        String[] StringCidade = new String[]{
                "Cidade",
                "AC", "AL", "AP", "AM", "BA", "CE", "ES", "GO", "MA", "MT",
                "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS",
                "RO", "RR", "SC", "SP", "SE", "TO", "DF"
        };

        //ESTADO SPINNER
        Spinner spinnerEstado = (Spinner)this.findViewById(R.id.spinnerEstado);
        ArrayAdapter arrayAdapterEstado = new ArrayAdapter(this, R.layout.spinner_item, new ArrayList(Arrays.asList(StringEstado))) {
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView)view;
                if (position == 0) { tv.setTextColor(Color.GRAY);return view; } else { tv.setTextColor(Color.BLACK);return view; }
            }
            public boolean isEnabled(int position) { return position != 0; }
        };
        arrayAdapterEstado.setDropDownViewResource(R.layout.spinner_item);
        spinnerEstado.setAdapter(arrayAdapterEstado);
        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                estado = (String)parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView var1) { }
        });

        //CIDADE SPINNER
        Spinner spinnerCidade = (Spinner)this.findViewById(R.id.spinnerCidade);
        ArrayAdapter arrayAdapterCidade = new ArrayAdapter(this, R.layout.spinner_item, new ArrayList(Arrays.asList(StringCidade))) {
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView)view;
                if (position == 0) { tv.setTextColor(Color.GRAY);return view; } else { tv.setTextColor(Color.BLACK);return view; }
            }
            public boolean isEnabled(int position) { return position != 0; }
        };
        arrayAdapterEstado.setDropDownViewResource(R.layout.spinner_item);
        spinnerCidade.setAdapter(arrayAdapterCidade);
        spinnerCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                cidade = (String)parent.getItemAtPosition(position);
            }
            public void onNothingSelected(AdapterView var1) { }
        });

    }

}
