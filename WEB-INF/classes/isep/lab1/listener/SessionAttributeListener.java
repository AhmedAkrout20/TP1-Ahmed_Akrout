package isep.lab1.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class SessionAttributeListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        System.out.println("[SessionAttributeListener] attributeAdded:    " + event.getName()
                + " = " + event.getValue());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        System.out.println("[SessionAttributeListener] attributeReplaced: " + event.getName()
                + " : " + event.getValue() + " -> " + event.getSession().getAttribute(event.getName()));
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        System.out.println("[SessionAttributeListener] attributeRemoved:  " + event.getName()
                + " (was " + event.getValue() + ")");
    }
}
