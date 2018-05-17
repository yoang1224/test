
package jdbc ;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jdbc.MessageDao;
import jdbc.Message;
import jdbc.JdbcUtil;

public class OracleMessageDao extends MessageDao {

	public int insert(Connection conn, Message message) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into guestbook_message1224 "
					+ " (message_id, guest_name, password, message) "
					+ " values (message_id_seq1224.NEXTVAL, ?, ?, ?)");
			pstmt.setString(1, message.getGuestName());
			pstmt.setString(2, message.getPassword());
			pstmt.setString(3, message.getMessage());
			return pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}

	public List<Message> selectList(Connection conn, int firstRow, int endRow)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
				"select message_id, guest_name, password, message from ( "
				+ "    select rownum rnum, message_id, guest_name, password, message from ( "
				+ "        select * from guestbook_message1224 m order by m.message_id desc "
				+ "    ) where rownum <= ? "
				+ ") where rnum >= ?");

			pstmt.setInt(1, endRow);
			pstmt.setInt(2, firstRow);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				List<Message> messageList = new ArrayList<Message>();
				do {
					messageList.add(super.makeMessageFromResultSet(rs));
				} while (rs.next());
				return messageList;
			} else {
				return Collections.emptyList();
			}
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}




