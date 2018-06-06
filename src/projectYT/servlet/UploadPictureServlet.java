package projectYT.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import projectYT.dao.UserDAO;
import projectYT.model.User;

public class UploadPictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		HttpSession session = request.getSession();
		User channel = (User) session.getAttribute("channel");
		String imagesPath = (String) getServletContext().getAttribute("imagesPath");
		ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
		
		String thumbnailUrl = "";
		try
		{
			List<FileItem> multiFiles = servletFileUpload.parseRequest( request);
			for (FileItem item : multiFiles)
			{
				if (!item.isFormField())
				{
					String imageExtension = "." + item.getContentType().split("/")[1];
					File saveLocation = new File(imagesPath + channel.getUserName() + imageExtension);
					System.out.println(saveLocation);
					item.write(saveLocation);
					thumbnailUrl = channel.getUserName() + imageExtension;
				
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		channel.setProfileUrl(thumbnailUrl);
		UserDAO.update(channel);
		session.setAttribute("channel", null);
		
		
	}
}
