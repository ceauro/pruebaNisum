# Prueba Técnica (README)

El repositorio contiene la solución a la prueba técnica enviada para el puesto de Java Backedn Sr.

## Table de Contenidos

- [Inicio](#Inicio)
    - [Por Consola](#Por-Consola)
    - [Installation](#Desde-un-IDE)
- [Componentes](#Componentes)
- [Endpoints](#Endpoints)
    - [Login](#Login)
    - [Installation](#Crear-Usuario)
- [Base de Datos](#Base-de-Datos)
    - [Tablas](#Tablas)
    - [Modelo Entidad Relación](#Modelo-Entidad-Relación)
    - [Consola H2](#Consola-H2)
- [Diagrama de la solución](#Diagrama-de-la-solución)
- [Postman](#Postman)
- [Generalidades](#Generalidades)

## Inicio
### Por Consola

- Como prerequisito se debe tener instalado mvn y una version de Java 8+

1. Descargar el repositorio en una carpeta local.
2. Abrir una terminal/consola y navegar hasta la carpeta local y una vez allí posicionarse en donde se encuentre el archivo pom.xml
3. Construir el proyecto usando el siguiente comando Maven:
````
mvn clean install
````
4. Una vez la construcción del proyecto haya terminado, se debe ejecutar el siguiente comando java:
````
java -jar target/app-name.jar
````
5. La aplicación SpringBoot debería de iniciar, por lo que debería de ver el log de inicialización en la terminal.
6. Una vez inicie la aplicación de SpringBoot, utilizando postman, curl o cualquier herramienta que permita realizar llamados http, se puede empezar a realizar la prueba de los llamados de la api.

### Desde un IDE
Para ejecutar el proyecto desde un Entorno de Desarrollo Integrado (IDE), se pueden seguir los pasos generales. Los pasos pueden variar ligeramente según el IDE que estés utilizando (por ejemplo, Eclipse, IntelliJ IDEA o Visual Studio Code), pero el proceso general es similar:
1. Abre tu IDE e importa el proyecto Maven: como el proyecto es maven, la mayoría de los IDE reconocerán automáticamente que es un proyecto Maven. Por lo general, se puede importar el proyecto seleccionando "Importar" o "Abrir Proyecto" en el menú del IDE y luego navegando hasta el directorio raíz de tu proyecto donde se encuentra el archivo pom.xml.
2. Una vez que el proyecto esté importado, el IDE descargará y resolverá automáticamente las dependencias de Maven declaradas en el archivo pom.xml. Este proceso asegura que todas las bibliotecas y dependencias necesarias estén disponibles para que el proyecto se pueda construir y ejecutar.
3. Para construir el proyecto, puedes usar el soporte integrado de Maven en el IDE. La mayoría de los IDEs tienen soporte integrado de Maven, lo que te permite construir el proyecto con unos pocos clics. Busca opciones como "Construir Proyecto," "Ejecutar Build de Maven," o opciones similares para iniciar el proceso de construcción.
4. Una vez que el proyecto se haya construido correctamente, puedes ejecutar la aplicación Spring Boot directamente desde el IDE. En la mayoría de los IDEs, puedes hacer clic derecho en la clase principal (la que está anotada con @SpringBootApplication) y seleccionar "Ejecutar" o "Run As" -> "Java Application" o "Spring Boot App." Alternativamente, algunos IDEs pueden proporcionar botones dedicados o accesos directos para ejecutar aplicaciones Spring Boot.
5. Después de que la aplicación comience a ejecutarse, puedes acceder a ella mediante postman, curl o cualquier cliente que te permita hacer llamados http. La URL base es ``http://localhost:8080``

## Componentes

El proyecto fue creado utilizando SpringBoot 3.0.8 y contiene los siguientes elementos:
+ Spring Security: utilizado para la autenticación y la autorización.
+ Spring Data: utilizado para realizar los CRUD en la base de datos.
+ H2: motor de base de datos, utilizado para el almacenamiento de los datos.
+ Lombok: utilizado para reducir el codigo boilerplate y mejorar la legibilidad del código
+ ModelMapper: utilizado para hacer mapeos entre clases, de una manera más sencilla.
+ Jsonwebtoken: utilizado para la administración del jwt, utilizado en la autenticación y autorización de la solución.
+ Flyway: utilizado para la creación de objetos y datos iniciales en la base de datos.
+ SpringDoc: utilizado para la creación de la documentación en Swagger

## Documentación SWAGGER
Para ingresar a la documentación generada por swagger, se debe tener el proyecto corriendo es ingresar a la siguiente URL ``http://localhost:8080/swagger-ui.html`` utilizando un browser, se deberia ver la siguiente imagen:
![Screen Documetación Swagger](images/documentacionSwagger.png)

## Endpoints
### Login
Para crear un usuario, primero se debe de obtener una token jwt válido, para ésto se debe de llamar el endpoint de login (`http://<server>/login` o si se corre localmente `http://localhost:8080/login`). El cuerpo de la petición debe de ir un json como el siguiente:
```
{
    "email": "admin@admin.com",
    "password": "admin"
}
```
Al ejecutar el poyecto por primera vez, se crea el usuario admin con la contraseña admin, por lo que se debe de retornar el siguiente json:

```
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBhZG1pbi5jb20iLCJleHAiOjgyNjMwNTU0NzQ0NTIyNjcsIm5vbWJyZSI6IkFETUlOIn0.n4fm8JVUkHG1PuM_Z4oCD9cUfTe3u8YS6TJ3JowroVbIMSrgApEIkWx1BcKjCc5ZgODMlqVmGRpM38bcGIeQtA"
}
```
El valor del la propiedad token, es un token jwt válido y debe de ser utilizado como valor dentro del Header Authorization para crear un usuario. Cada token generado es válido por 30 días.

### Crear Usuario
Para consumir crear un usuario, se debe de llamar al endpoint `http://<server>/api/user`, si se corre localmente el proyecto debería de ser `http://localhost:8080/api/user`.
El body debe ser de tipo json y debe seguir la siguiente estructura:
```
{
    "name": "name",
    "email": "email",
    "password": "password",
    "phones": [
        {
            "number": "00000000",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}
```
Y debe de tener un Header autorización de la siguiente manera

| Clave         |           Valor           | 
|---------------|:-------------------------:|
| Authorization | Bearer <token_jwt_valido> |

#### Errores al crear un usuario
* Si no se envía el header Authorization correctamente o no se envía, el servicio va a responder con un Http Code 403
* Si se crea por segunda vez un usuario que comparta el mismo email de un usuario ya almacenado, va a responde con Http code 409 y en el body un json que contiene el mensaje de error.
* Si se envía un json con una incorrecto, el servicio va a responsere un Http code 400 y en el body un json en donde se muestra el mensaje del error.

## Base de Datos
### Tablas
* Person: en esta tabla se persisten los datos de los usuarios.
* Phone: en esta tabla se persisten los datos de los teléfonos de los usuarios
* Configuration: en esta tabla se persisten las configuraciones

### Modelo Entidad Relación
![Modelo Entidad Relación](images/erModel.png)

### Consola H2
El proyecto tiene activada la consola de H2 para realizar operaciones sobre la base de datos. Para ingresar a ella, se debe de tener el proyecto corriendo y en un browser ingresar la URL: ``http://localhost:8080/h2-ui``. Debe de aparecer una pantalla como la siguiente:

![Pantalla Ingreso Consola H2](images/ingresoh2.png)

Los datos que deben de ir son los siguientes:
* Driver Class: org.h2.Driver
* JDBC URL: jdbc:h2:file:./testdb
* User Name: sa
* Password: dejar el campo vacío.

Una vez se coloquen los datos correctos, se debe poder ingresar a la consola de H2 que tendrá una vista como la siguiente:
![Consola H2](images/consolah2.png)

Allí se pueden hacer las operaciones que se deseen sobre la BD.

## Diagrama de la solución
![Diagrama de la Solución](images/DiagramaSolucion.png)

## Postman
Cree un proyecto postman desde donde se pueden hacer los llamados a los edpoints de la solución.
La url del proyecto es ``https://api.postman.com/collections/1294860-2beac776-4189-4c81-9eff-4aa55a206729?access_key=PMAT-01H5TA8SMT07DGV7Y7NTWW3RMM``. Con ésta URL deben importar el proyecto en Postman y allí les aparecerá los dos llamados.
![Proyecto Postman](images/postman.png)
Se deberá crear una variable global llamada token y en ésta variable colocar el jwt, o simplemente colocar el token jwt en donde dice token borrando {{token}} (ver circulo en la imagen)

## Generalidades
+ La expresión regular que valida la constraseña, está almacenada en la tabla configuration en la base de datos. Por defecto se creo la expresión regular que sigue las siguientes reglas:
    + Debe contener al menos una letra minúscula.
    + Debe contener al menos una letra mayúscula.
    + Debe contener al menos un dígito.
    + Debe tener una longitud mínima de 8 caracteres.

Para cambiar la expresión regular, se debe hacer a través de la consola de H2, cambiando el valor a la propiedad regexp.password. Se puede utilizar la siguiente sentencia DML en SQL:
````
UPDATE CONFIGURATION
   SET property_value = '<colocar_aquí_expresion_regular>'
 WHERE  property_name = 'regexp.password'
````
Aclaro que si la expresión regular queda mal formateada o erronea, al momento de validar la clave, el servicio retornará un error Http code 400 indicando que el password es inválido.
* Los archivos de creación de tablas y los datos iniciales de la base de datos, se encuentras en el directorio ``src/main/resources/db/migration``. Estos archivos son utilizados por Flyway para la creación de las tablas y la poblar las tablas adecuadamente, no se deberían manipular. **No se deben ejecutar manualmente en ningún momento**.
