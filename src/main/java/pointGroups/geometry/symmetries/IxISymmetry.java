package pointGroups.geometry.symmetries;

import java.util.ArrayList;
import java.util.Collection;

import pointGroups.geometry.Point4D;
import pointGroups.geometry.Quaternion;
import pointGroups.geometry.Symmetry;

public class IxISymmetry implements Symmetry<Point4D, IxISymmetry>
{
  /**
   * The singleton instance of the IxI's symmetry class.
   */
  private final static IxISymmetry sym = new IxISymmetry(); 
  private Collection<Rotation4D> generator = new ArrayList<>();
  private SymmetryGenerated4D symGen;
  
  protected IxISymmetry(){
    generator.add(new Rotation4D(GeneratorCreator.qI, Quaternion.ONE));
    generator.add(new Rotation4D(GeneratorCreator.qw, Quaternion.ONE));
    
    generator.add(new Rotation4D(Quaternion.ONE,GeneratorCreator.qI));
    generator.add(new Rotation4D(Quaternion.ONE,GeneratorCreator.qw));     
    symGen = new SymmetryGenerated4D(generator, false, false, this.getName()+".sym");
  }
  public enum Subgroups
  implements Subgroup<IxISymmetry> {
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
  
  
  public static IxISymmetry getSym(){
    return sym;
  }
  
  @Override
  public Collection<Point4D> images(Point4D p,
      pointGroups.geometry.Symmetry.Subgroup<IxISymmetry> s) {
    return calculateImages(new Quaternion(p.re, p.i, p.j, p.k));
  }

  @Override
  public Collection<Point4D> images(Point4D p, String s) {
    //TODO: subgroups?
    return images(p, Subgroups.Full);
  }

  @Override
  public Collection<pointGroups.geometry.Symmetry.Subgroup<IxISymmetry>>
      getSubgroups() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int order(pointGroups.geometry.Symmetry.Subgroup<IxISymmetry> s) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getName() {
    return "[I x I]";
  }

  @Override
  public Class<Point4D> getType() {
   return Point4D.class;
  }

  @Override
  public pointGroups.geometry.Symmetry.Subgroup<IxISymmetry> getSubgroupByName(
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
    for(Rotation4D r : symGen.generateSymmetryGroup4D()){
      rotatedPointcollection.add(r.rotate(q).asPoint4D());
    }
    return rotatedPointcollection;
  }



}
