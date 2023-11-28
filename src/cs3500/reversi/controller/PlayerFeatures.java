package cs3500.reversi.controller;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.view.ReversiView;

public class PlayerFeatures {
  private final IReversiModel model;
  private final ReversiView view;
  private final IPlayer player;
  private final TokenColor color;
  public PlayerFeatures(IReversiModel model, ReversiView view, IPlayer player, TokenColor color) {
    this.model = model;
    this.view = view;
    this.player = player;
    this.color = color;
  }

  public void playMove(ICell cell) {
    try {
      this.model.placeCurrentPlayerPiece(cell);
    } catch (IllegalStateException ex) {
      view.error(ex.getMessage());
    }
  }

  public void pass() {
    if(model.getCurrentColor().equals(this.color)){
      this.model.passTurn(true);
    }
  }

  public void quit() {
    System.exit(0);
  }

}
