<%@ page import="sv.com.editorial.model.Categoria" %>
<%--
  Created by IntelliJ IDEA.
  User: edalb
  Date: 10/9/2025
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Categoria categoria = (Categoria) request.getAttribute("categoria");
    if (categoria == null) {
        categoria = new Categoria();
    }
%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<h2><%= categoria.getId() == 0 ? "Nueva Categoría" : "Editar Categoría" %></h2>

<form action="CategoriaServlet" method="post">
    <input type="hidden" name="id" value="<%= categoria.getId() == 0 ? "" : categoria.getId() %>"/>

    <div class="mb-3">
        <label for="nombre" class="form-label">Nombre</label>
        <input type="text" id="nombre" name="nombre" class="form-control" value="<%= categoria.getNombre() == null ? "" : categoria.getNombre() %>" required/>
    </div>

    <button type="submit" class="btn btn-success">Guardar</button>
    <a href="CategoriaServlet?action=listar" class="btn btn-secondary">Cancelar</a>
</form>