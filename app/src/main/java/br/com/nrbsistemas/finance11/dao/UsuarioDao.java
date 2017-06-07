package br.com.nrbsistemas.finance11.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.nrbsistemas.finance11.entidades.Usuario;
import br.com.nrbsistemas.finance11.helper.HelperSqlite;
import br.com.nrbsistemas.finance11.util.Constantes;

/**
 * Created by Everton on 28/05/2017.
 */

public class UsuarioDao {

    private SQLiteDatabase database;

    public UsuarioDao(Context context) {
        HelperSqlite helper = new HelperSqlite(context);
        database = helper.getWritableDatabase();
    }

    public void salvar(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("login", usuario.getLogin());
        values.put("senha", usuario.getSenha());
        values.put("email", usuario.getEmail());
        database.insert(Constantes.TB_USU, null, values);
    }

    public void editar(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("login", usuario.getLogin());
        values.put("senha", usuario.getSenha());
        values.put("email", usuario.getEmail());
        database.update(Constantes.TB_USU, values, " _id = ?", new String[]{"" + usuario.getId()});
    }

    public List<Usuario> listar() {
        String[] columns = {" _id ", "nome", "login", "senha", "email"};
        List<Usuario> listaUsu = new ArrayList<>();
        Cursor cursor = database.query(Constantes.TB_USU, columns, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Usuario u = new Usuario();
                u.setId(cursor.getInt(0));
                u.setNome(cursor.getString(1));
                u.setLogin(cursor.getString(2));
                u.setSenha(cursor.getString(3));
                u.setEmail(cursor.getString(4));
                listaUsu.add(u);
            } while (cursor.moveToNext());
        }
        return listaUsu;
    }

    public void deletar(Usuario usuario) {
        database.delete(Constantes.TB_USU, " _id = ? " + usuario.getId(), null);
    }

    //TODO conrrigir null pointer
    public void removerPorId(int id) {
        database.delete(Constantes.TB_USU, " _id = ? ", new String[]{Integer.toString(id)});
    }

    public Usuario logarUsu(String login, String senha) {
        //TODO logar
        Usuario usuario = new Usuario();
        String[] logar = new String[]{usuario.getLogin(), usuario.getSenha()};
        Cursor cursor = database.query(
                "usuario",
                null,
                "login = ? and senha = ?",
                new String[]{usuario.getLogin().toString(), usuario.getSenha().toString()},
                null, null, null, null
        );
        if (cursor.moveToFirst()) {
            return usuario;
        }
        return null;
    }

    public boolean logar(String login, String senha) {
        Cursor cursor = database.query(
                "usuario",
                null,
                "login = ? and senha = ?", new String[]{login, senha}, null, null, null

        );

        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    public void fechar() {
        database.close();
    }
}
