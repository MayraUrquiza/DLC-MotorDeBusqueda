<%-- 
    Document   : error
    Created on : 09/05/2019, 23:52:58
    Author     : mayur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Resultados de la búsqueda</title>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="assets/css/materialize.css" type="text/css">
        <link href="assets/css/layout.css" rel="stylesheet" type="text/css" />
    </head>
    <body id="content">
        <header id="header">
            <jsp:include page="busqueda.jsp"/>
        </header>

        <main>
            <h5 class="center" style="color: white">No se encontraron resultados para "${busqueda}"</h5>
        </main>
        
        <footer id="footer">
            <span style="color: white" class="copyright">&copy; DLC 2019 UTN FRC</a>.</span>
        </footer>
    </body>
</html>