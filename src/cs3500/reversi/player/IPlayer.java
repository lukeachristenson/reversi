package cs3500.reversi.player;

import cs3500.reversi.controller.PlayerFeatures;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.IReversiModel;

/**
 * An interface to represent a player.
 * Maybe a human player or an AI Player
 */
public interface IPlayer {

  void addListener(PlayerFeatures listener);

  void playMove();

  void listenForMove(TokenColor tokenColor);
}