package util.bst;
import java.lang.reflect.Array;
import java.util.Optional;

public class Fork<Key extends Comparable<Key>, Value> implements Bst<Key, Value> {

	private final Key key;
	private final Value value;
	private final Bst<Key, Value> left;
	private final Bst<Key, Value> right;

	public Fork(Key key, Value value, Bst<Key, Value> left, Bst<Key, Value> right) {
		// check if left and right trees are not null.
		assert (left != null);
		assert (right != null);

		// check if left and right trees are BST
		// assert(this.isBST() == true);

		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;
	}

	private boolean isBST() {
		Entry<Integer, String> e = new Entry<Integer, String>(5, "5");
		@SuppressWarnings("unchecked")
		Entry<Key, Value>[] arr = (Entry<Key, Value>[]) Array.newInstance(Entry.class, this.size());

		this.saveInOrder(arr);

		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i].getKey().compareTo(arr[i + 1].getKey()) > 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean smaller(Key k) {
		if(k.compareTo(key) > 0)
		{
			if(left instanceof Empty)
			{
				return true;
			}else{
				return left.smaller(k);
			}
		}else{
			return false;
		}
	}

	@Override
	public boolean bigger(Key k) {
		if(k.compareTo(key) < 0)
		{
			if(right instanceof Empty)
			{
				return true;
			}else{
				return right.bigger(k);
			}
		}else{
			return false;
		}
	}

	@Override
	public boolean has(Key k) {

		switch (k.compareTo(key)) {
		case -1:
			return left.has(k);

		case 0:
			return true;

		case 1:
			return right.has(k);

		default:
			return false;
		}
	}

	@Override
	public Optional<Value> find(Key k) {

		switch (k.compareTo(key)) {
		case -1:
			return left.find(k);

		case 0:
			return Optional.of(value);

		case 1:
			return right.find(k);

		default:
			return Optional.empty();
		}
	}

	@Override
	public Bst<Key, Value> put(Key k, Value v) {

		if (k.compareTo(key) == 0) {
			return this;
		} else if (k.compareTo(key) < 0) {
			return new Fork<Key, Value>(key, value, left.put(k, v), right);
		} else {
			return new Fork<Key, Value>(key, value, left, right.put(k, v));
		}
	}

	@Override
	public Optional<Bst<Key, Value>> delete(Key k) {
		if (this.find(k).isPresent()) {
			switch (k.compareTo(key)) {
			case -1:
				return Optional.of(new Fork<Key, Value>(key, value, left.delete(k).get(), right));

			case 0:
				if (left.isEmpty()) {
					return Optional.of(right);
				} else {
					if (right.isEmpty()) {
						return Optional.of(left);
					} else {
						Optional<Entry<Key, Value>> entry = right.smallest();

						if (!entry.isPresent()) {
							return Optional.of(new Empty<Key, Value>());
						} else {
							return Optional.of(new Fork<Key, Value>(entry.get().getKey(), entry.get().getValue(), left,
									right.deleteSmallest().get()));
						}
					}
				}
			case 1:
				return Optional.of(new Fork<Key, Value>(key, value, left, right.delete(k).get()));
			default:
				return Optional.of(this);
			}
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Entry<Key, Value>> smallest() {

		if (left instanceof Empty) {
			return Optional.of(new Entry<Key, Value>(key, value));
		} else {
			return left.smallest();
		}
	}

	@Override
	public Optional<Bst<Key, Value>> deleteSmallest() {
		if (left instanceof Empty) {
			return Optional.of(right);
		} else {
			return Optional.of(new Fork<Key, Value>(key, value, left.deleteSmallest().get(), right));
		}
	}

	@Override
	public Optional<Entry<Key, Value>> largest() {
		if (right instanceof Empty) {
			return Optional.of(new Entry<Key, Value>(key, value));
		} else {
			return right.largest();
		}
	}

	@Override
	public Optional<Bst<Key, Value>> deleteLargest() {
		if (right instanceof Empty) {
			return Optional.of(left);
		} else {
			return Optional.of(new Fork<Key, Value>(key, value, left, right.deleteLargest().get()));
		}
	}

	@Override
	public String fancyToString() {
		return "\n\n\n" + fancyToString(0) + "\n\n\n";
	}

	@Override
	public String fancyToString(int d) {
		int step = 4; // depth step
		String l = left.fancyToString(d + step);
		String r = right.fancyToString(d + step);
		return r + spaces(d) + value + "\n" + l;
	}

	private String spaces(int n) { // Helper method for the above:
		String s = "";
		for (int i = 0; i < n; i++)
			s = s + " ";
		return s;
	}

	@Override
	public int size() {

		return 1 + right.size() + left.size();
	}

	@Override
	public int height() {
		return Integer.max(left.height(), right.height()) + 1;
	}

	@Override
	public void printInOrder() {
		left.printInOrder();
		System.out.println(value);
		right.printInOrder();
	}

	@Override
	public Bst<Key, Value> balanced() {
		@SuppressWarnings("unchecked")
		Entry<Key, Value>[] arr = (Entry<Key, Value>[]) Array.newInstance(Entry.class, this.size());

		saveInOrder(arr);
		return createBalancedBST(arr, 0, arr.length - 1);

	}

	private Bst<Key, Value> createBalancedBST(Entry<Key, Value>[] arr, int first, int last) {
		if (first > last) {
			return new Empty<Key, Value>();
		} else {
			int mid = first + (last - first) / 2;

			return new Fork<Key, Value>(arr[mid].getKey(), arr[mid].getValue(), createBalancedBST(arr, first, mid - 1),
					createBalancedBST(arr, mid + 1, last));
		}
	}

	@Override
	public void saveInOrder(Entry<Key, Value>[] a) {
		saveInOrder(a, 0);
	}

	@Override
	public int saveInOrder(Entry<Key, Value>[] a, int i) {
		int t1 = left.saveInOrder(a, i);
		a[t1] = new Entry<Key, Value>(key, value);
		return right.saveInOrder(a, ++t1);
	}

	@Override
	public Optional<Key> getKey() {
		return Optional.of(key);
	}

	@Override
	public Optional<Value> getValue() {
		return Optional.of(value);
	}

	@Override
	public Optional<Bst<Key, Value>> getLeft() {
		return Optional.of(left);
	}

	@Override
	public Optional<Bst<Key, Value>> getRight() {
		return Optional.of(right);
	}

}
