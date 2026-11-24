package isep.lab1.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import isep.lab1.util.CookieUtil;
import isep.lab1.util.HtmlUtil;

@WebServlet(name = "DeleteCookies", urlPatterns = {"/DeleteCookies"})
public class DeleteCookies extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        for (String[] c : ReadCookie.COOKIES) {
            CookieUtil.delete(req, resp, c[0]);
        }
        CookieUtil.delete(req, resp, VisitCounterServlet.COOKIE_NAME);

        String ctx = req.getContextPath();
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(HtmlUtil.begin(ctx, "Cookies destroyed"));
        out.println("<p>The cookies were deleted. The browser removes them as soon as it receives this response.</p>");
        out.println("<p><a href=\"" + ctx + "/ReadCookie\">Check with ReadCookie</a></p>");
        out.print(HtmlUtil.end(ctx));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
