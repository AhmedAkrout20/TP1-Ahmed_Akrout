package isep.lab1.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import isep.lab1.util.CookieUtil;
import isep.lab1.util.HtmlUtil;

@WebServlet(name = "ReadCookie", urlPatterns = {"/ReadCookie"})
public class ReadCookie extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static final String[][] COOKIES = {
            {"firstName", "First name"},
            {"lastName", "Last name"},
            {"birthDay", "Birth date"},
            {"sex", "Sex"}
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String ctx = req.getContextPath();
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(HtmlUtil.begin(ctx, "Cookies read from your browser"));
        out.println("<table>");
        boolean any = false;
        for (String[] c : COOKIES) {
            String v = CookieUtil.get(req, c[0]);
            any |= v != null;
            out.println("<tr><th>" + c[1] + " <small>(" + c[0] + ")</small></th><td>"
                    + (v == null ? "<em>not set</em>" : HtmlUtil.escape(v)) + "</td></tr>");
        }
        out.println("</table>");
        if (!any) {
            out.println("<p>No cookie found. Submit the <a href=\"" + ctx + "/form.html\">form</a> first.</p>");
        }
        out.println("<p><a href=\"" + ctx + "/DeleteCookies\">Destroy these cookies</a> &middot; "
                + "<a href=\"" + ctx + "/form.html\">Back to the form</a></p>");
        out.print(HtmlUtil.end(ctx));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
