# MarsRover

<!-- Breve descripción (1-2 frases) -->
La aplicación simula el movimiento de rovers robóticos de la NASA en una meseta rectangular de Marte. Los rovers se controlan mediante una secuencia de comandos para girar ('L', 'R') o moverse ('M') por una cuadrícula, con el objetivo de explorar el terreno.

## ✨ Características

*   Definición de la Meseta y Posición Inicial del Rover: Permite al usuario definir las dimensiones de la meseta marciana (cuadrícula) y la posición inicial (coordenadas x, y, y dirección cardinal) del rover.
*   Envío de Secuencias de Comandos: Posibilita la introducción de una cadena de comandos ('L' para girar izquierda, 'R' para girar derecha, 'M' para mover) para controlar el rover.
*   Simulación Visual del: Muestra visualmente el movimiento del rover en la cuadrícula a medida que ejecuta los comandos, actualizando su posición y orientación.
*   Manejo de Límites de la Meseta: Implementa la lógica para que el rover no se mueva más allá de los límites establecidos de la meseta, ignorando los comandos de movimiento que lo sacarían de la cuadrícula.

## 📸 Capturas de Pantalla (Opcional)

<p align="center">
  <img width="233" alt="image" src="https://github.com/user-attachments/assets/c4d00f8d-737d-4512-95b3-d5bfd2c050ce" />
</p> 

## 🛠️ Tecnologías Utilizadas

*   **Lenguaje:** Kotlin
*   **UI:** Jetpack Compose
*   **Arquitectura:** MVVM, Clean Arquitecture
*   **Asincronía:** Kotlin Coroutines & Flow
*   **Networking:** Retrofit
*   **Inyeccion de dependenciaa** Koin
*   **Fuente de datos remota** Github Gist

## 🚀 Cómo Empezar

Abre tu terminal o línea de comandos y ejecuta el siguiente comando para clonar el proyecto desde GitHub: https://github.com/RafaelSMartin/MarsRover.git
Una vez que el proyecto esté sincronizado y tengas un dispositivo/emulador seleccionado, haz clic en el botón "Run 'app'"

## 🤔 Decisiones y Consideraciones

Durante el desarrollo de esta aplicación, se tomaron varias decisiones de diseño y se consideraron diferentes enfoques. Aquí se destacan algunos de los más relevantes:

### 1. Representación de la Meseta y Posición del Rover
*   **Decisión:** Se optó por representar la meseta como una cuadrícula bidimensional (x, y) y la posición del rover mediante coordenadas enteras y una dirección cardinal (Norte, Sur, Este, Oeste). La esquina inferior izquierda de la meseta se considera el origen (0,0).
*   **Consideración:** Este enfoque es simple de implementar y entender, alineándose con la descripción del problema. Se podría haber considerado un sistema de coordenadas más complejo o representaciones geográficas, pero para la escala del problema actual, la cuadrícula es suficiente y eficiente.

### 2. Procesamiento de Comandos
*   **Decisión:** Los comandos ('L', 'R', 'M') se procesan secuencialmente a partir de una única cadena de entrada. Cada comando modifica el estado del rover (dirección u posición) de forma inmediata antes de procesar el siguiente.
*   **Consideración:** Este método es directo y fácil de depurar. Se consideró la posibilidad de validar toda la cadena de comandos antes de la ejecución o implementar un sistema de comandos más robusto con cancelación/deshacer, pero se optó por la simplicidad para esta versión.

### 3. Manejo de Límites de la Meseta
*   **Decisión:** Si un comando 'M' (mover) haría que el rover se saliera de los límites definidos para la meseta, el rover no se mueve y mantiene su posición y orientación actuales. No se genera un error explícito al usuario por este evento, simplemente se ignora el movimiento.
*   **Consideración:** Esta es la lógica especificada en el problema ("If the rover tries to move and is heading to the limit of the plateau, It won’t move."). Una alternativa podría haber sido detener toda la secuencia de comandos al primer intento de salida o notificar al usuario, pero se adhirió a la especificación.

### 4. Estructura del Código y Arquitectura
*   **Decisión:** Se utilizó una estructura modular con Clean Arquitecture utilizando MVVM, Casos de Uso y Repository para separar las preocupaciones. 
*   **Consideración:** Aunque para una aplicación de esta escala una arquitectura muy compleja podría ser excesiva, separar la lógica del rover de la interfaz de usuario facilita las pruebas unitarias y la mantenibilidad futura.

### 5. Interfaz de Usuario (si aplica)
*   **Decisión:** Se optó por una interfaz de usuario simple basada en botones de datos y una cuadrícula para la meseta, Se utilizó Jetpack Compose para construir una representación visual básica de la cuadrícula y el rover.
*   **Consideración:** El enfoque principal fue la correcta implementación de la lógica del rover. Una interfaz más elaborada o con animaciones podría ser una mejora futura, pero no era el objetivo central de esta etapa.

### 6. Estructura de la Capa de Data
*   **Decisión:** Se ha desarrollado un sistema de Mock (MockMarsRoverRepositoryImpl) que lanza un json interno con los datos de inicio y tambien esta disponible de forma remota (MarsRoverRepositoryImpl) utilizando Github Gist, controlado por inyeccion de dependencia si BuildConfig.IS_DEMO
*   **Consideración:** Tener un Mock permite un desarrollo y pruebas rápidas y aisladas de la capa de datos, sin depender de una conexión a internet o de la disponibilidad del servicio remoto. Asegura que la aplicación pueda funcionar y ser probada en un entorno controlado con datos predecibles

### 7. Pruebas
*   **Decisión:** Se implementaron pruebas unitarias para la lógica principal del movimiento del rover, cubriendo giros, avances y el comportamiento en los límites de la meseta.
*   **Consideración:** Las pruebas son cruciales para asegurar la correctitud de la simulación. Se podrían añadir mas pruebas en el futuro si la aplicación crece en complejidad.
