package hello3.action;

import com.opensymphony.xwork2.ActionSupport;


public class HelloWorld extends ActionSupport {
      private String name;
      private String msg;
      
      public void validate() {
    	  if(name == null || "".equals(name)) {
    		  addFieldError("name","Enter Your Name!!");
    	  }
      }
      public String getMsg() {
    	  return msg;
      }
      public void setName(String name) {
    	  this.name = name;
      }
      public String execute() throws Exception{
    	  msg = "Hello," +name;
    	  return SUCCESS;
      }
      
}
