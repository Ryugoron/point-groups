package pointGroups.geometry.symmetries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pointGroups.geometry.Point4D;
import pointGroups.geometry.Quaternion;
import pointGroups.geometry.Symmetry;


public enum Symmetry4DReflection
  implements Symmetry<Point4D> {

  IxIm2("+-[IxI]*2", "[3,3,5]", "", Symmetry4D.IxI, getStar()), //
  IxI60m2("+- 1/60 [IxI]*2", "2.[3,5]", "", Symmetry4D.IxI60, getStar()), //
  IxIPlus60m2_3("+ 1/60 [IxI]*2_3", "[3,5]", "", Symmetry4D.IxIPlus60,
      getStar()), //
  IxIPlus60m2_1("+ 1/60 [IxI]*2_1", "[3,5]°", "", Symmetry4D.IxIPlus60,
      getMinusstar()), //

  IxIQuer60m2("+ 1/60 [IxIQuer]*2", "2.[3,3,3]", "", Symmetry4D.IxIQuer60,
      getStar()), //

  OxOm2("+-[OxO]*2", "[3,4,3]:2", "", Symmetry4D.OxO, getStar()), //
  OxO2m2("+- 1/2 [OxO]*2", "[3,4,3]", "", Symmetry4D.OxO2, getStar()), //
  OxO2m2Quer("+- 1/2 [OxO]*2Quer", "[3,4,3]+*2", "", Symmetry4D.OxO2,
      getOnexio()), //

  OxO6m2("+- 1/6 [OxO]*2", "[3,3,4]", "", Symmetry4D.OxO6, getStar()), //
  OxO24m2("+- 1/24 [OxO]*2", "2.[3,4]", "", Symmetry4D.OxO24, getStar()), //

  OxOPlus24m2_3("+ 1/24 [OxO]*2_3", "[3,4]", "", Symmetry4D.OxO24Plus,
      getStar()), //
  OxOPlus24m2_1("+ 1/24 [OxO]*2_1", "[3,4]°", "", Symmetry4D.OxO24Plus,
      getMinusstar()), //

  OxOQuerPlus24m2_3("+ 1/24 [OxOquer]*2_3", "[2,3,3]°", "",
      Symmetry4D.OxOQuerPlus24, getStar()), //
  OxOQuerPlus24m2_1("+ 1/24 [OxOquer]*2_1", "[2,3,3]", "",
      Symmetry4D.OxOQuerPlus24, getMinusstar()), //

  TxTm2("+- [TxT]*2", "[3,4,3+]", "", Symmetry4D.TxT, getStar()), //
  TxT3m2("+- 1/3 [TxT]*2", "[+3,3,4]", "", Symmetry4D.TxT3, getStar()), //
  TxTQuer3m2("+- 1/3 [TxTquer]*2", "[3,3,4+]", "", Symmetry4D.TxTQuer3,
      getStar()), //

  TxT12m2("+- 1/12 [TxT]*2", "2.[+3,4]", "", Symmetry4D.TxT12, getStar()), //
  TxTQuer12m2("+- 1/12 [TxTquer]*2", "2.[3,3]", "", Symmetry4D.TxT12, getStar()), //

  TxTPlus12m2_3("+ 1/12 [TxT]*2_3", "[+3,4]", "", Symmetry4D.TxTPlus12,
      getStar()), //
  TxTPlus12m2_1("+ 1/12 [TxT]*2_1", "[+3,4]°", "", Symmetry4D.TxTPlus12,
      getMinusstar()), //

  TxTQuerPlus12m2_3("+ 1/12 [TxTquer]*2_3", "[3,3]°", "",
      Symmetry4D.TxTQuerPlus12, getStar()), //
  TxTQuerPlus12m2_1("+ 1/12 [TxTquer]*2_1", "[3,3]", "",
      Symmetry4D.TxTQuerPlus12, getMinusstar()) //
  ;

  static final Set<Symmetry4DReflection> groups;
  static final Map<Symmetry4DReflection, Collection<Symmetry4DReflection>> subgroups;
  static final Map<Symmetry4DReflection, Point4D> normalPoints;

  /**
   * extending element: * = [1,1]
   */
  private final static Reflection4D getStar() {
    Reflection4D star = new Reflection4D(Quaternion.ONE, Quaternion.ONE);
    return star;
  }

  /**
   * extending element: -* = [1,-1]
   */
  private final static Reflection4D getMinusstar() {
    Reflection4D minusStar =
        new Reflection4D(Quaternion.ONE, Quaternion.ONE.minus());
    return minusStar;
  }

  private final static Reflection4D getOnexio() {
    Reflection4D OnexiO = new Reflection4D(Quaternion.ONE, Quaternion.qO);
    return OnexiO;
  }

  static {
    groups = new HashSet<>();
    subgroups = new HashMap<>();
    normalPoints = new HashMap<>();

    groups.add(IxIQuer60m2);
    groups.add(OxO2m2Quer);
    groups.add(OxOQuerPlus24m2_1);
    groups.add(OxOQuerPlus24m2_3);
    groups.add(TxTQuer12m2);
    groups.add(IxI60m2);
    groups.add(IxIPlus60m2_1);
    groups.add(IxIPlus60m2_3);
    groups.add(IxIm2);
    groups.add(OxO24m2);
    groups.add(OxO2m2);
    groups.add(OxO6m2);
    groups.add(OxOm2);
    groups.add(TxT12m2);
    groups.add(TxT3m2);
    groups.add(TxTPlus12m2_1);
    groups.add(TxTPlus12m2_3);
    groups.add(TxTm2);

  }

  private final String coxeter;
  private final String schoenflies;
  private final String orbifold;
  private final Symmetry4D rotationsym;
  private final Reflection4D extendingElem;

  private Symmetry4DReflection(final String schoenflies, final String coxeter,
      final String orbifold, final Symmetry4D rotationsym,
      Reflection4D extendingElem) {
    this.schoenflies = schoenflies;
    this.coxeter = coxeter;
    this.orbifold = orbifold;
    this.rotationsym = rotationsym;
    this.extendingElem = extendingElem;
  }

  @Override
  public String coxeter() {
    return this.coxeter;
  }

  @Override
  public String schoenflies() {
    return this.schoenflies;
  }

  @Override
  public String orbifold() {
    return this.orbifold;
  }

  @Override
  public Collection<? extends Symmetry<Point4D>> subgroups() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int order() {
    return Symmetry4D.groups.get(this.rotationsym).size() * 2;
  }

  @Override
  public Collection<Point4D> images(Point4D p) {
    Collection<Point4D> rotatedPointcollection = this.rotationsym.images(p);
    Collection<Point4D> reflectedPointcollection = new ArrayList<>();
    for (Point4D point4d : rotatedPointcollection) {
      reflectedPointcollection.add(extendingElem.reflect(point4d).asPoint4D());
    }
    rotatedPointcollection.addAll(reflectedPointcollection);
    return rotatedPointcollection;
  }

  @Override
  public Class<Point4D> getType() {
    return Point4D.class;
  }

  @Override
  public Point4D getNormalPoint() {
    return this.rotationsym.getNormalPoint();
  }

  public static void main(String[] args) {
    Symmetry4D.main(null);
    Collection<Point4D> result =
        TxTPlus12m2_3.images(new Point4D(0.5, 0.25, 0.125, 0.125));
    System.out.println(result.size());
  }

}
