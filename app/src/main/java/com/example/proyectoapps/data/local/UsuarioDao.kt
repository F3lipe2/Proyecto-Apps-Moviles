package com.example.proyectoapps.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsuarioDao {

    /** Inserta un nuevo usuario. Si el correo ya existe, lanza error (ABORT). */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registrar(usuario: Usuario)

    /** Busca un usuario por correo y contraseña (para el Login). Retorna null si no existe. */
    @Query("SELECT * FROM usuarios WHERE correo = :correo AND contrasena = :contrasena LIMIT 1")
    suspend fun login(correo: String, contrasena: String): Usuario?

    /** Verifica si ya existe un usuario con ese correo (para validar en Register). */
    @Query("SELECT * FROM usuarios WHERE correo = :correo LIMIT 1")
    suspend fun buscarPorCorreo(correo: String): Usuario?
}
