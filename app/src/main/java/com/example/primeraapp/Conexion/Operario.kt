package com.example.primeraapp.Conexion

class Operario {
    private var idoperario = 0
    private var persona: Persona? = null
    private var externo = 0
    private var idGremio = 0
    private var idEmpresa: String? = null
    private var maquina: Maquinas? = null
    private var encargado = 0
    private var seccion: String? = null
    private var supervisor = 0
    private var seleccionado = false
    private var ittTime = 0

    fun getSeccion(): String? {
        return seccion
    }

    fun setSeccion(seccion: String?) {
        this.seccion = seccion
    }

    fun getSupervisor(): Int {
        return supervisor
    }

    fun setSupervisor(supervisor: Int) {
        this.supervisor = supervisor
    }

    fun getEncargado(): Int {
        return encargado
    }

    fun setEncargado(encargado: Int) {
        this.encargado = encargado
    }


    fun getIdGremio(): Int {
        return idGremio
    }

    fun setIdGremio(idGremio: Int) {
        this.idGremio = idGremio
    }


    fun getIdoperario(): Int {
        return idoperario
    }

    fun setIdoperario(idoperario: Int) {
        this.idoperario = idoperario
    }

    fun getPersona(): Persona? {
        return persona
    }

    fun setPersona(persona: Persona?) {
        this.persona = persona
    }

    fun getExterno(): Int {
        return externo
    }

    fun setExterno(externo: Int) {
        this.externo = externo
    }

    fun getIdEmpresa(): String? {
        return idEmpresa
    }

    fun setIdEmpresa(idEmpresa: String?) {
        this.idEmpresa = idEmpresa
    }

    fun getMaquina(): Maquinas? {
        return maquina
    }

    fun setMaquina(maquina: Maquinas?) {
        this.maquina = maquina
    }

    fun isSeleccionado(): Boolean {
        return seleccionado
    }

    fun setSeleccionado(seleccionado: Boolean) {
        this.seleccionado = seleccionado
    }

    fun getIttTime(): Int {
        return ittTime
    }

    fun setIttTime(ittTime: Int) {
        this.ittTime = ittTime
    }
}