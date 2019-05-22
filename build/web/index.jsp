<%-- 
    Document   : index
    Created on : 11/04/2019, 22:26:24
    Author     : mayur
--%>

<%@page import="java.io.File"%>
<%@page import="BD.BD"%>
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
            File f = new File("C:\\NetBeansProjects\\DLC-MotorDeBusqueda\\posteo.txt");
            try
            {
                f.delete();
            }
            catch(Exception ex1)
            {
                ex1.printStackTrace();
            }
            v.agregarCarpeta();
            VocabularioWriter vw = new VocabularioWriter("C:\\NetBeansProjects\\DLC-MotorDeBusqueda\\vocabulario.dat");
            vw.write(v);
            System.out.println("vocabulario cargado.");
            
            BD posteo = new BD();
            try
            {
                posteo.openConnection();
                posteo.loadData("C:\\\\NetBeansProjects\\\\DLC-MotorDeBusqueda\\\\posteo.txt", "palabraXDocumento");
                System.out.println("Posteo cargado correctamente.");
            }
            catch (Exception ex2)
            {
                ex2.printStackTrace();
            }
            finally
            {
                posteo.closeConnection();
            }
        }
        session.setAttribute("vocabulario", v);
    %>
    <jsp:include page="principal.jsp"/>
</body>

</html>