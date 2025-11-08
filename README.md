# Prueba Técnica TotalPlay



https://github.com/user-attachments/assets/1c850402-aaf7-4091-a8e7-e2f7e1d82c88



## Descripción

**Prueba Técnica TotalPlay** es una aplicación Android desarrollada en **Kotlin** que 
permite a los usuarios consultar y visualizar información detallada de distintos lugares. 
La aplicación utiliza una arquitectura basada en el patrón MVVM (Model-View-ViewModel) y 
consume servicios externos para obtener datos de ubicaciones, mostrando detalles como nombre, 
dirección, calificación, reseñas y fotografías.

El propósito principal del proyecto es mostrar un flujo completo de consumo de API 
y gestión del estado dentro de una app moderna.

## Instrucciones para ejecutar el proyecto
1. Clonar el repositorio (git clone https://github.com/AbimaelHuerta/TestTotalPlayApp.git)
2. Abrir el proyecto (Abre la carpeta del proyecto en Android Studio)
3. Agregar la clave de API:
   - Dentro del archivo local.properties(ubicado en la raiz del proyecto), agrega la siguiente linea:
     **API_KEY=TU_API_KEY**
   - Sustituye *TU_API_KEY* por tu clave de API generada desde Google Cloud con acceso a Places API
4. Sincroniza el proyecto
5. Ejecuta la aplicación
6. Al momento de compilar la app, se generara una carpeta llamada **Java (Generated)**, dentro de ella
   abra un paquete "com.example.pruebatotalplayapp" dentro de este paquete se encuentra el archivo
   **BuildConfig** donde se genera automaticamente al momento de compilar la app y aqui se encuentra nuestra
   API_KEY para que nuestro *Repository* tenga acceso a ella.

## Credenciales de inicio de sesión:
-Usuario: admin

-Contraseña: admin

## Tecnologías y herramientas utilizadas

-Kotlin

-Jetpack Compose

-Android Studio

-MVVM Architecture

-Coroutines y Flow

-Retrofit para la comunicación con APIs

-Hilt para la inyección de dependencias

-Google Places API (para obtener información de lugares)

## Estructura del proyecto
- **Data/**
  
    -*model/*: Contiene las clases de modelo de datos(Model.kt) que representa las estructuras utilizadas
                por la API de Google Places.
  
    -*remote/*: Incluye las interfaces que definen la comunicación con los servicios externos, como Retrofit.
  
    -*repository/*: Implementa la lógica de obtención de datos y actúa como intermediario entre la capa de
                    datos y la capa de presentación.
  
- **Network/**
     Contiene la configuracion de red y los módulos de Hilt encargados de proveer instancias de Retrofit y demás
     dependencias necesarias.
  
- **Navigation/**
      Define la navegación entre pantallas principales de la aplicación

- **Presentation/**
  Contiene las pantallas y su lógica de negocio, separads por módulos.
  
   -*details/*: Pantalla de detalles de un lugar y su viewModel
  
  -*login/*: Pantalla de inicio de sesión y su viewModel
  
  -*search/*: Pantalla de búsqueda de lugares y su viewModel
  
- **MainActivity.kt**:
      Es el punto de entrada principal de la aplicación. Se encarga de inicializar la navegación y aplicar
      el tema global
  
- **PlacesApplication.kt**:
      Clase de aplicación base anotada con @HiltAndroidApp que inicializa Hilt para la inyección de
      dependencias
  <img width="1113" height="921" alt="image" src="https://github.com/user-attachments/assets/251fb476-4da3-450f-8e29-f121c56239cd" />

## Pruebas Unitarias

<img width="1917" height="671" alt="image" src="https://github.com/user-attachments/assets/c688c1f9-6fe1-46df-b874-7e5fcea0ade7" />

<img width="1870" height="656" alt="image" src="https://github.com/user-attachments/assets/727af549-38dd-4784-9616-95841c083615" />


