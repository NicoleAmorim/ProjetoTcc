package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DadosOpenHelperDestinatario extends SQLiteOpenHelper {

    public DadosOpenHelperDestinatario(@Nullable Context context)
    { super(context, "tbl_usuario_pedido", null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db){db.execSQL(ScriptDLL.getCreateTableUsuariosPedidos());}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
