package article;

public class ReplyingRequest extends WritingRequest {  // �亯 ���� ��û������ ǥ���ϴ� Ŭ���� �θ�Ŭ�������� ���� �ۼ����̸� ���� ��ȣ�� �ִ�. 
     
	private int parentArticleId;  //�θ�Խñ��� id�� �߰� �Ͽ���. 
	
	public int getParentArticleId() { 
		return parentArticleId;
	}
	public void setParentArticleId(int parentArticleId) {
		this.parentArticleId = parentArticleId;
	}
}
