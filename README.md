<div align="center">
  <img src="https://img.icons8.com/color/100/000000/android-os.png" alt="Android Logo" width="80" />
  <h1>🎓 ProyectoApps - Control de Asistencia 📱</h1>
  <p><strong>Aplicación Android moderna en Jetpack Compose para la gestión integral de asistencia académica sincronizada en la nube.</strong></p>

  <!-- Badges -->
  <p>
    <img src="https://img.shields.io/badge/Kotlin-100%25-B125EA?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin" />
    <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android" />
    <img src="https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose" />
    <img src="https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black" alt="Firebase" />
  </p>
</div>

<br />

## 📖 Tabla de Contenidos
- [Acerca del Proyecto](#-acerca-del-proyecto)
- [Características Principales](#-características-principales)
- [Arquitectura y Tecnologías](#-arquitectura-y-tecnologías)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación y Configuración](#-instalación-y-configuración)
- [Capturas de Pantalla](#-capturas-de-pantalla)

---

## 🌟 Acerca del Proyecto

**Control de Asistencia** es una solución móvil diseñada para automatizar y simplificar el seguimiento de la participación estudiantil. Migrada recientemente a una arquitectura de nube completa, la aplicación utiliza **Firebase** para garantizar que los datos estén disponibles en tiempo real para profesores y alumnos, eliminando la dependencia de bases de datos locales volátiles.

---

## ✨ Características Principales

### 👨‍🏫 Para Profesores
- **Panel de control (Mis Cursos):** Gestión de asignaturas con sincronización en tiempo real.
- **Toma de Asistencia (QR):** Escaneo inteligente de códigos de alumnos con validación instantánea en la nube.
- **Historial Centralizado:** Revisión de asistencias pasadas con reportes detallados por alumno.
- **Gestión de Miembros:** Control de estudiantes inscritos por curso.

### 🧑‍🎓 Para Estudiantes
- **Mi Perfil QR:** Generación de código de identificación único para el registro de asistencia.
- **Cursos y Progreso:** Seguimiento personal de asistencias e inasistencias sincronizado con el profesor.
- **Acceso Multi-dispositivo:** Gracias a Firebase Auth, el alumno mantiene sus datos al cambiar de dispositivo.

### 🔒 Sistema General
- **Autenticación con Firebase Auth:** Registro e Inicio de sesión seguro de usuarios.
- **Nube de Firestore:** Almacenamiento escalable y persistente para cursos y registros.
- **Sincronización Robusta:** Sistema de inscripción forzada que asegura la visibilidad de datos incluso con perfiles parciales.

---

## 🛠 Arquitectura y Tecnologías

El proyecto utiliza el stack más moderno para desarrollo Android (MAD):

- **Lenguaje:** Kotlin (`JVM Target 11`).
- **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) con Material 3.
- **Backend/Base de Datos:** [Firebase Firestore](https://firebase.google.com/docs/firestore) para persistencia en tiempo real.
- **Autenticación:** [Firebase Authentication](https://firebase.google.com/docs/auth).
- **Escaneo QR:** [ML Kit Barcode Scanning](https://developers.google.com/ml-kit/vision/barcode-scanning).
- **Generación QR:** [ZXing Library](https://github.com/zxing/zxing).

---

## 📂 Estructura del Proyecto

```text
📦 com.example.proyectoapps
 ┣ 📂 data            # Capa de datos y Repositorios.
 ┃ ┣ 📜 Repository.kt # Lógica central de comunicación con Firestore.
 ┃ ┗ 📂 local         # Clases de datos (Models: Usuario, Curso, Asistencia).
 ┣ 📂 navegation      # Rutas y NavGraph centralizado.
 ┣ 📂 screens         # UI central, fragmentado por feature:
 ┃ ┣ 📂 auth          # Registro e Inicio de sesión.
 ┃ ┣ 📂 estudiante    # Generación de QR y Mis Cursos.
 ┃ ┗ 📂 profesor      # Detalles, Historial y Escáner QR.
 ┣ 📂 ui              # Tema visual (Material 3, colores, tipografía).
 ┗ 📂 utils           # Helpers (SharedPrefs, QRUtils).
```

---

## 📋 Requisitos Previos

- **Android Studio:** Ladybug | 2024.2.1 o superior.
- **Android SDK:** 36 (Minimum SDK 26).
- **Firebase Project:** Se requiere una cuenta en Firebase Console.

---

## 🚀 Instalación y Configuración

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/TuUsuario/Proyecto-Apps-Moviles.git
   ```
2. **Configurar Firebase**
   - Crea un proyecto en [Firebase Console](https://console.firebase.google.com/).
   - Registra la aplicación Android con el ID de paquete `com.example.proyectoapps`.
   - Descarga el archivo `google-services.json` y colócalo en la carpeta `/app`.
   - Habilita **Email/Password** en Authentication y crea una base de datos **Firestore**.
3. **Compilar y Ejecutar**
   - Abre el proyecto en Android Studio.
   - Sincroniza Gradle y presiona `Run`.

---

## 📱 Capturas de Pantalla (Próximamente)

<p align="center">
  <img src="https://via.placeholder.com/250x500.png?text=Login+Firebase" alt="Login Screenshot" width="200" /> &nbsp;
  <img src="https://via.placeholder.com/250x500.png?text=Mis+Cursos+Cloud" alt="Dashboard Screenshot" width="200" /> &nbsp;
  <img src="https://via.placeholder.com/250x500.png?text=Escaneo+Tiempo+Real" alt="Scanner Screenshot" width="200" />
</p>

---

<div align="center">
  <i>Desarrollado con ❤️ para la gestión académica moderna.</i>
</div>
