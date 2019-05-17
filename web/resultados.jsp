<%-- 
    Document   : index
    Created on : 28/03/2019, 18:04:14
    Author     : mayur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Resultados de la búsqueda</title>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="assets/css/materialize.css" type="text/css">
        <link href="assets/css/layout.css" rel="stylesheet" type="text/css" />
    <noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
    </head>
    <body id="content">
        <header id="header">
            <jsp:include page="busqueda.jsp"/>
        </header>

        <main>
            <table>
                <thead>
                    <tr>
                        <th>DOCUMENTO</th>
                        <th>PESO</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach items="${documentos}" var="doc">
                        <tr>
                            <td><a style="color: white; font-weight: bold" href="CtrlDocumentoView?documento=${doc.nombre}">${doc.nombre}</a></td>
                            <td style="color: white; font-weight: bold">${doc.peso}</td>
                        </tr>
                    </c:forEach>
                        <tr>
                            <td><br></td>
                            <td><br></td>
                        </tr>
                </tbody>
            </table>
        </main>
        
        <footer id="footer">
            <span style="color: white" class="copyright">&copy; DLC 2019 UTN FRC</a>.</span>
        </footer>
    </body>
</html>
