<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="sv.com.editorial.model.Material" %>

<html>
<head>
    <title>Lista de Materiales</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Lista de Materiales</h2>
    <a href="MaterialServlet?action=nuevo" class="btn btn-primary">Nuevo Material</a>
    <table class="table table-striped mt-3">
        <thead>
        <tr>
            <th>ID</th>
            <th>Título</th>
            <th>Tipo</th>
            <th>Categoría</th>
            <th>Autor</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Material> materiales = (List<Material>) request.getAttribute("listaMateriales");
            if (materiales != null) {
                for (Material m : materiales) {
        %>
        <tr>
            <td><%= m.getId() %></td>
            <td><%= m.getTitulo() %></td>
            <td><%= m.getTipo() %></td>
            <td><%= m.getNombreCategoria() %></td>
            <td><%= m.getNombreAutor() %></td>
            <td>
                <a href="MaterialServlet?action=editar&id=<%= m.getId() %>" class="btn btn-warning btn-sm">Editar</a>
                <a href="MaterialServlet?action=eliminar&id=<%= m.getId() %>" class="btn btn-danger btn-sm"
                   onclick="return confirm('¿Seguro que deseas eliminar este material?');">Eliminar</a>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
