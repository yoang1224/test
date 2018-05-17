package login.action;
import login.dao.UserDAO;
import login.model.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class UserAction3 implements Action, Preparable, ModelDriven {
   
	UserInfo userInfo;
    Log log = LogFactory.getLog(UserAction3.class);
    public String execute()throws Exception{
    	log.info(">>>>>>>>execute");
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
    public void prepare()throws Exception{
    	userInfo = new UserInfo();
    }
    public Object getModel() {
    	return userInfo;
    }
}
