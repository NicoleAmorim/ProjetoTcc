package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DadosOpenHelperMessage extends SQLiteOpenHelper {

    public DadosOpenHelperMessage(@Nullable Context context)
    { super(context, "tbl_mensagem", null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db){db.execSQL(ScriptDLL.getCreateTableMensagem());}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
