package isep.lab1.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

public class ApplicationAttributeListener implements ServletContextAttributeListener {

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        System.out.println("[ApplicationAttributeListener] attributeAdded:    " + event.getName()
                + " = " + event.getValue());
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
        System.out.println("[ApplicationAttributeListener] attributeReplaced: " + event.getName()
                + " : " + event.getValue() + " -> " + event.getServletContext().getAttribute(event.getName()));
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
        System.out.println("[ApplicationAttributeListener] attributeRemoved:  " + event.getName()
                + " (was " + event.getValue() + ")");
    }
}
