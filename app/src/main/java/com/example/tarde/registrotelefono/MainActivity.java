package com.example.tarde.registrotelefono;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity implements ListaFragment.OnFragmentInteractionListener{


    private String[] titulos;
    private TypedArray iconos;
    private DrawerLayout NavDrawerLayout;
    private ActionBarDrawerToggle toggle;

    private ListView NavList;
    private NavigationAdapter adapter;
    private ArrayList<Item_object> NavItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavList = (ListView) findViewById(R.id.lista_categorias);
        titulos = getResources().getStringArray(R.array.nav_options);
        iconos = getResources().obtainTypedArray(R.array.nav_icons);

        NavItems = new ArrayList<Item_object>();
        for(int i=0;i<titulos.length;i++) NavItems.add(new Item_object(titulos[i],iconos.getResourceId(i,-1)));


        // Para ponerle el header
        //View header = getLayoutInflater().inflate(R.layout.header,null);
        //NavList.addHeaderView(header);

        adapter = new NavigationAdapter(this, NavItems);

        NavList.setAdapter(adapter);

        NavList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    getFragmentManager().beginTransaction().replace(R.id.content_frame, ListaFragment.newInstance("mensajes"))
                            .commit();


                } else {
                    getFragmentManager().beginTransaction().replace(R.id.content_frame, ListaFragment.newInstance("llamadas"))
                            .commit();
                }
                Toast.makeText(MainActivity.this,
                        "Hemos pulsado la opción: " + position,
                        Toast.LENGTH_SHORT).show();

                NavDrawerLayout.closeDrawers();

            }
        });

        NavDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        toggle = new ActionBarDrawerToggle
                (this,NavDrawerLayout,R.string.app_name,R.string.app_name) {

            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("Tipo Mensajes");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }
        };


        //NavDrawerLayout.
        //toggle.setDrawerIndicatorEnabled(true);


        NavDrawerLayout.setDrawerListener(toggle);

        NavDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
        NavDrawerLayout.openDrawer(Gravity.START);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
