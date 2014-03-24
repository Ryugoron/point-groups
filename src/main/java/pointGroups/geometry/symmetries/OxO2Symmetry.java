package pointGroups.geometry.symmetries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import pointGroups.geometry.Point4D;
import pointGroups.geometry.Quaternion;
import pointGroups.geometry.Symmetry;

public class OxO2Symmetry implements Symmetry<Point4D, OxO2Symmetry>
{
  /**
   * The singleton instance of the 1/2OxO's symmetry class ([3,4,3]+).
   */
  private final static OxO2Symmetry sym = new OxO2Symmetry(); 
  private Collection<Rotation4D> groupelems;

  
  public final static Collection<Rotation4D> generator(){
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE));
    generator.add(new Rotation4D(GeneratorCreator.qw, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE,Quaternion.I));
    generator.add(new Rotation4D(Quaternion.ONE,GeneratorCreator.qw));
    generator.add(new Rotation4D(GeneratorCreator.qO,GeneratorCreator.qO));     
    return generator;
  }
  
  protected OxO2Symmetry(){
    
    // read file
    try {
      groupelems = SymmetryGenerated4D.readSymmetryGroup(getName());
    }
    catch (ClassNotFoundException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }  }
  public enum Subgroups
  implements Subgroup<OxO2Symmetry> {
  Id("Trivial group"), Full("Full [3,4,3]+ symmetry");

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
  
  
  public static OxO2Symmetry getSym(){
    return sym;
  }
  
  @Override
  public Collection<Point4D> images(Point4D p,
      Subgroup<OxO2Symmetry> s) {
    return calculateImages(new Quaternion(p.re, p.i, p.j, p.k));
  }

  @Override
  public Collection<Point4D> images(Point4D p, String s) {
    //TODO: subgroups?
    return images(p, Subgroups.Full);
  }

  @Override
  public Collection<Subgroup<OxO2Symmetry>>
      getSubgroups() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int order(Subgroup<OxO2Symmetry> s) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getName() {
    return "[3,4,3]+";
  }

  @Override
  public Class<Point4D> getType() {
   return Point4D.class;
  }

  @Override
  public Subgroup<OxO2Symmetry> getSubgroupByName(
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
