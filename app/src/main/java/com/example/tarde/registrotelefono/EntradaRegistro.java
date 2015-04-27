package com.example.tarde.registrotelefono;

import android.provider.CallLog;
import android.provider.Telephony;

/**
 * Created by tarde on 24/04/15.
 */
public class EntradaRegistro {

    String _id;
    String interlocutor;
    String fecha;
    String mensaje;
    int tipo;
    int categoria;
    int icono;

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getInterlocutor() {
        return interlocutor;
    }

    public void setInterlocutor(String interlocutor) {
        this.interlocutor = interlocutor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return interlocutor + " " +fecha;
    }
}
