package jdbc;
import java.sql.Connection;
import java.sql.SQLException;

import jdbc.MessageDao;
import jdbc.MessageDaoProvider;
import jdbc.Message;
import jdbc.JdbcUtil;
import jdbc.ConnectionProvider;


public class DeleteMessageService {
    private static DeleteMessageService instance = new DeleteMessageService();
    
    public static DeleteMessageService getInstance() {
    	return instance;
    }
    
    private DeleteMessageService() {}
    
    public void deleteMessage(int messageId,String password)
       throws ServiceException,InvalidMessagePassowrdException,MessageNotFoundException{
    	Connection conn = null;
    	try {
    		conn = ConnectionProvider.getConnection();
    		conn.setAutoCommit(false);
    		
    		MessageDao messageDao = MessageDaoProvider.getInstnace().getMessageDao();
    		Message message = messageDao.select(conn, messageId);
    		if(message == null) {
    			throw new MessageNotFoundException("메세지가 없습니다:"
    					+ messageId);
    		}
    		if(!message.hasPassword()) {
    			throw new InvalidMessagePassowrdException();
    		}
    		messageDao.delete(conn, messageId);
    		
    		conn.commit();
    	}catch(SQLException ex) {
    		JdbcUtil.rollback(conn);
    		throw new ServiceException("삭제 처리 중 에러가 발생하였습니다."
    				  +ex.getMessage(),ex);
    	}catch(InvalidMessagePassowrdException ex) {
    		JdbcUtil.rollback(conn);
    		throw ex;
    	}catch(MessageNotFoundException ex) {
    		JdbcUtil.rollback(conn);
    		throw ex;
    	}finally {
    		if(conn != null) {
    			try {
    				conn.setAutoCommit(false);
    			}catch(SQLException e) {
    				
    			}
    			JdbcUtil.close(conn);
    		}
    	}
    }
}
