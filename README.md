<div align="center">

  <img src="https://img.icons8.com/color/100/000000/android-os.png" alt="Android Logo" width="80" />

  <h1>🎓 Control de Asistencia Académica</h1>
  <p><strong>Aplicación Android nativa en Jetpack Compose para la gestión integral de asistencia escolar, sincronizada en tiempo real con Firebase.</strong></p>

  <p>
    <img src="https://img.shields.io/badge/Kotlin-100%25-B125EA?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin" />
    <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android" />
    <img src="https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose" />
    <img src="https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black" alt="Firebase" />
    <img src="https://img.shields.io/badge/Material_3-757575?style=for-the-badge&logo=materialdesign&logoColor=white" alt="Material 3" />
  </p>

  <p>
    <img src="https://img.shields.io/badge/Min_SDK-26_(Android_8.0)-brightgreen?style=flat-square" />
    <img src="https://img.shields.io/badge/Target_SDK-36-blue?style=flat-square" />
    <img src="https://img.shields.io/badge/Version-1.0-orange?style=flat-square" />
  </p>

</div>

---

## 📖 Tabla de Contenidos

- [Acerca del Proyecto](#-acerca-del-proyecto)
- [Características](#-características)
- [Flujo de la Aplicación](#-flujo-de-la-aplicación)
- [Arquitectura y Tecnologías](#-arquitectura-y-tecnologías)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Modelos de Datos (Firestore)](#-modelos-de-datos-firestore)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación y Configuración](#-instalación-y-configuración)

---

## 🌟 Acerca del Proyecto

**Control de Asistencia** es una solución móvil diseñada para automatizar el registro de asistencia en entornos académicos. La app opera con dos roles diferenciados: **Profesor** y **Estudiante**, cada uno con su propio flujo de trabajo.

El registro de asistencia se realiza mediante **códigos QR**: el estudiante muestra su QR personal y el profesor lo escanea en tiempo real. Todos los datos se sincronizan instantáneamente en **Cloud Firestore**, sin necesidad de base de datos local.

---

## ✨ Características

### 👨‍🏫 Perfil Profesor

| Funcionalidad | Descripción |
|---|---|
| **Mis Cursos** | Dashboard con todos los cursos creados, sincronizados desde la nube |
| **Crear Curso** | Formulario con nombre, código, descripción y selección de **color personalizado** para la card |
| **Escanear QR** | Cámara con ML Kit para registrar la asistencia de un estudiante al instante |
| **Lista de Estudiantes** | Visualización de todos los alumnos inscritos en un curso |
| **Historial de Asistencia** | Reporte completo de asistencias pasadas filtrado por curso |

### 🧑‍🎓 Perfil Estudiante

| Funcionalidad | Descripción |
|---|---|
| **Mis Cursos** | Cards con nombre del curso, código, descripción, **nombre del profesor** y color personalizado |
| **Mi Código QR** | Generación de QR personal único vinculado al UID de Firebase Auth |
| **Historial Personal** | Consulta del historial de asistencias propio por curso |

### 🔒 Sistema General

- **Autenticación Firebase Auth** — Registro e inicio de sesión con email/contraseña.
- **Roles de usuario** — Separación completa de flujos entre `profesor` y `estudiante`.
- **Sincronización robusta** — Los datos del estudiante se vinculan al curso incluso si el perfil aún no está completamente cargado.
- **Retrocompatibilidad** — Los campos nuevos (`color`, `nombreProfesor`) usan valores por defecto para no romper datos existentes en Firestore.

---

## 🔄 Flujo de la Aplicación

```
┌─────────────────────────────────────────────┐
│                  Login / Registro            │
│             (Firebase Authentication)        │
└──────────────┬──────────────────────────────┘
               │ rol almacenado en Firestore
       ┌───────┴────────┐
       │                │
  👨‍🏫 Profesor      🧑‍🎓 Estudiante
       │                │
  Mis Cursos       Mis Cursos
       │           (con nombre del profesor
  [+ Nuevo Curso]   y color del curso)
  (color picker)         │
       │            Mi Código QR
  Detalle Curso     (para ser escaneado)
       │
  ┌────┴──────────────┐
  │                   │
Escanear QR      Lista de Estudiantes
(registra           │
asistencia)    Historial de Asistencia
```

---

## 🛠 Arquitectura y Tecnologías

El proyecto sigue el patrón **MVVM (Model-View-ViewModel)** con una capa de repositorio centralizada:

```
UI (Compose) ──► ViewModel ──► Repository ──► Firebase
```

### Stack Tecnológico

| Capa | Tecnología |
|---|---|
| **Lenguaje** | Kotlin (JVM Target 11) |
| **UI** | Jetpack Compose + Material 3 |
| **Navegación** | Jetpack Navigation Compose |
| **Arquitectura** | MVVM + Repository Pattern |
| **Base de datos** | Firebase Cloud Firestore |
| **Autenticación** | Firebase Authentication (Email/Password) |
| **Escaneo QR** | CameraX + ML Kit Barcode Scanning |
| **Generación QR** | ZXing Library |
| **Estado** | `StateFlow` / `SharedFlow` (Kotlin Coroutines) |

---

## 📂 Estructura del Proyecto

```text
📦 com.example.proyectoapps
 ┣ 📜 MainActivity.kt              # Punto de entrada, configuración NavHost
 ┣ 📂 data
 ┃ ┣ 📜 Repository.kt              # Toda la lógica de Firestore (CRUD, consultas)
 ┃ ┗ 📂 local
 ┃   ┣ 📜 Curso.kt                 # Modelo: nombre, código, color, nombreProfesor
 ┃   ┣ 📜 Usuario.kt               # Modelo: id, nombre, correo, rol
 ┃   ┗ 📜 Asistencia.kt            # Modelo: estudiante, curso, fecha, presente
 ┣ 📂 navegation
 ┃ ┗ 📜 Routes.kt + NavGraph.kt    # Rutas y grafo de navegación
 ┣ 📂 screens
 ┃ ┣ 📂 auth
 ┃ ┃ ┣ 📜 Login.kt
 ┃ ┃ ┗ 📜 Register.kt
 ┃ ┣ 📂 profesor
 ┃ ┃ ┣ 📜 CursosPro.kt             # Dashboard de cursos del profesor
 ┃ ┃ ┣ 📜 NuevoCurso.kt            # Formulario + selector de 8 colores
 ┃ ┃ ┣ 📜 DetalleCurso.kt          # Vista detallada de un curso
 ┃ ┃ ┣ 📜 EscanerQr.kt             # Escaneo de QR con CameraX/ML Kit
 ┃ ┃ ┣ 📜 ListaEstudiantes.kt      # Alumnos inscritos en un curso
 ┃ ┃ ┣ 📜 HistorialAsistencia.kt   # Reporte de asistencias del curso
 ┃ ┃ ┣ 📜 ProfesorViewModel.kt
 ┃ ┃ ┗ 📜 ProfesorViewModelFactory.kt
 ┃ ┗ 📂 estudiante
 ┃   ┣ 📜 CursosEstu.kt            # Cards con color y nombre del profesor
 ┃   ┣ 📜 CodigoQR.kt              # Generación y display del QR personal
 ┃   ┣ 📜 DetalleCursoEstu.kt      # Vista detallada del estudiante
 ┃   ┣ 📜 HistorialAsistenciaEstu.kt
 ┃   ┣ 📜 EstudianteViewModel.kt
 ┃   ┗ 📜 EstudianteViewModelFactory.kt
 ┣ 📂 ui
 ┃ ┗ 📂 theme
 ┃   ┗ 📜 AppColors.kt             # Paleta de colores (Material 3)
 ┗ 📂 utils
   ┣ 📜 SharedPrefsHelper.kt       # Persistencia local de sesión (userId, nombre, rol)
   ┗ 📜 QRUtils.kt                 # Generación de bitmaps QR con ZXing
```

---

## 🗄 Modelos de Datos (Firestore)

### Colección `users/{uid}`
```json
{
  "id":     "uid_de_firebase",
  "nombre": "Ana García",
  "correo": "ana@email.com",
  "rol":    "profesor" | "estudiante"
}
```

### Colección `cursos/{codigo}`
```json
{
  "nombre":         "Introducción a la Programación",
  "codigo":         "CS101",
  "descripcion":    "Fundamentos de algoritmos y estructuras de datos.",
  "idProfesor":     "uid_del_profesor",
  "nombreProfesor": "Carlos López",
  "color":          "#5C6BC0"
}
```

### Colección `cursos/{codigo}/estudiantes/{uid}`
Subcolección con el perfil del estudiante inscrito (copia del documento `users/{uid}`).

### Colección `asistencias/{docId}`
`docId` = `{idEstudiante}_{codigoCurso}_{fecha}`
```json
{
  "idEstudiante": "uid_del_estudiante",
  "codigoCurso":  "CS101",
  "fecha":        "2026-05-16",
  "presente":     true
}
```

---

## 📋 Requisitos Previos

- **Android Studio:** Ladybug 2024.2.1 o superior
- **JDK:** 11+
- **Android SDK:** compileSdk 36, minSdk 26 (Android 8.0+)
- **Cuenta Firebase:** Con proyecto activo en [Firebase Console](https://console.firebase.google.com/)

---

## 🚀 Instalación y Configuración

### 1. Clonar el repositorio
```bash
git clone https://github.com/F3lipe2/Proyecto-Apps-Moviles.git
cd Proyecto-Apps-Moviles
```

### 2. Configurar Firebase
1. Crea (o abre) tu proyecto en [Firebase Console](https://console.firebase.google.com/).
2. Agrega una app Android con el ID de paquete: `com.example.proyectoapps`.
3. Descarga `google-services.json` y colócalo en la carpeta **`/app`**.
4. En **Authentication → Sign-in method**, habilita **Email/Password**.
5. En **Firestore Database**, crea la base de datos y aplica estas reglas mínimas de desarrollo:
   ```js
   rules_version = '2';
   service cloud.firestore {
     match /databases/{database}/documents {
       match /{document=**} {
         allow read, write: if request.auth != null;
       }
     }
   }
   ```

### 3. Compilar y ejecutar
```bash
./gradlew assembleDebug
```
O simplemente abre el proyecto en Android Studio y presiona **▶ Run**.

El APK de debug se genera en:
```
app/build/outputs/apk/debug/app-debug.apk
```

---

## 🎨 Paleta de Colores de Cards

Al crear un curso, el profesor puede elegir entre 8 colores predefinidos:

| Color | Hex | | Color | Hex |
|---|---|---|---|---|
| Azul | `#3D8BCD` | | Verde | `#2E9E6B` |
| Índigo | `#5C6BC0` | | Ámbar | `#D97706` |
| Violeta | `#7B52A8` | | Rojo | `#DC2626` |
| Cian | `#0891B2` | | Grafito | `#374151` |

Los estudiantes ven el color elegido por el profesor en la cabecera de cada card, junto al nombre del profesor encargado del curso.

---

<div align="center">
  <i>Desarrollado con ❤️ · Proyecto de Aplicaciones Móviles</i>
</div>
