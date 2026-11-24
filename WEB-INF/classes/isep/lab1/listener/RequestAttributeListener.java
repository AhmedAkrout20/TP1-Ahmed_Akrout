package isep.lab1.listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class RequestAttributeListener implements ServletRequestAttributeListener {

    @Override
    public void attributeAdded(ServletRequestAttributeEvent event) {
        if (internal(event.getName())) {
            return;
        }
        System.out.println("[RequestAttributeListener] attributeAdded:    " + event.getName()
                + " = " + event.getValue());
    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent event) {
        if (internal(event.getName())) {
            return;
        }
        // getValue() returns the OLD value
        System.out.println("[RequestAttributeListener] attributeReplaced: " + event.getName()
                + " : " + event.getValue() + " -> " + event.getServletRequest().getAttribute(event.getName()));
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent event) {
        if (internal(event.getName())) {
            return;
        }
        System.out.println("[RequestAttributeListener] attributeRemoved:  " + event.getName()
                + " (was " + event.getValue() + ")");
    }

    private static boolean internal(String name) {
        return name.startsWith("javax.servlet.") || name.startsWith("org.apache.");
    }
}
