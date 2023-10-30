package model;

/**
 * An interface to represent a player.
 * Maybe a human player or an AI Player
 */
public interface IPlayer {

  /**
   * Returns the color of the token for this player.
   * @return  Color Color of this player.
   */
  Color getColor();

}
