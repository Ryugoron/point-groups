package pointGroups.geometry;

import java.util.Collection;

import pointGroups.geometry.symmetries.IcosahedralSymmetry;
import pointGroups.geometry.symmetries.OctahedralSymmetry;
import pointGroups.geometry.symmetries.TetrahedralSymmetry;


/**
 * Representation of the point groups (symmetry groups) in three and four
 * dimensions. The polyhedral groups are any of the symmetry groups of the
 * Platonic solids. See also {@link http
 * ://en.wikipedia.org/wiki/Point_groups_in_three_dimensions}
 * 
 * @author Alex
 * @param <P> The type of points accepted and created by the symmetry class
 *          (e.g. points in R^3 or in R^4)
 * @param <E> The specific symmetry under consideration
 * @see TetrahedralSymmetry
 * @see OctahedralSymmetry
 * @see IcosahedralSymmetry
 */
public interface Symmetry<P extends Point, E extends Symmetry<P, E>>
{
  /**
   * Identifiers (possibly enum class) for the point group's subgroups.
   * 
   * @author Alex
   * @param <E> The symmetry under consideration
   */
  interface Subgroup<E extends Symmetry<?, ?>>
  {
    /**
     * Returns the name of the underlying subgroup.
     * 
     * @return Name of the sungroup
     */
    public String getName();

    /**
     * Gives the order of the according subgroup
     * 
     * @return the subgroup order
     */
    public int order();
  };

  /**
   * Returns a {@link Collection} of points which are the images of applying the
   * symmetries of the specific point group. It is possible to create only the
   * images with respect to certain subgroups.
   * 
   * @param p The {@link Point} which images are to be calculated
   * @param s the {@link Subgroup} of the specific {@link Symmetry} under which
   *          images are to be calculated
   * @return All points in the image of the symmetry
   */
  public Collection<P> images(P p, Subgroup<E> s);

  /**
   * Returns a {@link Collection} that contains all {@link Subgroup} elements of
   * the underlying symmetry.
   * 
   * @return Set of subgroups
   */
  public Collection<Subgroup<E>> getSubgroups();

  /**
   * The order of the specified subgroup.
   * 
   * @param s The subgroup under consideration
   * @return Order of subgroup s
   */
  public int order(Subgroup<E> s);

  /**
   * Returns the name of the underlying symmetry group
   * 
   * @return the groups name
   */
  public String getName();

  /**
   * Returns the subgroup associated to subgroup, if existing.
   * 
   * @param subgroup The canonical representation of the subgroup, i.e.
   *          <code>subgroup.toString()</code>
   * @return the subgroup object, if existing
   */
  public Subgroup<E> getSubgroupByName(String subgroup);
}
