package mar2930;
import jdbc.JdbcUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import board.CommentDataBean;

public class CommentDBBean {
	private static CommentDBBean instance = new CommentDBBean();
	
	public static CommentDBBean getInstance() {
		return instance;
	}
	private CommentDBBean () {}
	
	private Connection getConnection() throws Exception{
		String jdbcDriver = "jdbc:apache:commons:dbcp:/pool";
		return DriverManager.getConnection(jdbcDriver);
	}

	   public void insertComment(CommentDataBean cdb) throws Exception{
		   
		   Connection conn = null;
		   PreparedStatement pstmt = null;
		   
		   try {
			      conn = getConnection();
			      String sql = "insert into b_comment1224 values(?,?,?,?,?,?,?)";
			      pstmt = conn.prepareStatement(sql);
			      pstmt.setInt(1, cdb.getContent_num());
			      pstmt.setString(2, cdb.getCommenter());
			      pstmt.setString(3, cdb.getCommentt());
			      pstmt.setString(4, cdb.getPasswd());
			      pstmt.setTimestamp(5, cdb.getReg_date());
			      pstmt.setInt(6, cdb.getContent_num());
			      pstmt.executeUpdate();
			      
		   }catch(Exception e) {
			   e.printStackTrace();
		   }finally {
			   JdbcUtil.close(pstmt);
				JdbcUtil.close(conn);
		   }
	   }
}
