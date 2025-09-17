package sv.com.editorial.Servlets;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sv.com.editorial.model.Categoria;
import sv.com.editorial.util.Conexion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;


public class CategoriaServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("Inicializando CategoriaServlet");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action == null) action = "listar";

        switch (action) {
            case "nuevo":
                request.getRequestDispatcher("/categoria/formulario.jsp").forward(request, response);
                break;

            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                Categoria categoria = buscarPorId(idEditar);
                request.setAttribute("categoria", categoria);
                request.getRequestDispatcher("/categoria/formulario.jsp").forward(request, response);
                break;

            case "eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                eliminar(idEliminar);
                response.sendRedirect("CategoriaServlet?action=listar");
                break;

            case "listar":
            default:
                List<Categoria> lista = listar();
                request.setAttribute("listaCategorias", lista);
                request.getRequestDispatcher("/categoria/lista.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String nombre = request.getParameter("nombre");

        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);

        if (id == null || id.isEmpty()) {
            insertar(categoria);
        } else {
            categoria.setId(Integer.parseInt(id));
            actualizar(categoria);
        }

        response.sendRedirect("CategoriaServlet?action=listar");
    }

    private List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria";

        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) { //id = 1 nombre = Terror
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

    private Categoria buscarPorId(int id) {
        Categoria c = null;
        String sql = "SELECT * FROM categoria WHERE id=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    private void insertar(Categoria categoria) {
        String sql = "INSERT INTO categoria (nombre) VALUES (?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, categoria.getNombre());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actualizar(Categoria categoria) {
        String sql = "UPDATE categoria SET nombre=? WHERE id=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, categoria.getNombre());
            ps.setInt(2, categoria.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eliminar(int id) {
        String sql = "DELETE FROM categoria WHERE id=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}