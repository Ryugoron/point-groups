package pointGroups.gui;

import java.awt.BorderLayout;
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

import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.ChangeCoordinate3DPointEvent;
import pointGroups.gui.event.types.ChangeCoordinate3DPointHandler;
import pointGroups.gui.event.types.ChangeCoordinate4DPointEvent;
import pointGroups.gui.event.types.ChangeCoordinate4DPointHandler;
import pointGroups.gui.event.types.DimensionSwitchEvent;
import pointGroups.gui.event.types.DimensionSwitchHandler;
import pointGroups.gui.event.types.ScaleFundamentalDomainEvent;
import pointGroups.gui.event.types.ScaleFundamentalDomainHandler;
import pointGroups.gui.event.types.Schlegel3DComputeEvent;
import pointGroups.gui.event.types.Schlegel4DComputeEvent;
import pointGroups.gui.event.types.Symmetry3DChooseEvent;
import pointGroups.gui.event.types.Symmetry3DChooseHandler;
import pointGroups.gui.event.types.Symmetry4DChooseEvent;
import pointGroups.gui.event.types.Symmetry4DChooseHandler;
import pointGroups.util.jreality.JRealityUtility;
import pointGroups.util.point.PointUtil;


/**
 * @author Oliver, Nadja
 */
public class CoordinateView
  extends JPanel
  implements ActionListener, Symmetry3DChooseHandler, Symmetry4DChooseHandler,
  DimensionSwitchHandler, ChangeCoordinate3DPointHandler,
  ChangeCoordinate4DPointHandler, ScaleFundamentalDomainHandler
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

  private double[] lastCoords = new double[]{0.0, 0.0, 0.0};
  
  public CoordinateView(int dimension, int maxDimension, EventDispatcher dispatcher) {
    this.setLayout(new BorderLayout());
    inputField = new DimensioninputField(dimension, maxDimension);
    run = new JButton("Run");
    randomCoord = new JButton("Create random coordinate");
    this.dispatcher = dispatcher;

    this.add(inputField, BorderLayout.PAGE_START);
    this.add(run,BorderLayout.LINE_END);
    this.add(randomCoord, BorderLayout.CENTER);

    dispatcher.addHandler(DimensionSwitchHandler.class, this);
    dispatcher.addHandler(ChangeCoordinate3DPointHandler.class, this);
    dispatcher.addHandler(ChangeCoordinate4DPointHandler.class, this);
    dispatcher.addHandler(Symmetry3DChooseHandler.class, this);
    dispatcher.addHandler(Symmetry4DChooseHandler.class, this);
    dispatcher.addHandler(ScaleFundamentalDomainHandler.class, this);

    run.addActionListener(this);
    randomCoord.addActionListener(this);
    run.setEnabled(false);
  }

  @Override
  public void onSymmetry3DChooseEvent(Symmetry3DChooseEvent event) {
    lastSymmetry3DChooseEvent = event;
    lastCoords = event.getSymmetry3D().getNormalPoint().getComponents();
    this.inputField.setCoordinate(lastCoords);
    run.setEnabled(true);
  }

  @Override
  public void onSymmetry4DChooseEvent(Symmetry4DChooseEvent event) {
    lastSymmetry4DChooseEvent = event;
    lastCoords = event.getSymmetry4D().getNormalPoint().getComponents();
    this.inputField.setCoordinate(lastCoords);
    run.setEnabled(true);
  }
  

  @Override
  public void onScaleFundamentalDomainEvent(ScaleFundamentalDomainEvent event) {
    inputField.scale = event.getScale();
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == run) {
      double[] point = inputField.getCoords();
      if (lastSymmetry3DChooseEvent != null) {
        Point3D point3D = JRealityUtility.asPoint3D(point);
        dispatcher.fireEvent(new Schlegel3DComputeEvent(
            lastSymmetry3DChooseEvent, point3D));
      }
      else if (lastSymmetry4DChooseEvent != null) {
        Point4D point4D = JRealityUtility.asPoint4D(point);
        dispatcher.fireEvent(new Schlegel4DComputeEvent(
            lastSymmetry4DChooseEvent, point4D));

      }
      else {
        throw new RuntimeException("No Symmetrie was choosen");
      }
    }
    else if (e.getSource() == randomCoord) {
      inputField.createRandomCoords();
    }

  }

  @Override
  public void onDimensionSwitchEvent(DimensionSwitchEvent event) {
    // reset all coordinates to 0.0
    inputField.setCoordinate(new double[inputField.dimension]);

    if (event.switchedTo3D()) {
      inputField.setDimension(3);
      this.validate();
      this.repaint();
      lastSymmetry4DChooseEvent = null;
      run.setEnabled(false);
    }
    else if (event.switchedTo4D()) {
      inputField.setDimension(4);
      lastSymmetry3DChooseEvent = null;
      run.setEnabled(false);
      this.validate();
      this.repaint();
    }
  }

  @Override
  public void
      onChangeCoordinate4DPointEvent(ChangeCoordinate4DPointEvent event) {
    if (!event.isSource(this)) {
      lastCoords = event.getPickedPoint().getComponents();
      inputField.setCoordinate(event.getPickedPoint());
    }

  }

  @Override
  public void
      onChangeCoordinate3DPointEvent(ChangeCoordinate3DPointEvent event) {
    if (!event.isSource(this)) {
      lastCoords = event.getPickedPoint().getComponents();
      inputField.setCoordinate(event.getPickedPoint());
    }
  }


  class Dimensioninput
    extends JPanel
  {
    /**
	 * 
	 */
    private static final long serialVersionUID = -8537572626851513709L;
    private final JLabel dimLabel;

    private final JFormattedTextField coordField;
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
    int scale = 10;

    public DimensioninputField(int dim, int maxdim) {
      this.setLayout(new FlowLayout());
      dimensioninputs = new ArrayList<Dimensioninput>();
      setDimension(maxdim);
      setDimension(dim);
    }

    public void createRandomCoords() {
      // TODO: Choosing a random point of the surface?!
      Random rand = new Random(System.currentTimeMillis());
      for (int i = 0; i < dimension; i++) {
        dimensioninputs.get(i).removePropertyChangeListener("value", this);
        dimensioninputs.get(i).setCoord(rand.nextDouble() * scale);
        dimensioninputs.get(i).addPropertyChangeListener("value", this);
      }
      changeCoordinateEvent();
    }

    public void setCoordinate(Point3D p) {
      if (dimension == 3) {
        setCoordinate(p.getComponents());
      }
    }

    public void setCoordinate(Point4D p) {
      if (dimension == 4) {
        setCoordinate(p.getComponents());
      }
    }

    public void setCoordinate(double[] coords) {
      for (int i = 0; i < dimension; i++) {
        dimensioninputs.get(i).removePropertyChangeListener("value", this);
        dimensioninputs.get(i).setCoord(coords[i]);
        dimensioninputs.get(i).addPropertyChangeListener("value", this);
      }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      changeCoordinateEvent();
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
        dimInput.validate();
        dimInput.repaint();
      }
      // activate panels
      for (int i = 0; i < dimension; i++) {
        dimensioninputs.get(i).activ();
      }
      // deactivate unnecessary panels
      for (int i = dimension; i < dimensioninputs.size(); i++) {
        dimensioninputs.get(i).deactiv();
      }
      this.validate();
      this.repaint();
    }

    protected void changeCoordinateEvent() {
      double[] point = inputField.getCoords();

      if(PointUtil.distance(lastCoords, point) < 1e-09) return;
      
      if (dimension == 3) {
        Point3D point3D = JRealityUtility.asPoint3D(point);
        dispatcher.fireEvent(new ChangeCoordinate3DPointEvent(point3D,
            CoordinateView.this));
      }
      else if (dimension == 4) {
        Point4D point4D = JRealityUtility.asPoint4D(point);
        dispatcher.fireEvent(new ChangeCoordinate4DPointEvent(point4D,
            CoordinateView.this));

      }
    }
  }


}
