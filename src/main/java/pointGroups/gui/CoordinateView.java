package pointGroups.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ChangeCoordinateEvent;
import pointGroups.gui.event.types.RunEvent;


/**
 * @author Oliver, Nadja
 */
public class CoordinateView
  extends JPanel
  implements ActionListener
{
  /**
	 * 
	 */
  private static final long serialVersionUID = -3113107578779994265L;
  private DimensioninputField inputField;
  private JButton run;
  private JButton randomCoord;
  protected final EventDispatcher dispatcher;

  public CoordinateView(int dimension, EventDispatcher dispatcher) {
    this.setLayout(new FlowLayout());
    inputField = new DimensioninputField(dimension);
    run = new JButton("Run");
    randomCoord = new JButton("Create random coordinate");
    this.dispatcher = dispatcher;

    this.add(inputField);
    this.add(run);
    this.add(randomCoord);

    run.addActionListener(this);
    randomCoord.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == run) {
      dispatcher.fireEvent(new RunEvent(inputField.getCoords()));
    }
    else if (e.getSource() == randomCoord) {
      inputField.createRandomCoords();
    }

  }


  class DimensioninputField
    extends JPanel
    implements PropertyChangeListener
  {
    /**
	 * 
	 */
    private static final long serialVersionUID = 8813600622648961730L;
    private List<Dimensioninput> dimensioninputs;
    int dimension;

    public DimensioninputField(int dim) {
      this.setLayout(new FlowLayout());
      dimensioninputs = new ArrayList<Dimensioninput>();
      setDimension(dim);
    }

    public void createRandomCoords() {
      // TODO: Choosing a random point of the surface?!
      Random rand = new Random(System.currentTimeMillis());
      for (int i = 0; i < dimension; i++) {
        dimensioninputs.get(i).removePropertyChangeListener(this);
        dimensioninputs.get(i).setCoord(rand.nextDouble() * 100);
        dimensioninputs.get(i).addPropertyChangeListener(this);
      }
      dispatcher.fireEvent(new ChangeCoordinateEvent(getCoords()));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      dispatcher.fireEvent(new ChangeCoordinateEvent(getCoords()));

    }

    public double[] getCoords() {
      double[] coords = new double[dimension];
      for (int i = 0; i < coords.length; i++) {
        coords[i] = dimensioninputs.get(i).getCoord();
      }
      return coords;
    }

    public void setDimension(int dimension) {
      this.dimension = dimension;
      // generate new inputfields
      int dim = dimensioninputs.size();
      int size = dim;
      for (int i = 0; i < dimension - size; i++) {
        dim++;
        Dimensioninput dimInput = new Dimensioninput(dim, 0, this);
        dimensioninputs.add(dimInput);
        this.add(dimInput);
      }
      // activate panels
      for (int i = 0; i < dimension; i++) {
        dimensioninputs.get(i).activ();
      }
      // deactivate unnecessary panels
      for (int i = dimension; i < dimensioninputs.size(); i++) {
        dimensioninputs.get(i).deactiv();
      }
    }
  }


  class Dimensioninput
    extends JPanel
  {
    /**
	 * 
	 */
    private static final long serialVersionUID = -8537572626851513709L;
    private JLabel dimLabel;

    private JFormattedTextField coordField;
    private NumberFormat coordFormat = NumberFormat.getCurrencyInstance();

    public Dimensioninput(int dim, double startvalue,
        PropertyChangeListener listener) {
      this.setLayout(new GridLayout(2, 0));
      // TODO: Wie viele Nachkommastellen?
      coordFormat = new DecimalFormat("0.00");
      coordField = new JFormattedTextField(coordFormat);
      coordField.setColumns(5);
      coordField.setValue(startvalue);
      coordField.addPropertyChangeListener("value", listener);

      dimLabel = new JLabel("x" + dim);
      this.add(dimLabel);
      this.add(coordField);

    }

    public double getCoord() {
      return ((Number) coordField.getValue()).doubleValue();
    }

    public void setCoord(double coord) {
      coordField.setValue(coord);
    }

    public void deactiv() {
      this.setEnabled(false);
    }

    public void activ() {
      this.setEnabled(true);
    }

    @Override
    public void setEnabled(boolean enabled) {
      super.setEnabled(enabled);
      for (Component child : ((Container) this).getComponents()) {
        child.setEnabled(enabled);
      }
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
      coordField.removePropertyChangeListener("value", listener);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
      if (coordField != null)
        coordField.addPropertyChangeListener("value", listener);
    }

  }
}

