package isep.lab1.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import isep.lab1.util.CookieUtil;
import isep.lab1.util.HtmlUtil;


@WebServlet(name = "VisitCounterServlet", urlPatterns = {"/VisitCounter"})
public class VisitCounterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static final String COOKIE_NAME = "visits";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int visits = 0;
        String stored = CookieUtil.get(req, COOKIE_NAME);
        if (stored != null) {
            try {
                visits = Integer.parseInt(stored.trim());
            } catch (NumberFormatException ignored) {
                visits = 0;   // tampered cookie: start again
            }
        }
        visits++;
        CookieUtil.add(req, resp, COOKIE_NAME, String.valueOf(visits), CookieUtil.ONE_YEAR);

        String ctx = req.getContextPath();
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(HtmlUtil.begin(ctx, "Visit counter (cookie)"));
        if (visits == 1) {
            out.println("<p class=\"big\">This is your first visit.</p>");
        } else {
            out.println("<p class=\"big\">You have visited this page <strong>" + visits + "</strong> times.</p>");
        }
        out.println("<p><a href=\"" + ctx + "/VisitCounter\">Reload</a></p>");
        out.print(HtmlUtil.end(ctx));
    }
}
