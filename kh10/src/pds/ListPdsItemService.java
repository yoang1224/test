package pds;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import pds.PdsItemDao;
import pds.PdsItem;
import pds.PdsItemListModel;
import jdbc.JdbcUtil;
import jdbc.ConnectionProvider;

public class ListPdsItemService {
                private static ListPdsItemService instance = new ListPdsItemService();
                public static ListPdsItemService getInstance() {
                	return instance;
                }
                private ListPdsItemService(){}
                
                public static final int COUNT_PER_PAGE = 10;
                
                public PdsItemListModel getPds
}
