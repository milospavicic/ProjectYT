package projectYT.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import projectYT.dao.ConnectionMenager;

public class InitListener implements ServletContextListener {
    public void contextDestroyed(ServletContextEvent arg0)  { 
        ConnectionMenager.close();
    }

    public void contextInitialized(ServletContextEvent event)  { 
        ConnectionMenager.open();
    }
	
}
