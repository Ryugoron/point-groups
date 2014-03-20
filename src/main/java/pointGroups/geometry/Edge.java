package pointGroups.geometry;

/**
 * Class for points.
 * 
 * @author Alex
 * @param <P>
 */
public final class Edge
{
  public final int left;
  public final int right;

  public <P extends Point> P getLeft(P[] points) {
    return points[left];
  }

  public <P extends Point> P getRight(P[] points) {
    return points[right];
  }

  /**
   * Creates a new {@link Edge} object with given fields.
   * 
   * @param left Left value of tuple
   * @param right Right value of tuple
   */
  public Edge(int left, int right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public int hashCode() {
    return ((Integer) this.left).hashCode() ^ ((Integer) this.left).hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) return false;
    if (!(o instanceof Edge)) return false;
    Edge p = (Edge) o;
    return ((Integer) this.left).equals(p.left) &&
        ((Integer) this.right).equals(p.right);
  }
}
