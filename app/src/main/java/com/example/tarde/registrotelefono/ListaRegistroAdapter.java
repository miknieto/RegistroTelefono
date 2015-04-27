package com.example.tarde.registrotelefono;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tarde on 24/04/15.
 */
public class ListaRegistroAdapter extends BaseAdapter {

    private ArrayList<EntradaRegistro> arrayList;
    private Activity activity;

    public static class FilaView{
        TextView interlocutor_item;
        TextView fecha_item;
        ImageView icono;
    }

    public ListaRegistroAdapter(Activity activity, ArrayList<EntradaRegistro> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        FilaView view;

        EntradaRegistro item = arrayList.get(position);
        LayoutInflater inflater= activity.getLayoutInflater();

        if (convertView == null){

            view = new FilaView();
            convertView = inflater.inflate(R.layout.lista_item,null);

            convertView.setTag(view);

        }else{
            view = (FilaView) convertView.getTag();

        }

        view.interlocutor_item = (TextView) convertView.findViewById(R.id.texto_titulo);
        view.fecha_item = (TextView) convertView.findViewById(R.id.texto_fecha);
        view.icono = (ImageView) convertView.findViewById(R.id.image_icon);

        view.interlocutor_item.setText(item.getInterlocutor());
        view.fecha_item.setText(item.getFecha());
        view.icono.setImageResource(item.getIcono());

        return convertView;


    }
}
