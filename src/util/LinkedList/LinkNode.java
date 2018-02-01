package util.linkedList;

public class LinkNode<T> {
	private T data;
	private LinkNode<T> next;
	
	public LinkNode(T data){
		this.data = data;
	}
	
	public T getData(){
		return data;
	}
	
	public LinkNode<T> getNext(){
		return next;
	}
	
	public void setNext(LinkNode<T> next) {
		this.next = next;
	}
}
