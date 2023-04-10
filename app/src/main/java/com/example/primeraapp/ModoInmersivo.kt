package com.example.primeraapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast

open class ModoInmersivo : AppCompatActivity() {





    private val handler = Handler()
    private val immersiveRunnable = Runnable {
        // Volver a entrar en el modo inmersivo
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Ocultar la barra de navegación
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        // Ocultar la barra de estado
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
        // Hacer que la actividad ocupe toda la pantalla
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun enterImmersiveMode() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Ocultar la barra de navegación
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        // Ocultar la barra de estado
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
        // Hacer que la actividad ocupe toda la pantalla
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun exitImmersiveMode() {
        // Salir del modo inmersivo
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_VISIBLE
                        // Limpiar los flags para mostrar la barra de navegación
                        and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv()
                        // Limpiar los flags para mostrar la barra de estado
                        and View.SYSTEM_UI_FLAG_FULLSCREEN.inv()
                )

        // Programar una tarea para volver al modo inmersivo después de 2 segundos
        handler.postDelayed(immersiveRunnable, 2000)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Si ya estamos en modo inmersivo, salir de él
                if (window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                    exitImmersiveMode()
                }
                // Si no estamos en modo inmersivo, entrar en él
                else {
                    enterImmersiveMode()
                }
            }
            else -> {}
        }
        return super.onTouchEvent(event)
    }

    override fun onPause() {
        super.onPause()
        // Cancelar cualquier tarea pendiente para volver al modo inmersivo
        handler.removeCallbacks(immersiveRunnable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modo_inmersivo)

        enterImmersiveMode()

    }
}