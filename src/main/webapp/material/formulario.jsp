<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="sv.com.editorial.model.Categoria" %>
<%@ page import="sv.com.editorial.model.Autor" %>
<%@ page import="sv.com.editorial.model.Material" %>

<html>
<head>
    <title>Formulario de Material</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2><%= (request.getAttribute("material") != null) ? "Editar Material" : "Nuevo Material" %></h2>

    <form action="MaterialServlet" method="post">
        <!-- Campo oculto para id (solo se llena al editar) -->
        <input type="hidden" name="id"
               value="<%= (request.getAttribute("material") != null) ? ((Material) request.getAttribute("material")).getId() : "" %>">

        <!-- Campo oculto para enviar la acción -->
        <input type="hidden" name="action" value="<%= (request.getAttribute("material") != null) ? "actualizar" : "insertar" %>">

        <div class="mb-3">
            <label for="titulo" class="form-label">Título</label>
            <input type="text" class="form-control" id="titulo" name="titulo"
                   value="<%= (request.getAttribute("material") != null) ? ((Material) request.getAttribute("material")).getTitulo() : "" %>"
                   required>
        </div>

        <div class="mb-3">
            <label for="tipo" class="form-label">Tipo</label>
            <select class="form-select" id="tipo" name="tipo" required>
                <option value="Libro" <%= (request.getAttribute("material") != null && "Libro".equals(((Material) request.getAttribute("material")).getTipo())) ? "selected" : "" %>>Libro</option>
                <option value="Revista" <%= (request.getAttribute("material") != null && "Revista".equals(((Material) request.getAttribute("material")).getTipo())) ? "selected" : "" %>>Revista</option>
                <option value="Articulo" <%= (request.getAttribute("material") != null && "Articulo".equals(((Material) request.getAttribute("material")).getTipo())) ? "selected" : "" %>>Artículo</option>
            </select>
        </div>

        <div class="mb-3">
            <label for="categoria" class="form-label">Categoría</label>
            <select class="form-select" id="categoria" name="categoriaId" required>
                <%
                    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
                    int categoriaSeleccionada = (request.getAttribute("material") != null) ? ((Material) request.getAttribute("material")).getIdCategoria() : -1;
                    for (Categoria c : categorias) {
                %>
                <option value="<%= c.getId() %>" <%= (c.getId() == categoriaSeleccionada) ? "selected" : "" %>>
                    <%= c.getNombre() %>
                </option>
                <% } %>
            </select>
        </div>

        <div class="mb-3">
            <label for="autor" class="form-label">Autor</label>
            <select class="form-select" id="autor" name="autorId" required>
                <%
                    List<Autor> autores = (List<Autor>) request.getAttribute("autores");
                    int autorSeleccionado = (request.getAttribute("material") != null) ? ((Material) request.getAttribute("material")).getIdAutor() : -1;
                    for (Autor a : autores) {
                %>
                <option value="<%= a.getId() %>" <%= (a.getId() == autorSeleccionado) ? "selected" : "" %>>
                    <%= a.getNombre() %>
                </option>
                <% } %>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Guardar</button>
        <a href="MaterialServlet?action=listar" class="btn btn-secondary">Cancelar</a>
    </form>
</div>
</body>
</html>
