package isep.lab1.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityFilter implements Filter {

    private static final String PREFIX = "[SECURITY-FILTER]";

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println(PREFIX + " init()");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        boolean connected = session != null && session.getAttribute("user") != null;

        System.out.println(PREFIX + " BEFORE chain.doFilter() - user connected: " + connected);

        if (connected) {
            chain.doFilter(request, response);
            System.out.println(PREFIX + " AFTER  chain.doFilter() - access was authorized");
        } else {
            System.out.println(PREFIX + " access REFUSED, redirecting to login.html (chain.doFilter() not called)");
            resp.sendRedirect(req.getContextPath() + "/login.html");
        }
    }

    @Override
    public void destroy() {
        System.out.println(PREFIX + " destroy()");
    }
}
