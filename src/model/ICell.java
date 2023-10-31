package model;

import java.util.List;

public interface ICell {
  /**
   * Returns the location of this cell.
   */
  List<Integer> getCoordinates();

  boolean equals(Object o);

  int hashCode();
}
