package cs3500.otherreversi.ourmodel;

import java.util.List;

/**
 * Represents a cell within a reversi game with a hexagonal board.
 */
public interface ICell {
  /**
   * Returns the location of this cell.
   */
  List<Integer> getCoordinates();

  /**
   * Returns whether the given object is equal to this cell.
   *
   * @param o the object to compare to this cell.
   * @return whether the given object is equal to this cell.
   */
  boolean equals(Object o);

  /**
   * Returns the hashcode of this cell.
   *
   * @return the hashcode of this cell.
   */
  int hashCode();


}
