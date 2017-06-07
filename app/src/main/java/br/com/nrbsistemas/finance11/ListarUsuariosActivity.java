package br.com.nrbsistemas.finance11;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.nrbsistemas.finance11.adapters.UsusarioAdapter;
import br.com.nrbsistemas.finance11.dao.UsuarioDao;
import br.com.nrbsistemas.finance11.entidades.Usuario;
import br.com.nrbsistemas.finance11.util.Constantes;

public class ListarUsuariosActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener,DialogInterface.OnClickListener{
    private ListView lista;
    private List<Usuario> usuarioList;
    private UsusarioAdapter adapter;
    private UsuarioDao usuarioDao;
    private Usuario usuario;

    private AlertDialog alertDialog, alertConfirmacao;
    private int posicaoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuarios);


        usuarioDao = new UsuarioDao(this);
        usuario = new Usuario();

        //lista recebe os usuario do banco
        usuarioList = usuarioDao.listar();
        //adapter carrega a lista populada
        adapter = new UsusarioAdapter(this, usuarioList);

        lista = (ListView) findViewById(R.id.lista_usuarios);
        lista.setAdapter(adapter);

        alertDialog = Constantes.criarAlerta(this);
        alertConfirmacao = Constantes.criarDialogo(this);

        lista.setOnItemClickListener(this);

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        //atenção ao cast
        int idPosicao = (int) usuarioList.get(posicaoItem).getId();

        switch (which) {
            //Editar
            case 0:
                Intent intent = new Intent(this, CadUsuActivity.class);
                intent.putExtra("id_usuario", idPosicao);//passando parametros para a act cadastro de usuarios
                startActivity(intent);
                break;
            //Excluir da lista
            case 1:
                alertConfirmacao.show();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                usuarioList.remove(posicaoItem);//remove da lista
                //TODO corrigir bug da exclusao
                usuarioDao.removerPorId(idPosicao);//remove do banco
                lista.invalidateViews();//atualiza a lista
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                alertConfirmacao.dismiss();
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        posicaoItem = position;
        alertDialog.show();
    }

}
