/**
 * 
 */
package pointGroups.util.polymake;

/**
 * Right now this only contains a String. We may change this to a propper
 * representation of the result like points and edges.
 * 
 * @author nadjascharf
 */
public class SchlegelResult
  implements PolymakeResult

{
  public final String result;

  public SchlegelResult(String result) {
    this.result = result;
  }
}
