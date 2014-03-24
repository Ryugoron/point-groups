// package pointGroups.geometry.symmetries;
//
// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.List;
//
// import pointGroups.geometry.Point4D;
// import pointGroups.geometry.Quaternion;
//
//
// public class IxISymmetry
// implements Symmetry<Point4D, IxISymmetry>
// {
// /**
// * The singleton instance of the IxI's symmetry class.
// */
// private final static IxISymmetry sym = new IxISymmetry();
// private final List<Rotation4D> gen = new ArrayList<Rotation4D>();
//
// private static Collection<Rotation4D> group;
//
// protected IxISymmetry() {
// gen.add(new Rotation4D(GeneratorCreator.qI, Quaternion.ONE));
// gen.add(new Rotation4D(GeneratorCreator.qw, Quaternion.ONE));
//
// gen.add(new Rotation4D(Quaternion.ONE, GeneratorCreator.qI));
// gen.add(new Rotation4D(Quaternion.ONE, GeneratorCreator.qw));
// group = GeneratorCreator.generateSymmetryGroup4D(gen);
// }
//
//
// public enum Subgroups
// implements Subgroup<IxISymmetry> {
// Id("Trivial group"), Full("Full [IxI] symmetry");
//
// private final String name;
//
// Subgroups(final String name) {
// this.name = name;
// }
//
// @Override
// public String getName() {
// return this.name;
// }
//
// @Override
// public int order() {
// return sym.order(this);
// }
// }
//
// public static IxISymmetry getSym() {
// return sym;
// }
//
// @Override
// public Collection<Point4D> images(final Point4D p,
// pointGroups.geometry.Symmetry.Subgroup<IxISymmetry> s) {
// return calculateImages(new Quaternion(p.re, p.i, p.j, p.k));
// }
//
// @Override
// public Collection<Point4D> images(final Point4D p, final String s) {
// // TODO: subgroups?
// return images(p, Subgroups.Full);
// }
//
// @Override
// public Collection<pointGroups.geometry.Symmetry.Subgroup<IxISymmetry>>
// getSubgroups() {
// // TODO Auto-generated method stub
// return null;
// }
//
// @Override
// public int order(pointGroups.geometry.Symmetry.Subgroup<IxISymmetry> s) {
// // TODO Auto-generated method stub
// return 0;
// }
//
// @Override
// public String getName() {
// return "[I x I]";
// }
//
// @Override
// public Class<Point4D> getType() {
// return Point4D.class;
// }
//
// @Override
// public pointGroups.geometry.Symmetry.Subgroup<IxISymmetry> getSubgroupByName(
// final String subgroup) {
// // TODO Auto-generated method stub
// return null;
// }
//
// @Override
// public Point4D getNormalPoint() {
// // TODO Auto-generated method stub
// return null;
// }
//
// private Collection<Point4D> calculateImages(final Quaternion q) {
// Collection<Point4D> rotatedPointcollection = new ArrayList<>();
// for (Rotation4D r : group) {
// rotatedPointcollection.add(r.rotate(q).asPoint4D());
// }
// return rotatedPointcollection;
// }
//
// }
