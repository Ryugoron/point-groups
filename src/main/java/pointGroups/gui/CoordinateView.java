/**
 * 
 */
package pointGroups.gui;

import javax.swing.JPanel;


/**
 * @author Oliver, Nadja
 */
public class CoordinateView
  extends JPanel
{
  private int dimension;

  public CoordinateView(int dimension) {
    this.setDimension(dimension);
  }

  private void setDimension(int dimension) {
    // TODO delete or generate inputfields
    this.dimension = dimension;
  }
}
