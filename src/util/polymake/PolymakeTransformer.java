package util.polymake;

/**
 * Transforms points into a script as string for polymake
 * @author Nadja, Simon
 */

public interface PolymakeTransformer
{
  public String toScript();

  public Object fromPolymake(String str);
}
