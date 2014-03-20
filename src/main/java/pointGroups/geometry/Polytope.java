package pointGroups.geometry;

public class Polytope<P extends Point>
{
  public final P[] points;
  public final Edge[] edges;
  public final Face[] faces;

  public Polytope(P[] points, Edge[] edges, Face[] faces) {
    this.points = points;
    this.edges = edges;
    this.faces = faces;
  }
}
