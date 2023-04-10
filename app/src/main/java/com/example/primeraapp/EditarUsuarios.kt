package com.example.primeraapp

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import clases.miOpenHelper
import com.androidadvance.topsnackbar.TSnackbar
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.ByteArrayOutputStream
import java.util.*


class EditarUsuarios : ModoInmersivo() {

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private val PERMISSIONS_REQUEST_CAMERA = 100
    private val PERMISSIONS_REQUEST_GALLERY = 101
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_SELECT_IMAGE = 2

    private fun dispatchOpenGalleryIntent() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(
            Intent.createChooser(intent, "Seleccione una imagen"),
            REQUEST_SELECT_IMAGE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_usuarios)

        val byteArray: ByteArray = intent.getByteArrayExtra("fotoPerfil")!!
        val usuarioFotoEditar = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        val usuarioNombreEditar = intent.getStringExtra("usuario")
        val usuarioContraseñaEditar = intent.getStringExtra("contraseña")
        val usuarioFechaEditar = intent.getStringExtra("fechaNacimiento")
        val usuarioGrupoEditar = intent.getStringExtra("grupo")

        val textNombreUsuario: TextInputLayout = findViewById(R.id.textUsuarioEditar)
        val textContraseñaUsuario: TextInputLayout = findViewById(R.id.textContraseñaEditar)
        val textContraseña2Usuario: TextInputLayout = findViewById(R.id.textContraseña2Editar)
        val botonEditar: Button = findViewById(R.id.botonEditarUsuario)

        val textoFechaNacimiento: TextInputLayout = findViewById(R.id.textFechaNacimientoEditar)
        val botonFechaNacimiento: TextInputEditText = findViewById(R.id.botonMostrarFecha)

        val botonBuscarFoto: Button = findViewById(R.id.botonBuscarFotoPerfilEditar)

        val db: SQLiteDatabase = miOpenHelper(this).writableDatabase

        val img: ImageView = findViewById(R.id.imgFotoPerfilEditar)

        Glide.with(this)
            .load(usuarioFotoEditar)
            .placeholder(R.drawable.iconoperfil)
            .error(R.drawable.iconoperfil)
            .into(img)
        textNombreUsuario.editText?.setText(usuarioNombreEditar)
        textContraseñaUsuario.editText?.setText(usuarioContraseñaEditar)
        textoFechaNacimiento.editText?.setText(usuarioFechaEditar)

        botonEditar.setOnClickListener {

            val bitmap = (findViewById<ImageView>(R.id.imgFotoPerfilEditar)).drawable.toBitmap()
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val imageBytes = byteArrayOutputStream.toByteArray()

            val regex = Regex("^[a-zA-Z0-9]+$")
            val usuarioValido = regex.matches(textNombreUsuario.editText?.text.toString())
            val contraseñaValida = regex.matches(textContraseñaUsuario.editText?.text.toString())
            val longitudValida =
                textNombreUsuario.editText?.text?.length ?: 0 <= 12 && textContraseñaUsuario.editText?.text?.length ?: 0 <= 12
            val usuarioNoVacio = textNombreUsuario.editText?.text?.isNotBlank() ?: false
            val contraseñaNoVacia = textContraseñaUsuario.editText?.text?.isNotBlank() ?: false
            val usuarioSinEspacios = !textNombreUsuario.editText?.text?.contains(" ")!!
            val contraseñaSinEspacios = !textContraseñaUsuario.editText?.text?.contains(" ")!!



            if (usuarioValido && contraseñaValida && longitudValida && usuarioNoVacio && contraseñaNoVacia && usuarioSinEspacios && contraseñaSinEspacios) {

                val nombreUsuario =
                    textNombreUsuario.editText?.text?.toString()?.toLowerCase(Locale.ROOT)

                val cursor = db.rawQuery(
                    "SELECT * FROM usuarios WHERE nombreUsuario=?",
                    arrayOf(nombreUsuario)
                )
                if (cursor.count > 0 && usuarioNombreEditar.toString()!=nombreUsuario.toString()) {
                    TSnackbar.make(findViewById(android.R.id.content),"El usuario ya existe", TSnackbar.LENGTH_LONG).show()
                } else if (textContraseña2Usuario.editText?.text?.toString() != textContraseñaUsuario.editText?.text?.toString()) {
                    TSnackbar.make(findViewById(android.R.id.content),"Las costraseña no son las mismas",TSnackbar.LENGTH_LONG).show()
                } else {
                    val values = ContentValues().apply {
                        put("nombreUsuario", nombreUsuario)
                        put(
                            "contraseñaUsuario",
                            textContraseñaUsuario.editText?.text?.toString()
                                ?.toLowerCase(Locale.ROOT)
                        )
                        put("fechaRegistro", textoFechaNacimiento.editText?.text?.toString())
                        put("fotoPerfil", imageBytes)
                        if (usuarioGrupoEditar != null) {
                            if (usuarioGrupoEditar.toInt() == 1) {
                                put("grupo", 1)
                            }else{
                                put("grupo", 2)
                            }

                        }else{
                            put("grupo", 2)
                        }
                    }
                    db.update(
                        "usuarios",
                        values,
                        "nombreUsuario=?",
                        arrayOf(usuarioNombreEditar.toString())
                    )
                    val intent = Intent(this, PrimeraPantalla::class.java)
                    finish()
                    intent.putExtra("usuario",nombreUsuario.toString())
                    intent.putExtra("grupo",usuarioGrupoEditar!!.toInt().toString())
                    startActivity(intent)
                }
                cursor.close()

            } else {
                TSnackbar.make(findViewById(android.R.id.content),"Rellene bien todos los campos", TSnackbar.LENGTH_LONG).show()
            }
        }



        botonFechaNacimiento.setOnClickListener {

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, R.style.Theme_App, { _, y, m, d ->
                textoFechaNacimiento.editText?.setText("$d/${m + 1}/$y")

            }, year, month, dayOfMonth)

            datePickerDialog.show()

        }


        botonBuscarFoto.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Seleccione una imagen")
            builder.setItems(arrayOf("Hacer una foto", "Abrir la galería")) { dialog, which ->
                // Lógica para seleccionar entre hacer una foto o abrir la galería
                when (which) {
                    0 -> {
                        // Solicitar permisos para la cámara
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                this,
                                arrayOf(Manifest.permission.CAMERA),
                                PERMISSIONS_REQUEST_CAMERA
                            )
                        } else {
                            // Abrir la cámara
                            dispatchTakePictureIntent()
                        }
                    }
                    1 -> {
                        // Solicitar permisos para la galería
                        if (ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                            != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                this,
                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                PERMISSIONS_REQUEST_GALLERY
                            )
                        } else {
                            // Abrir la galería
                            dispatchOpenGalleryIntent()
                        }
                    }
                }
            }
            builder.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var mostrarFoto: ImageView = findViewById(R.id.imgFotoPerfilEditar)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    mostrarFoto.setImageBitmap(imageBitmap)
                    // Guardar imagen en la galería
                    MediaStore.Images.Media.insertImage(
                        contentResolver,
                        imageBitmap,
                        "Imagen capturada",
                        null
                    )
                }
                REQUEST_SELECT_IMAGE -> {
                    val selectedImage = data?.data
                    if (selectedImage != null) {
                        mostrarFoto.setImageURI(selectedImage)
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CAMERA -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    dispatchTakePictureIntent()
                } else {
                    Toast.makeText(
                        this,
                        "Se requiere permiso para usar la cámara",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            PERMISSIONS_REQUEST_GALLERY -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    dispatchOpenGalleryIntent()
                } else {
                    Toast.makeText(
                        this,
                        "Se requiere permiso para acceder a la galería",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    override fun onBackPressed() {
        val mensaje = AlertDialog.Builder(this)
        mensaje.setTitle("¿Desea Salir de la Edicion de Usuario?")
        mensaje.setCancelable(false)
        mensaje.setPositiveButton(
            "Salir"
        ) { dialog, which -> finish() }
        mensaje.setNegativeButton(
            "Cancelar"
        ) { dialog, which -> }
        mensaje.show()
    }

}