package isep.lab1.listener;

import java.io.Serializable;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class SessionUser implements HttpSessionBindingListener, Serializable {

    private static final long serialVersionUID = 1L;

    private final String username;

    public SessionUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        System.out.println("[SessionUser] valueBound:   " + username + " was added to the session as '"
                + event.getName() + "'");
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        System.out.println("[SessionUser] valueUnbound: " + username + " was removed from the session ('"
                + event.getName() + "')");
    }

    @Override
    public String toString() {
        return "SessionUser[" + username + "]";
    }
}
