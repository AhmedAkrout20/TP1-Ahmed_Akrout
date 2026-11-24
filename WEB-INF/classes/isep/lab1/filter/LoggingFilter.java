package isep.lab1.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class LoggingFilter implements Filter {

    private String prefix = "[LoggingFilter]";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String p = filterConfig.getInitParameter("logPrefix");
        if (p != null && !p.isEmpty()) {
            prefix = p;
        }
        System.out.println(prefix + " init(): filter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String url = describe(request);
        long start = System.nanoTime();

        System.out.println(prefix + " BEFORE servlet -> " + url);

        try {
            chain.doFilter(request, response);
        } finally {
            double ms = (System.nanoTime() - start) / 1_000_000.0;
            System.out.println(String.format(Locale.ROOT,
                    "%s AFTER  servlet <- %s (%.3f ms)", prefix, url, ms));
        }
    }

    @Override
    public void destroy() {
        System.out.println(prefix + " destroy(): filter destroyed");
    }

    private static String describe(ServletRequest request) {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest r = (HttpServletRequest) request;
            StringBuffer url = r.getRequestURL();
            if (r.getQueryString() != null) {
                url.append('?').append(r.getQueryString());
            }
            return r.getMethod() + " " + url;
        }
        return request.getRemoteAddr();
    }
}
