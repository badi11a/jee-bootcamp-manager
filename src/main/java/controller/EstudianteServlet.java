package controller;

import dao.EstudianteDAO;
import model.Estudiante;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/estudiantes")
public class EstudianteServlet extends HttpServlet {
    private EstudianteDAO estudianteDAO = new EstudianteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "listar";
        try {
            switch (action) {
                case "nuevo":
                    Estudiante nuevoEstudiante = new Estudiante();
                    nuevoEstudiante.setActivo(true);
                    request.setAttribute("estudiante", nuevoEstudiante);
                    request.getRequestDispatcher("estudiante-form.jsp").forward(request, response);
                    break;
                case "editar":
                    int idEditar = Integer.parseInt(request.getParameter("id"));
                    Estudiante estudianteEditar = estudianteDAO.obtenerPorId(idEditar);
                    request.setAttribute("estudiante", estudianteEditar);
                    request.getRequestDispatcher("estudiante-form.jsp").forward(request, response);
                    break;
                case "eliminar":
                    int idEliminar = Integer.parseInt(request.getParameter("id"));
                    boolean eliminado = estudianteDAO.eliminar(idEliminar);
                    if (!eliminado) {
                        request.getSession().setAttribute("error",
                                "No se puede eliminar: el estudiante tiene inscripciones asociadas.");
                    }
                    response.sendRedirect("estudiantes");
                    break;
                default:
                    List<Estudiante> estudiantes = estudianteDAO.listarTodos();
                    request.setAttribute("estudiantes", estudiantes);
                    Object error = request.getSession().getAttribute("error");
                    if (error != null) {
                        request.setAttribute("error", error);
                        request.getSession().removeAttribute("error");
                    }
                    request.getRequestDispatcher("estudiantes.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException("Error de base de datos", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String rut = request.getParameter("rut");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        boolean activo = request.getParameter("activo") != null;

        try {
            boolean guardado;
            if (idStr == null || idStr.isEmpty()) {
                // Insertar
                Estudiante nuevo = new Estudiante();
                nuevo.setRut(rut);
                nuevo.setNombre(nombre);
                nuevo.setEmail(email);
                nuevo.setActivo(activo);
                guardado = estudianteDAO.insertar(nuevo);
            } else {
                // Actualizar
                Estudiante existente = new Estudiante();
                existente.setId(Integer.parseInt(idStr));
                existente.setRut(rut);
                existente.setNombre(nombre);
                existente.setEmail(email);
                existente.setActivo(activo);
                guardado = estudianteDAO.actualizar(existente);
            }
            if (!guardado) {
                request.getSession().setAttribute("error", "No se pudo guardar: ya existe un estudiante con ese RUT.");
            }
        } catch (SQLException ex) {
            throw new ServletException("Error de base de datos", ex);
        }
        response.sendRedirect("estudiantes");
    }
}
