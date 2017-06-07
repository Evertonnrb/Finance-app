package br.com.nrbsistemas.finance11;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.nrbsistemas.finance11.adapters.DespesasAdapter;
import br.com.nrbsistemas.finance11.adapters.DespesasBaseAdapter;
import br.com.nrbsistemas.finance11.dao.DespesaDao;
import br.com.nrbsistemas.finance11.entidades.Despesa;

public class ListarDespesaAct extends AppCompatActivity {

    private Despesa despesa;
    private DespesaDao despesaDao;
    //private DespesasAdapter adapter;
    private DespesasBaseAdapter despesasBaseAdapter;
    private List<Despesa> despesasList;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_despesa);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        despesa = new Despesa();
        despesaDao = new DespesaDao(this);
        despesasList = despesaDao.listar();
        //adapter = new DespesasAdapter(this,despesasList);
        despesasBaseAdapter = new DespesasBaseAdapter(this,despesasList);
        listView = (ListView)findViewById(R.id.lista_de_despesas);
        listView.setAdapter(despesasBaseAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
