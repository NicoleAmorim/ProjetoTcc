package database;

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

    public static String getCreateTableUsuariosPedidos(){

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS tbl_usuario_pedido (");
        sql.append("id INTEGER PRIMARY KEY AUTOINCREMENT,");
        sql.append("cod_usuario INT UNIQUE,");
        sql.append("nome_usuario VARCHAR(100));");

        return sql.toString();
    }


    public static String getCreateTableMensagem(){

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS tbl_mensagem (");
        sql.append("id_mensage integer primary key autoincrement not null,");
        sql.append("texto VARCHAR(40) NOT NULL,");
        sql.append("hora long NOT NULL,");
        sql.append("remetenteID INT NOT NULL," );
        sql.append("destinatarioID INT NOT NULL);");

        return sql.toString();
    }
}
