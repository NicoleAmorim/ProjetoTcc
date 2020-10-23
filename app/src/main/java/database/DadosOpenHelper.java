package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DadosOpenHelper extends SQLiteOpenHelper {

    public DadosOpenHelper(@Nullable Context context)
    {
        super(context, "tbl_usuario", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){db.execSQL(ScriptDLL.getCreateTableCliente());}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
