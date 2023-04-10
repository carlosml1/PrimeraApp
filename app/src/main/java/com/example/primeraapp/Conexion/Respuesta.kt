package com.example.primeraapp.Conexion

class Respuesta {

    var id = 0
    var mensaje: String? = null

    fun obtenerId(): Int {
        return id
    }

    fun ponerId(id: Int) {
        this.id = id
    }

    fun obtenerMensaje(): String? {
        return mensaje
    }

    fun ponerMensaje(mensaje: String?) {
        this.mensaje = mensaje
    }

}