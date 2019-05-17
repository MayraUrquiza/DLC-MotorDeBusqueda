<%-- 
    Document   : index
    Created on : 11/04/2019, 22:26:24
    Author     : mayur
--%>

<%@page import="Serializacion.VocabularioWriter"%>
<%@page import="Serializacion.VocabularioReader"%>
<%@page import="Vocabulario.Vocabulario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>

<head>
    <title>Buscador Vectorial</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="assets/css/materialize.css" type="text/css">
    <link rel="stylesheet" href="assets/css/main.css" />
    <link href="assets/css/layout.css" rel="stylesheet" type="text/css" />
    <noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
</head>

<body class="is-preload">
    <%
        session = request.getSession(true);
        Vocabulario v = null;
        try
        {
            VocabularioReader vr = new VocabularioReader("C:\\NetBeansProjects\\DLC-MotorDeBusqueda\\vocabulario.dat");
            v = vr.read();
            System.out.println("vocabulario cargado.");
        }
        catch (Exception ex)
        {
            v = new Vocabulario();
            v.agregarCarpeta();
            VocabularioWriter vw = new VocabularioWriter();
            vw.write(v);
            System.out.println("vocabulario cargado.");
        }
        session.setAttribute("vocabulario", v);
    %>
    <div id="wrapper">
        <div id="bg"></div>
        <div id="overlay"></div>
        <div id="main">

            <!-- Header -->
            <header id="header">
                <h1>DLC Buscador Vectorial</h1>
                <jsp:include page="busqueda.jsp"/>
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