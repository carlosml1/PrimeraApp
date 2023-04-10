package clases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class miOpenHelper(contexto:Context) : SQLiteOpenHelper(contexto,"Usuarios_BD",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("create table usuarios(nombreUsuario varchar(150),contraseñaUsuario varchar(150), fechaRegistro varchar(150),fotoPerfil blob,grupo integer(10),fOREIGN KEY(grupo) REFERENCES cargos(id));")

        db!!.execSQL("create table cargos(Id integer primary key autoincrement,nombreCargo varchar(150));")

        db!!.execSQL("insert into cargos (nombreCargo) values ('Admin');")
        db!!.execSQL("insert into cargos (nombreCargo) values ('Usuario');")
        db!!.execSQL("insert into cargos (nombreCargo) values ('Practicas');")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}