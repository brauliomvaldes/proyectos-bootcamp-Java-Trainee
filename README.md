"# Repo-Braulio-Valdes"

Examen Final Módulo 7

- Para los proyectos springboot maven.
- Para todas las consultas a la base de datos se empleó jdbctemplate y no jpa porque el tipo de consultas retornaba datos que provenian de más de una tablas y crei que lo más flexible para este tipo de consultas era utilizar jdbctemplate.

Sobre el proyecto fase 1:
- Se incluyen un archivo con los  5 scripts sql requeridos.
- El ejemplo dado para la pregunta 3 no refleja exactamente la consultas solicitada porque el resultado muestra datos de más de una sucursal.

Sobre el proyecto fase 2:
- jre 17
- dependencias: jpa, jdbc, mysql-connector-j, test, lombok.
- Es un proyecto spring empaquetado jar, que descativa los logs de spring y desde main llama a una clase que hace de controladora o menu
- Las clases DTO sólo contienen los campos que representan las respuestas a las consultas a la base de datos.
- Los algoritmos consultan a la base de datos.
- En el primer algoritmo se indica lo siguiente "Se deberá pedir al usuario que entregue por consola el número de la sucursal y el id de producto para re éste cálculo" pero en la imagen de ejemplo, se ve que sólo se requiere el id de la sucursal y se despliega un listado de los "productos" que no cuentan con existencias suficientes de acuerdo a los requerimientos, entonces, en mi algoritmo se implementa solicitar solamente el id de la sucursal. Si fuera el caso de incluir el id del producto entonces el listado ejemplo sólo mostraría a lo más 1 item si fuera es caso de no contar con stock suficiente.
- creación de prueba unitaria con Junit para método validación de fecha para el segundo algoritmo que emplea fecha.

Sobre el proyecto fase 3:
- jre 21
- dependencias: thymeleaf, web, devtools, test, jakarta.servlet.jsp.jstl-api, jakarta.servlet.jsp.jstl, tomcat-embed-jasper, jdbc, mysql-connector-j, lombok.
- Es un proyecto spring empaquetado jar, que emplea vistas jsp y el motor de plantillas jstl.
- Se solicita un listado que indica "distintas atenciones que debe realizarse en un dia", lo que me indica que debo emplear el campo "fecha_proxima_revision" y esto significa que se lista las atenciones futuras o por realizar.
- Las clases DTO sólo contienen los campos que representan las respuestas a las consultas a la base de datos.
- La uri en el navegador es: http://localhost:8080/listado/atenciones
- La uri para consumir api rest (postman) es: http://localhost:8080/listado/api/atenciones-sucursal?idSucursal=1&idTipoAtencion=1
- Para consumir el endpoint api rest para listar las citas, se debe incluir 2 parámetros, el id de sucursal y el id del tipo de atención, esto muestra los registros que hacen match pero si se necesita listar todas las citas, se debe dejar en cero (0) ambos parámetros y entonces se buscarán todas las citas almacenadas.
- El json que entrega la api contine sólo los datos que se muestran en la vista y corresponde a un objeto dto.
- creación de prueba unitaria con Junit para servicio listar sucursales, no se realizó a otro servicio porque sería repetir el mismo procedimiento.





