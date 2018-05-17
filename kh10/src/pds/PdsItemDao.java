package pds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pds.PdsItem;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;


public class PdsItemDao {
	
	
	private static PdsItemDao instance = new PdsItemDao();
	public static PdsItemDao getInstance() {
		return instance;
	}
	
	private PdsItemDao() {
	}
	public int insert(Connection conn,PdsItem item)throws SQLException {
		PreparedStatement pstmt =null;
		Statement stmt = null;
		ResultSet rs =null;
		
		try {
			  pstmt = conn.prepareStatement("insert into pds_item1224 "
					                + "(pds_item_id, filename, realpath, filesize, downloadcount, "
					                + "description) "
					                + "values (pds_item_id_seq1224.NEXTVAL, ?, ?, ?, 0, ?)");
			      pstmt.setString(1, item.getFileName());
			      pstmt.setString(2, item.getRealPath());
			      pstmt.setLong(3, item.getFileSize());
			      pstmt.setString(4, item.getDescription());
			      int insertedCount = pstmt.executeUpdate();
			     
			      if(insertedCount > 0) {
			    	  stmt = conn.createStatement();
			    	  rs = stmt.executeQuery("select pds_item_id_seq1224.CURRVAL from dual");
			    	  if(rs.next()) {
			    		  return rs.getInt(1);
			    	  }
			      }
			      return -1;
			      
					     
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
		
	}
	private PdsItem makeItemFromResultSet(ResultSet rs)throws SQLException {
		PdsItem item = new PdsItem();
		item.setId(rs.getInt("pds_item_id"));
		item.setFileName(rs.getString("filename"));
		item.setRealPath(rs.getString("realpath"));
		item.setFileSize(rs.getLong("filesize"));
		item.setDownloadCount(rs.getInt("downloadcount"));
		item.setDescription(rs.getString("description"));
		return item;
	}
	
	
	
	public PdsItem selectById(Connection conn, int itemId)throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			   pstmt = conn.prepareStatement("select * from pds_item1224 "
					              + "where pds_item_id =?");
			   pstmt.setInt(1, itemId);
			   rs = pstmt.executeQuery();
			   if(!rs.next()) {
				   return null;
			   }
			   PdsItem item = makeItemFromResultSet(rs);
			   return item;
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public int increaseCount(Connection conn, int id)throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("update pds_item1224 set downloadcount = downloadcount +1 where pds_item_id =?");
			pstmt.setInt(1, id);
			return pstmt.executeUpdate();
		}finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public int selectCount(String searchKeyword) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = ConnectionProvider.getConnection().prepareStatement("select count(*) from pds_item1224 where filename like '%'||?||'%'");
			pstmt.setString(1, searchKeyword);
			rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public List<PdsItem> select(int firstRow, int endRow, String searchKeyword) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = ConnectionProvider.getConnection().prepareStatement("select * from (select rownum rnum, pds_item_id, filename, realpath, filesize, downloadcount, description "
							+ "from (select * from pds_item1224 m where filename like '%'||?||'%' order by m.pds_item_id desc) where rownum <= ?) "
							+ "where rnum >=?");
			pstmt.setString(1, searchKeyword);
			pstmt.setInt(2, endRow);
			pstmt.setInt(3, firstRow);
			rs = pstmt.executeQuery();
			
			if (!rs.next()) {
				return Collections.emptyList();
			}
			List<PdsItem> itemList = new ArrayList<PdsItem>();
			do {
				PdsItem article = makeItemFromResultSet(rs);
				itemList.add(article);
			} while (rs.next());
			return itemList;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

}

