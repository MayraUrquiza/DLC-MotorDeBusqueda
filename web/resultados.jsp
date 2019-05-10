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
        <link rel="stylesheet" href="css/materialize.css" type="text/css">
        <link href="css/layout.css" rel="stylesheet" type="text/css" />
    </head>
    <body id="content">
        <header id="header">
            <jsp:include page="busqueda.jsp"/>
        </header>

        <c:forEach items="${documentos}" var="doc">
            <tr>
                <td><a href="">${doc}</a><br></td>
            </tr>
        </c:forEach>
        
        <footer>
            <jsp:include page="footer.jsp"/>
        </footer>
    </body>
</html>
