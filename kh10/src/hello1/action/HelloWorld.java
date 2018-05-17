package hello1.action;

public class HelloWorld {
      
	private String name;
	private String msg;
	
	public String getMsg() {
		return msg;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String execute() throws Exception{
		msg = "Hello,"+name;
		return "success";
	}
}
