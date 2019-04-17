<%-- 
    Document   : header
    Created on : 11/04/2019, 22:38:05
    Author     : mayur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <form class="container" action="ControladorPaginaPrincipal" method="get">
            <div class="valign-wrapper">
                <input  id="input" type="text" name="busqueda" placeholder="Ingrese criterio a buscar." style="color: white">
                <button id="button" type="submit" class="btn-floating btn-large waves-effect waves-light">
                    <i class="material-icons">search</i>
                </button>
            </div>
        </form>
    </body>
</html>
