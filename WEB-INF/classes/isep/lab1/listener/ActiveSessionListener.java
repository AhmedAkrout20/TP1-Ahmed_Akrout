package isep.lab1.listener;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class ActiveSessionListener implements HttpSessionListener {

    public static final String ATTRIBUTE = "activeSessions";

    private static final AtomicInteger ACTIVE = new AtomicInteger();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        int now = ACTIVE.incrementAndGet();
        publish(se, now);
        System.out.println("[ActiveSessionListener] sessionCreated: " + se.getSession().getId()
                + " -> active sessions = " + now);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        int now = ACTIVE.decrementAndGet();
        publish(se, now);
        System.out.println("[ActiveSessionListener] sessionDestroyed: " + se.getSession().getId()
                + " -> active sessions = " + now);
    }

    private static void publish(HttpSessionEvent se, int value) {
        ServletContext ctx = se.getSession().getServletContext();
        ctx.setAttribute(ATTRIBUTE, value);
    }
}
