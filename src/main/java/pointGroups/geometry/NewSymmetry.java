package pointGroups.geometry;

import java.util.Collection;

import pointGroups.geometry.Symmetry.Subgroup;


/**
 * Representation of the point groups (symmetry groups) in three and four
 * dimensions. The polyhedral groups are any of the symmetry groups of the
 * Platonic solids. See also {@link http
 * ://en.wikipedia.org/wiki/Point_groups_in_three_dimensions}
 * 
 * @author Alex
 * @param <P> The type of points accepted and created by the symmetry class
 *          (e.g. points in R^3 or in R^4)
 */
public interface NewSymmetry<P extends Point>
{

  public String coxeter();

  public String schoenflies();

  public String orbifold();

  public Collection<? extends NewSymmetry<P>> subgroups();

  public int order();

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
  public Collection<P> images(P p);

  public Class<P> getType();

  /**
   * Returns a point that is not located on any of the symmetry axis. This
   * returned point is Normalized, that it is located on the unitsphere in the
   * Dimension of P
   * 
   * @return a point on the UnitSphere
   */
  public P getNormalPoint();
}
