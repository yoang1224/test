package staticParams.action;

import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import staticParams.dao.SumDAO;
import staticParams.model.NumberForAdd;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.config.entities.Parameterizable;


public class SumAction implements Action, Parameterizable{
       NumberForAdd num;
       private int sum =0;
       Map<String,Object>params;
       
       public void setParams(Map<String,Object>params) {
    	   this.params = params;
       }
       public void addParam(String name,Object value) {}
       
       public Map<String,Object>getParams(){
    	   return params;
       }
       public String execute() throws Exception{
    	   num = new NumberForAdd();
    	   num.setNum1(params.get("num1").toString());
    	   num.setNum2(params.get("num2").toString());
    	   SumDAO dao = new SumDAO();
    	   sum = dao.add(num);
    	   return SUCCESS;
    	   
       }
       public int getSum() {
    	   return num;
       }
}
