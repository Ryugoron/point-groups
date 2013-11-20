package util.polymake;

import geometry.Point;

import java.util.Collection;


/**
 * Transforms points into a script as string for computing schlegel vertices and edges
 * @author Nadja, Simon
 */
public class SchlegelTransformer
  implements PolymakeTransformer
{
  
  private final Collection<Point> points;
  private final int facet;
  
  public SchlegelTransformer(Collection<Point> points)
  {
    this.points = points;
    this.facet = -1;
  }
  
  public SchlegelTransformer(Collection<Point> points, int facet)
  {
    this.points = points;
    this.facet = facet;
  }

  @Override
  public String toScript() {
    // TODO Auto-generated method stub
    String script = "";
    script += "use application \"polytope\";\n";

    my $mat=new Matrix<Rational>([[1,0,0,0],[1,0,0,1],[1,0,1,0],[1,0,1,1],[1, 0.5, 0.5, 0.5]]);
    my $p = new Polytope(POINTS=>$mat);

    my $schlegelverts = $p->SCHLEGEL_DIAGRAM->VERTICES;
    my $edges = $p->GRAPH->EDGES;


    my $v = "$schlegelverts";
    my $e = "$edges";

    print $v.$e"
    return null;
  }

  @Override
  public Object fromPolymake(String str) {
    // TODO Auto-generated method stub
    return null;
  }

}
