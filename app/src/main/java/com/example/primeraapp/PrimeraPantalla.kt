package com.example.primeraapp

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import clases.miOpenHelper
import com.androidadvance.topsnackbar.TSnackbar
import com.example.primeraapp.Clases.Usuarios
import java.io.ByteArrayOutputStream

class PrimeraPantalla : ModoInmersivo() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UsuarioAdapter

    private val PERMISSION_REQUEST_CODE = 123



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primera_pantalla)

        val usuario = intent.getStringExtra("usuario")
        val usuarioGrupo = intent.getStringExtra("grupo")

        val usuariosList: ArrayList<Usuarios> = ArrayList()

        val botonCerrarSesion: Button = findViewById(R.id.botonCerrarSesion)

        botonCerrarSesion.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            finish()
            startActivity(intent)
        }


        if (checkSelfPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {


        } else {

            requestPermissions(
                arrayOf(
                    Manifest.permission.MODIFY_AUDIO_SETTINGS,
                    Manifest.permission.VIBRATE
                ),
                PERMISSION_REQUEST_CODE
            )
        }

        var querier: SQLiteDatabase = miOpenHelper(this).writableDatabase //readableDatabase
        var cursor: Cursor = querier.query(
            "usuarios",
            arrayOf("*"),
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val username = cursor.getString(cursor.getColumnIndexOrThrow("nombreUsuario"))
            val date = cursor.getString(cursor.getColumnIndexOrThrow("fechaRegistro"))
            val pfp = cursor.getBlob(cursor.getColumnIndexOrThrow("fotoPerfil"))
            val grupo = cursor.getInt(cursor.getColumnIndexOrThrow("grupo"))

            val byteArray: ByteArray = pfp
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            val user = Usuarios(username, null, date, bitmap,grupo)
            usuariosList.add(user)
        }

        recyclerView = findViewById(R.id.recycler_view_usuarios)


        adapter = UsuarioAdapter(this,usuariosList, usuario.toString(), usuarioGrupo!!.toInt())


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            } else {

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    class UsuarioAdapter(private val context: Context,
                         private var listaUsuarios: ArrayList<Usuarios>,
                         val usuarioLogin: String,
                         val grupo: Int
    ) : RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nombreUsuarioTextView: TextView = itemView.findViewById(R.id.text_nombre_usuario)
            val fechaNacimientoTextView: TextView =
                itemView.findViewById(R.id.text_fecha_nacimiento)
            val fotoPerfilImageView: ImageView = itemView.findViewById(R.id.image_view_usuario)

            val botonBorrarUsuario: Button = itemView.findViewById(R.id.botonBorrarUsuario)
            val botonEditarUsuario: Button = itemView.findViewById(R.id.botonEditarUsuario)


        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_usuario, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val usuarioActual = listaUsuarios[position]
            holder.nombreUsuarioTextView.setText(usuarioActual.username)
            holder.fechaNacimientoTextView.setText(usuarioActual.date)
            holder.fotoPerfilImageView.setImageBitmap(usuarioActual.pfp)

            if (grupo == 2) {
                holder.botonBorrarUsuario.isVisible = false
                holder.botonEditarUsuario.isVisible = false

            }


            val theme = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            if (theme == Configuration.UI_MODE_NIGHT_NO) {
                if(usuarioActual.grupo == 2){
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.azul_claro))
                    holder.fechaNacimientoTextView.setTextColor(ContextCompat.getColor(context, R.color.red))
                    holder.nombreUsuarioTextView.setTextColor(ContextCompat.getColor(context, R.color.red))
                }else if(usuarioActual.grupo == 1){
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.black))
                    holder.fechaNacimientoTextView.setTextColor(ContextCompat.getColor(context, R.color.teal_200))
                    holder.nombreUsuarioTextView.setTextColor(ContextCompat.getColor(context, R.color.teal_200))
                }else{
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_200))
                    holder.fechaNacimientoTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
                    holder.nombreUsuarioTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
                }
            } else {
                if(usuarioActual.grupo == 2){
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.azul_claro))
                    holder.fechaNacimientoTextView.setTextColor(ContextCompat.getColor(context, R.color.red))
                    holder.nombreUsuarioTextView.setTextColor(ContextCompat.getColor(context, R.color.red))
                }else if(usuarioActual.grupo == 1){
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
                    holder.fechaNacimientoTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
                    holder.nombreUsuarioTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
                }else{
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.black))
                    holder.fechaNacimientoTextView.setTextColor(ContextCompat.getColor(context, R.color.purple_200))
                    holder.nombreUsuarioTextView.setTextColor(ContextCompat.getColor(context, R.color.purple_200))
                }
            }



            holder.botonEditarUsuario.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, EditarUsuarios::class.java)
                intent.putExtra("usuario", usuarioActual.username)
                intent.putExtra("fechaNacimiento", usuarioActual.date)
                intent.putExtra("contraseña", usuarioActual.password)
                intent.putExtra("grupo", grupo.toString())
                val stream = ByteArrayOutputStream()
                usuarioActual.pfp.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()
                intent.putExtra("fotoPerfil", byteArray)

                context.startActivity(intent)

            }



            holder.botonBorrarUsuario.setOnClickListener {

                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

                val alertDialog = AlertDialog.Builder(context)
                    .setTitle("Borrar Usuario")
                    .setMessage("¿Seguro que quieres borrar el usuario?")
                    .setPositiveButton("Aceptar"){ dialog, _ ->
                        val context = holder.itemView.context
                        val database: SQLiteDatabase = miOpenHelper(context).writableDatabase
                        val rowsDeleted = database.delete(
                            "usuarios",
                            "nombreUsuario = ?",
                            arrayOf(usuarioActual.username)
                        )
                        if (rowsDeleted > 0) {
                            listaUsuarios.removeAt(position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, listaUsuarios.size)

                            if (usuarioActual.username == usuarioLogin) {
                                TSnackbar.make(holder.itemView,"Te has Borrado",TSnackbar.LENGTH_LONG).show()
                                val intent = Intent(context, Login::class.java)
                                context.startActivity(intent)
                                (context as Activity).finish()
                            }
                            val snackbar:TSnackbar = TSnackbar.make(holder.itemView,"Usuario Borrado", TSnackbar.LENGTH_LONG)
                            snackbar.setActionTextColor(Color.RED)
                            snackbar.setIconLeft(R.drawable.iconoexclamacion,30f)
                            snackbar.setIconRight(R.drawable.iconoexclamacion,30f)
                            snackbar.view.textAlignment = TEXT_ALIGNMENT_CENTER
                            val snackbarView: View = snackbar.view
                            snackbarView.setBackgroundColor(Color.parseColor("#FF0000"))
                            val textView =
                                snackbarView.findViewById<View>(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
                            textView.setTextColor(Color.YELLOW)
                            textView.textAlignment = TEXT_ALIGNMENT_CENTER
                            snackbar.show()
                        } else {
                            TSnackbar.make(holder.itemView,"Error al borrar el usuario",TSnackbar.LENGTH_LONG).show()
                        }

                        database.close()
                    }
                    .setNegativeButton("Cancelar",null)
                    .create()

                alertDialog.setOnShowListener {
                    vibrator.vibrate(2000)
                    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                    val previousVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) , 0)
                    val mediaPlayer = MediaPlayer.create(context, R.raw.notification_sound)
                    mediaPlayer.start()

                    Handler(Looper.getMainLooper()).postDelayed({
                        val newVolume = if (previousVolume == 0) 0 else audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0)
                    }, 5000)

                }
                alertDialog.show()


            }
        }


        override fun getItemCount() = listaUsuarios.size

    }
}


