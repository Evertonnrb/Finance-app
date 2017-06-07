package br.com.nrbsistemas.finance11.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.nrbsistemas.finance11.R;
import br.com.nrbsistemas.finance11.entidades.Despesa;

/**
 * Created by Everton on 07/06/2017.
 */

public class DespesasBaseAdapter extends BaseAdapter{

    Context context;
    private List<Despesa> despesasList;

    public DespesasBaseAdapter(Context context, List<Despesa> despesasList) {
        this.context = context;
        this.despesasList = despesasList;
    }

    @Override
    public int getCount() {
        return despesasList.size();//tamanho da lista
    }

    @Override
    public Object getItem(int position) {
        return despesasList.get(position);//posicao do objto na lista
    }

    @Override
    public long getItemId(int position) {
        return despesasList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int auxPosition = position;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.lista_despesas, null);

        TextView txt_despesa_categoria = (TextView) layout.findViewById(R.id.txt_categoria_despesas);
        txt_despesa_categoria.setText(despesasList.get(position).getCategoria());

        TextView txt_despesa_descrisao = (TextView) layout.findViewById(R.id.txt_descrisao_despesas);
        txt_despesa_descrisao.setText(despesasList.get(position).getDescrisao());

        TextView txt_despesa_valor = (TextView) layout.findViewById(R.id.txt_valor_despesas);
        txt_despesa_valor.setText(despesasList.get(position).getValor().toString());

        TextView txt_despesa_imposto = (TextView) layout.findViewById(R.id.txt_imposto_despesas);
        txt_despesa_imposto.setText(despesasList.get(position).getImposto().toString());

        TextView txt_despesa_data = (TextView) layout.findViewById(R.id.txt_data_despesas);
        txt_despesa_data.setText(despesasList.get(position).getData().toString());

        return layout;
    }
}
