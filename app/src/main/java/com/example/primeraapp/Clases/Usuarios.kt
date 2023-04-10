package com.example.primeraapp.Clases


import android.graphics.Bitmap


class Usuarios {
    var username: String
     var password: String?
     var date: String
     var pfp: Bitmap
     var grupo: Int


    constructor(user: String, pass: String?, date: String, pfp: Bitmap, grupo: Int) {
        this.username = user
        this.password = pass
        this.date =date
        this.pfp = pfp
        this.grupo = grupo
    }

    data class Usuario(val nombreUsuario: String, val fechaNacimiento: String, val foto: ByteArray)
}