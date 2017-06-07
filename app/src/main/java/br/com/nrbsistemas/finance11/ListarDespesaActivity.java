package br.com.nrbsistemas.finance11;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.nrbsistemas.finance11.adapters.DespesasAdapter;
import br.com.nrbsistemas.finance11.dao.DespesaDao;
import br.com.nrbsistemas.finance11.entidades.Despesa;

public class ListarDespesaActivity extends AppCompatActivity {

    private Despesa despesa;
    private DespesaDao despesaDao;
    private DespesasAdapter adapter;
    private List<Despesa> despesasList;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_despesa);

        despesa = new Despesa();
        despesaDao = new DespesaDao(this);
        despesasList = despesaDao.listar();
        adapter = new DespesasAdapter(this,despesasList);
        listView = (ListView)findViewById(R.id.lista_de_despesas);
        listView.setAdapter(adapter);
    }


}
