package pointGroups.util;

public class Pair<E,F>
{
  private E left;
  private F right;
  
  public Pair(E left, F right){
    this.left = left;
    this.right = right;
  }
  
  public E getLeft() {
    return left;
  }
  public void setLeft(E left) {
    this.left = left;
  }
  public F getRight() {
    return right;
  }
  public void setRight(F right) {
    this.right = right;
  }
  
  
}
