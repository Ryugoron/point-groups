package pointGroups.util.polymake;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pointGroups.geometry.Point;
import pointGroups.util.AbstractTransformer;

public class FundamentalTransformerTransformer
  extends AbstractTransformer<FundamentalTransformer>
{

  private String script;
  private final Point[] pointsOrder;
  
  private FundamentalTransformer answer;
  
  public FundamentalTransformerTransformer(Collection<? extends Point> points){
    this.pointsOrder = points.toArray(new Point[points.size()]);
    this.script = null;
  }
  
  @Override
  public String toScript() {
    if (script != null) return script;
    
    StringBuilder sb = new StringBuilder(1000);
    sb.append("my $points = new Matrix<Rational>([");
    boolean first = true;
    for (Point point : this.pointsOrder) {
      if (!first) {
        sb.append(",");
      }
      else {
        first = false;
      }
      sb.append("[1.0");
      for (double comp : point.getComponents()) {
        sb.append(", " + comp);
      }
      sb.append("]");
    }
    sb.append("]);");
    sb.append("my $v = new VoronoiDiagram(SITES=>$points);");
    sb.append("print $v->DUAL_GRAPH->ADJACENCY->adjacent_nodes(0);");
    sb.append("print \"\\n\"");
    this.script = sb.toString();
    return this.script;
  }

  @Override
  protected FundamentalTransformer transformResultString() {
    if(this.answer != null) return this.answer;
    
    String[] adjstring = this.resultString.substring(1, this.resultString.length()-2).split(" ");
    
    System.out.println("");
    
    // The last entry is the infinity facette
    int[] adj = new int[adjstring.length-1];
    
    for(int i = 0; i < adjstring.length-1; i++){
      adj[i] = Integer.parseInt(adjstring[i]);
    }
    
    List<Point> adjNodes = new ArrayList<Point>(adj.length);
    for(int a : adj){
      adjNodes.add(this.pointsOrder[a]);
    }
    this.answer = new FundamentalTransformer(this.pointsOrder[0], adjNodes);
    
    return this.answer;
  }
}
