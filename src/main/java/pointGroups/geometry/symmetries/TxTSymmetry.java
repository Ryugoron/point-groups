package pointGroups.geometry.symmetries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pointGroups.geometry.Point4D;
import pointGroups.geometry.Quaternion;
import pointGroups.geometry.Symmetry;

public class TxTSymmetry implements Symmetry<Point4D, TxTSymmetry>
{

  /**
   * The singleton instance of the IxI's symmetry class.
   */
  private final static TxTSymmetry sym = new TxTSymmetry();
  private final List<Rotation4D> gen = new ArrayList<Rotation4D>();
 
  private static Collection<Rotation4D> group;
  

  protected TxTSymmetry(){
    gen.add(new Rotation4D(Quaternion.I, Quaternion.ONE));
    gen.add(new Rotation4D(GeneratorCreator.qw, Quaternion.ONE));
    
    gen.add(new Rotation4D(Quaternion.ONE,Quaternion.I));
    gen.add(new Rotation4D(Quaternion.ONE, GeneratorCreator.qw));
    group = GeneratorCreator.generateSymmetryGroup4D(gen);
  }
  public enum Subgroups
  implements Subgroup<TxTSymmetry> {
  Id("Trivial group"), Full("Full [IxI] symmetry");

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
  
  
  public static TxTSymmetry getSym(){
    return sym;
  }
  
  @Override
  public Collection<Point4D> images(Point4D p,
      pointGroups.geometry.Symmetry.Subgroup<TxTSymmetry> s) {
    return calculateImages(new Quaternion(p.re, p.i, p.j, p.k));
  }

  @Override
  public Collection<Point4D> images(Point4D p, String s) {
    //TODO: subgroups?
    return images(p, Subgroups.Full);
  }

  @Override
  public Collection<pointGroups.geometry.Symmetry.Subgroup<TxTSymmetry>>
      getSubgroups() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int order(pointGroups.geometry.Symmetry.Subgroup<TxTSymmetry> s) {
    // TODO Auto-generated method stub
    return 0;
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
  public pointGroups.geometry.Symmetry.Subgroup<TxTSymmetry> getSubgroupByName(
      String subgroup) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Point4D getNormalPoint() {
    // TODO Auto-generated method stub
    return null;
  }
  
  private Collection<Point4D> calculateImages(Quaternion q){
    Collection<Point4D> rotatedPointcollection = new ArrayList<>();
    for(Rotation4D r : group){
      rotatedPointcollection.add(r.rotate(q).asPoint4D());
    }
    return rotatedPointcollection;
  }
}
