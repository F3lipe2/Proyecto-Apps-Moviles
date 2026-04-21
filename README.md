<div align="center">
  <img src="https://img.icons8.com/color/100/000000/android-os.png" alt="Android Logo" width="80" />
  <h1>🎓 ProyectoApps - Control de Asistencia 📱</h1>
  <p><strong>Aplicación Android moderna en Jetpack Compose para la gestión integral de asistencia académica.</strong></p>

  <!-- Badges -->
  <p>
    <img src="https://img.shields.io/badge/Kotlin-100%25-B125EA?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin" />
    <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android" />
    <img src="https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose" />
    <img src="https://img.shields.io/badge/Room_Database-000000?style=for-the-badge&logo=sqlite&logoColor=white" alt="Room" />
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

**Control de Asistencia** es una solución móvil diseñada para automatizar y simplificar el seguimiento de la participación estudiantil en las aulas de clase. Construida completamente bajo las nuevas directrices de **Android** usando **Jetpack Compose** y los lineamientos de **Material Design 3**, garantiza una experiencia de usuario fluida, reactiva y visualmente hermosa.

La aplicación distingue entre los roles de **Profesor** y **Estudiante**, proporcionando herramientas a medida para ambos ecosistemas (como lectura de códigos QR, listados de cursos y chequeo de asistencias pasadas).

---

## ✨ Características Principales

### 👨‍🏫 Para Profesores
- **Panel de control (Mis Cursos):** Visualización de las asignaturas a cargo con tarjetas coloridas y dinámicas.
- **Gestión de Cursos:** Detalles completos que incluyen número de alumnos inscritos, sesiones y métricas.
- **Toma de Asistencia (QR):** Escaneo eficiente a través de códigos QR, reduciendo el proceso a unos cuantos segundos.
- **Historial Detallado:** Control y revisión de sesiones previas en tablas de asistencia.
- **Gestión de Estudiantes:** Vista detallada de todos los estudiantes asignados a una asignatura.

### 🧑‍🎓 Para Estudiantes
- **Cursos Inscritos:** Perfil en donde el estudiante observa sus materias.
- **Registro de Presencia:** Capacidad de verificar y confirmar la presecia a través de tecnología rápida (Lector / Generador QR dependiendo del flujo).
- **Reportes Personales:** Seguimiento de ausencias e inasistencias en línea de tiempo.

### 🔒 Sistema General
- **Autenticación Segura:** Login seguro y navegación protegida (NavGraph).
- **Modo Offline:** Guardado local de la información a través de la arquitectura Room (Base de Datos Local).

---

## 🛠 Arquitectura y Tecnologías

El proyecto se sustenta en herramientas vanguardistas y el "Modern Android Development" (MAD):

- **Lenguaje:** Kotlin (`JVM Target 11`).
- **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (declarativo) con Material 3.
- **Navegación:** `navigation-compose` para el manejo de rutas y backstack (NavHost, NavGraph).
- **Persistencia de Datos:** [Room Database](https://developer.android.com/training/data-storage/room) con manejo de entidades, DAOs y migraciones.
- **Patrón de Navegación:** Centralizado en `NavGraph.kt` para orquestar la comunicación entre pantallas.

---

## 📂 Estructura del Proyecto

El código está estructurado en paquetes limpios y escalables dentro de `app/src/main/java/com/example/proyectoapps/`:

```text
📦 com.example.proyectoapps
 ┣ 📂 auth            # Lógica y Vistas de Login / Autenticación.
 ┣ 📂 data            # Capa de datos.
 ┃ ┗ 📂 local         # Room Database (DAOs, Entities, Database).
 ┣ 📂 navegation      # Rutas, NavGraph y NavHostController.
 ┣ 📂 screens         # UI central, fragmentado por feature:
 ┃ ┣ 📂 auth          # Pantallas de Login y Registro.
 ┃ ┣ 📂 estudiante    # Panel y vistas para alumnos.
 ┃ ┗ 📂 profesor      # Detalles de curso, Historial, Escáner QR.
 ┣ 📂 ui              # Componentes compartidos y Theme (Colors, Type).
 ┗ 📂 utils           # Helpers, constantes y funciones de utilidad.
```

---

## 📋 Requisitos Previos

Asegúrate de contar con lo siguiente para poder construir el proyecto:

- **Android Studio:** Iguana | 2023.2.1 o superior.
- **Android SDK:** 36 (Minimum SDK 26 - Android 8.0 Oreo).
- **Java Development Kit (JDK):** Versión 11 o superior.
- **Gradle:** Plugins versión `8.x`.

---

## 🚀 Instalación y Configuración

Sigue estos pasos para compilar y ejecutar el repositorio de tu lado:

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/TuUsuario/Proyecto-Apps-Moviles.git
   ```
2. **Abrir en Android Studio**
   - Ejecuta Android Studio y selecciona `File > Open`.
   - Busca el directorio del proyecto descargado y haz clic en `OK`.
3. **Sincronizar Gradle**
   - Deja que Android Studio descargue las librerías necesarias de Jetpack Compose, Room y Compose Navigation.
4. **Ejecutar  ▶️**
   - Conecta un dispositivo físico con opciones de desarrollador activas o inicia un Emulador.
   - Presiona `Shift + F10` o al botón de `Run 'app'`.

---

## 📱 Capturas de Pantalla (Próximamente)

<p align="center">
  <img src="https://via.placeholder.com/250x500.png?text=Login" alt="Login Screenshot" width="200" /> &nbsp;
  <img src="https://via.placeholder.com/250x500.png?text=Mis+Cursos" alt="Dashboard Screenshot" width="200" /> &nbsp;
  <img src="https://via.placeholder.com/250x500.png?text=Asistencia" alt="Scanner Screenshot" width="200" />
</p>

---

<div align="center">
  <i>Desarrollado con ❤️ y mucho café. ☕</i>
</div>
