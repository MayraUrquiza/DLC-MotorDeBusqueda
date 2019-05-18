<%-- 
    Document   : agregarDoc
    Created on : 17/05/2019, 21:13:28
    Author     : mayur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuevo documento</title>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="assets/css/materialize.css" type="text/css">
        <link href="assets/css/layout.css" rel="stylesheet" type="text/css" />
        <noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
    </head>
    
    <body class="valign-wrapper" id="view">
        <div class="container">
            <form action="CtrlAgregarDoc" method="post">
                <input id="input" type="text" name="nombre" placeholder="Ingrese el nombre del documento." style="color: white">
                <textarea id="textarea" class="materialize-textarea" type="text" name="contenido" placeholder="Ingrese el contenido del documento." style="color: white"></textarea>
                <div id="botones">
                    <button type="submit" class="btn btn-large waves-effect waves-light">Guardar</button>
                    <a href="principal.jsp" class="btn btn-large waves-effect waves-light">Volver</a>
                </div>
            </form>
        </div>
    </body>
</html>
