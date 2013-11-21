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
  
  private final Collection<? extends Point> points;
  private final int facet;
  private String result;
  
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
    // TODO mit Stringbuilder bauen
    String script = "";
    script += "use application \"polytope\";\n";

    script += "my $mat=new Matrix<Rational>(";
    script += pointsToString();
    script += ");\n";
    script += "my $p = new Polytope(POINTS=>$mat);\n";

    script += "my $schlegelverts = $p->SCHLEGEL_DIAGRAM->VERTICES;\n";
    script += "my $edges = $p->GRAPH->EDGES;\n";


    script += "my $v = \"$schlegelverts\";\n";
    script += "my $e = \"$edges\";\n";

    script += "print $v.$e";
    return script;
  }

  private String pointsToString(){
    String matrix = "[";
    String pointString;
    
    for(Point point : points){
      matrix += "[1,";
      pointString = point.toString();
      pointString = pointString.substring(1, pointString.length()-1);
      matrix += pointString;
      matrix += "],";
    }
    
    matrix = matrix.substring(0, matrix.length()-1) + "]";
    return matrix;
  }

  @Override
  public void setResult(String result) {
    this.result = result;
  }

  @Override
  public PolymakeResult getResult() {
    if (result == null){
      
    }
    return new SchlegelResult(result);
  }

}
