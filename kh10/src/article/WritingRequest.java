package article;

public class WritingRequest {   //사용자의 게시글 쓰기 요청정보를 저장
	  
	private String writerName;
	private String password;
	private String title;
	private String content;
	
	
	public String getWriterName() {
		return writerName;
	}
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Article toArticle() {
		Article article = new Article();  //article객체를 생성하여서
	    article.setWriterName(writerName);    //WritingRequest클래스에 저장된 값들을 article에 저장
	    article.setPassword(password);
	    article.setTitle(title);
	    article.setContent(content);
	    return article;    //아티클을 반환
	}
	

}
