package br.com.nrbsistemas.finance11.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.com.nrbsistemas.finance11.util.Constantes;

/**
 * Created by Everton on 28/05/2017.
 */

public class HelperSqlite extends SQLiteOpenHelper {

    private static final String BANCO = "db";
    private static final int VERSAO = 1;

    public HelperSqlite(Context context) {
        super(context, BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tb_usu = "create table usuario(_id integer primary key , nome text not null, login text not null" +
                ", senha text not null ,email text not null);";
        String tb_despesas = "create table despesas (_id integer primay key , categoria text not null, " +
                "descrisao text not null, valor double not null,imposto double, data text);";
        db.execSQL(tb_usu);
        db.execSQL(tb_despesas);
        Log.i(Constantes.TAG, "Tabelas criadas com sucesso ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("drop table usuario");
        db.execSQL("drop table despesas");
        onCreate(db);
        Log.i(Constantes.TAG, "TB atualizadas");
    }

/*
    public long merge(Usuario usuario) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put("id",usuario.getId());
        values.put("nome", usuario.getNome());
        values.put("login", usuario.getLogin());
        values.put("senha", usuario.getSenha());
        values.put("email", usuario.getEmail());
        if (usuario.getId() == null || usuario.getId() == 0) {
            return database.insert(Constantes.TB_USU, null, values);
        }
        return database.update(Constantes.TB_USU, values, " id = ?", new String[]{usuario.getId().toString()});
    }*/
}
