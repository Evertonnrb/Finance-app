package br.com.nrbsistemas.finance11;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.nrbsistemas.finance11.dao.UsuarioDao;
import br.com.nrbsistemas.finance11.entidades.Usuario;
import br.com.nrbsistemas.finance11.util.Constantes;

public class LoginActivity extends AppCompatActivity {

    private static final String MANTER = "manter_conetado";
    private static final String PREFERENCIA = "LoginAct";
    private static final int TEMPORIZADOR = 5000;
    private int atualiza = 0;
    private TextView txttCadastrar;
    private EditText edtNome, edtSenha, edtLogin;
    private Button btnLogar;
    private CheckBox ckbConect;
    private ImageView imgage;
    private Toolbar mToolbar;

    private Usuario usuario;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = new Usuario();
        usuarioDao = new UsuarioDao(this);

        mToolbar = (Toolbar) findViewById(R.id.inc_booton);
        //mToolbar.inflateMenu(R.menu.menu_toolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent it = null;
                switch (item.getItemId()){
                    case R.id.menu_face:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://facebook.com"));
                        break;
                }
                startActivity(it);
                return true;
            }
        });
        //mToolbar.setTitle("Finance app");
        //mToolbar.setLogo(R.drawable.logo);
        //mToolbar.setSubtitle("App de controle financeiro");
        //setSupportActionBar(mToolbar);

        txttCadastrar = (TextView) findViewById(R.id.txt_cad);
        edtLogin = (EditText) findViewById(R.id.edt_login);
        edtSenha = (EditText) findViewById(R.id.edt_senha);
        btnLogar = (Button) findViewById(R.id.btn_logar);
        ckbConect = (CheckBox) findViewById(R.id.ckb_conectado);
        imgage = (ImageView) findViewById(R.id.img_click);

        imgage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                startActivity(new Intent(LoginActivity.this, ListarUsuariosActivity.class));
                return false;
            }
        });

        txttCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CadastroUsuariosAct.class));
                finish();
            }
        });
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _logar();
            }
        });

        //pega a preferencia ao criar a act
        SharedPreferences preferences = getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        //verefica a preferencia com valor default false
        boolean conectado = preferences.getBoolean(MANTER, false);
        if (conectado) {
            chamarMain();
        }
    }

    public void _logar() {
        boolean validar = true;
        String login = edtLogin.getText().toString();
        String senha = edtSenha.getText().toString();

        if (login == null || login.equals("")) {
            onRestart();
            edtLogin.setError(getString(R.string.msg_erro_null));
            edtLogin.requestFocus();
            validar = false;
        }
        if (senha == null || senha.equals("")) {
            onRestart();
            edtLogin.setError(getString(R.string.msg_erro_null));
            edtLogin.requestFocus();
            validar = false;
        }
        if (validar) {
            //logar
            if (usuarioDao.logar(login, senha)) {
                //preferenccia do check box
                if (ckbConect.isChecked()) {
                    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(MANTER, true);
                    editor.commit();
                }
                chamarMain();
            } else {
                Constantes._alertaSimples("Atenção", "Usuario e ou senha inválidos", this);
                onRestart();
                edtLogin.requestFocus();
            }
        }
    }


    public void chamarMain() {
        //Usuario usuario = new Usuario();
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        //Bundle bundle= new Bundle();
        //bundle.putString(usuario.getNome().toString(),"nome");
        //intent.putExtras(bundle);
        startActivity(intent);
        //startActivity(new Intent(this, MenuActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        edtLogin.setText("");
        edtSenha.setText("");

    }

    @Override
    protected void onDestroy() {
        usuarioDao.fechar();
        super.onDestroy();
    }
}
