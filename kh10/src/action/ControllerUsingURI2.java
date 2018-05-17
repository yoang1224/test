package action;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class ControllerUsingURI2 extends HttpServlet {
     
	private Map commandMap = new HashMap();
    
	public void init(ServletConfig config)throws ServletException{
		
		String props = config.getInitParameter("configFile2");
		Properties pr = new Properties();
		FileInputStream f = null;
		try {
			String configFilePath = config.getServletContext().getRealPath(props);
			f = new FileInputStream(configFilePath);
			pr.load(f);
		}catch (IOException e) {
			throw new ServletException(e);
		}finally {
			if(f != null)try {f.close();}catch(IOException ex) {}
		}
		Iterator keyIter = pr.keySet().iterator();
		while(keyIter.hasNext()) {
			String command = (String)keyIter.next();
			System.out.println(12);
			System.out.println(command);
			String className = pr.getProperty(command);
			try {
				Class commandClass = Class.forName(className);
				Object commandInstance = commandClass.newInstance();
				commandMap.put(command, commandInstance);
			}catch(ClassNotFoundException e) {
				throw new ServletException (e);
			}catch(InstantiationException e) {
				throw new ServletException(e);
			}catch(IllegalAccessException e) {
				throw new ServletException(e);
			}
		}
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletExcepiton,IOException{
		requestPro(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		requestPro(request,response);
	}
	private void requestPro(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String view = null;
		CommandAction com= null;
		try {
			String command = request.getRequestURI();
			if (command.indexOf(request.getContextPath())==0) {
				command = command.substring(request.getContextPath().length());
			}
			com = (CommandAction)commandMap.get(command);
			if(com == null) {
				com = new NullAction();
			}
			view = com.requestPro(request, response);
		}catch(Throwable e) {
			throw new ServletException(e);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}
     
}
