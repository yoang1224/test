package shop.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
public class MemberDAO {
         
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	
	public MemberDAO() {
		try{
			Context initCtx=new InitialContext();
			Context envCtx=(Context)initCtx.lookup("java:comp/env");
			DataSource ds=(DataSource)envCtx.lookup("jdbc/OracleDB");
			con=ds.getConnection();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public boolean insertMember(MemberBean mb) throws SQLException{
		String sql=null;
		
		try{
			sql="insert into member values "+
				"(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mb.getMEMBER_ID());
			pstmt.setString(2, mb.getMEMBER_PW());
			pstmt.setString(3, mb.getMEMBER_NAME());
			pstmt.setInt(4, mb.getMEMBER_JUMIN1());
			pstmt.setInt(5, mb.getMEMBER_JUMIN2());
			pstmt.setString(6, mb.getMEMBER_EMAIL());
			pstmt.setString(7, mb.getMEMBER_EMAIL_GET());
			pstmt.setString(8, mb.getMEMBER_MOBILE());
			pstmt.setString(9, mb.getMEMBER_PHONE());
			pstmt.setString(10, mb.getMEMBER_ZIPCODE());
			pstmt.setString(11, mb.getMEMBER_ADDR1());
			pstmt.setString(12, mb.getMEMBER_ADDR2());
			pstmt.setInt(13, mb.getMEMBER_ADMIN());
			pstmt.setTimestamp(14, mb.getMEMBER_JOIN_DATE());
			pstmt.executeUpdate();
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt != null){ pstmt.close();}

		}
		
		return false;
	}
	
	public int userCheck(String id, String pw) throws SQLException{
		String sql=null;
		int x=-1;
		
		try{
			sql="select MEMBER_PW from member where MEMBER_ID=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			if(rs.next()){
				String memberpw=rs.getString("MEMBER_PW");
				
				if(memberpw.equals(pw)){
					x=1;
				}else{
					x=0;
				}
			}else{
				x=-1;
			}
			
			return x;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt != null){ pstmt.close(); }
			if(rs != null){ rs.close(); }
			
		}
		
		return -1;
	}
	
	public int confirmId(String id)throws SQLException{
		String sql = null;
		int x =-1;
		
		try {
			sql = "select MEMBER_ID from member where MEMBER_ID=? ";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				x=1;
			}else {
				x=-1;
			}
			return x;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt !=null) {pstmt.close();}
			if(rs != null) {rs.close();}
			
		}
		return -1;
	}
	public MemberBean getMember(String id) throws SQLException{
		MemberBean member=null;
		String sql = null;
		
		try {
			sql="select * from member where MEMBER_ID=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new MemberBean();
				
				member.setMEMBER_ID(rs.getString("MEMBER_ID"));
			}
		}
	}
}
