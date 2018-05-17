package mar2930;

import java.sql.*;

import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class BoardDBBean {
	  
	private static BoardDBBean instance = new BoardDBBean();
	   
	    public static BoardDBBean getInstance() {
	        return instance;
	    }
	   
	    private BoardDBBean() {
	    }

	    private Connection getConnection() throws Exception {
	        String jdbcDriver = "jdbc:apache:commons:dbcp:/pool";         
	            return DriverManager.getConnection(jdbcDriver);
	        }
	   
	   public void insertArticle(BoardDataBean article)throws Exception{
		   Connection conn = null;
		   PreparedStatement pstmt = null;
		   ResultSet rs = null;
		   
		   int num = article.getNum();
		   int ref = article.getRef();
		   int re_step = article.getRe_step();
		   int re_level = article.getRe_level();
		   int number = 0;
		   String sql="";
		   try {
			   conn = getConnection();
			   pstmt = conn.prepareStatement("select max(num) from  board1224");
			   rs = pstmt.executeQuery();
			   
			   if(rs.next())
				   number=rs.getInt(1)+1;
			   else
				   number=1;
			   
			   if(num !=0) {
				   sql ="update board1224 set re_step=re_step+1 where ref=? and re_step>?";
				   pstmt = conn.prepareStatement(sql);
				   pstmt.setInt(1, ref);
				   pstmt.setInt(2, re_step);
				   pstmt.executeUpdate();
				   re_step=re_step+1;
				   re_level = re_level+1;
			   }else {
				   ref=number;
				   re_step=0;
				   re_level=0;
			   }
			   sql="insert into board1224(num,writer,email,subject,passwd,red_date,";
			   sql+="ref,re_step,re_level,content,ip)  values(board_num1224.NEXTVAL,?,?,?,?,?,?,?,?,?,?)";
			   
			   pstmt = conn.prepareStatement(sql);
			   pstmt.setString(1, article.getWriter());
			   pstmt.setString(2, article.getEmail());
			   pstmt.setString(3, article.getSubject());
			   pstmt.setString(4, article.getPasswd());
			   pstmt.setTimestamp(5, article.getReg_date());
			   pstmt.setInt(6, ref);
			   pstmt.setInt(7, re_step);
			   pstmt.setInt(8, re_level);
			   pstmt.setString(9, article.getContent());
			   pstmt.setString(10, article.getIp());
			   
			   pstmt.executeUpdate();
			   
		   }catch(Exception ex) {
			   ex.printStackTrace();
		   }finally {
			   if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		   }
	   }
	   
	   public int getArticleCount() throws Exception{  //목록 출력을 위해서는 행의 수가 필요하다.
		   Connection conn = null;
		   PreparedStatement pstmt = null;
		   ResultSet rs = null;
		   
		   int x =0;
		   
		   try {
			   conn = getConnection();
			   
			   pstmt = conn.prepareStatement("select count(*) from board1224");
			   rs = pstmt.executeQuery();
			   
			   if(rs.next()) {
				   x=rs.getInt(1);  //결과값의 값을 x에..
			   }
		   }catch(Exception ex) {
			   ex.printStackTrace();
			   
		   }finally {
			   if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		   }
		   return x;
	   }
	   
	   public List getArticles(int start, int end)throws Exception{ //여러 행을 결과로 받는다.
		   Connection conn = null;
		   PreparedStatement pstmt = null;
		   ResultSet rs = null;
		   List articleList = null;
		   try {
			   conn = getConnection();
			   
			   pstmt = conn.prepareStatement(
					   "select num,writer,email,subject,passwd,reg_date,ref,re_step,re_level,content,ip,readcount,r "+
			           "from (select num,writer,email,subject,passwd,reg_date,ref,re_step,re_level,content,ip,readcount,rownum r "+
				       "from (select num,writer,email,subject,passwd,reg_date,ref,re_step,re_level,content,ip,readcount " +
			           "from board1224 order by ref desc , re_step asc)order by ref desc , re_step asc) where r >=? and r <=?");
			            pstmt.setInt(1, start);
			            pstmt.setInt(2, end);
			            rs = pstmt.executeQuery();
			            
			            if(rs.next()) {
			            	articleList = new ArrayList(end);
			            	do {
			            		BoardDataBean article = new BoardDataBean();
			            		article.setNum(rs.getInt("num"));
			            		article.setWriter(rs.getString("writer"));
			            		article.setEmail(rs.getString("email"));
			            		article.setSubject(rs.getString("subject"));
			                    article.setPasswd(rs.getString("passwd"));
			                    article.setReg_date(rs.getTimestamp("reg_date"));
			                    article.setReadcount(rs.getInt("readcount"));
			                    article.setRef(rs.getInt("ref"));
			                    article.setRe_step(rs.getInt("re_step"));
			                    article.setRe_level(rs.getInt("re_level"));
			                    article.setContent(rs.getString("content"));
			                    article.setIp(rs.getString("ip"));
			                    
			                    articleList.add(article);
			            	}while(rs.next());
			            }
		   }catch(Exception ex) {
			   ex.printStackTrace();
			            		
			            	}finally {
			            		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			                    if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			                    if (conn != null) try { conn.close(); } catch(SQLException ex) {}
			            }
		             return articleList;
		   
	   }
	   public BoardDataBean getArticle(int num)throws Exception{
		   Connection conn = null;
		   PreparedStatement pstmt = null;
		   ResultSet rs =null;
		   BoardDataBean article =null;
		   try {
			   conn = getConnection();
			   pstmt = conn.prepareStatement(
			   		"update board1224 set readcount = readcount+1 where num=?");
			   pstmt.setInt(1,num);
			   pstmt.executeQuery();
			   
			   pstmt = conn.prepareStatement(
					   "select * from board1224 where num=?");
			   pstmt.setInt(1, num);
			   rs = pstmt.executeQuery();
			   
			   if(rs.next()) {
				   article = new BoardDataBean();
				   article.setNum(rs.getInt("num"));
				   article.setWriter(rs.getString("writer"));
				   article.setEmail(rs.getString("email"));
	                article.setSubject(rs.getString("subject"));
	                article.setPasswd(rs.getString("passwd"));
	                article.setReg_date(rs.getTimestamp("reg_date"));
	                article.setReadcount(rs.getInt("readcount"));
	                article.setRef(rs.getInt("ref"));
	                article.setRe_step(rs.getInt("re_step"));
	                article.setRe_level(rs.getInt("re_level"));
	                article.setContent(rs.getString("content"));
	                article.setIp(rs.getString("ip"));    
			   }
					   
		   }catch(Exception ex) {
			   ex.printStackTrace();
		   }finally {
			   if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		   }
		   return article;
	   }
	   
	   
	}
