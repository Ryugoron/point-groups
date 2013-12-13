package pointGroups.gui.symchooser.elements;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;

import javax.swing.JButton;

import pointGroups.geometry.UnitQuaternion;
import pointGroups.gui.symchooser.SubgroupInfo;


public class SubgroupEntry
  extends JButton
  implements MouseListener
{
  private static final long serialVersionUID = 8333248703174706898L;
  private final SubgroupInfo info;

  public SubgroupEntry(SubgroupInfo info) {
    super(info.getName() + " (Order: " + info.getOrder() + ")");
    this.info = info;
    this.addMouseListener(this);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    System.out.println("You chose subgroup " + info.getName() +
        " of symmetry " + info.getSymmetryInfo().getTitle());
    System.out.println("The subgroup consists of the following symmetries:");

    Collection<UnitQuaternion> test =
        info.getSymmetryInfo().get().getSymmetriesByName(info.get().toString());

    System.out.println(test.toString());
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

}
