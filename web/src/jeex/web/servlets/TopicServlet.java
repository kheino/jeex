package jeex.web.servlets;

import jeex.ejb.beans.TopicManagerLocal;
import jeex.ejb.domain.Topic;

import javax.ejb.EJB;
import javax.ejb.ObjectNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet("/topic")
public class TopicServlet extends HttpServlet {
   @EJB
   private TopicManagerLocal ejb;

   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      response.setContentType("text/html; charset=UTF-8");
      Writer out = response.getWriter();

      out.append("<!doctype html>");
      out.append("<html lang=\"en\">");
      out.append("<title>Topic</title>");

      String idParam = request.getParameter("id");

      try {
         long id = Long.parseLong(idParam);
         Topic topic = ejb.find(id);
         out.append("Topic name: ");
         out.append(topic.getName());
      } catch (NumberFormatException e) {
         out.append(idParam == null ? "Please specify id" : "The specified id is not a valid number");
      } catch (ObjectNotFoundException e) {
         out.append("Topic could not be found");
      }

      out.append("</html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
   }
}
