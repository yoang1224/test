package article;

public class ReplyingRequest extends WritingRequest {  // 답변 쓰기 요청정보를 표현하는 클래스 부모클래스에는 제목 작성자이름 내용 암호가 있다. 
     
	private int parentArticleId;  //부모게시글의 id를 추가 하였다. 
	
	public int getParentArticleId() { 
		return parentArticleId;
	}
	public void setParentArticleId(int parentArticleId) {
		this.parentArticleId = parentArticleId;
	}
}
