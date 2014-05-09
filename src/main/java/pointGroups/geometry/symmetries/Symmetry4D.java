package pointGroups.geometry.symmetries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pointGroups.geometry.Point4D;
import pointGroups.geometry.Quaternion;
import pointGroups.geometry.Symmetry;
import pointGroups.util.HashMapKeysLikeInserted;
import pointGroups.util.LoggerFactory;
import pointGroups.util.PointGroupsUtility;


/**Enum for 4D-symmetries without reflections
 * @author Oliver
 */
public enum Symmetry4D
  implements Symmetry<Point4D> {

  IxO("+-[IxO]", "", "", "IxO", generatorIxO()), //
  IxT("+-[IxT]", "", "", "IxT", generatorIxT()), //
  OxT("+-[OxT]", "", "", "OxT", generatorOxT()), //

  IxI("+-[IxI]", "[3,3,5]+", "", "IxI", generatorIxI()), //
  IxI60("+- 1/60 [IxI]", "2.[3,5]+", "", "IxI60", generatorIxI60()), //
  IxIPlus60("+ 1/60 [IxI]", "[3,5]+", "", "IxIPlus60", generatorIxIPlus60()), //
  IxIQuer60("+- 1/60 [IxIQuer]", "2.[3,3,3]+", "", "IxIQuer60",
      generatorIxIQuer60()), //
  IxIQuerPlus60("+ 1/60 [IxIQuer]", "[3,3,3]+", "", "IxIQuerPlus60",
      generatorIxIQuerPlus60()), //

  OxO("+-[OxO]", "[3,4,3]+:2", "", "OxO", generatorOxO()), //
  OxO2("+- 1/2 [OxO]", "[3,4,3]+", "", "OxO2", generatorOxO2()), //
  OxO6("+- 1/6 [OxO]", "[3,3,4]+", "", "OxO6", generatorOxO6()), //
  OxO24("+- 1/24 [OxO]", "2.[3,4]+", "", "OxO24", generatorOxO24()), //
  OxOPlus24("+ 1/24 [OxO]", "[3,4]+", "", "OxOPlus24", generatorOxOPlus24()), //
  OxOQuerPlus24("+ 1/24 [OxOquer]", "[2,3,3]+", "", "OxOQuerPlus24",
      generatorOxOQuerPlus24()), //

  TxT("+- [TxT]", "[+3,4,3+]", "", "TxT", generatorTxT()), //
  TxT3("+- 1/3 [TxT]", "[+3,3,4+]", "", "TxT3", generatorTxT3()), //
  TxTQuer3("+- 1/3 [TxTQuer]", "[+3,3,4+]", "", "TxTQuer3", generatorTxTQuer3()), //
  TxT12("+- 1/12 [TxT]", "2.[3,3]+", "", "TxT12", generatorTxT12()), //
  TxTQuer12("+- 1/12 [TxTQuer]", "2.[3,3]+", "", "TxTQuer12",
      generatorTxTQuer12()), //
  TxTPlus12("+ 1/12 [TxT]", "[3,3]+", "", "TxTPlus12", generatorTxTPlus12()), //
  TxTQuerPlus12("+ 1/12 [TxTQuer]", "[3,3]+", "", "TxTQuerPlus12",
      generatorTxTQuerPlus12());//

  static final Map<Symmetry4D, Collection<Rotation4D>> groups;
  static final Map<Symmetry4D, Collection<Symmetry4D>> subgroups;

  private final static Collection<Rotation4D> generatorIxO() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.qI, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qO));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qw));

    generator.add(new Rotation4D(Quaternion.qI, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qO.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qw.minus()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorIxT() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.qI, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qw));

    generator.add(new Rotation4D(Quaternion.qI, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qw.minus()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorOxT() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.qO, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qw));

    generator.add(new Rotation4D(Quaternion.qO, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qw.minus()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorIxI() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.qI, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qI));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qw));

    generator.add(new Rotation4D(Quaternion.qI, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qI.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qw.minus()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorIxI60() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw));
    generator.add(new Rotation4D(Quaternion.qI, Quaternion.qI));

    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw.minus()));
    generator.add(new Rotation4D(Quaternion.qI, Quaternion.qI.minus()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorIxIPlus60() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw));
    generator.add(new Rotation4D(Quaternion.qI, Quaternion.qI));
    return generator;
  }

  private final static Collection<Rotation4D> generatorIxIQuer60() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw.conjugate()));
    generator.add(new Rotation4D(Quaternion.qI, Quaternion.qI.conjugate()));

    generator.add(new Rotation4D(Quaternion.qw,
        Quaternion.qw.conjugate().minus()));
    generator.add(new Rotation4D(Quaternion.qI,
        Quaternion.qI.conjugate().minus()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorIxIQuerPlus60() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw.conjugate()));
    generator.add(new Rotation4D(Quaternion.qI, Quaternion.qI.conjugate()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorOxO() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.qO, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qO));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qw));

    generator.add(new Rotation4D(Quaternion.qO, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qO.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qw.minus()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorOxO2() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qw));
    generator.add(new Rotation4D(Quaternion.qO, Quaternion.qO));

    generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qw.minus()));
    generator.add(new Rotation4D(Quaternion.qO, Quaternion.qO.minus()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorOxO6() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.J, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.J));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw));
    generator.add(new Rotation4D(Quaternion.qO, Quaternion.qO));

    generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.J, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.J.minus()));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw.minus()));
    generator.add(new Rotation4D(Quaternion.qO, Quaternion.qO.minus()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorOxO24() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw));
    generator.add(new Rotation4D(Quaternion.qO, Quaternion.qO));

    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw.minus()));
    generator.add(new Rotation4D(Quaternion.qO, Quaternion.qO.minus()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorOxOPlus24() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw));
    generator.add(new Rotation4D(Quaternion.qO, Quaternion.qO));

    return generator;
  }

  private final static Collection<Rotation4D> generatorOxOQuerPlus24() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw));
    generator.add(new Rotation4D(Quaternion.qO, Quaternion.qO.minus()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorTxT() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qw));

    generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.qw.minus()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorTxT3() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I));
    generator.add(new Rotation4D(Quaternion.J, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.J));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw));

    generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I.minus()));
    generator.add(new Rotation4D(Quaternion.J, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.J.minus()));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw.minus()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorTxTQuer3() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I));
    generator.add(new Rotation4D(Quaternion.J, Quaternion.ONE));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.J));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw.conjugate()));

    generator.add(new Rotation4D(Quaternion.I, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.I.minus()));
    generator.add(new Rotation4D(Quaternion.J, Quaternion.ONE.minus()));
    generator.add(new Rotation4D(Quaternion.ONE, Quaternion.J.minus()));
    generator.add(new Rotation4D(Quaternion.qw,
        Quaternion.qw.conjugate().minus()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorTxT12() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.I, Quaternion.I));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw));

    generator.add(new Rotation4D(Quaternion.I, Quaternion.I.minus()));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw.minus()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorTxTQuer12() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.I, Quaternion.I.minus()));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw.conjugate()));

    generator.add(new Rotation4D(Quaternion.I, Quaternion.I.minus().minus()));
    generator.add(new Rotation4D(Quaternion.qw,
        Quaternion.qw.conjugate().minus()));
    return generator;
  }

  private final static Collection<Rotation4D> generatorTxTPlus12() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.I, Quaternion.I));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw));
    return generator;
  }

  private final static Collection<Rotation4D> generatorTxTQuerPlus12() {
    Collection<Rotation4D> generator = new ArrayList<>();
    generator.add(new Rotation4D(Quaternion.I, Quaternion.I.minus()));
    generator.add(new Rotation4D(Quaternion.qw, Quaternion.qw.conjugate()));

    return generator;
  }

  /**
   * Add new symmetry groups here
   */
  private static void createSymgroups() {
    System.out.println("Kreuz-Gruppe Start: " +
        Calendar.getInstance().getTime());
    createSymgroup(IxO);
    createSymgroup(IxT);
    createSymgroup(OxT);

    System.out.println("T-Gruppe Start: " + Calendar.getInstance().getTime());

    createSymgroup(TxT);
    createSymgroup(TxT3);
    createSymgroup(Symmetry4D.TxTQuer3);
    createSymgroup(TxT12);
    createSymgroup(TxTQuer12);
    createSymgroup(TxTPlus12);
    createSymgroup(TxTQuerPlus12);

    System.out.println("O-Gruppen Start: " + Calendar.getInstance().getTime());

    createSymgroup(OxO);
    createSymgroup(OxO2);
    createSymgroup(OxO6);
    createSymgroup(OxO24);
    createSymgroup(OxOPlus24);
    createSymgroup(OxOQuerPlus24);

    System.out.println("I-Gruppen Start: " + Calendar.getInstance().getTime());

    createSymgroup(IxIQuerPlus60);
    createSymgroup(IxIPlus60);
    createSymgroup(IxIQuer60);
    createSymgroup(IxI60);
    createSymgroup(IxI);

    System.out.println("Finish: " + Calendar.getInstance().getTime());
    LoggerFactory.get(Symmetry4D.class).info(
        "Symmetry data calculation finished.");

  }

  private static void createSymgroup(final Symmetry4D sym) {
    Collection<Rotation4D> group = generateSymmetryGroup4D(sym.getGenerator());
    System.out.println(sym.schoenflies + " / " + sym.coxeter + " groupsize: " +
        group.size());
    try {
      writeSymmetryGroup(sym, group);
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Find new group elem x s.t. x not in group, arg1, arg2 and it exists y in
   * arg1, z in arg2 s.t. x = y*z. Add x in dist.
   * 
   * @param group
   * @param arg1
   * @param arg2
   * @param dist
   * @return #new group elems
   */
  private static int calculate(final Collection<Rotation4D> group,
      final Collection<Rotation4D> arg1, final Collection<Rotation4D> arg2,
      final Collection<Rotation4D> dist) {
    int newElems = 0;
    Rotation4D z;
    for (Rotation4D x : arg1) {
      for (Rotation4D y : arg2) {
        z = x.nextRotation(y);
        if (!group.contains(z) && !arg1.contains(z) && !arg2.contains(z)) {
          dist.add(z);
          newElems++;
        }
      }
    }
    return newElems;
  }

  private static Collection<Rotation4D> generateSymmetryGroup4D(
      final Collection<Rotation4D> generator) {
    int newElems = 0;
    // All known group elems
    Set<Rotation4D> groupElems = new HashSet<Rotation4D>();
    // All new group elems. Test them for new elems.
    Set<Rotation4D> currentToTest = new HashSet<Rotation4D>(generator);
    // All new found group elems.
    Set<Rotation4D> newGroupelem = new HashSet<Rotation4D>();

    // Test for new elems since there are no more new elems....
    do {
      // x in Group, y in CurrentToText-> x*y new Elem?
      newElems = calculate(groupElems, groupElems, currentToTest, newGroupelem);
      // x in CurrentToText, y in Group-> x*y new Elem?
      newElems +=
          calculate(groupElems, currentToTest, groupElems, newGroupelem);
      // x in CurrentToText, y in CurrentToText-> x*y new Elem?
      newElems +=
          calculate(groupElems, currentToTest, currentToTest, newGroupelem);

      // Add all tested elems to the group
      for (Rotation4D g : currentToTest) {
        groupElems.add(g);
      }
      currentToTest.clear();

      // Now test all new found group elems
      for (Rotation4D g : newGroupelem) {
        currentToTest.add(g);
      }
      newGroupelem.clear();
    }
    while (newElems != 0);
    return groupElems;
  }

  private static void writeSymmetryGroup(final Symmetry4D sym,
      final Collection<Rotation4D> groupElems)
    throws IOException {
    // create a new file with an ObjectOutputStream

    FileOutputStream out =
        new FileOutputStream(new File("target/classes/symmetries/" +
            sym.filename + ".sym"));
    ObjectOutputStream oout = new ObjectOutputStream(out);

    oout.writeObject(groupElems);

    oout.close();
  }

  public static Collection<Rotation4D> readSymmetryGroup(final Symmetry4D sym)
    throws FileNotFoundException, IOException, ClassNotFoundException {

    InputStream is = PointGroupsUtility.getSymmetry(sym.filename + ".sym");

    ObjectInputStream ois = new ObjectInputStream(is);
    Object o = ois.readObject();
    // TODO: ugly
    Collection<Rotation4D> group = ((Collection<Rotation4D>) o);
    ois.close();
    return group;
  }

  private static void loadSymGroups() {
    try {
      groups.put(TxT, readSymmetryGroup(TxT));
      groups.put(TxT3, readSymmetryGroup(TxT3));
      groups.put(TxTQuer3, readSymmetryGroup(TxTQuer3));
      groups.put(TxT12, readSymmetryGroup(TxT12));
      groups.put(TxTQuer12, readSymmetryGroup(TxTQuer12));
      groups.put(TxTPlus12, readSymmetryGroup(TxTPlus12));
      groups.put(TxTQuerPlus12, readSymmetryGroup(TxTQuerPlus12));

      groups.put(OxO, readSymmetryGroup(OxO));
      groups.put(OxO2, readSymmetryGroup(OxO2));
      groups.put(OxO6, readSymmetryGroup(OxO6));
      groups.put(OxO24, readSymmetryGroup(OxO24));
      groups.put(OxOPlus24, readSymmetryGroup(OxOPlus24));
      groups.put(OxOQuerPlus24, readSymmetryGroup(OxOQuerPlus24));

      groups.put(IxI, readSymmetryGroup(IxI));
      groups.put(IxI60, readSymmetryGroup(IxI60));
      groups.put(IxIQuer60, readSymmetryGroup(IxIQuer60));
      groups.put(IxIPlus60, readSymmetryGroup(IxIPlus60));
      groups.put(IxIQuerPlus60, readSymmetryGroup(IxIQuerPlus60));

      groups.put(IxO, readSymmetryGroup(IxO));
      groups.put(IxT, readSymmetryGroup(IxT));
      groups.put(OxT, readSymmetryGroup(OxT));

    }
    catch (ClassNotFoundException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  static {
    groups = new HashMapKeysLikeInserted<Symmetry4D, Collection<Rotation4D>>();
    subgroups = new HashMap<>();

    // load group elems
    try {
      loadSymGroups();
    }
    catch (NullPointerException e) {
      LoggerFactory.get(Symmetry4D.class).warning(
          "Symmetry group data not found. Starting one-time calculation now (this may take a long time).");
      createSymgroups();
      loadSymGroups();
    }

    subgroups.put(IxO, Arrays.asList(TxT, TxT3, TxTQuer3, TxT12, TxTQuer12,
        TxTPlus12, TxTQuerPlus12, IxO, IxT));
    subgroups.put(OxT, Arrays.asList(TxT, TxT3, TxTQuer3, TxT12, TxTQuer12,
        TxTPlus12, TxTQuerPlus12, OxT));

    subgroups.put(IxT, Arrays.asList(TxT, TxT3, TxTQuer3, TxT12, TxTQuer12,
        TxTPlus12, TxTQuerPlus12, IxT));

    subgroups.put(IxI, Arrays.asList(TxT, TxT3, TxTQuer3, TxT12, TxTQuer12,
        TxTPlus12, TxTQuerPlus12, IxI, IxI60, IxIQuer60, IxIPlus60,
        IxIQuerPlus60, IxT));

    subgroups.put(IxIQuer60,
        Arrays.asList(TxTQuer12, TxTQuerPlus12, IxIQuer60, IxIQuerPlus60));

    subgroups.put(IxIQuerPlus60, Arrays.asList(TxTQuerPlus12, IxIQuerPlus60));
    subgroups.put(IxI60, Arrays.asList(IxI60, IxIPlus60)); // check
    subgroups.put(IxIPlus60, Arrays.asList(IxIPlus60)); // check

    subgroups.put(TxT, Arrays.asList(TxT, TxT3, TxTQuer3, TxT12, TxTQuer12,
        TxTPlus12, TxTQuerPlus12)); // check
    subgroups.put(TxT3, Arrays.asList(TxT3, TxT12, TxTPlus12)); // check
    subgroups.put(TxT12, Arrays.asList(TxT12, TxTPlus12)); // check
    subgroups.put(TxTPlus12, Arrays.asList(TxTPlus12)); // check
    subgroups.put(TxTQuer3, Arrays.asList(TxTQuer3, TxTQuer12, TxTQuerPlus12)); // check
    subgroups.put(TxTQuer12, Arrays.asList(TxTQuer12, TxTQuerPlus12)); // check
    subgroups.put(Symmetry4D.TxTQuerPlus12, Arrays.asList(TxTQuerPlus12));

    subgroups.put(OxO, Arrays.asList(TxT, TxT3, TxTQuer3, TxT12, TxTQuer12,
        TxTPlus12, TxTQuerPlus12, OxO, OxO2, OxO6, OxO24, OxOPlus24,
        OxOQuerPlus24, OxT)); // check

    subgroups.put(OxO2, Arrays.asList(TxT, TxT3, TxTQuer3, TxT12, TxTQuer12,
        TxTPlus12, TxTQuerPlus12, OxO2, OxO6, OxO24, OxOPlus24, OxOQuerPlus24)); // check
    subgroups.put(OxO6, Arrays.asList(TxT3, TxT12, TxTPlus12, OxO6, OxO24,
        OxOPlus24, OxOQuerPlus24)); // check
    subgroups.put(OxO24,
        Arrays.asList(TxTPlus12, OxO24, OxOPlus24, OxOQuerPlus24, TxT12)); // check
    subgroups.put(OxOPlus24, Arrays.asList(TxTPlus12, OxOPlus24)); // check
    subgroups.put(OxOQuerPlus24, Arrays.asList(TxTPlus12, OxOQuerPlus24)); // check

  }

  private final String coxeter;
  private final String schoenflies;
  private final String orbifold;
  private final String filename;
  private final Collection<Rotation4D> generator;

  private Symmetry4D(final String schoenflies, final String coxeter,
      final String orbifold, final String filename,
      final Collection<Rotation4D> generator) {
    this.schoenflies = schoenflies;
    this.coxeter = coxeter;
    this.orbifold = orbifold;
    this.filename = filename;
    this.generator = generator;
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
    return subgroups.get(this);
  }

  @Override
  public int order() {
    return groups.get(this).size();
  }

  @Override
  public Collection<Point4D> images(final Point4D p) {
    Collection<Point4D> rotatedPointcollection = new HashSet<>();
    for (Rotation4D r : groups.get(this)) {
      rotatedPointcollection.add(r.rotate(p).asPoint4D());
    }
    return rotatedPointcollection;
  }

  @Override
  public Class<Point4D> getType() {
    return Point4D.class;
  }

  @Override
  public Point4D getNormalPoint() {
    return new Point4D(0.8, 0.6, 0.3, 0.4);
  }

  public Collection<Rotation4D> getGenerator() {
    return generator;
  }

  public static Collection<Symmetry4D> getSymmetries() {
    return groups.keySet();
  }

  public static void main(final String[] args) {
    // Collection<Symmetry4D> syms = getSymmetries();
    // for(Symmetry4D sym : syms){
    // System.out.println("size of "+sym.coxeter()+" / "+sym.schoenflies()+": "+sym.order());
    // }
    // System.out.println("size of "+TxT12.coxeter()+" / "+TxT12.schoenflies()+": "+TxT12.order());

    // createSymgroups();
    createSymgroup(TxT);

    // System.out.println("size of "+TxT12.coxeter()+" / "+TxT12.schoenflies()+": "+TxT12.order());

    // syms = getSymmetries();
    // for(Symmetry4D sym : syms){
    // System.out.println("size of "+sym.coxeter()+" / "+sym.schoenflies()+": "+sym.order());
    // }
  }

}
