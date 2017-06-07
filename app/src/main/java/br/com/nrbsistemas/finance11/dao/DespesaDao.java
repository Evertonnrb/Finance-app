package br.com.nrbsistemas.finance11.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.nrbsistemas.finance11.entidades.Despesa;
import br.com.nrbsistemas.finance11.helper.HelperSqlite;
import br.com.nrbsistemas.finance11.util.Constantes;

/**
 * Created by Everton on 28/05/2017.
 */

public class DespesaDao {

    private SQLiteDatabase database;

    public DespesaDao(Context context) {
        HelperSqlite helper = new HelperSqlite(context);
        database = helper.getWritableDatabase();
    }

    public void salvar(Despesa despesa) {
        ContentValues values = new ContentValues();
        values.put("categoria", despesa.getCategoria());
        values.put("descrisao", despesa.getDescrisao());
        values.put("imposto", despesa.getImposto());
        values.put("valor", despesa.getValor());
        values.put("data", String.valueOf(despesa.getData()));
        database.insert(Constantes.TB_DESPESA, null, values);
    }

    public void editar(Despesa despesa) {
        ContentValues values = new ContentValues();
        values.put("categoria", despesa.getCategoria());
        values.put("descrisao", despesa.getDescrisao());
        values.put("imposto", despesa.getImposto());
        values.put("valor", despesa.getValor());
        values.put("data", String.valueOf(despesa.getData()));
        database.update(Constantes.TB_DESPESA, values, " _id = ?", new String[]{"" + despesa.getId()});
    }

    public List<Despesa> listar() {
        String[] columns = {" _id ", "categoria", "descrisao", "valor", "imposto",  "data"};
        List<Despesa> despesas = new ArrayList<>();
        Cursor cursor = database.query(Constantes.TB_DESPESA, columns, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Despesa d = new Despesa();
                d.setId(cursor.getInt(0));
                d.setCategoria(cursor.getString(1));
                d.setDescrisao(cursor.getString(2));
                d.setValor(cursor.getDouble(3));
                d.setImposto(cursor.getDouble(4));
                d.setData(cursor.getString(5));
                despesas.add(d);
            } while (cursor.moveToNext());
        }
        return despesas;
    }

    public void somarImpostos(){

        database.rawQuery("select sum(imposto) from "+Constantes.TB_DESPESA,null);
    }

    public void deletar(Despesa despesa) {
        database.delete(Constantes.TB_DESPESA, " _id = ? " + despesa.getId(), null);
    }
    public void apagarDespesas(){
        try {
            database.execSQL("delete from "+Constantes.TB_DESPESA);
            Log.i(Constantes.TAG,"Registros apagados");
        }catch (Exception e){
            Log.e(Constantes.TAG,"Erro "+e);
        }
        }

    public void fecharConexao(){
        database.close();
    }
}
