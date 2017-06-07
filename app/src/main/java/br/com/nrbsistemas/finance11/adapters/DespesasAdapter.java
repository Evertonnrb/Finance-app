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
import br.com.nrbsistemas.finance11.entidades.Despesa;

/**
 * Created by Everton on 02/06/2017.
 */

public class DespesasAdapter extends BaseAdapter {

    private Context context;
    private List<Despesa> listaGastos;

    public DespesasAdapter(Context context, List<Despesa> listaGastos) {
        this.context = context;
        this.listaGastos = listaGastos;
    }

    @Override
    public int getCount() {
        return listaGastos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaGastos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Despesa despesa = listaGastos.get(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_despesas, null);

            TextView txt_despesa_id = (TextView) view.findViewById(R.id.txt_id_despesas);
            txt_despesa_id.setText(String.valueOf(despesa.getId()));

            TextView txt_despesa_categoria = (TextView) view.findViewById(R.id.txt_categoria_despesas);
            txt_despesa_categoria.setText(despesa.getCategoria());

            TextView txt_despesa_descrisao = (TextView) view.findViewById(R.id.txt_descrisao_despesas);
            txt_despesa_descrisao.setText(despesa.getDescrisao());

            TextView txt_despesa_valor = (TextView) view.findViewById(R.id.txt_valor_despesas);
            txt_despesa_valor.setText(String.valueOf(despesa.getValor()));

            TextView txt_despesa_imposto = (TextView) view.findViewById(R.id.txt_imposto_despesas);
            txt_despesa_imposto.setText(String.valueOf(despesa.getImposto()));

           TextView txt_despesa_data = (TextView) view.findViewById(R.id.txt_data_despesas);
            txt_despesa_data.setText(despesa.getData());

            return view;
        }
        return null;
    }
}
