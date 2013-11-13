package geometry;

public final class Pair<A, B>
{
  public final A left;
  public final B right;

  public Pair(A left, B right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public int hashCode() {
    return left.hashCode() ^ right.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) return false;
    if (!(o instanceof Pair)) return false;
    Pair<?, ?> p = (Pair<?, ?>) o;
    return this.left.equals(p.left) && this.right.equals(p.right);
  }
}
