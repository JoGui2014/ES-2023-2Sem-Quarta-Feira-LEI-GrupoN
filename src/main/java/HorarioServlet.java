import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HorarioServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("fileName");

        // Create a new instance of Horario and load the schedule
        Horario horario = new Horario(fileName);

        // Perform any operations on the horario object as needed
        // For example:
        // horario.addAula(new Aula(...)); // Add a class to the schedule
        // horario.removeAula("Aula Name"); // Remove a class from the schedule

        // Set the horario object as an attribute in the request
        request.setAttribute("horario", horario);

        // Forward the request to a JSP file to display the schedule
        request.getRequestDispatcher("src/main/jsps/horario.jsp").forward(request, response);
    }
}