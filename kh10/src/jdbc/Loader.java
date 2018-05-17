package jdbc;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.StringTokenizer; 

public class Loader extends HttpServlet { 
	
	public void inint(ServletConfig config) throws ServletException{
		try {
			String drivers = config.getInitParameter("jdbcdriver");
			StringTokenizer st = new StringTokenizer(drivers,",");
			while(st.hasMoreTokens()) {
				String jdbcDriver = st.nextToken();
				System.out.println(jdbcDriver);
				Class.forName(jdbcDriver);
			}
		}catch(Exception ex) {
			throw new ServletException(ex);
		}
	}

}
