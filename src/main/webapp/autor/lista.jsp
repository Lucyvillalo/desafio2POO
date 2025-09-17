
<%@ page import="sv.com.editorial.model.Autor" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%--

  Created by IntelliJ IDEA.
  User: lucia
  Date: 13/09/2025
  Time: 06:49 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <title>Autor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<h2>Lista de Autores</h2>
<a href="AutorServlet?action=nuevo" class="btn btn-primary">Nuevo Autor</a>
<table class="table table-striped mt-3">
    <thead>
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Nacionalidad</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Autor> autores = (List<Autor>) request.getAttribute("listaAutores");
        if (autores != null) {
            for (Autor c : autores) {
    %>
    <tr>
        <td><%= c.getId() %></td>
        <td><%= c.getNombre() %></td>
        <td><%= c.getNacionalidad() %></td>
        <td>
            <a href="AutorServlet?action=editar&id=<%= c.getId() %>"
               class="btn btn-warning btn-sm">Editar</a>

            <a href="AutorServlet?action=eliminar&id=<%= c.getId() %>"
               class="btn btn-danger btn-sm"
               onclick="return confirm('¿Estás seguro de que deseas eliminar este autor?');">Eliminar</a>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
</body>
</html>
