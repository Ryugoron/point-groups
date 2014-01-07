package pointGroups.gui.symchooser;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pointGroups.geometry.Point;
import pointGroups.geometry.Point3D;
import pointGroups.geometry.Point4D;
import pointGroups.gui.event.EventDispatcher;
import pointGroups.gui.event.types.DimensionSwitchEvent;
import pointGroups.gui.symchooser.elements.SymmetryEntry;
import pointGroups.util.gui.MouseClickListener;


public class SymmetryChoosePanel
  extends JTabbedPane
{

  private final Logger logger = Logger.getLogger(this.getClass().getName());
  protected final JPanel symmetries3DPanel, symmetries4DPanel;
  protected final SymmetryInfoProvider prov;
  protected final SubgroupChoosePanel subgroupChoosePanel;
  protected final EventDispatcher dispatcher = EventDispatcher.get();
  private int lastChosen = 0;

  public SymmetryChoosePanel(SubgroupChoosePanel subgroupChoosePanel) {
    super();

    Dimension dim = new Dimension(250, 0);
    setMinimumSize(dim);
    setPreferredSize(dim);

    this.subgroupChoosePanel = subgroupChoosePanel;
    prov = new SymmetryInfoProvider();
    symmetries3DPanel = new JPanel();
    symmetries4DPanel = new JPanel();
    fillTabs();

    add("3D", symmetries3DPanel);
    add("4D", symmetries4DPanel);

    addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        JTabbedPane t = SymmetryChoosePanel.this;
        if (e.getSource() == t) {
          if (t.getSelectedIndex() != lastChosen) {
            lastChosen = t.getSelectedIndex();
            logger.fine("SymmetryChoosePanel: Tab switched");

            Class<? extends Point> pointType;
            switch (lastChosen) {
            case 0:
              pointType = Point3D.class;
              break;
            case 1:
              pointType = Point4D.class;
              break;

            default:
              pointType = null;
              logger.warning("SymmetryChoosePanel: Unknown Tab index selected");
              break;
            }

            dispatcher.fireEvent(new DimensionSwitchEvent(pointType));
          }
        }
      }
    });
  }

  private void fillTabs() {
    // Fill 3D-Tab with Top-Level-3D-Symmetries
    Collection<SymmetryInfo<Point3D>> symmetries3D = prov.get(Point3D.class);

    for (final SymmetryInfo<Point3D> symmetryInfo : symmetries3D) {
      // try {
      final SymmetryEntry s = new SymmetryEntry(symmetryInfo);
      s.addMouseListener(new MouseClickListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
          subgroupChoosePanel.choose(symmetryInfo);
          logger.fine("SymmetryChoosePanel: Symmetry chosen");
        }
      });
      symmetries3DPanel.add(s);
      // }
      // catch (IOException e) {
      // logger.severe("SymmetryChoosePanel: Symmetry Icon not found, could not create button for "
      // +
      // symmetryInfo.getTitle());
      // }
    }

    // Fill 4D-Tab with Top-Level-3D-Symmetries
    Collection<SymmetryInfo<Point4D>> symmetries4D = prov.get(Point4D.class);

    for (final SymmetryInfo<Point4D> symmetryInfo : symmetries4D) {
      // try {
      final SymmetryEntry s = new SymmetryEntry(symmetryInfo);
      s.addMouseListener(new MouseClickListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
          subgroupChoosePanel.choose(symmetryInfo);
          logger.fine("SymmetryChoosePanel: Symmetry chosen");
        }
      });
      symmetries4DPanel.add(s);
      // }
      // catch (IOException e) {
      // logger.severe("SymmetryChoosePanel: Symmetry Icon not found, could not create button for "
      // +
      // symmetryInfo.getTitle());
      // }
    }
  }
}
