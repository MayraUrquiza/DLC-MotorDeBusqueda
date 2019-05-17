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
        <form action="CtrlBusqueda" method="get">
            <div id="busqueda">
                <div class="container">
                    <a href="principal.jsp"><h5 style="color: white; font-weight: bold">DLC Buscador Vectorial</h5></a>
                    <section class="valign-wrapper">
                        <input id="input" type="text" name="busqueda" placeholder="Ingrese criterio a buscar." style="color: white;">
                        <button id="button" type="submit" class="btn-floating btn-large waves-effect waves-light">
                            <i class="material-icons">search</i>
                        </button>
                    </section>
                </div>
            </div>
        </form>
    </body>
</html>
