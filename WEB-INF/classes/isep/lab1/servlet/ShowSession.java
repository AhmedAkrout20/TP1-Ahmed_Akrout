package isep.lab1.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import isep.lab1.util.HtmlUtil;

@WebServlet(name = "ShowSession", urlPatterns = {"/ShowSession"})
public class ShowSession extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(true);
        synchronized (session) {
            Integer visits = (Integer) session.getAttribute("visits");
            session.setAttribute("visits", visits == null ? 1 : visits + 1);
        }
        render(req, resp, session, null);
    }

    /** Receives the "full name + date of birth" form. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(true);
        if (session.getAttribute("visits") == null) {
            session.setAttribute("visits", 1);
        }

        String fullName = req.getParameter("fullName") == null ? "" : req.getParameter("fullName").trim();
        String birth = req.getParameter("birthDate") == null ? "" : req.getParameter("birthDate").trim();
        String error = null;

        if (fullName.isEmpty() || birth.isEmpty()) {
            error = "Please fill in both fields.";
        } else {
            try {
                LocalDate dob = LocalDate.parse(birth);
                if (dob.isAfter(LocalDate.now())) {
                    error = "The date of birth cannot be in the future.";
                } else {
                    session.setAttribute("fullName", fullName);
                    session.setAttribute("birthDate", dob);
                }
            } catch (DateTimeParseException e) {
                error = "Invalid date. Use the format yyyy-mm-dd.";
            }
        }
        render(req, resp, session, error);
    }

    private void render(HttpServletRequest req, HttpServletResponse resp,
                        HttpSession session, String error) throws IOException {
        String ctx = req.getContextPath();
        int visits = (Integer) session.getAttribute("visits");
        String fullName = (String) session.getAttribute("fullName");
        LocalDate dob = (LocalDate) session.getAttribute("birthDate");

        Object active = getServletContext().getAttribute("activeSessions");
        int activeSessions = active instanceof Integer ? (Integer) active : 0;

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(HtmlUtil.begin(ctx, "Session tracking"));

        out.println("<p class=\"big\">" + (visits == 1 ? "Welcome on my site" : "Welcome back") + "</p>");
        out.println("<p>Number of visits in this session: <strong>" + visits + "</strong></p>");
        out.println("<p>Currently active sessions on the server: <strong>" + activeSessions + "</strong></p>");
        out.println("<p><small>Session id: " + HtmlUtil.escape(session.getId()) + "</small></p>");

        if (fullName == null || dob == null) {
            if (error != null) {
                out.println("<p class=\"error\">" + HtmlUtil.escape(error) + "</p>");
            }
            out.println("<h2>Tell us about you</h2>");
            out.println("<form action=\"" + ctx + "/ShowSession\" method=\"POST\">");
            out.println("<label>Full name <input type=\"text\" name=\"fullName\" required></label>");
            out.println("<label>Date of birth <input type=\"date\" name=\"birthDate\" required></label>");
            out.println("<input type=\"submit\" value=\"Save\">");
            out.println("</form>");
        } else {
            long days = daysUntilBirthday(dob, LocalDate.now());
            if (days == 0) {
                out.println("<p class=\"msg\">Hi, " + HtmlUtil.escape(fullName)
                        + ". Today is your birthday, happy birthday!</p>");
            } else {
                out.println("<p class=\"msg\">Hi, " + HtmlUtil.escape(fullName) + ". There are "
                        + days + " days to your birthday.</p>");
            }
        }

        out.println("<p><a href=\"" + ctx + "/ShowSession\">Reload</a> &middot; "
                + "<a href=\"" + ctx + "/SessionAction?action=invalidate\">Invalidate my session</a></p>");
        out.print(HtmlUtil.end(ctx));
    }

    /** Days from {@code today} to the next birthday (0 if it is today). Feb 29 falls on Feb 28 in common years. */
    static long daysUntilBirthday(LocalDate dob, LocalDate today) {
        MonthDay birthday = MonthDay.from(dob);
        LocalDate next = birthday.atYear(today.getYear());
        if (next.isBefore(today)) {
            next = birthday.atYear(today.getYear() + 1);
        }
        return ChronoUnit.DAYS.between(today, next);
    }
}
