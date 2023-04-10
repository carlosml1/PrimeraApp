package com.example.primeraapp.Conexion

class Persona {

    private var denominacionSocial: String? = null
    private var telefono: String? = null
    private var poblacion: String? = null
    private var provincia: String? = null
    private var direccion: String? = null
    private var dniCif: String? = null
    private var email: String? = null
    private var telefono2: String? = null
    private var centro = 0
    private var zona: String? = null
    private var codigoPostal: String? = null
    private var clasificacion: String? = null
    private var urbanizacion: String? = null

    fun getDenominacionSocial(): String? {
        return denominacionSocial
    }

    fun setDenominacionSocial(denominacionSocial: String?) {
        this.denominacionSocial = denominacionSocial
    }

    fun getTelefono(): String? {
        return telefono
    }

    fun setTelefono(telefono: String?) {
        this.telefono = telefono
    }

    fun getPoblacion(): String? {
        return poblacion
    }

    fun setPoblacion(poblacion: String?) {
        this.poblacion = poblacion
    }

    fun getProvincia(): String? {
        return provincia
    }

    fun setProvincia(provincia: String?) {
        this.provincia = provincia
    }

    fun getDireccion(): String? {
        return direccion
    }

    fun setDireccion(direccion: String?) {
        this.direccion = direccion
    }

    fun getDniCif(): String? {
        return dniCif
    }

    fun setDniCif(dniCif: String?) {
        this.dniCif = dniCif
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    fun getTelefono2(): String? {
        return telefono2
    }

    fun setTelefono2(telefono2: String?) {
        this.telefono2 = telefono2
    }

    fun getCentro(): Int {
        return centro
    }

    fun setCentro(centro: Int) {
        this.centro = centro
    }

    fun getZona(): String? {
        return zona
    }

    fun setZona(zona: String?) {
        this.zona = zona
    }

    fun getCodigoPostal(): String? {
        return codigoPostal
    }

    fun setCodigoPostal(codigoPostal: String?) {
        this.codigoPostal = codigoPostal
    }

    fun getClasificacion(): String? {
        return clasificacion
    }

    fun setClasificacion(clasificacion: String?) {
        this.clasificacion = clasificacion
    }

    fun getUrbanizacion(): String? {
        return urbanizacion
    }

    fun setUrbanizacion(urbanizacion: String?) {
        this.urbanizacion = urbanizacion
    }

}