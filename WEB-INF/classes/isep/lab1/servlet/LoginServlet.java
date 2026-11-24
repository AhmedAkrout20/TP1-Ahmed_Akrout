package isep.lab1.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import isep.lab1.listener.SessionUser;
import isep.lab1.util.HtmlUtil;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        username = username == null ? "" : username.trim();
        String ctx = req.getContextPath();

        if (username.isEmpty()) {
            resp.sendRedirect(ctx + "/login.html");
            return;
        }

        // creates the session if needed, otherwise returns the existing one
        HttpSession session = req.getSession(true);
        session.setAttribute("user", username);
        session.setAttribute("sessionUser", new SessionUser(username));

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(HtmlUtil.begin(ctx, "Login successful"));
        out.println("<p class=\"msg\">You are now logged in as <strong>" + HtmlUtil.escape(username) + "</strong>.</p>");
        out.println("<p><a href=\"" + ctx + "/items.html\">Go to the items page (items.html)</a></p>");
        out.println("<p><small>Session tests: "
                + "<a href=\"" + ctx + "/SessionAction\">replace / remove attributes, invalidate the session</a></small></p>");
        out.print(HtmlUtil.end(ctx));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
}
