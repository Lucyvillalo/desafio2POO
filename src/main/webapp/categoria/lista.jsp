<%--
  Created by IntelliJ IDEA.
  User: edalb
  Date: 10/9/2025
  Time: 10:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="sv.com.editorial.model.Categoria" %>
<html>
<head>
    <title>Categoria</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<h2>Lista de Categorías</h2>
<a href="CategoriaServlet?action=nuevo" class="btn btn-primary">Nueva Categoría</a>
<table class="table table-striped mt-3">
    <thead>
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Categoria> categorias = (List<Categoria>) request.getAttribute("listaCategorias");
        if (categorias != null) {
            for (Categoria c : categorias) {
    %>
    <tr>
        <td><%= c.getId() %></td>
        <td><%= c.getNombre() %></td>
        <td>
            <a href="CategoriaServlet?action=editar&id=<%= c.getId() %>"
               class="btn btn-warning btn-sm">Editar</a>

            <a href="CategoriaServlet?action=eliminar&id=<%= c.getId() %>"
               class="btn btn-danger btn-sm"
               onclick="return confirm('¿Estás seguro de que deseas eliminar esta categoría?');">
                Eliminar
            </a>
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
