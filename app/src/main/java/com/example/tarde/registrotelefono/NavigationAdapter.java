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
 * Created by tarde on 20/04/15.
 */
public class NavigationAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Item_object> arrayList;


    public NavigationAdapter(Activity activity, ArrayList<Item_object> arrayList) {
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

    public static class fila{
        TextView titulo_item;
        ImageView icono;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        fila view;

        Item_object item = arrayList.get(position);
        LayoutInflater inflater= activity.getLayoutInflater();

        if (convertView == null){

            view = new fila();
            convertView = inflater.inflate(R.layout.drawer_item,null);

            convertView.setTag(view);

        }else{
            view = (fila) convertView.getTag();

        }

        view.titulo_item = (TextView) convertView.findViewById(R.id.titulo);
        view.icono = (ImageView) convertView.findViewById(R.id.icono);

        view.titulo_item.setText(item.getTitulo());
        view.icono.setImageResource(item.getIcono());

        return convertView;
    }
}






