package com.example.tarde.registrotelefono;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaFragment extends Fragment {



    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    private String modo;


    Uri uri;
    ContentResolver cr;
    Cursor cursor;

    private ListView listaCositas;


    private OnFragmentInteractionListener mListener;


    public static ListaFragment newInstance(String modo) {
        ListaFragment fragment = new ListaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, modo);
        fragment.setArguments(args);
        return fragment;
    }

    public ListaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            modo = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        listaCositas = (ListView) getActivity().findViewById(R.id.listado);
        mostrarMensajes();
        super.onViewCreated(view, savedInstanceState);
    }



    private void mostrarMensajes() {

        String[] projectionSms = {Telephony.Sms._ID,Telephony.Sms.ADDRESS,Telephony.Sms.BODY,Telephony.Sms.DATE,Telephony.Sms.TYPE};
        String[] projectionCall = {CallLog.Calls._ID,CallLog.Calls.CACHED_NAME,CallLog.Calls.DATE,CallLog.Calls.TYPE};
        String[] projection=null;

        switch (modo){
            case "mensajes":
                uri= Telephony.Sms.CONTENT_URI;
                projection= projectionSms;
                break;
            case "llamadas":
                uri= CallLog.Calls.CONTENT_URI;
                projection= projectionCall;
                break;
            default:
                return;
        }


        cr = getActivity().getContentResolver();


        cursor = cr.query(uri,projection,null,null,null);
//        ArrayList<EntradaRegistro> resultado = cursorToArray(cursor, modo);

        ListaRegistroAdapter adaptador = new ListaRegistroAdapter(getActivity(),cursorToArray(cursor, modo));

        //ArrayAdapter<EntradaRegistro> adapter = new ArrayAdapter<EntradaRegistro>(getActivity(),android.R.layout.simple_list_item_1,resultado);

        listaCositas.setAdapter(adaptador);

        listaCositas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private ArrayList<EntradaRegistro> cursorToArray(Cursor cursor,String tipo) {

        String sDate;
        String address;
        int addressIdx=0;
        int dateIdx=0;
        int bodyIdx=0;
        int typeIdx=0;

        Long idate;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        EntradaRegistro entrada;

        ArrayList<EntradaRegistro> resultado = new ArrayList<EntradaRegistro>();


        switch (tipo){
            case "mensajes":
                //String[] projectionSms = {Telephony.Sms._ID,Telephony.Sms.ADDRESS,Telephony.Sms.BODY,Telephony.Sms.DATE,Telephony.Sms.TYPE};
                addressIdx = cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS);
                dateIdx = cursor.getColumnIndexOrThrow(Telephony.Sms.DATE);
                bodyIdx = cursor.getColumnIndexOrThrow(Telephony.Sms.BODY);
                typeIdx = cursor.getColumnIndexOrThrow(Telephony.Sms.TYPE);
                break;
            case "llamadas":
                //String[] projectionCall = {CallLog.Calls._ID,CallLog.Calls.CACHED_NAME,CallLog.Calls.DATE,CallLog.Calls.TYPE};
                addressIdx = cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME);
                dateIdx = cursor.getColumnIndexOrThrow(CallLog.Calls.DATE);
                typeIdx = cursor.getColumnIndexOrThrow(CallLog.Calls.TYPE);
                break;

        }
        while (cursor.moveToNext()){
            entrada = new EntradaRegistro();
            entrada.setInterlocutor(cursor.getString(addressIdx));
            idate = Long.valueOf(cursor.getString(dateIdx));
            sDate = sdf.format(idate);
            entrada.setFecha(sDate);

            int tipomsg = Integer.valueOf(cursor.getString(typeIdx));

            if (tipo=="mensajes"){
                entrada.setMensaje(cursor.getString(bodyIdx));
                switch (tipomsg){
                    case Telephony.Sms.MESSAGE_TYPE_INBOX:
                        // entrante
                        entrada.setIcono(android.R.drawable.sym_call_incoming);
                        break;
                    case Telephony.Sms.MESSAGE_TYPE_OUTBOX:
                    case Telephony.Sms.MESSAGE_TYPE_SENT:
                        // Saliente
                        entrada.setIcono(android.R.drawable.sym_call_outgoing);
                        break;
                    default:
                        //icono por defecto
                }


            }else{

                switch (tipomsg){
                    case CallLog.Calls.INCOMING_TYPE:
                        // entrante
                        entrada.setIcono(android.R.drawable.sym_call_incoming);
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        // Saliente
                        entrada.setIcono(android.R.drawable.sym_call_outgoing);

                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        entrada.setIcono(android.R.drawable.sym_call_missed);
                        // perdida
                        break;
                    default:
                        //icono por defecto
                }


            }

            resultado.add(entrada);
        }


        return resultado;
    }











    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
