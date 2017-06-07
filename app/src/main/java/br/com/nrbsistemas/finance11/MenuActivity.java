package br.com.nrbsistemas.finance11;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import br.com.nrbsistemas.finance11.dao.DespesaDao;
import br.com.nrbsistemas.finance11.util.Constantes;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView fotoPerfil;
    private TextView txtBemvindo;
    private DespesaDao despesaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constantes.PERMISSION_REQUEST);
        }

        despesaDao = new DespesaDao(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_configuracao:
                startActivity(new Intent(this, ConfiguracoesAct.class));
                break;
            case R.id.action_logout://removendo preferencia via SharedPreferences
                Constantes.msgConfirm(this, "Atenção", "Deseja efetuar logout?", R.drawable.icone,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferences = getSharedPreferences("LoginAct", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.commit();
                                finish();
                            }
                        });
                break;
            case R.id.action_sair:
                Constantes.msgConfirm(this, "Sair", "Deseja realmente sair",
                        R.drawable.icone, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_lista_gastos) {

            startActivity(new Intent(this, ListarDespesaAct.class));

        } else if (id == R.id.nav_editar) {

            Constantes._toastCurto(this, "editar");

        } else if (id == R.id.nav_imposto) {

            Constantes._toastCurto(this, "Calcular imposto");

        } else if (id == R.id.nav_apagar_gastos) {

            Constantes.msgConfirm(this, getString(R.string.alerta_atencao),
                    getString(R.string.alerta_eclusao),
                    R.drawable.alerta_exclusao,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            despesaDao.apagarDespesas();
                            Constantes._toastCurto(MenuActivity.this, getString(R.string.alerta_registro_apagado));
                        }
                    });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void _selecionarFoto(View view) {
        // Toast.makeText(this, "work", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
        String aquivo = Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".jpg";
        File file = new File(aquivo);
        Uri outPutUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);

    }

    public void tirarFoto(View view) {
        //TODO carregar imagem
    }

    /**
     * Retorna a permissaod e acesso a galeria
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constantes.PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permitido
            } else {
                //negado
            }
        }
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        return;
    }

    public void _personalizarIcone(final View view) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Selecione");
        alerta.setMessage("Selecione uma opção");
                alerta.setPositiveButton("Galeria", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Pega imagem da galeria
                Intent fotoGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(fotoGaleria, Constantes.GALERIA_IMAGENS);
            }
        });
        alerta.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent tirarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(tirarFoto.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(tirarFoto,Constantes.TIRAR_FOTO);
                }
            }
        });
        alerta.create();
        alerta.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constantes.GALERIA_IMAGENS) {
            Uri imageSelecionada = data.getData();
            String[] foto = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(imageSelecionada, foto, null, null, null);
            c.moveToFirst();
            int indexColuna = c.getColumnIndex(foto[0]);
            String carregaFoto = c.getString(indexColuna);
            c.close();
            Bitmap fotoGaleria = (BitmapFactory.decodeFile(carregaFoto));
            fotoPerfil.setImageBitmap(fotoGaleria);
        }
        if (requestCode == Constantes.TIRAR_FOTO && requestCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap)extras.get("data");
            fotoPerfil.setImageBitmap(bitmap);
        }
    }


    public void _selecionarOpcao(View view) {
        switch (view.getId()) {
            case R.id.txt_novo_gasto:
                startActivity(new Intent(this, CadastroDespesasAct.class));
                break;
            case R.id.txt_gasto_realizado:
                startActivity(new Intent(this, ListarDespesaAct.class));
                break;
            case R.id.txt_gasto_orientacao:
                startActivity(new Intent(this, SobreActivity.class));
                break;
            case R.id.txt_gasto_configuracoes:
                startActivity(new Intent(this, ConfiguracoesAct.class));
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_main, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        despesaDao.fecharConexao();
    }
}
