package br.com.nrbsistemas.finance11;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.nrbsistemas.finance11.dao.UsuarioDao;
import br.com.nrbsistemas.finance11.entidades.Usuario;
import br.com.nrbsistemas.finance11.util.Constantes;

public class CadastroUsuariosAct extends AppCompatActivity {

    private Usuario usuario;
    private UsuarioDao usuarioDao;

    private EditText edtNome, edtLogin, edtSenha1, edtSenha2, edtEmail;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_usu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Suporte botao home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        usuario = new Usuario();
        usuarioDao = new UsuarioDao(this);

        edtNome = (EditText) findViewById(R.id.edt_cad_nome);
        edtLogin = (EditText) findViewById(R.id.edt_cad_login);
        edtSenha1 = (EditText) findViewById(R.id.edt_cad_senha1);
        edtSenha2 = (EditText) findViewById(R.id.edt_cad_senha2);
        edtEmail = (EditText) findViewById(R.id.edt_cad_email);
        btnSalvar = (Button) findViewById(R.id.btn_cad_salvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validarCampos() == true) {
                    if (validarSenhas() == true) {
                        try {
                            carregarUsuario();
                            onRestart();
                            Constantes._toastCurto(CadastroUsuariosAct.this, "Usuario salvo");
                        } catch (Exception e) {
                            Constantes._toastCurto(CadastroUsuariosAct.this, "Erro " + e);
                        }
                    }
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!edtNome.getText().toString().equals("") || !edtLogin.getText().toString().equals("") ||
                        !edtSenha1.getText().toString().equals("") || !edtSenha2.getText().toString().equals("")) {
                    carregarUsuario();
                    onRestart();
                    Snackbar.make(view, "Usuário salvo com sucesso", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                }

                Snackbar.make(view, "Preencha os campos", Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(R.color.colorVermelho))
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * Conpara se as senhas são iguais
     *
     * @return true para validação
     */
    public boolean validarSenhas() {
        String senha1 = edtSenha1.getText().toString();
        String senha2 = edtSenha2.getText().toString();
        if (senha1.equals(senha2)) {
            return true;
        }
        validar();
        return false;
    }

    /**
     * Valida os campos com alerta de erro
     */
    public void validar() {
        edtSenha1.setText("");
        edtSenha2.setText("");
        edtSenha1.setError(getString(R.string.str_campo_ob));
    }

    /**
     * Carrega uma instancia de ususario
     */
    public void carregarUsuario() {
        usuario.setNome(edtNome.getText().toString());
        usuario.setLogin(edtLogin.getText().toString());
        usuario.setSenha(edtSenha1.getText().toString());
        usuario.setEmail(edtEmail.getText().toString());
        usuarioDao.salvar(usuario);
    }

    /**
     * Verifica os campos estão OK
     *
     * @return true para persistir
     */
    public boolean validarCampos() {
        if (edtNome.getText().toString().length() == 0) {
            edtNome.setError(getString(R.string.str_campo_ob));
            return false;
        } else if (edtSenha1.getText().toString().length() == 0) {
            edtSenha1.setError(getString(R.string.str_campo_ob));
            return false;
        } else if (edtSenha2.getText().toString().length() == 0) {
            edtSenha2.setError(getString(R.string.str_campo_ob));
            return false;
        } else if (edtSenha1.length() <= 5) {
            edtSenha1.setError((getString(R.string.str_senha_minima)));
            edtSenha1.setText("");
            edtSenha2.setText("");
            return false;
        } else if (edtEmail.getText().toString().length() == 0) {
            edtEmail.setError(getString(R.string.str_senha_minima));
            edtEmail.setText("");
        }
        return true;
    }

    //TODO resolver edição
    public void editarUsuario() {
        usuario.setNome(edtNome.getText().toString());
        usuario.setLogin(edtLogin.getText().toString());
        usuario.setSenha(edtSenha1.getText().toString());
        usuario.setEmail(edtEmail.getText().toString());
        usuarioDao.editar(usuario);
    }

    /**
     *
     * @param item
     * @return ação da seleção
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Limpa os edt
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        edtNome.setText("");
        edtEmail.setText("");
        edtSenha1.setText("");
        edtSenha2.setText("");
        edtLogin.setText("");
        edtEmail.setText("");
    }
}
