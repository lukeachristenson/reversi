package cs3500.otherreversi.controller;

import model.CubicCoordinate;

/**
 * The actions (features) guaranteed for a controller in the game of Reversi.
 * Allows for a controller to be a listener of a view.
 */
public interface ControllerFeatures {

  /**
   * The action of passing their move.
   */
  void pass();

  /**
   * The action of moving at the given coordinate.
   */
  void move(CubicCoordinate c);
}
