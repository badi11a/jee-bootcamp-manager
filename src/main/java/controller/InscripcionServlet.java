package controller;

import dao.InscripcionDAO;
import model.Inscripcion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/inscripciones")
public class InscripcionServlet extends HttpServlet {
    private InscripcionDAO inscripcionDAO = new InscripcionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("estudianteId");
        if (idStr != null) {
            int estudianteId = Integer.parseInt(idStr);
            try {
                List<Inscripcion> inscripciones = inscripcionDAO.listarPorEstudiante(estudianteId);
                request.setAttribute("inscripciones", inscripciones);
                request.getRequestDispatcher("inscripciones.jsp").forward(request, response);
            } catch (SQLException ex) {
                throw new ServletException("Error de base de datos", ex);
            }
        } else {
            response.sendRedirect("estudiantes");
        }
    }
}
