package isep.lab1.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        ctx.setAttribute("appName", "ING1 Web Application");
        System.out.println("[ApplicationListener] contextInitialized: application "
                + ctx.getContextPath() + " started, appName = " + ctx.getAttribute("appName"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[ApplicationListener] contextDestroyed: application "
                + sce.getServletContext().getContextPath() + " stopped");
    }
}
