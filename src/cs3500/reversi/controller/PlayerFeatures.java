package cs3500.reversi.controller;

import cs3500.reversi.model.ICell;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.view.ReversiView;

/**
 * A class to give functionalities that a player(Human or AI) can
 * access in the game (playing a move, passing, quitting).
 */
public class PlayerFeatures implements IPlayerFeature {
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


  @Override
  public void playMove(ICell cell) {
    if (this.color.equals(this.model.getCurrentColor())) {
      try {
        this.model.placeCurrentPlayerPiece(cell);
      } catch (IllegalStateException ex) {
        view.error(ex.getMessage());
      }
    } else {
      view.error("It is not your turn!");
    }
  }

  @Override
  public void pass() {
    if (model.getCurrentColor().equals(this.color)) {
      this.model.passTurn(true);
    }
  }

  @Override
  public void quit() {
    System.exit(0);
  }
}
