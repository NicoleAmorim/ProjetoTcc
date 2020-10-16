package com.example.projetotcc.ManterLogado;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetotcc.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ManterLogadoRepositorio {

    private SQLiteDatabase conexao;

    public ManterLogadoRepositorio(SQLiteDatabase conexao){

        this.conexao = conexao;

    }

    public void inserir(Usuario usuario){

        ContentValues contentValues = new ContentValues();

        contentValues.put("id", 1);
        contentValues.put("cod_usuario", usuario.getCod());
        contentValues.put("nome_usuario", usuario.getNome());
        contentValues.put("imagem_usuario", usuario.getImagem());
        contentValues.put("email_usuario", usuario.getEmail());
        contentValues.put("userName_usuario", usuario.getUsername());
        contentValues.put("senha_usuario", usuario.getSenha());
        contentValues.put("telefone_usuario", usuario.getTel());
        contentValues.put("cpf_usuario", usuario.getCpf());
        contentValues.put("idade_usuario", usuario.getIdade());

        conexao.insertOrThrow("tbl_usuario", null, contentValues);
    }

    public void excluir(Usuario usuario){

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(usuario.getCod());

        conexao.delete("tbl_usuario", "cod_usuario = ?", parametros);

    }

    public Usuario buscarUsuario(){

        Usuario usuario = new Usuario();

        StringBuilder sql = new StringBuilder();

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(1);

        sql.append("SELECT*");
        sql.append("FROM tbl_usuario ");
        sql.append("WHERE id = ?");

        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);

        if(resultado.getCount() > 0) {

            resultado.moveToFirst();

            usuario.setCod(Integer.parseInt(resultado.getString(resultado.getColumnIndexOrThrow("cod_usuario"))));
            usuario.setNome(resultado.getString(resultado.getColumnIndexOrThrow("nome_usuario")));
            usuario.setEmail(resultado.getString(resultado.getColumnIndexOrThrow("email_usuario")));
            usuario.setUsername(resultado.getString(resultado.getColumnIndexOrThrow("userName_usuario")));
            usuario.setImagem(resultado.getString(resultado.getColumnIndexOrThrow("imagem_usuario")));
            usuario.setSenha(resultado.getString(resultado.getColumnIndexOrThrow("senha_usuario")));
            usuario.setTel(Integer.parseInt(resultado.getString(resultado.getColumnIndexOrThrow("telefone_usuario"))));
            usuario.setCpf(resultado.getString(resultado.getColumnIndexOrThrow("cpf_usuario")));
            usuario.setIdade(Integer.parseInt(resultado.getString(resultado.getColumnIndexOrThrow("idade_usuario"))));

            return usuario;
        }
        return null;
    }
}
