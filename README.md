 
## Table of Contents
1. [Infomación General](#general-info)
2. [Requisitos](#Requisitos)
3. [Estructura de proyecto](#estructura)
4. [Plugins necesarios](#Pluginsnecesarios)
5. [Colavoración](#Colavoración)
  

## Infomación General
Este repositorio contiene un conjunto de proyectos Java que han sido utilizados para hacer un caso de estudio para una POC. 

Todos los proyectos han sido parte del estudio, aunque algunos han sido descartados para la solucion final. 

El caso de estudio a analizar es mediante un servidor de aplicaciones Tomcat  desplegar aplicaciones y de forma automatica en los logs de la aplicacion se serialize un correlationId en todas las lineas de log.

## Requisitos
Para ello tenemos los siguientes requisitos principales del proyecto:
* Registrar en el log el conjunto acciones que realiza una petición ( de un usuario ) 
* No se quiere usar la session del usuario como alternativa al correlationId
* En la solución se debe evitar la programación

Los requisitos técnicos
* Se debe identificar la correlacion entre peticiones de servicios HTTP como un único ID
* La solución se debe integrar con los diferentes sistemas de log: 
* La solución se debe integrar en diferentes Frameworks de Java (Spring boot , ....)
* * **Log4Java**
* * **Log4Java2**
* * **SLF4J**
* * **LogBack**

## Estructura de proyecto

## Estructura de proyecto
Todos los proyectos que se incluyen en la pipiline deben tener un JenkinsFile , como este: 
   ```
   library('pipeline-common')
   // Lanzar la pipeline 
    DeployApp("DOCKER",  "sensors-52n-sos-2", "images/52n-sos/", ".", "1.0.0", "daniel.horta-vidal@t-systems.com")
   ```

>>> Donde la libreria "pipeline-common" està registrada en Jenkins (General > Ajustes > Librerias Externas.
 

