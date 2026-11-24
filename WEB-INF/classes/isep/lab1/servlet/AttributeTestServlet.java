package isep.lab1.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import isep.lab1.util.HtmlUtil;

@WebServlet(name = "AttributeTestServlet", urlPatterns = {"/AttributeTest"})
public class AttributeTestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletContext app = getServletContext();
        HttpSession session = req.getSession(true);

        app.setAttribute("demoApp", "v1");
        app.setAttribute("demoApp", "v2");
        app.removeAttribute("demoApp");

        session.setAttribute("demoSession", "v1");
        session.setAttribute("demoSession", "v2");
        session.removeAttribute("demoSession");

        req.setAttribute("demoRequest", "v1");
        req.setAttribute("demoRequest", "v2");
        req.removeAttribute("demoRequest");

        String ctx = req.getContextPath();
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(HtmlUtil.begin(ctx, "Attribute events"));
        out.println("<p>For each scope (application, session, request) one attribute was <strong>added</strong>, "
                + "<strong>replaced</strong> and <strong>removed</strong>.</p>");
        out.println("<p>Look at the Tomcat console: you should see three events per scope, "
                + "printed by the matching listener.</p>");
        out.println("<table>");
        out.println("<tr><th>Scope</th><th>Listener</th><th>Lives as long as</th></tr>");
        out.println("<tr><td>application</td><td>ApplicationAttributeListener</td><td>the web application is deployed (shared by all users)</td></tr>");
        out.println("<tr><td>session</td><td>SessionAttributeListener</td><td>the user's session (one user)</td></tr>");
        out.println("<tr><td>request</td><td>RequestAttributeListener</td><td>one HTTP request (until the response is sent)</td></tr>");
        out.println("</table>");
        out.print(HtmlUtil.end(ctx));
    }
}
