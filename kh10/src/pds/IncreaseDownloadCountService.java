package pds;

import java.sql.Connection;
import java.sql.SQLException;

import pds.PdsItemDao;
import jdbc.JdbcUtil;
import jdbc.ConnectionProvider;

public class IncreaseDownloadCountService {
	private static IncreaseDownloadCountService instance = new IncreaseDownloadCountService();
   
	public static IncreaseDownloadCountService getInstance() {
		return instance;
	}
	private IncreaseDownloadCountService() {}
	
	public boolean increaseCount(int id) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			int updateCount = PdsItemDao.getInstance().increaseCount(conn, id);
			return updateCount == 0 ? false : true;
			
		}catch(SQLException e) {
			throw new RuntimeException("DB처리 에러 발생:"+e.getMessage(),e);
		}finally {
			JdbcUtil.close(conn);
		}
	}
}
