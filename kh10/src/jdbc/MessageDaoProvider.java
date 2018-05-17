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
		this.dbms = dbms; //��ü�� ȸ�縶�� �ٸ��� ���� ��
	}
	
	
	
	public MessageDao getMessageDao() {
		if ("oracle".equals(dbms)) {
			return oracleDao; //��ü ��
		} 
		return null;
	}
}

