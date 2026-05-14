package com.example.proyectoapps.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Helper para guardar y leer la sesión activa del usuario
 * usando SharedPreferences (clave-valor simple).
 */
class SharedPrefsHelper(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("MiAppPrefs", Context.MODE_PRIVATE)

    companion object {
        const val KEY_IS_LOGGED_IN = "is_logged_in"
        const val KEY_USER_ID      = "user_id"
        const val KEY_USER_ROLE    = "user_role"
        const val KEY_USER_NAME    = "user_name"
        const val KEY_USER_EMAIL   = "user_email"
    }

    // ── Guardar ───────────────────────────────────────────────
    fun guardarSesion(id: String, nombre: String, correo: String, rol: String) {
        prefs.edit()
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .putString(KEY_USER_ID, id)
            .putString(KEY_USER_NAME, nombre)
            .putString(KEY_USER_EMAIL, correo)
            .putString(KEY_USER_ROLE, rol)
            .apply()
    }

    // ── Leer ──────────────────────────────────────────────────
    fun estaLogueado(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    fun getUserId(): String     = prefs.getString(KEY_USER_ID, "") ?: ""
    fun getRol(): String        = prefs.getString(KEY_USER_ROLE, "Estudiante") ?: "Estudiante"
    fun getNombre(): String     = prefs.getString(KEY_USER_NAME, "") ?: ""
    fun getCorreo(): String     = prefs.getString(KEY_USER_EMAIL, "") ?: ""

    // ── Cerrar sesión ─────────────────────────────────────────
    fun cerrarSesion() = prefs.edit().clear().apply()
}
