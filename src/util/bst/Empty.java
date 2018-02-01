package util.bst;
import java.util.Optional;

public class Empty<Key extends Comparable<Key>, Value> implements Bst<Key, Value> {

	public Empty() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean smaller(Key k) {
		return true;
	}

	@Override
	public boolean bigger(Key k) {
		return true;
	}

	@Override
	public boolean has(Key k) {
		return false;
	}

	@Override
	public Optional<Value> find(Key k) {
		return Optional.empty();
	}

	@Override
	public Bst<Key, Value> put(Key k, Value v) {
		return new Fork<Key, Value>(k, v, new Empty<Key, Value>(), new Empty<Key, Value>());
	}

	@Override
	public Optional<Bst<Key, Value>> delete(Key k) {
		return Optional.empty();
	}

	@Override
	public Optional<Entry<Key, Value>> smallest() {
		return Optional.empty();
	}

	@Override
	public Optional<Bst<Key, Value>> deleteSmallest() {
		return Optional.empty();
	}

	@Override
	public Optional<Entry<Key, Value>> largest() {
		return Optional.empty();
	}

	@Override
	public Optional<Bst<Key, Value>> deleteLargest() {
		return Optional.empty();
	}

	@Override
	public String toString() {
		return "Empty";
	}

	@Override
	public String fancyToString() {
		return "";
	}

	@Override
	public String fancyToString(int d) {
		return fancyToString();
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public int height() {
		return -1;
	}

	@Override
	public void printInOrder() {
	}

	@Override
	public Bst<Key, Value> balanced() {
		return this;
	}

	@Override
	public void saveInOrder(Entry<Key, Value>[] a) {

	}

	@Override
	public int saveInOrder(Entry<Key, Value>[] a, int i) {
		return i;
	}

	@Override
	public Optional<Key> getKey() {
		return Optional.empty();
	}

	@Override
	public Optional<Value> getValue() {
		// TODO Auto-generated method stub
		return  Optional.empty();
	}

	@Override
	public Optional<Bst<Key, Value>> getLeft() {
		// TODO Auto-generated method stub
		return  Optional.empty();
	}

	@Override
	public Optional<Bst<Key, Value>> getRight() {
		// TODO Auto-generated method stub
		return  Optional.empty();
	}

}
