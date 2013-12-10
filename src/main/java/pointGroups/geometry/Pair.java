package pointGroups.geometry;

/**
 * <b>Obsolete class</b>. May be used in the future, though. An implementation
 * of a tuple object (2-pair).
 * 
 * @author Alex
 * @param <A> Type of left entry
 * @param <B> Type of right entry
 */
public final class Pair<A, B>
{
  public final A left;
  public final B right;

  /**
   * Creates a new {@link Pair} object with given fields.
   * 
   * @param left Left value of tuple
   * @param right Right value of tuple
   */
  public Pair(A left, B right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public int hashCode() {
    return this.left.hashCode() ^ this.right.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) return false;
    if (!(o instanceof Pair)) return false;
    Pair<?, ?> p = (Pair<?, ?>) o;
    return this.left.equals(p.left) && this.right.equals(p.right);
  }
}
