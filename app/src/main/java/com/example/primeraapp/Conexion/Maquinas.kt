package com.example.primeraapp.Conexion

class Maquinas {

    private var matricula: String? = null
    private var denominacion: String? = null
    private var centro = 0
    private var almacen: String? = null
    private var agente = 0
    private var nombre: String? = null

    fun getMatricula(): String? {
        return matricula
    }

    fun setMatricula(matricula: String?) {
        this.matricula = matricula
    }

    fun getDenominacion(): String? {
        return denominacion
    }

    fun setDenominacion(denominacion: String?) {
        this.denominacion = denominacion
    }

    fun getCentro(): Int {
        return centro
    }

    fun setCentro(centro: Int) {
        this.centro = centro
    }

    fun getAlmacen(): String? {
        return almacen
    }

    fun setAlmacen(almacen: String?) {
        this.almacen = almacen
    }

    fun getAgente(): Int {
        return agente
    }

    fun setAgente(agente: Int) {
        this.agente = agente
    }

    fun getNombre(): String? {
        return nombre
    }

    fun setNombre(nombre: String?) {
        this.nombre = nombre
    }


}
