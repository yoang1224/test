package pds;

import java.sql.Connection;
import java.sql.SQLException;

import pds.PdsItemDao;
import pds.PdsItem;
import jdbc.JdbcUtil;
import jdbc.ConnectionProvider;

public class GetPdsItemService {
     private static GetPdsItemService instance = new GetPdsItemService();
     
     public static GetPdsItemService getInstance() {
    	 return instance;
     }
     private GetPdsItemService() {}
     
     public PdsItem getPdsItem(int id)throws PdsItemNotFoundException {
    	 Connection conn = null;
    	 try {
    		 conn = ConnectionProvider.getConnection();
    		 PdsItem pdsItem = PdsItemDao.getInstance().selectById(conn,id);
    		 if(pdsItem ==null) {
    			 throw new PdsItemNotFoundException("�������� �ʽ��ϴ�:"+id);
    			 
    		 }
    		 return pdsItem;
    	 }catch(SQLException e) {
    		 throw new RuntimeException("DB ó�� ���� �߻�:"+e.getMessage(),e);
    		 
    	 }finally {
    		 JdbcUtil.close(conn);
    	 }
     }
}
