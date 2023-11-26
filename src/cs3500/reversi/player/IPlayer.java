package cs3500.reversi.player;

import cs3500.reversi.model.Color;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.view.ViewFeatures;

/**
 * An interface to represent a player.
 * Maybe a human player or an AI Player
 */
public interface IPlayer {

  /**
   * Returns the color of the token for this player.
   *
   * @return Color: Color of this player.
   */
  Color getColor();

  void addListener(ViewFeatures listener);

  void playMove(IReversiModel model);

  void listenForMove(Color color, IReversiModel model);
}