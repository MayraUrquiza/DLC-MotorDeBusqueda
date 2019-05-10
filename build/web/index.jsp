<%-- 
    Document   : index
    Created on : 11/04/2019, 22:26:24
    Author     : mayur
--%>

<%@page import="Serializacion.VocabularioWriter"%>
<%@page import="Serializacion.VocabularioReader"%>
<%@page import="Vocabulario.Vocabulario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Buscador</title>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="css/materialize.css" type="text/css">
        <link href="css/layout.css" rel="stylesheet" type="text/css" />
    </head>
    <body id="content">
        <%
            session = request.getSession(true);
            Vocabulario v;
            try
            {
                VocabularioReader vr = new VocabularioReader("vocabulario.dat");
                v = vr.read();
                System.out.println("vocabulario cargado.");
            }
            catch (Exception ex)
            {
                v = new Vocabulario();
                v.agregarCarpeta();
                VocabularioWriter vw = new VocabularioWriter();
                vw.write(v);
            }
            session.setAttribute("vocabulario", v);
        %>
        <div id="search">
            <jsp:include page="busqueda.jsp"/>
        </div>
        
        <footer>
            <jsp:include page="footer.jsp"/>
        </footer>
    </body>
</html>
