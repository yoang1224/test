package article;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import article.Article;
import article.ArticleDao;
import article.ArticleListModel;
import jdbc.JdbcUtil;
import jdbc.ConnectionProvider;

public class ListArticleService {
	private static ListArticleService instance = new ListArticleService();
	public static ListArticleService getInstance() {
		return instance;
	}
    
	public static final int COUNT_PER_PAGE =10;
	
	public ArticleListModel getArticleList(int requestPageNumber) {
		if(requestPageNumber <0) {
			throw new IllegalArgumentException("page number <0 :"
					 + requestPageNumber);
		}
		ArticleDao articleDao = ArticleDao.getInstance();
		Connection conn = null;
		try {
			 conn = ConnectionProvider.getConnection();
			 int totalArticleCount = articleDao.selectCount(conn);
			 
			 if(totalArticleCount == 0) {
				 return new ArticleListModel();
			 }
			 int totalPageCount = calculateTotalPageCount(totalArticleCount);
			 
			 int firstRow = (requestPageNumber -1) * COUNT_PER_PAGE +1;
			 int endRow = firstRow + COUNT_PER_PAGE -1;
			 
			 if(endRow > totalArticleCount) {
				 endRow = totalArticleCount;
			 }
			 List<Article> articleList = articleDao.select(conn, firstRow, endRow);
			 
			 ArticleListModel articleListView = new ArticleListModel(articleList,requestPageNumber,totalPageCount,firstRow,endRow);
			 return articleListView;
		}catch(SQLException e) {
			throw new RuntimeException("DB에러 발생:"+e.getMessage(),e);
		}finally {
                  JdbcUtil.close(conn);
		}
		
	}
	private int calculateTotalPageCount(int totalArticleCount) { // 몇쪽이 존재하는가
		if(totalArticleCount ==0) {
			return 0;
		}
		int pageCount = totalArticleCount / COUNT_PER_PAGE;
		if(totalArticleCount % COUNT_PER_PAGE >0) {
			pageCount++;
		}
		return pageCount;
	}
}
