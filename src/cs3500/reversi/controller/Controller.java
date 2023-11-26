package cs3500.reversi.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import cs3500.reversi.model.Color;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.ModelFeature;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.view.CartesianPosn;
import cs3500.reversi.view.ReversiView;
import cs3500.reversi.view.ViewFeatures;

/**
 * A controller for the Reversi game. This controller takes in a model and two views and allows
 * the user to play the game.
 * NOTE: The constructor was made only to manually test the game, and, in particular, the view and
 * the strategies(Highlighting the valid moves, and the strategies themselves).
 */
public class Controller implements ViewFeatures, ModelFeature {
  private final IReversiModel model;
  private final ReversiView view;
  private final IPlayer player;

  /**
   * Constructs a Controller with the given model and views.
   *
   * @param model the model to be used
   * @param view  the view to be used
   */
  public Controller(IReversiModel model, ReversiView view, IPlayer player) {
    this.model = model;
    this.view = view;
    this.player = player;
    view.addFeatureListener(this);
    player.addListener(this);
  }

  /**
   * Constructs a Controller with the given model and views.
   */
  public void controllerGo() {
    this.emitMoveColor(this.model.getCurrentColor());
  }


  @Override
  public void playMove(ICell cell) {
    boolean validate = this.model.getValidMoves(this.model.getCurrentColor()).contains(cell);
    if (validate) {
      this.model.placeCurrentPlayerPiece(cell);
    } else {
      view.error();
    }
    this.emitMoveColor(this.model.getCurrentColor());
  }

  @Override
  public void pass() {
    this.model.passTurn(true);
    this.emitMoveColor(this.model.getCurrentColor());
  }

  // gets the highest score of the opponent color player for a given
  private ICell getCell(CartesianPosn posn) {
    Map<ICell, Optional<Color>> map = this.model.createBoardCopy().getPositionsMapCopy();
    Map<CartesianPosn, ICell> cartesianPosnICellMap = new HashMap<>();

    for (ICell cell : map.keySet()) {
      cartesianPosnICellMap.put(posn.getFromICell(cell), cell);
    }
    return cartesianPosnICellMap.get(posn);
  }

  @Override
  public void quit() {
    System.exit(0);
  }

  @Override
  public void emitMoveColor(Color color) {
    System.out.println("emitMoveColor: " + color.toString());
    System.out.println("Model current color: " + this.model.getCurrentColor().toString());
    this.player.listenForMove(color, this.model);
    this.view.listenToMove(color, this.model);
  }

  @Override
  public void advanceFrame() {
    this.view.display(true);
    this.view.advance();
  }
}
