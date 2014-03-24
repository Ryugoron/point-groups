package pointGroups.geometry.symmetries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import pointGroups.geometry.Point4D;
import pointGroups.geometry.Quaternion;
import pointGroups.geometry.Symmetry;

public class OxOSymmetry implements Symmetry<Point4D, OxOSymmetry>
{
  /**
   * The singleton instance of the OxO's symmetry class.
   */
  private final static OxOSymmetry sym = new OxOSymmetry(); 
  private Collection<Rotation4D> groupelems;

  
  public final static Collection<Rotation4D> generator(){
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(GeneratorCreator.qO, Quaternion.ONE));
    generator.add(new Rotation4D(GeneratorCreator.qw, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE,GeneratorCreator.qO));
    generator.add(new Rotation4D(Quaternion.ONE,GeneratorCreator.qw));     
    return generator;
  }
  
  protected OxOSymmetry(){
    
    // read file
    try {
      groupelems = SymmetryGenerated4D.readSymmetryGroup(getName());
    }
    catch (ClassNotFoundException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }  }
  public enum Subgroups
  implements Subgroup<OxOSymmetry> {
  Id("Trivial group"), Full("Full [OxO] symmetry");

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
  
  
  public static OxOSymmetry getSym(){
    return sym;
  }
  
  @Override
  public Collection<Point4D> images(Point4D p,
      pointGroups.geometry.Symmetry.Subgroup<OxOSymmetry> s) {
    return calculateImages(new Quaternion(p.re, p.i, p.j, p.k));
  }

  @Override
  public Collection<Point4D> images(Point4D p, String s) {
    //TODO: subgroups?
    return images(p, Subgroups.Full);
  }

  @Override
  public Collection<Subgroup<OxOSymmetry>>
      getSubgroups() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int order(Subgroup<OxOSymmetry> s) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getName() {
    return "[O x O]";
  }

  @Override
  public Class<Point4D> getType() {
   return Point4D.class;
  }

  @Override
  public pointGroups.geometry.Symmetry.Subgroup<OxOSymmetry> getSubgroupByName(
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
    for(Rotation4D r : groupelems){
      rotatedPointcollection.add(r.rotate(q).asPoint4D());
    }
    return rotatedPointcollection;
  }



}
