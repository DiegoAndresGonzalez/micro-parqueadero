Puerto Micro Parqueadero = 8080
Puerto Micro Correos = 8081

Recomiendo utilizar el paquete de postman del repositorio puesto que es el más actualizado

Swagger = http://localhost:8080/swagger-ui/index.html#/

Configuracion postgresql (se puede cambiar en application.properties si hace falta):
localhost:5433/parqueadero (Es necesario crear una base de datos llamada parqueadero primero)
username=postgres
password=1234

El usuario de admin se creará automaticamente al haber iniciado el micro de parqueadero,
pero para ello hace falta que exista conexión con la base de datos.

Version de java = 17
Al abrir el IDE se debe configurar la versión de Gradle a Java 17 (la versión utilizada fue coretto-17)

Los endpoint request están documentados/preparados en sus respectivas carpetas en POSTMAN
El archivo de Postman se encuentra en la carpeta raiz del proyecto por si hace falta.

Para poder ejecutar un endpoint se requieren los tokens generados por medio del login, 
tanto admin como socio pueden iniciar sesión sin problemas.

La firma de los tokens es aleatoria cada vez que se inicia el API, por lo que al finalizar la micro
y volverla a iniciar, se debe crear un token nuevo mediante el login o tirará una excepción 500 (JWTSignature)

Para poder ejecutar el endpoint de enviar un correo, es necesario tener la micro de "mail" activa


