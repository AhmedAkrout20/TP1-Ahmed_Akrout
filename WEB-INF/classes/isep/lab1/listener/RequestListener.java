package isep.lab1.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@WebListener
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("[RequestListener] requestInitialized: " + describe(sre));
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("[RequestListener] requestDestroyed:   " + describe(sre));
    }

    private static String describe(ServletRequestEvent sre) {
        if (sre.getServletRequest() instanceof HttpServletRequest) {
            HttpServletRequest r = (HttpServletRequest) sre.getServletRequest();
            return r.getMethod() + " " + r.getRequestURI();
        }
        return sre.getServletRequest().getRemoteAddr();
    }
}
