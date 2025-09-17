package sv.com.editorial.Servlets;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sv.com.editorial.model.Autor;
import sv.com.editorial.util.Conexion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class AutorServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("Inicializando AutorServlet");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action == null) action = "listar";

        switch (action) {
            case "nuevo":
                request.getRequestDispatcher("/autor/formulario.jsp").forward(request, response);
                break;

            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                Autor autor = buscarPorId(idEditar);
                request.setAttribute("autor", autor);
                request.getRequestDispatcher("/autor/formulario.jsp").forward(request, response);
                break;

            case "eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                eliminar(idEliminar);
                response.sendRedirect("AutorServlet?action=listar");
                break;

            case "listar":
            default:
                List<Autor> lista = listar(); // Cambiado de Categoria a Autor
                request.setAttribute("listaAutores", lista);
                request.getRequestDispatcher("/autor/lista.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String nacionalidad = request.getParameter("nacionalidad"); // Nuevo campo

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setNacionalidad(nacionalidad); // Establecer nacionalidad

        if (id == null || id.isEmpty()) {
            insertar(autor);
        } else {
            autor.setId(Integer.parseInt(id));
            actualizar(autor); // Cambiado de Categoria a Autor
        }

        response.sendRedirect("AutorServlet?action=listar");
    }

    private List<Autor> listar() { // Cambiado de Categoria a Autor
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM autor";

        try (Connection con = Conexion.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Autor a = new Autor();
                a.setId(rs.getInt("id"));
                a.setNombre(rs.getString("nombre"));
                a.setNacionalidad(rs.getString("nacionalidad")); // Nuevo campo
                lista.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    private Autor buscarPorId(int id) {
        Autor a = null;
        String sql = "SELECT * FROM autor WHERE id=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                a = new Autor();
                a.setId(rs.getInt("id"));
                a.setNombre(rs.getString("nombre"));
                a.setNacionalidad(rs.getString("nacionalidad")); // Nuevo campo
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    private void insertar(Autor autor) {
        String sql = "INSERT INTO autor (nombre, nacionalidad) VALUES (?, ?)"; // Modificado
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad()); // Nuevo campo
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actualizar(Autor autor) { // Cambiado de Categoria a Autor
        String sql = "UPDATE autor SET nombre=?, nacionalidad=? WHERE id=?"; // Modificado
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad()); // Nuevo campo
            ps.setInt(3, autor.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eliminar(int id) {
        String sql = "DELETE FROM autor WHERE id=?";
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