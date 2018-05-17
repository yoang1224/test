<%@ page contentType="text.html; charset=euc-kr" %>
<%@ page import="article.ReadArticleService" %>
<%@ page import="article.ArticleNotFoundException" %>
<%@ page import="article.Article" %>

<%

    int articleId = Integer.parseInt(request.getParameter("articleId"));
    String viewPage = null;
    try{
    	Article article = ReadArticleService.getInstance().readArticle(articleId);
    	request.setAttribute("article",article);
    	viewPage = "read_view.jsp";
    }catch(ArticleNotFoundException ex){
    	viewPage = "article_not_found.jsp";
    }
 %>
 <jsp:forward page="<%=viewPage%>" />
    	