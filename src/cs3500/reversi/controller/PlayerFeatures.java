package cs3500.reversi.controller;

import cs3500.reversi.model.ICell;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.view.ReversiView;

/**
 * A class to give functionalities that a player(Human or AI) can
 * access in the game (playing a move, passing, quitting).
 */
public class PlayerFeatures {
  private final IReversiModel model;
  private final ReversiView view;
  private final TokenColor color;

  /**
   * Constructs a PlayerFeatures object.
   *
   * @param model the model to be used for the game.
   * @param view  the view for the particular player.
   * @param color the color of the player that this controller controls.
   */
  public PlayerFeatures(IReversiModel model, ReversiView view, TokenColor color) {
    this.model = model;
    this.view = view;
    this.color = color;
  }

  /**
   * A method that allows a player to play a move on a given cell.
   *
   * @param cell the cell that the player wants to play a move on.
   */
  public void playMove(ICell cell) {
    try {
      this.model.placeCurrentPlayerPiece(cell);
    } catch (IllegalStateException ex) {
      view.error(ex.getMessage());
    }
  }

  /**
   * A method that allows a player to pass their turn.
   */
  public void pass() {
    if (model.getCurrentColor().equals(this.color)) {
      this.model.passTurn(true);
    }
  }

  /**
   * A method that allows a player to quit the game.
   */
  public void quit() {
    System.exit(0);
  }
}
