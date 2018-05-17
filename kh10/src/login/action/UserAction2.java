package login.action;
import login.dao.UserDAO;
import login.model.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.xwork2.Action;

public class UserAction2 implements Action {
       UserInfo userInfo;
       
       Log log = LogFactory.getLog(UserAction.class);    
       
       public String execute() throws Exception{
    	   log.info(">>>>>>>execute");
    	   
    	   UserInfo userInfo = new UserInfo();
    	   log.info(">>>>>>userInfo Object Create...");
    	   
    	   UserDAO loginDao = new UserDAO();
    	   if(loginDao.save(userInfo)) {
    		   return SUCCESS;
    	   }else {
    		   return ERROR;
    	   }
       }
       public UserInfo getUserInfo() {
    	   return userInfo;
       }
       public void setUserInfo(UserInfo userInfo) {
    	   this.userInfo = userInfo;
       }
}
