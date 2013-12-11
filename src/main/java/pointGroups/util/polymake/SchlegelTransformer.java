package pointGroups.util.polymake;

import pointGroups.geometry.Point;
import pointGroups.util.AbstractTransformer;

import java.util.Collection;


/**
 * Transforms points into a script as string for computing schlegel vertices and edges
 * @author Nadja, Simon
 */
public class SchlegelTransformer<Schlegel>
  extends AbstractTransformer<Schlegel>
{
  
  private final Collection<? extends Point> points;
  private final int facet;
  
  public SchlegelTransformer(Collection<? extends Point> points)
  {
    this.points = points;
    this.facet = -1;
  }
  
  public SchlegelTransformer(Collection<? extends Point> points, int facet)
  {
    this.points = points;
    this.facet = facet;
  }

  @Override
  public String toScript() {
    
    // Suppose the script has around 1000 characters
    StringBuilder script = new StringBuilder(1000);
    script.append("use application \"polytope\";\n");

    script.append("my $mat=new Matrix<Rational>(");
    script.append(pointsToString());
    script.append(");\n");
    script.append("my $p = new Polytope(POINTS=>$mat);\n");

    script.append("my $schlegelverts = $p->SCHLEGEL_DIAGRAM->VERTICES;\n");
    script.append("my $edges = $p->GRAPH->EDGES;\n");

    script.append("my $v = \"$schlegelverts\";\n");
    script.append("my $e = \"$edges\";\n");

    script.append("print $v.$e");
    return script.toString();
  }

  private String pointsToString(){
    StringBuilder matrix = new StringBuilder(200);
    double[] pointComps;
    
    matrix.append("[");
    
    for(Point point : points){
      matrix.append("[1");
      pointComps = point.getComponents();
      for (double comp : pointComps) {
        matrix.append("," + comp);
      }
      // for simplicity always appending a comma after each transformation of a point
      // afterwards the last comma will be replaced by a closing bracket ']' of the matrix 
      matrix.append("],");
    }
    // replacing last comma of the for-loop with a closing bracket of the matrix
    matrix.setCharAt(matrix.length()-1, ']');
    return matrix.toString();
  }

  @Override
  protected Schlegel transformResultString() {
    // TODO Auto-generated method stub
    return null;
  }

}
