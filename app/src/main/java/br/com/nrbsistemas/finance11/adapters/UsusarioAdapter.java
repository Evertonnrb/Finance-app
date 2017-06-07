package br.com.nrbsistemas.finance11.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.nrbsistemas.finance11.R;
import br.com.nrbsistemas.finance11.entidades.Usuario;


/**
 * Created by Everton on 01/06/2017.
 */

public class UsusarioAdapter extends BaseAdapter {

    private Context context;
    private List<Usuario> listaUsuarios;

    public UsusarioAdapter(Context context, List<Usuario> usuarios) {
        this.context = context;
        this.listaUsuarios = usuarios;
    }

    //retorna o tamanho da liste
    @Override
    public int getCount() {
        return listaUsuarios.size();
    }

    //retorna a posição da lista
    @Override
    public Object getItem(int position) {
        return listaUsuarios.get(position);
    }

    //retorna a posição
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Usuario usuario = listaUsuarios.get(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_usuarios, null);

            TextView txtId = (TextView) view.findViewById(R.id.txt_usu_id);
            txtId.setText((String.valueOf(usuario.getId())));

            TextView txtNome = (TextView) view.findViewById(R.id.txt_usu_nome);
            txtNome.setText(usuario.getNome());

            TextView txtLogin = (TextView) view.findViewById(R.id.txt_usu_login);
            txtLogin.setText(usuario.getLogin());

            TextView txtSenha = (TextView) view.findViewById(R.id.txt_usu_senha);
            txtSenha.setText(usuario.getSenha());

            TextView txtemail = (TextView) view.findViewById(R.id.txt_usu_email);
            txtemail.setText(usuario.getEmail());

            return view;

        }
        return null;
    }
}
