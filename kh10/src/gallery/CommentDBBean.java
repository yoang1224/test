package gallery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import jdbc.JdbcUtil;
import gallery.CommentDataBean;

public class CommentDBBean {
		
	private static CommentDBBean instance=new CommentDBBean();
	
	public static CommentDBBean getInstance(){
		return instance;
	}
	
	private CommentDBBean(){}
	
	private Connection getConnection() throws Exception{
		String jdbcDriver="jdbc:apache:commons:dbcp:/pool";
		return DriverManager.getConnection(jdbcDriver);
	}
	
	public void insertComment(CommentDataBean cdb) throws Exception{  //
		
		Connection conn=null;
		PreparedStatement pstmt=null;

		try{
			conn=getConnection();
			String sql="insert into b_comment1224 values(?,?,?,?,?,?,comment_num1224.NEXTVAL)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, cdb.getContent_num());
			pstmt.setString(2, cdb.getCommenter());
			pstmt.setString(3, cdb.getCommentt());
			pstmt.setString(4, cdb.getPasswd());
			pstmt.setTimestamp(5, cdb.getReg_date());
			pstmt.setString(6, cdb.getIp());
			
			pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
	}
	
	public ArrayList getComments(int con_num, int start, int end)throws Exception{  //
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList cm=null;
		try{
			conn=getConnection();
			String sql="select content_num,commenter,commentt,reg_date,ip,comment_num,r "
					+ "from (select content_num,commenter,commentt,reg_date,ip,comment_num, rownum r "
					+ "from (select content_num,commenter,commentt,reg_date,ip,comment_num "
					+ "from b_comment1224 where content_num="+con_num+" order by reg_date desc) order by reg_date desc) where r >= ? and r <= ?";			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				cm=new ArrayList();
				do{
					CommentDataBean cdb=new CommentDataBean();
					cdb.setComment_num(rs.getInt("comment_num"));
					cdb.setContent_num(rs.getInt("content_num"));
					cdb.setCommenter(rs.getString("commenter"));
					cdb.setCommentt(rs.getString("commentt"));
					cdb.setIp(rs.getString("ip"));
					cdb.setReg_date(rs.getTimestamp("reg_date"));
					cm.add(cdb);
				}while(rs.next());
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return cm;
	}
	
	public int getCommentCount(int con_num)throws Exception{    //
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList cm=null;
		int count=0;
		
		try{
			conn=getConnection();
			String sql="select * from b_comment1224 where content_num="+con_num+" order by reg_date desc";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				cm=new ArrayList();
				do{
					CommentDataBean cdb=new CommentDataBean();
					cdb.setCommenter(rs.getString("commenter"));
					cdb.setCommentt(rs.getString("commentt"));
					cdb.setIp(rs.getString("ip"));
					cdb.setReg_date(rs.getTimestamp("reg_date"));
					cm.add(cdb);
				}while(rs.next());
				count=cm.size();
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return count;
	}
}