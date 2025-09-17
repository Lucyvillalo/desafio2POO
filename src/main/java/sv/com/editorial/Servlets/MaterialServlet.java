package sv.com.editorial.Servlets;

import java.io.*;
import java.sql.*;
import java.util.*;
import sv.com.editorial.model.*;
import sv.com.editorial.util.Conexion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class MaterialServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action == null) action = "listar";

        switch (action) {
            case "nuevo":
                request.setAttribute("categorias", listarCategorias());
                request.setAttribute("autores", listarAutores());
                request.getRequestDispatcher("/material/formulario.jsp").forward(request, response);
                break;

            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                Material material = buscarPorId(idEditar);
                request.setAttribute("material", material);
                request.setAttribute("categorias", listarCategorias());
                request.setAttribute("autores", listarAutores());
                request.getRequestDispatcher("/material/formulario.jsp").forward(request, response);
                break;

            case "eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                eliminar(idEliminar);
                response.sendRedirect("MaterialServlet?action=listar");
                break;

            case "listar":
            default:
                List<Material> lista = listar();
                request.setAttribute("listaMateriales", lista);
                request.getRequestDispatcher("/material/lista.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");  // <- capturamos action del form
        String id = request.getParameter("id");
        String titulo = request.getParameter("titulo");
        String tipo = request.getParameter("tipo");
        int categoriaId = Integer.parseInt(request.getParameter("categoriaId"));
        int autorId = Integer.parseInt(request.getParameter("autorId"));

        Material material = new Material();
        material.setTitulo(titulo);
        material.setTipo(tipo);
        material.setIdCategoria(categoriaId);
        material.setIdAutor(autorId);

        if ("insertar".equals(action)) {
            insertar(material);
        } else if ("actualizar".equals(action) && id != null && !id.isEmpty()) {
            material.setId(Integer.parseInt(id));
            actualizar(material);
        }

        response.sendRedirect("MaterialServlet?action=listar");
    }

    private List<Material> listar() {
        List<Material> lista = new ArrayList<>();
        String sql = "SELECT m.id, m.titulo, m.tipo, c.nombre AS categoria, a.nombre AS autor " +
                "FROM material m " +
                "JOIN categoria c ON m.id_categoria = c.id " +
                "JOIN autor a ON m.id_autor = a.id";

        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Material m = new Material();
                m.setId(rs.getInt("id"));
                m.setTitulo(rs.getString("titulo"));
                m.setTipo(rs.getString("tipo"));
                m.setNombreCategoria(rs.getString("categoria"));
                m.setNombreAutor(rs.getString("autor"));
                lista.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    private Material buscarPorId(int id) {
        Material m = null;
        String sql = "SELECT * FROM material WHERE id=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                m = new Material();
                m.setId(rs.getInt("id"));
                m.setTitulo(rs.getString("titulo"));
                m.setTipo(rs.getString("tipo"));
                m.setIdCategoria(rs.getInt("id_categoria"));
                m.setIdAutor(rs.getInt("id_autor"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    private void insertar(Material m) {
        String sql = "INSERT INTO material (titulo, tipo, id_categoria, id_autor) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getTitulo());
            ps.setString(2, m.getTipo());
            ps.setInt(3, m.getIdCategoria());
            ps.setInt(4, m.getIdAutor());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actualizar(Material m) {
        String sql = "UPDATE material SET titulo=?, tipo=?, id_categoria=?, id_autor=? WHERE id=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getTitulo());
            ps.setString(2, m.getTipo());
            ps.setInt(3, m.getIdCategoria());
            ps.setInt(4, m.getIdAutor());
            ps.setInt(5, m.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eliminar(int id) {
        String sql = "DELETE FROM material WHERE id=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Categoria> listarCategorias() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria";
        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<Autor> listarAutores() {
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM autor";
        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Autor a = new Autor();
                a.setId(rs.getInt("id"));
                a.setNombre(rs.getString("nombre"));
                lista.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
