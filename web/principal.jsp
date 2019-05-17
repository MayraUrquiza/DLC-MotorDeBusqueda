<%-- 
    Document   : principal
    Created on : 17/05/2019, 16:37:22
    Author     : mayur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Buscador Vectorial</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="assets/css/materialize.css" type="text/css">
        <link rel="stylesheet" href="assets/css/main.css" />
        <noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
    </head>
    <body>
        <div id="wrapper">
        <div id="bg"></div>
        <div id="overlay"></div>
            <div id="main">

                <!-- Header -->
                <header id="header">
                    <h1>DLC Buscador Vectorial</h1>
                    <form action="CtrlBusqueda" method="get">
                        <input id="input" type="text" name="busqueda" placeholder="Ingrese criterio a buscar." style="color: white;">
                        <button id="button" type="submit" class="btn-floating btn-large waves-effect waves-light">
                            <i class="material-icons">search</i>
                        </button>
                    </form>
                </header>

                <!-- Footer -->
                <footer id="footer">
                    <span class="copyright">&copy; DLC 2019 UTN FRC</a>.</span>
                </footer>

            </div>
        </div>
        <script>
            window.onload = function() {
                document.body.classList.remove('is-preload');
            }
            window.ontouchmove = function() {
                return false;
            }
            window.onorientationchange = function() {
                document.body.scrollTop = 0;
            }
        </script>
    </body>
</html>
