package pointGroups.geometry;

/**
 * Class for points.
 * 
 * @author Alex
 * @param <P>
 */
public final class Edge<P extends Point>
{
  public final P[] points;
  public final int left;
  public final int right;

  public P getLeft() {
    return points[left];
  }

  public P getRight() {
    return points[right];
  }

  /**
   * Creates a new {@link Edge} object with given fields.
   * 
   * @param left Left value of tuple
   * @param right Right value of tuple
   */
  public Edge(P[] points, int left, int right) {
    this.left = left;
    this.right = right;
    this.points = points;
  }

  @Override
  public int hashCode() {
    return ((Integer) this.left).hashCode() ^ ((Integer) this.left).hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) return false;
    if (!(o instanceof Edge)) return false;
    Edge<?> p = (Edge<?>) o;
    return ((Integer) this.left).equals(p.left) &&
        ((Integer) this.right).equals(p.right) && this.points.equals(p.points);
  }
}
