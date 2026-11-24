package isep.lab1.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import isep.lab1.util.HtmlUtil;

@WebServlet(name = "SessionActionServlet", urlPatterns = {"/SessionAction"})
public class SessionActionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String ctx = req.getContextPath();
        String action = req.getParameter("action");
        String result;

        if (action == null) {
            result = "Choose an action below.";
        } else {
            HttpSession session = req.getSession(false);
            if (session == null) {
                result = "There is no session. <a href=\"" + ctx + "/login.html\">Log in</a> first.";
            } else {
                switch (action) {
                    case "invalidate":
                        session.invalidate();
                        result = "The session was invalidated. /ListItems is protected again.";
                        break;
                    case "replaceUser":
                        String value = req.getParameter("value");
                        session.setAttribute("user", value == null || value.isEmpty() ? "renamed-user" : value);
                        result = "Attribute <code>user</code> replaced (attributeReplaced event).";
                        break;
                    case "removeUser":
                        session.removeAttribute("user");
                        result = "Attribute <code>user</code> removed (attributeRemoved event).";
                        break;
                    case "removeSessionUser":
                        session.removeAttribute("sessionUser");
                        result = "Attribute <code>sessionUser</code> removed (valueUnbound event).";
                        break;
                    default:
                        result = "Unknown action.";
                }
            }
        }

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(HtmlUtil.begin(ctx, "Session actions"));
        out.println("<p class=\"msg\">" + result + "</p>");
        out.println("<p>Watch the Tomcat console after each click.</p>");
        out.println("<ul>");
        out.println("<li><a href=\"" + ctx + "/SessionAction?action=replaceUser&amp;value=bob\">Replace the attribute user</a></li>");
        out.println("<li><a href=\"" + ctx + "/SessionAction?action=removeUser\">Remove the attribute user</a></li>");
        out.println("<li><a href=\"" + ctx + "/SessionAction?action=removeSessionUser\">Remove the SessionUser object</a></li>");
        out.println("<li><a href=\"" + ctx + "/SessionAction?action=invalidate\">Invalidate the session</a></li>");
        out.println("<li><a href=\"" + ctx + "/ListItems\">Try to access /ListItems</a></li>");
        out.println("</ul>");
        out.print(HtmlUtil.end(ctx));
    }
}
