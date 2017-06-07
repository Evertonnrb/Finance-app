package br.com.nrbsistemas.finance11;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.util.Calendar;

import br.com.nrbsistemas.finance11.dao.DespesaDao;
import br.com.nrbsistemas.finance11.entidades.Despesa;
import br.com.nrbsistemas.finance11.util.Constantes;

public class CadastroDespesasAct extends AppCompatActivity {

    private DespesaDao despesaDao;
    private Despesa despesa;

    DateFormat dateFormat = DateFormat.getDateInstance();
    Calendar dateTime = Calendar.getInstance();
    private int dia, mes, ano;

    private EditText edtDescrisao, edtValor, edtValorImpo;
    private Button btnData, btnAdd;
    private Spinner spnCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_despesa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampos() == true) {
                    carregaGasto();
                    try {
                        despesaDao.salvar(despesa);
                        Log.i(Constantes.TAG, "salvou");
                        onRestart();
                        Constantes._toastCurto(CadastroDespesasAct.this, "Gasto adicionado com sucesso");
                    } catch (Exception e) {
                        Constantes._toastCurto(CadastroDespesasAct.this, "Erro ao salvar \n");
                    }
                }
            }
        });
        //Suporte botao home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        despesa = new Despesa();
        despesaDao = new DespesaDao(this);

        spnCategoria = (Spinner) findViewById(R.id.spn_categoria);
        edtDescrisao = (EditText) findViewById(R.id.edt_descrisao);
        edtValor = (EditText) findViewById(R.id.edt_valor);
        edtValorImpo = (EditText) findViewById(R.id.edt_imposto);
        btnData = (Button) findViewById(R.id.btn_data);
        btnAdd = (Button) findViewById(R.id.btn_add_desp);
        //Charsquence do spinner
        ArrayAdapter<CharSequence> categoriaLista = ArrayAdapter.createFromResource(
                this, R.array.lista_desp, android.R.layout.simple_spinner_dropdown_item
        );
        spnCategoria.setAdapter(categoriaLista);

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarData(v);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos() == true) {
                    carregaGasto();
                    try {
                        despesaDao.salvar(despesa);
                        Log.i(Constantes.TAG, "salvou");
                        Constantes._toastCurto(CadastroDespesasAct.this, "Gasto adicionado com sucesso");
                        onRestart();
                    } catch (Exception e) {
                        Constantes._toastCurto(CadastroDespesasAct.this, "Erro ao salvar \n");
                    }
                } else {
                    Constantes._toastCurto(CadastroDespesasAct.this, "Erro ao salvar \n");
                }
            }
        });

    }

    /**
     * Carrega uma instancia de despesas
     */
    private void carregaGasto() {
        String categoria = spnCategoria.getSelectedItem().toString();
        String descrisao = edtDescrisao.getText().toString();
        Double valor = Double.valueOf(edtValor.getText().toString());
        Double imposto = Double.valueOf(edtValorImpo.getText().toString());
        String data = listener.toString();
        despesa.setCategoria(categoria);
        despesa.setDescrisao(descrisao);
        despesa.setValor(valor);
        despesa.setImposto(imposto);
        despesa.setData(data);
        despesaDao.salvar(despesa);

    }

    /**
     * @param id escuta o botao
     * @return retorna uma data
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        if (R.id.btn_data == id) {
            return new DatePickerDialog(this, listener, dia, mes, ano);
        }
        return null;
    }

    public void selecionarData(View view) {
        showDialog(view.getId());
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            ano = year;
            mes = monthOfYear;
            ano = dayOfMonth;
        }
    };

    /*
    Botao home
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
     * Valida so campos null e ""
     *
     * @return true persiste a despesa
     */
    private boolean validarCampos() {
        if (spnCategoria.getSelectedItem().equals("selecione")) {
            Constantes._alertaSimples("Atenção", "Selecione uma opção", this);
            return false;
        }
        if (edtDescrisao.getText().toString().equals("")) {
            edtDescrisao.setError(getString(R.string.str_campo_ob));
            return false;
        }
        if (edtValor.getText().toString().equals("")) {
            edtValor.setError(getString(R.string.str_campo_ob));
            edtValor.setError(getString(R.string.str_campo_ob));
            return false;
        }
        if (edtValorImpo.getText().toString().equals("")) {
            edtValorImpo.setError(getString(R.string.str_campo_ob));
            return false;
        }
        if (btnData.getText().toString().equals("") || btnData.getText().toString().isEmpty()) {
            Constantes._alertaSimples("Atenção", "Informe a data", this);
            return false;
        }
        return true;
    }

    /**
     * Limpas o edt ao restart app
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        spnCategoria.setSelection(0);
        edtDescrisao.setText("");
        edtValorImpo.setText("");
        edtValor.setText("");
        edtValorImpo.setText("");
        btnData.setText("");
    }
}

