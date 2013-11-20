package util.polymake;


/**
 * An instance holds everything polymake needs for computation.
 * 
 * @author Nadja, Simon
 */
public interface PolymakeTransformer
{
  /**
   * Generates a script.
   * @return a polymake-script as String.
   */
  public String toScript();

  /**
   * Sets the result of the computation.
   * @param result String returned by polymake
   */
  public void setResult(String result);
  
  /**
   * @return a representation ({@link PolymakeResult}) of the computation-result set by {@link PolymakeTransformer#setResult(String)}.
   */
  public PolymakeResult getResult();
}
