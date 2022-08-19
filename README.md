## JWT (JSON Web Tokens)
https://jwt.io

Estándar abierto que permite transmitir información entre dos partes: 
* el navegador (aplicación de React, Angular, etc)
* un servidor (aplicación de Spring Boot)

Funcionamiento Session (conjunto de peticiones durante un determinado tiempo):
1. Cliente envía una petición a un servidor (/api/login)
2. Servidor valida username y password
* si son válidos, se almacena el usuario en sesión y se genera una cookie en el Cliente
* si no son válidos, devolverá una respuesta 401 Unauthorized
3. En próximas peticiones se comprueba que el Cliente está en sesión

Desventajas:
* La información de la sesión se almacena en el servidor, lo cual consume recursos.

Funcionamiento JWT:
1. Cliente envía una petición a un servidor o endpoint de la API REST (/api/login)
2. Servidor valida username y password
 * si son válidos, genera un token JWT utilizando una secret key (en application.properties o variable de entorno del sistema)
 * si no son válidos, devolverá una respuesta 401 Unauthorized
3. Servidor devuelve el token JWT generado al Cliente
4. Cliente guarda el token y envía peticiones a los endpoints REST del servidor utilizando el token JWT en las cabeceras
5. Servidor valida el token JWT en cada petición, y si es correcto, permite el acceso a los datos

Ventajas:
* El token se almacena en el Cliente, de manera que consume menos recursos en el Servidor, lo cual permite mejor escalabilidad (dar servicio a más usuarios)

Desventajas:
* El token está almacenado en el navegador, no podríamos invalidarlo antes de la fecha de expiración asignada cuando se creó.
Lo que se realiza es dar la opción de Logout, lo que simplemente borra el token.

Estructura del token:
1. Header (algoritmo y tipo de token)
```json
{
  "alg": "HS512",
  "typ": "JWT"
}
```
2. Payload (información, datos del usuario no sensibles)
```json
{
  "name": "John Doe",
  "admin": true
}
```
3. Signature 
```
HMACSHA256(
base64UrlEncode(header) + "." + base64UrlEncode(payload), secret
)
```

Ejemplo de token generado: 
header.payload.signature
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.
SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```
El User/Agent (Cliente) envía el token JWT en las cabeceras:
```
Authorization: Bearer <token>
```

Configuración en Spring:
Crear proyecto Spring Boot con dependencias:
* Spring Security
* Spring Web
* Spring Boot DevTools
* Spring Data JPA
* PostgreSQL

Añadir: 
* JWT
```
<!-- https://mvnrepository.com/artifact/io.jsonwebtoken -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
```

File > Settings > Build, Execution, Deployment > Compiler: Build project automatically

File > Settings > Advanced Settings: Allow auto-make to start even if developed application is currently running

## Configurar Swagger 2

https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api

## Migrar de WebSecurityConfigurerAdapter (deprecated)
https://www.appsdeveloperblog.com/migrating-from-deprecated-websecurityconfigureradapter/

https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter

Ejemplo:

https://github.com/bezkoder/spring-boot-spring-security-jwt-authentication

# Backend: Configuración Heroku
1. Crear el archivo system.properties en la raíz del proyecto especificando la versión de java (la misma que está en pom.xml): 
`java.runtime.version=17`
2. Subir proyecto a Github
3. Conectar el proyecto a Heroku
4. Añadir add-on de PostgreSQL en Heroku en la pestaña Resources en el dashboard
5. Dar click en el add-on agregado (Heroku Postgres) y en la ventana que abre ir a Settings > View credentials
6. En el dashboard de Heroku, ir a Settings y dar click en Reveal Config Vars, agregar con los valores obtenidos anteriormente
```
HOST: 
DATABASE:
DATABASE_URL:
DB_USER:
DB_PASSWORD: 
```
Más información: https://devcenter.heroku.com/articles/config-vars
7. Actualizar application properties copiando los valores de Host:Port/Database, User y Password
```
spring.datasource.url=jdbc:postgresql://ec2-54-159-22-90.compute-1.amazonaws.com:5432/d3o07c0vsctete
spring.datasource.url=jdbc:postgresql://${HOST}:5432/${DATABASE}

spring.datasource.username=opakqpyovgsjhh
spring.datasource.username=${DB_USER}

spring.datasource.password=45d346975063932ac81c707af0a912459947bd5ccd57d5148d3fd6186286f887
spring.datasource.password=${DB_PASSWORD}
```
8. (WIN) Crear variables de entorno > Variables de usuario para el sistema
   * HOST: localhost 
   * DATABASE: d3o07c0vsctete 
   * DB_USER: opakqpyovgsjhh
   * DB_PASSWORD: 45d346975063932ac81c707af0a912459947bd5ccd57d5148d3fd6186286f887
9. Hacer commit con los cambios
10. En el dashboard de Heroku, ir a Deploy > Manual Deploy > Deploy Branch
11. Probar en Postman:
* Request POST https://spring-security--jwt.herokuapp.com/api/auth/register 
 ```json
{
"username": "srl",
"email": "srl@mail.com",
"password": "123456"
}
```
* Request POST https://spring-security--jwt.herokuapp.com/api/auth/login
 ```json
{
"username": "srl",
"password": "123456"
}
```
* Request GET https://spring-security--jwt.herokuapp.com/api/cars
Authorization > Bearer Token > Copiar token generado en /login 
12. Abrir DBeaver > Crear nueva conexión> PostgreSQL y en la ventana Conectar a base de datos, ingresar host, database, username y password generados en Heroku. Para cambiar el nombre de la conexión, dar click derecho en la conexión > Editar Connection > General > Nombre de la conexión: nuevo nombre

# Frontend: Configuración Vercel
1. En github hacer fork del repositorio https://github.com/alansastre/angular-springboot1
2. En IntelliJ ir a Git > Clone > https://github.com/fortythreesunsets/angular-springboot1
3. Abrir el proyecto clonado en terminal y ejecutar `npm install` para generar la carpeta node_modules
4. Actualizar la url del backend en la variable `const AUTH_API` en src > app > _services
* auth.service.ts: `'https://spring-security--jwt.herokuapp.com/api/auth/'`
* cars.service.ts: `'https://spring-security--jwt.herokuapp.com/api/'`
* hello.service.ts: `'https://spring-security--jwt.herokuapp.com/api/'`
5. Commit proyecto a Github
6. Enlazar Github a Vercel y seleccionar repositorio e importarlo
8. En la pantalla Configure project, abrir Environment variables y agregar los mismos valores que las creadas en Heroku:
```
HOST: 
DATABASE:
DATABASE_URL:
DB_USER:
DB_PASSWORD: 
```
dar click en Deploy y copiar la URL que se genera en el Bean CorsConfigurationSource de la clase SecurityConfig
9. Hacer commit de los cambios
10. Nuevamente hacer deploy del proyecto desde Heroku

## Generar site de documentación del proyecto
1. Agregar `%JAVA_HOME%\bin` a Path en Variables del Sistema
2. Agregar plugin de maven-site a pom.xml en <build>
```
<plugin>
   <groupId>org.apache.maven.plugins</groupId>
   <artifactId>maven-site-plugin</artifactId>
   <version>3.12.1</version>
</plugin>
<plugin>
   <groupId>org.apache.maven.plugins</groupId>
   <artifactId>maven-surefire-plugin</artifactId>
   <version>3.0.0-M7</version>
</plugin>
<!-- Medir cobertura de los test -->
<plugin>
   <groupId>org.jacoco</groupId>
   <artifactId>jacoco-maven-plugin</artifactId>
   <version>0.8.8</version>
   <executions>
      <execution>
         <id>prepare-agent</id>
		 <goals>
            <goal>prepare-agent</goal>
		 </goals>
	  </execution>
	  <execution>
	     <id>report</id>
		 <phase>test</phase>
		 <goals>
		    <goal>report</goal>
		 </goals>
	  </execution>
   </executions>
</plugin>
```
2. Agregar maven-javadoc-plugin, maven-project-info-reports-plugin, maven-surefire-report-plugin y jacoco-maven-plugin debajo de </dependencies>:
```
<reporting>
   <plugins>
      <plugin>
         <groupId>org.apache.maven.plugins</groupId>
         <artifactId>maven-javadoc-plugin</artifactId>
         <version>3.4.0</version>
	  </plugin>
      <plugin>
         <groupId>org.apache.maven.plugins</groupId>
         <artifactId>maven-project-info-reports-plugin</artifactId>
         <version>3.4.0</version>
	  </plugin>
      <plugin>
         <groupId>org.apache.maven.plugins</groupId>
         <artifactId>maven-surefire-report-plugin</artifactId>
         <version>3.0.0-M5</version>
      </plugin>
      <plugin>
         <groupId>org.jacoco</groupId>
         <artifactId>jacoco-maven-plugin</artifactId>
         <version>0.8.8</version>
      </plugin>
      <plugin>
         <groupId>org.jacoco</groupId>
         <artifactId>jacoco-maven-plugin</artifactId>
         <reportSets>
            <reportSet>
               <reports>
                  <!-- Select non-aggregate reports -->
                  <report>report</report>
			   </reports>
			</reportSet>
		 </reportSets>
	  </plugin>
   </plugins>
</reporting>
```
3. En la pestaña Maven de IntelliJ > Lifecycle > doble click en site
4. En la pestaña Project, abrir carpeta target > site > index.html
5. En el site, ir a Project Reports 

## Solución de problemas (WIP)
File > Invalidate Caches... > Invalidate and Restart 
