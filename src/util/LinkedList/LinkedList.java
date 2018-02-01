package util.LinkedList;


/**
 * 
 * @author 0VOID0
 *
 *	NOT FINISHED
 *
 *	TODO
 *
 * @param <T>
 */
public class LinkedList<T> {

	private LinkNode<T> firstNode = null;
	private LinkNode<T> lastNode = null;

	private int size;

	public LinkedList() {
		size = 0;
	}

	public void add(T data) {
		LinkNode<T> node = new LinkNode<T>(data);
		if (firstNode == null) {
			firstNode = node;
			return;
		}

		lastNode.setNext(node);
		lastNode = node;
		size++;
	}

	public T delete(LinkNode<T> node) {
		LinkNode<T> tempNode = firstNode;
		LinkNode<T> previous = null;
		while (tempNode != null) {
			if (tempNode.equals(node)) {
				if (previous != null) {
					previous.setNext(tempNode.getNext());
				}else {
					firstNode = tempNode.getNext();
				}
				tempNode.setNext(null);
				return tempNode.getData();
			}
			previous = tempNode;
			tempNode = tempNode.getNext();
		}
		return null;
	}

	public T delete(int i) {
		if (i >= size)
			return null;

		LinkNode<T> tempNode = firstNode;
		LinkNode<T> previous = null;
		while (i > 0) {
			tempNode = tempNode.getNext();
			previous = tempNode;
			i--;
		}
		
		if (previous != null) {
			previous.setNext(tempNode.getNext());
		}else {
			firstNode = tempNode.getNext();
		}
		tempNode.setNext(null);
		return tempNode.getData();
	}

	public T get(int i) {
		if (i >= size)
			return null;

		LinkNode<T> tempNode = firstNode;
		while (i > 0) {
			tempNode = tempNode.getNext();
			i--;
		}
		return tempNode.getData();
	}

}
