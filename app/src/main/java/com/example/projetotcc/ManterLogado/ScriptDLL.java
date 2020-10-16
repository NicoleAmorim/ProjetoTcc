package com.example.projetotcc.ManterLogado;

public class ScriptDLL {
    public static String getCreateTableCliente(){

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS tbl_usuario (");
        sql.append("id INTEGER PRIMARY KEY,");
        sql.append("cod_usuario INT,");
        sql.append("imagem_usuario VARCHAR,");
        sql.append("nome_usuario VARCHAR(100) ," );
        sql.append("email_usuario INT,");
        sql.append("userName_usuario VARCHAR(100),");
        sql.append("senha_usuario VARCHAR(100),");
        sql.append("telefone_usuario VARCHAR(50),");
        sql.append("cpf_usuario VARCHAR(50),");
        sql.append("idade_usuario INT );");

        return sql.toString();
    }
}
