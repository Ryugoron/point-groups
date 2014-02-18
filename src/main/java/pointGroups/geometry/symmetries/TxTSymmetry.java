package pointGroups.geometry.symmetries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import pointGroups.geometry.Point4D;
import pointGroups.geometry.Quaternion;
import pointGroups.geometry.Symmetry;


public class TxTSymmetry
  implements Symmetry<Point4D, TxTSymmetry>
{

  /**
   * The singleton instance of the IxI's symmetry class.
   */
  private final static TxTSymmetry sym = new TxTSymmetry();
  private Collection<Rotation4D> generator = new ArrayList<>();
  private SymmetryGenerated4D symGen;
  
  protected TxTSymmetry() {
    generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE));
    generator.add(new Rotation4D(SymmetryGenerated4D.qw, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I));
    generator.add(new Rotation4D(Quaternion.ONE, SymmetryGenerated4D.qw));
    symGen = new SymmetryGenerated4D(generator, false, false, getName()+".sym");
  }


  public enum Subgroups
    implements Subgroup<TxTSymmetry> {
    Full("Full [TxT] symmetry");

    private final String name;

    Subgroups(final String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return this.name;
    }

    @Override
    public int order() {
      return sym.order(this);
    }
  }

  public static TxTSymmetry getSym() {
    return sym;
  }

  @Override
  public Collection<Point4D> images(final Point4D p,
      final Subgroup<TxTSymmetry> s) {
    return calculateImages(new Quaternion(p.re, p.i, p.j, p.k));
  }

  @Override
  public Collection<Point4D> images(final Point4D p, final String s) {
    return images(p, Subgroups.valueOf(s));
  }

  @Override
  public Collection<Subgroup<TxTSymmetry>> getSubgroups() {
    Collection<Subgroup<TxTSymmetry>> c = new LinkedList<>();
    c.add(Subgroups.Full);
    return c;
  }

  @Override
  public int order(final pointGroups.geometry.Symmetry.Subgroup<TxTSymmetry> s) {
    return 576;
  }

  @Override
  public String getName() {
    return "[T x T]";
  }

  @Override
  public Class<Point4D> getType() {
    return Point4D.class;
  }

  @Override
  public Subgroup<TxTSymmetry> getSubgroupByName(final String subgroup) {
    return Subgroups.valueOf(subgroup);
  }

  @Override
  public Point4D getNormalPoint() {
    return new Point4D(0.2, 0.3, 0.4, 0.5); // just anything
  }

  private Collection<Point4D> calculateImages(final Quaternion q) {
    Collection<Point4D> rotatedPointcollection = new ArrayList<>();
    for (Rotation4D r : symGen.generateSymmetryGroup4D()) {
      rotatedPointcollection.add(r.rotate(q).asPoint4D());
    }
    return rotatedPointcollection;
  }
}
