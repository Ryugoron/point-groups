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

import pointGroups.geometry.Point2D;
import pointGroups.geometry.Point3D;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ChangeCoordinateEvent;
import pointGroups.gui.event.types.Point3DPickedEvent;
import pointGroups.gui.event.types.Point4DPickedEvent;
import pointGroups.gui.event.types.RunEvent;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.gui.event.types.Symmetry3DChooseHandler;
import pointGroups.gui.event.types.Symmetry4DChooseEvent;
import pointGroups.gui.event.types.Symmetry4DChooseHandler;
import pointGroups.util.jreality.JRealityUtility;


/**
 * @author Oliver, Nadja
 */
public class CoordinateView
  extends JPanel
  implements ActionListener, Symmetry3DChooseHandler, Symmetry4DChooseHandler
{
  /**
	 * 
	 */
  private static final long serialVersionUID = -3113107578779994265L;
  private final DimensioninputField inputField;
  private final JButton run;
  private final JButton randomCoord;
  protected final EventDispatcher dispatcher;

  private Symmetry3DChooseEvent lastSymmetry3DChooseEvent;
  private Symmetry4DChooseEvent lastSymmetry4DChooseEvent;

  public CoordinateView(int dimension, EventDispatcher dispatcher) {
    this.setLayout(new FlowLayout());
    inputField = new DimensioninputField(dimension);
    run = new JButton("Run");
    randomCoord = new JButton("Create random coordinate");
    this.dispatcher = dispatcher;

    this.add(inputField);
    this.add(run);
    this.add(randomCoord);

    dispatcher.addHandler(Symmetry3DChooseHandler.class, this);
    dispatcher.addHandler(Symmetry4DChooseHandler.class, this);

    run.addActionListener(this);
    randomCoord.addActionListener(this);
  }

  @Override
  public void onSymmetry3DChooseEvent(Symmetry3DChooseEvent event) {
    // TODO: maybe we should not assume implicitly the change of the dimension
    // through this event, but by the proper DimensionsSwitchEvent.
    // This Event has to be used anyway to change the input fields for
    // the coordinates (x,y on 2D dimensional point picker).
    lastSymmetry3DChooseEvent = event;
    lastSymmetry4DChooseEvent = null;
  }

  @Override
  public void onSymmetry4DChooseEvent(Symmetry4DChooseEvent event) {
    lastSymmetry4DChooseEvent = event;
    lastSymmetry3DChooseEvent = null;
  }

  protected void firePoint3DPickedEvent() {
    if (lastSymmetry3DChooseEvent == null) return;

    // TODO: currently only a fix, we should get only 2 coordinates
    // not 3
    double[] point = inputField.getCoords();
    Point2D point2D = new Point2D(point[0], point[1]);

    dispatcher.fireEvent(new Point3DPickedEvent(lastSymmetry3DChooseEvent,
        point2D));
  }

  protected void firePoint4DPickedEvent() {
    if (lastSymmetry4DChooseEvent == null) return;

    double[] point = inputField.getCoords();
    Point3D point3D = JRealityUtility.asPoint3D(point);

    dispatcher.fireEvent(new Point4DPickedEvent(lastSymmetry4DChooseEvent,
        point3D));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == run) {
      dispatcher.fireEvent(new RunEvent(inputField.getCoords()));

      firePoint3DPickedEvent();
      firePoint4DPickedEvent();
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
    private final List<Dimensioninput> dimensioninputs;
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

    @Override
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
