package com.example.primeraapp

import android.annotation.SuppressLint

import android.content.Context
import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import clases.miOpenHelper
import com.androidadvance.topsnackbar.TSnackbar
import com.google.android.material.textfield.TextInputLayout
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL


class Login : ModoInmersivo() {




    @SuppressLint("MissingInflatedId", "Range", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        val nombreUsuario: TextInputLayout = findViewById(R.id.textUsuarioLogin)
        val contraseñaUsuario: TextInputLayout = findViewById(R.id.textContraseñaLogin)
        val botonLogin: Button = findViewById(R.id.botonLogin)

        val botonIrRegistro: Button = findViewById(R.id.botonRegistro)

        val db = miOpenHelper(this).readableDatabase





        botonIrRegistro.setOnClickListener {

            val intent = Intent(this, Registro::class.java)
            startActivity(intent)

        }

        botonLogin.setOnClickListener {

            //TODO Metodo normarl

            val regex = Regex("^[a-zA-Z0-9]+$")
            val usuarioValido = regex.matches(nombreUsuario.editText?.text.toString())
            val contraseñaValida = regex.matches(contraseñaUsuario.editText?.text.toString())
            val longitudValida = nombreUsuario.editText?.text?.length ?: 0 <= 12 && contraseñaUsuario.editText?.text?.length ?: 0 <= 12
            val usuarioNoVacio = nombreUsuario.editText?.text?.isNotBlank() ?: false
            val contraseñaNoVacia = contraseñaUsuario.editText?.text?.isNotBlank() ?: false
            val usuarioSinEspacios = !nombreUsuario.editText?.text?.contains(" ")!!
            val contraseñaSinEspacios = !contraseñaUsuario.editText?.text?.contains(" ")!!

            if (usuarioValido && contraseñaValida && longitudValida && usuarioNoVacio && contraseñaNoVacia && usuarioSinEspacios && contraseñaSinEspacios) {
                // El usuario y contraseña son válidos
                val query = "SELECT * FROM usuarios WHERE nombreUsuario = '"+nombreUsuario.editText?.text.toString()+"' AND contraseñaUsuario = '"+contraseñaUsuario.editText?.text.toString()+"'"
                val cursor = db.rawQuery(query, null)

                if (cursor.count > 0 && cursor.moveToFirst()) {
                    val nombrePasar = cursor.getString(cursor.getColumnIndex("nombreUsuario"))
                    val contraseñaPasar = cursor.getString(cursor.getColumnIndex("contraseñaUsuario"))
                    val fechaPasar = cursor.getString(cursor.getColumnIndex("fechaRegistro"))
                    val fotoPerfilPasar = cursor.getBlob(cursor.getColumnIndex("fotoPerfil"))
                    val grupoPasar = cursor.getString(cursor.getColumnIndex("grupo"))
                    val intent = Intent(this, PrimeraPantalla::class.java)
                    intent.putExtra("usuario", nombrePasar.toString())
                    intent.putExtra("fechaNacimiento",fechaPasar.toString())
                    intent.putExtra("contraseña",contraseñaPasar.toString())
                    intent.putExtra("fotoPerfil",fotoPerfilPasar)
                    intent.putExtra("grupo",grupoPasar)
                    finish()
                    startActivity(intent)
                } else {
                    TSnackbar.make(findViewById(android.R.id.content),"El usuario o la contraseña no son validos o no existe.",TSnackbar.LENGTH_LONG).show()
                }

            } else {
                // El usuario y/o la contraseña son inválidos
                TSnackbar.make(findViewById(android.R.id.content),"Rellene bien todos los campos",TSnackbar.LENGTH_LONG).show()
            }

            /*
            //TODO Metodo conexion a una web
            val regex = Regex("^[a-zA-Z0-9]+$")
            val usuarioValido = regex.matches(nombreUsuario.editText?.text.toString())
            val contraseñaValida = regex.matches(contraseñaUsuario.editText?.text.toString())
            val longitudValida = nombreUsuario.editText?.text?.length ?: 0 <= 12 && contraseñaUsuario.editText?.text?.length ?: 0 <= 12
            val usuarioNoVacio = nombreUsuario.editText?.text?.isNotBlank() ?: false
            val contraseñaNoVacia = contraseñaUsuario.editText?.text?.isNotBlank() ?: false
            val usuarioSinEspacios = !nombreUsuario.editText?.text?.contains(" ")!!
            val contraseñaSinEspacios = !contraseñaUsuario.editText?.text?.contains(" ")!!

            if (usuarioValido && contraseñaValida && longitudValida && usuarioNoVacio && contraseñaNoVacia && usuarioSinEspacios && contraseñaSinEspacios) {
                val SDK_INT = Build.VERSION.SDK_INT
                if (SDK_INT > 8) {
                    val policy = StrictMode.ThreadPolicy.Builder()
                        .permitAll().build()
                    StrictMode.setThreadPolicy(policy)

                    val client = OkHttpClient()

                    val request = Request.Builder()
                        .url("http://172.26.100.205:8585/Partes/resources/login?usuario=${nombreUsuario.editText?.text.toString()}&password=${contraseñaUsuario.editText?.text.toString()}")
                            .build()
                                client.newCall(request).execute().use { response ->
                            if (!response.isSuccessful) throw IOException("Unexpected code $response")
                            Toast.makeText(this,nombreUsuario.editText?.text.toString()+" "+contraseñaUsuario.editText?.text.toString(),Toast.LENGTH_SHORT).show()
                            val xml = response.body?.string()
                            TSnackbar.make(findViewById(android.R.id.content),xml.toString(),TSnackbar.LENGTH_LONG).show()
                        }
                }
            }*/
        }
    }
}





