package util.bst;
import java.util.*;

public interface Bst<Key extends Comparable<Key>,Value> {
  public boolean                    isEmpty();
  public boolean                    smaller(Key k); 
  public boolean                    bigger(Key k);  
  public boolean                    has(Key k);     
  public Optional<Value>            find(Key k);  
  public Bst<Key,Value>             put(Key k,Value v);  
  public Optional<Bst<Key,Value>>   delete(Key k);  
  public Optional<Entry<Key,Value>> smallest();   
  public Optional<Bst<Key,Value>>   deleteSmallest();
  public Optional<Entry<Key,Value>> largest(); 
  public Optional<Bst<Key,Value>>   deleteLargest();
  public String                     fancyToString();
  public String                     fancyToString(int d);
  public int                        size();       
  public int                        height();     
  public void                       printInOrder();     
  public void                       saveInOrder(Entry<Key,Value> a[]); 
  public int                        saveInOrder(Entry<Key,Value> a[], int i); 
  public Bst<Key,Value>             balanced(); 

  public Optional<Key> getKey();
  public Optional<Value> getValue();
  public Optional<Bst<Key,Value>> getLeft();
  public Optional<Bst<Key,Value>> getRight(); 
}
