<%@ page import="sv.com.editorial.model.Autor" %>
<%--
  Created by IntelliJ IDEA.
  User: edalb
  Date: 10/9/2025
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Formulario de Autor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>${autor.id != null ? 'Editar' : 'Nuevo'} Autor</h2>

    <form action="AutorServlet" method="post">
        <input type="hidden" name="id" value="${autor.id}">

        <div class="mb-3">
            <label for="nombre" class="form-label">Nombre</label>
            <input type="text" class="form-control" id="nombre" name="nombre"
                   value="${autor.nombre}" required>
        </div>

        <div class="mb-3">
            <label for="nacionalidad" class="form-label">Nacionalidad</label>
            <input type="text" class="form-control" id="nacionalidad" name="nacionalidad"
                   value="${autor.nacionalidad}" required>
        </div>

        <button type="submit" class="btn btn-primary">Guardar</button>
        <a href="AutorServlet?action=listar" class="btn btn-secondary">Cancelar</a>
    </form>
</div>
</body>
</html>