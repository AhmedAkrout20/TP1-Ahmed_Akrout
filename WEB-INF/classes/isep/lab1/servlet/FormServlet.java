package isep.lab1.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import isep.lab1.util.CookieUtil;
import isep.lab1.util.HtmlUtil;

@WebServlet(name = "FormServlet", urlPatterns = {"/FormServlet", "/Form_Servlet"})
public class FormServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String firstName = value(req.getParameter("firstName"));
        String lastName = value(req.getParameter("lastName"));
        String birthDay = value(req.getParameter("birthDay"));
        String sex = value(req.getParameter("sex"));

        CookieUtil.add(req, resp, "firstName", firstName, CookieUtil.ONE_WEEK);
        CookieUtil.add(req, resp, "lastName", lastName, CookieUtil.ONE_WEEK);
        CookieUtil.add(req, resp, "birthDay", birthDay, CookieUtil.ONE_WEEK);
        CookieUtil.add(req, resp, "sex", sex, CookieUtil.ONE_WEEK);

        String ctx = req.getContextPath();
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(HtmlUtil.begin(ctx, "Submitted person"));
        out.println("<table>");
        out.println("<tr><th>First name</th><td>" + HtmlUtil.escape(firstName) + "</td></tr>");
        out.println("<tr><th>Last name</th><td>" + HtmlUtil.escape(lastName) + "</td></tr>");
        out.println("<tr><th>Birth date (mm/dd/yyyy)</th><td>" + HtmlUtil.escape(birthDay) + "</td></tr>");
        out.println("<tr><th>Sex</th><td>" + HtmlUtil.escape(sex) + "</td></tr>");
        out.println("</table>");
        out.println("<p>Four cookies were created in your browser.</p>");
        out.println("<p><a href=\"" + ctx + "/ReadCookie\">Read the cookies</a> &middot; "
                + "<a href=\"" + ctx + "/form.html\">Back to the form</a></p>");
        out.print(HtmlUtil.end(ctx));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/form.html");
    }

    private static String value(String s) {
        return s == null ? "" : s.trim();
    }
}
