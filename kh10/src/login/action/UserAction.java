package login.action;
import login.dao.UserDAO;
import login.model.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.xwork2.Action;

public class UserAction implements Action {
     private String name;
     private String addr;
     private String tel;
     
     Log log = LogFactory.getLog(UserAction.class);
     
     public String execute() throws Exception{
    	 log.info(">>>>>>execute");
    	 
    	 UserInfo userInfo = new UserInfo();
    	 userInfo.setName(name);
    	 userInfo.setAddr(addr);
    	 userInfo.setTel(tel);
    	 log.info(">>>>>>>>userInfo Object Create...");
    	 
    	 UserDAO loginDao = new UserDAO();
    	 
    	 if(loginDao.save(userInfo)) { //DBø° ∞¥√º¿˙¿Â
    		 return SUCCESS;
    	 }
    	 else {
    		 return ERROR;
    	 }
     }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}
     

}
