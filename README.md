 
## Table of Contents
1. [Infomación General](#Infomación General)
2. [Requisitos](#Requisitos)
3. [Estructura de proyecto](#Estructura de proyecto)
4. [Configuración Tomcat](#Configuracion Tomcat)
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
    * **Log4Java**
    * **Log4Java2**
    * **SLF4J**
    * **LogBack**
  


## Estructura de proyecto
 
|Proyecto|Descripción|Column 3|
|---|---|---|
|local-filter| El proyecto es filter para integrar en el WAR y establecer un puente entre el filter del Tomcat y el del WAR | Deprecado |
|spring-boot-demo0| War Demo con un conjunto de servicios para ser testeado, se integrar la solución con Local Filter + Tomcat Filter | Deprecado |
|spring-boot-demo1| War Demo con un conjunto de servicios para ser testeado, se integrar la solución con Local Filter | Finalista|
|spring-boot-demo2| War Demo con un conjunto de servicios para ser testeado, se integrar la solución sin Local Filter | Finalista|
|tomcat-filter| El Filtro de Tomcat que se debe utilizar para integrar el correlationID|Finalista|
|tomcat-log-appender|Adaptación de un ConsoleAppender de Logback para hacer una prueba en la integración del correlationId|Deprecado|

## Configuracion Tomcat
* Copiar los Adapters o Frameworks dentro del tomcat %TOMCAT_HOME%\lib
* Copiar los configuradores en el %TOMCAT_HOME%\conf
* Copiar el Eulen-Global-Filter en el %TOMCAT_HOME%\lib
* Se activa el Filtro Valve de Tomcat en server.xml
```
<Valve className="com.eulen.core.EulenRequestIdGlobalFilter"/>
```
* Al desplegar el WAR, crear un contexto "delegado"
 ```
 <Context   path="/web-service1" autoDeploy="true" docBase="service1.war" >
     <Loader delegate="true" loaderClass="org.apache.catalina.loader.WebappClassLoader" />
 </Context>
 ```
Gracias! ;) 


![myImage](https://media.giphy.com/media/XRB1uf2F9bGOA/giphy.gif)
