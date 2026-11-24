package lab.mvc.controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lab.mvc.model.Person;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int LIMIT_YEAR = 1990;

    private static final Pattern US_DATE = Pattern.compile("^(\\d{1,2})/(\\d{1,2})/(\\d{4})$");
    private static final Pattern ISO_DATE = Pattern.compile("^(\\d{4})-(\\d{1,2})-(\\d{1,2})$");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String firstName = param(request, "firstName");
        String lastName = param(request, "lastName");
        String birthDay = param(request, "birthDay");
        String sex = param(request, "sex");

        Integer year = extractYear(birthDay);
        if (year == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid birth date. Expected format: mm/dd/yyyy");
            return;
        }

        Person person = new Person(firstName, lastName, birthDay, sex);

        request.setAttribute("person", person);

        String page = year < LIMIT_YEAR ? "/mvc/old.jsp" : "/mvc/young.jsp";

        response.setContentType("text/html;charset=UTF-8");

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.include(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/mvc/index.html");
    }

    private static String param(HttpServletRequest request, String name) {
        String v = request.getParameter(name);
        return v == null ? "" : v.trim();
    }

    static Integer extractYear(String date) {
        Matcher us = US_DATE.matcher(date);
        if (us.matches()) {
            return Integer.valueOf(us.group(3));
        }
        Matcher iso = ISO_DATE.matcher(date);
        if (iso.matches()) {
            return Integer.valueOf(iso.group(1));
        }
        return null;
    }
}
