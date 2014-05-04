/**
 * 
 */
package pointGroups.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author nadjascharf
 *
 */
public class HashMapKeysLikeInserted<K, V> extends HashMap<K, V>
{

  LinkedHashSet<K> keysLikeInserted;
  /**
   * 
   */
  private static final long serialVersionUID = -1963764186309936242L;

  public HashMapKeysLikeInserted() {
    super();
    keysLikeInserted = new LinkedHashSet<K>();
  }

  public HashMapKeysLikeInserted(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
    keysLikeInserted = new LinkedHashSet<K>();
  }

  public HashMapKeysLikeInserted(int initialCapacity) {
    super(initialCapacity);
    keysLikeInserted = new LinkedHashSet<K>();
  }

  public HashMapKeysLikeInserted(Map<? extends K, ? extends V> m) {
    super(m);
    keysLikeInserted = new LinkedHashSet<K>();
  }

  @Override
  public V put(K key, V value) {
    keysLikeInserted.add(key);
    return super.put(key, value);
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    keysLikeInserted.addAll(m.keySet());
    super.putAll(m);
  }

  @Override
  public V remove(Object key) {
    keysLikeInserted.remove(key);
    return super.remove(key);
  }

  @Override
  public void clear() {
    keysLikeInserted.clear();
    super.clear();
  }

  @SuppressWarnings("unchecked")
  @Override
  public Object clone() {
    HashMapKeysLikeInserted<K,V> result = null;
    result = (HashMapKeysLikeInserted<K,V>)super.clone();

    result.keysLikeInserted = (LinkedHashSet<K>) this.keysLikeInserted.clone();
    
    return result;
  }

  @Override
  public Set<K> keySet() {
    return keysLikeInserted;
  }  
}
