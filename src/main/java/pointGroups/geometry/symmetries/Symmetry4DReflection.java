package pointGroups.geometry.symmetries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pointGroups.geometry.Point4D;
import pointGroups.geometry.Quaternion;
import pointGroups.geometry.Symmetry;

/**
 * Enum for 4D-symmetries with reflections
 * @author nadjascharf
 *
 */

public enum Symmetry4DReflection
  implements Symmetry<Point4D> {

  IxIm2("+-[IxI]*2", "[3,3,5]", "", Symmetry4D.IxI, getStar()), //
  IxI60m2("+- 1/60 [IxI]*2", "2.[3,5]", "", Symmetry4D.IxI60, getStar()), //
  IxIPlus60m2_3("+ 1/60 [IxI]*2_3", "[3,5]", "", Symmetry4D.IxIPlus60,
      getStar()), //
  IxIPlus60m2_1("+ 1/60 [IxI]*2_1", "[3,5]°", "", Symmetry4D.IxIPlus60,
      getMinusstar()), //
  IxIQuer60m2("+- 1/60 [IxIQuer]*2", "2.[3,3,3]", "", Symmetry4D.IxIQuer60,
      getStar()), //
  IxIQuerPlus60m2_3("+ 1/60 [IxIQuer]*2_3", "[3,3,3]°", "",
      Symmetry4D.IxIQuerPlus60, getStar()), //
  IxIQuerPlus60m2_1("+ 1/60 [IxIQuer]*2_1", "[3,3,3]", "",
      Symmetry4D.IxIQuerPlus60, getMinusstar()), //

  OxOm2("+-[OxO]*2", "[3,4,3]:2", "", Symmetry4D.OxO, getStar()), //
  OxO2m2("+- 1/2 [OxO]*2", "[3,4,3]", "", Symmetry4D.OxO2, getStar()), //
  OxO2m2Quer("+- 1/2 [OxO]*2Quer", "[3,4,3]+*2", "", Symmetry4D.OxO2,
      getOnexio()), //
  OxO6m2("+- 1/6 [OxO]*2", "[3,3,4]", "", Symmetry4D.OxO6, getStar()), //
  OxO24m2("+- 1/24 [OxO]*2", "2.[3,4]", "", Symmetry4D.OxO24, getStar()), //
  OxOPlus24m2_3("+ 1/24 [OxO]*2_3", "[3,4]", "", Symmetry4D.OxOPlus24,
      getStar()), //
  OxOPlus24m2_1("+ 1/24 [OxO]*2_1", "[3,4]°", "", Symmetry4D.OxOPlus24,
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
  TxTQuer12m2("+- 1/12 [TxTquer]*2", "2.[3,3]", "", Symmetry4D.TxTQuer12,
      getStar()), //
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
  static final Map<Symmetry4DReflection, Collection<? extends Symmetry<Point4D>>> subgroups;

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
    subgroups =
        new HashMap<Symmetry4DReflection, Collection<? extends Symmetry<Point4D>>>();

    groups.add(IxIm2);
    groups.add(IxI60m2);
    groups.add(IxIQuer60m2);
    groups.add(IxIPlus60m2_1);
    groups.add(IxIPlus60m2_3);
    groups.add(IxIQuerPlus60m2_1);
    groups.add(IxIQuerPlus60m2_3);

    groups.add(OxO2m2Quer);
    groups.add(OxOQuerPlus24m2_1);
    groups.add(OxOQuerPlus24m2_3);
    groups.add(OxO24m2);
    groups.add(OxO2m2);
    groups.add(OxO6m2);
    groups.add(OxOm2);
    groups.add(OxOPlus24m2_1);
    groups.add(OxOPlus24m2_3);

    groups.add(TxTQuer12m2);
    groups.add(TxT12m2);
    groups.add(TxT3m2);
    groups.add(TxTPlus12m2_1);
    groups.add(TxTPlus12m2_3);
    groups.add(TxTm2);
    groups.add(TxTQuerPlus12m2_1);
    groups.add(TxTQuerPlus12m2_3);
    groups.add(TxTQuer3m2);

    // Subgroups

    subgroups.put(IxIm2, Arrays.asList(IxIm2, IxI60m2, IxIQuer60m2,
        IxIQuerPlus60m2_3, TxT3m2, TxTQuer3m2, TxT12m2, TxTm2, TxTQuer12m2,
        TxTPlus12m2_3, IxIPlus60m2_3, TxTQuerPlus12m2_3));

    subgroups.put(IxIQuer60m2, Arrays.asList(IxIQuer60m2, IxIQuerPlus60m2_3,
        TxTQuer12m2, TxTQuerPlus12m2_3));
    subgroups.put(IxIPlus60m2_1, Arrays.asList(IxIPlus60m2_1)); // minusStar
    subgroups.put(IxIPlus60m2_3, Arrays.asList(IxIPlus60m2_3));
    subgroups.put(IxI60m2, Arrays.asList(IxI60m2, IxIPlus60m2_3));
    subgroups.put(IxIQuerPlus60m2_1,
        Arrays.asList(IxIQuerPlus60m2_1, TxTQuerPlus12m2_1)); // minusStar
    
    subgroups.put(IxIQuerPlus60m2_3,
        Arrays.asList(IxIQuerPlus60m2_3, TxTQuerPlus12m2_3));

    subgroups.put(OxO2m2Quer, Arrays.asList(OxO2m2Quer)); // *[1,qo]
    
    subgroups.put(OxOQuerPlus24m2_1,
        Arrays.asList(OxOQuerPlus24m2_1, TxTPlus12m2_1)); // minusStar
    
    subgroups.put(OxOQuerPlus24m2_3,
        Arrays.asList(OxOQuerPlus24m2_3, TxTPlus12m2_3));
    
    subgroups.put(OxO24m2, Arrays.asList(OxO24m2, OxOQuerPlus24m2_3,
        OxOPlus24m2_3, TxT12m2, TxTPlus12m2_3));
    
    subgroups.put(OxO2m2, Arrays.asList(OxO2m2, OxOPlus24m2_3, TxT12m2, TxT3m2,
        TxTm2, OxO24m2, OxO6m2, OxOQuerPlus24m2_3, TxTPlus12m2_3, TxTQuer3m2,
        TxTQuerPlus12m2_3, TxTQuer12m2));
    
    subgroups.put(OxO6m2, Arrays.asList(OxO6m2, OxOPlus24m2_3, OxO24m2,
        OxOQuerPlus24m2_3, TxT12m2, TxTPlus12m2_3, TxT3m2));
    
    subgroups.put(OxOm2, Arrays.asList(OxOm2, OxO2m2, OxO6m2, OxO24m2,
        OxOPlus24m2_3, OxOQuerPlus24m2_3, TxTm2, TxT12m2, TxTPlus12m2_3, TxT3m2,
        TxTQuer3m2, TxTQuer12m2, TxTQuerPlus12m2_3));
    
    subgroups.put(OxOPlus24m2_1, Arrays.asList(OxOPlus24m2_1, TxTPlus12m2_1)); // minusStar
    
    subgroups.put(OxOPlus24m2_3, Arrays.asList(OxOPlus24m2_3, TxTPlus12m2_3));

    subgroups.put(TxTQuer12m2, Arrays.asList(TxTQuer12m2, TxTQuerPlus12m2_3));
    subgroups.put(TxT12m2, Arrays.asList(TxT12m2, TxTPlus12m2_3));
    subgroups.put(TxT3m2, Arrays.asList(TxT3m2, TxT12m2, TxTPlus12m2_3));
    subgroups.put(TxTPlus12m2_1, Arrays.asList(TxTPlus12m2_1)); // minusStar
    subgroups.put(TxTPlus12m2_3, Arrays.asList(TxTPlus12m2_3));
    subgroups.put(TxTm2, Arrays.asList(TxTm2, TxT12m2, TxTPlus12m2_3, TxT3m2,
        TxTQuer3m2, TxTQuer12m2, TxTQuerPlus12m2_3));
    
    subgroups.put(TxTQuerPlus12m2_1, Arrays.asList(TxTQuerPlus12m2_1)); // minusStar
    subgroups.put(TxTQuerPlus12m2_3, Arrays.asList(TxTQuerPlus12m2_3));
    subgroups.put(TxTQuer3m2,
        Arrays.asList(TxTQuer3m2, TxTQuer12m2, TxTQuerPlus12m2_3));

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

  public Symmetry4D getRotationsym() {
    return rotationsym;
  }

  public Reflection4D getExtendingElem() {
    return extendingElem;
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
    Collection<? extends Symmetry<Point4D>> subg1 =
        this.rotationsym.subgroups();
    Collection<? extends Symmetry<Point4D>> subg2 = subgroups.get(this);
    Collection<Symmetry<Point4D>> subg = new ArrayList<>();
    subg.addAll(subg1);
    subg.addAll(subg2);
    return subg;
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

  public static Collection<Symmetry4DReflection> getSymmetries() {
    return groups;
  }

  public static void main(String[] args) {
    Symmetry4D.main(null);
    Collection<Point4D> result =
        TxTPlus12m2_3.images(new Point4D(0.5, 0.25, 0.125, 0.125));
    System.out.println(result.size());
  }

}
