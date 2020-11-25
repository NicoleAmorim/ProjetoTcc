package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DadosOpenHelperDestinatario extends SQLiteOpenHelper {

    public DadosOpenHelperDestinatario(@Nullable Context context)
    { super(context, "tbl_message", null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db){db.execSQL(ScriptDLL.getDrop());}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
