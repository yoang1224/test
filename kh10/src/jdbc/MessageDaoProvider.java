package jdbc;

import jdbc.OracleMessageDao;

public class MessageDaoProvider {
	private static MessageDaoProvider instance = new MessageDaoProvider();
	public static MessageDaoProvider getInstnace() {
		return instance;
	}
	private MessageDaoProvider() {}
	
	
	private OracleMessageDao oracleDao = new OracleMessageDao();
	
	private String dbms;
	
	void setDbms(String dbms) {
		this.dbms = dbms; //객체를 회사마다 다르게 얻어내게 함
	}
	
	
	
	public MessageDao getMessageDao() {
		if ("oracle".equals(dbms)) {
			return oracleDao; //객체 얻어냄
		} 
		return null;
	}
}

