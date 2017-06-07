package br.com.nrbsistemas.finance11.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import br.com.nrbsistemas.finance11.R;

/**
 * Created by Everton on 04/06/2017.
 */

public class Constantes {
    //tabelas
    public static final String TB_USU = "usuario";
    public static final String TB_DESPESA = "despesas";
    //log
    public static final String TAG = "_info";

    public static void _toastCurto(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static void _alertaSimples(String titulo, String msg, Activity activity) {
        AlertDialog.Builder b = new AlertDialog.Builder(activity);
        b.setTitle(titulo);
        b.setMessage(msg);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        b.create();
        b.show();
    }

    public static void sneak(View view, String msg, final String acao) {
        FloatingActionButton fab = (FloatingActionButton) view;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "", Snackbar.LENGTH_LONG)
                        .setAction(acao, null).show();
            }
        });
    }

    //alerta de ok atenção
    public static void msgOk(Activity activity, String titulo,String msg,int icon){
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle(titulo);
        alerta.setMessage(msg);
        alerta.setNeutralButton("Ok",null);
        alerta.setIcon(R.drawable.icone);
        alerta.show();
    }
    //alerta de descisao
    public static void msgConfirm(Activity activity, String titulo, String msg, int icon, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(titulo);
        builder.setMessage(msg);
        builder.setPositiveButton(activity.getString(R.string.alerta_sim),listener);
        builder.setNegativeButton(activity.getString(R.string.alert_nao),null);
        builder.setIcon(R.drawable.icone);
        builder.show();
    }
    //combo box de opções
    public static AlertDialog criarAlerta(Activity activity){
        final CharSequence[] itens = {"Editar","Excluir"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.alerta_opcoes));
        builder.setItems(itens,(DialogInterface.OnClickListener)activity);
        return builder.create();
    }

    public static AlertDialog criarDialogo (Activity activity){
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setMessage("Deseja realizar a operação");
        alerta.setPositiveButton(activity.getString(R.string.alerta_sim), (DialogInterface.OnClickListener)activity);
        alerta.setNegativeButton(activity.getString(R.string.alert_nao), (DialogInterface.OnClickListener)activity);
        return alerta.create();
    }
}
