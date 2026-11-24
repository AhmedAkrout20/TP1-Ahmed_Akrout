package isep.lab1.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import isep.lab1.util.HtmlUtil;

@WebServlet(name = "ListItems", urlPatterns = {"/ListItems"})
public class ListItems extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String ITEMS = "items";
    private static final String COUNTS = "itemCounts";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String item = req.getParameter("newItem");
        item = item == null ? "" : item.trim();

        HttpSession session = req.getSession(true);
        synchronized (session) {
            List<String> items = items(session);
            Map<String, Integer> counts = counts(session);
            if (!item.isEmpty()) {
                items.add(item);
                counts.merge(item, 1, Integer::sum);
            }
        }
        render(req, resp, session);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        render(req, resp, req.getSession(true));
    }

    private void render(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException {
        String ctx = req.getContextPath();
        List<String> snapshot;
        Map<String, Integer> countsSnapshot;
        synchronized (session) {
            snapshot = new ArrayList<>(items(session));
            countsSnapshot = new LinkedHashMap<>(counts(session));
        }

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(HtmlUtil.begin(ctx, "Items stored in your session"));

        if (snapshot.isEmpty()) {
            out.println("<p><em>The list is empty.</em></p>");
        } else {
            out.println("<ol>");
            for (String s : snapshot) {
                out.println("<li>" + HtmlUtil.escape(s) + "</li>");
            }
            out.println("</ol>");

            out.println("<h2>Times each item was entered</h2>");
            out.println("<table><tr><th>Item</th><th>Times</th></tr>");
            for (Map.Entry<String, Integer> e : countsSnapshot.entrySet()) {
                out.println("<tr><td>" + HtmlUtil.escape(e.getKey()) + "</td><td>" + e.getValue() + "</td></tr>");
            }
            out.println("</table>");
        }
        out.println("<p><a href=\"" + ctx + "/items.html\">Add another item</a></p>");
        out.print(HtmlUtil.end(ctx));
    }

    @SuppressWarnings("unchecked")
    private static List<String> items(HttpSession session) {
        List<String> items = (List<String>) session.getAttribute(ITEMS);
        if (items == null) {
            items = new ArrayList<>();
            session.setAttribute(ITEMS, items);
        }
        return items;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Integer> counts(HttpSession session) {
        Map<String, Integer> counts = (Map<String, Integer>) session.getAttribute(COUNTS);
        if (counts == null) {
            counts = new LinkedHashMap<>();
            session.setAttribute(COUNTS, counts);
        }
        return counts;
    }
}
