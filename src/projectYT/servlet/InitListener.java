package projectYT.servlet;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import projectYT.dao.ConnectionMenager;

public class InitListener implements ServletContextListener {
    public void contextDestroyed(ServletContextEvent arg0)  { 
        ConnectionMenager.close();
    }

    public void contextInitialized(ServletContextEvent event)  { 
        ConnectionMenager.open();
        
        
		ServletContext servletContext = event.getServletContext();
		
		String realPath = servletContext.getRealPath(File.separator);
		

		String imagesPath = realPath + "pictures" + File.separator;
		
		event.getServletContext().setAttribute("imagesPath", imagesPath);
		
		System.out.println(imagesPath);
    }
	
}
