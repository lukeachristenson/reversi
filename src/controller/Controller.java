package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.Color;
import model.ICell;
import model.IReversiModel;
import view.CartesianPosn;
import view.ReversiView;
import view.ViewFeatures;

/**
 * A controller for the Reversi game. This controller takes in a model and two views and allows
 * the user to play the game.
 * NOTE: The constructor was made only to manually test the game, and, in particular, the view and
 * the strategies(Highlighting the valid moves, and the strategies themselves).
 */
public class Controller implements ViewFeatures {
  private final IReversiModel model;
  private final List<ReversiView> views;

  /**
   * Constructs a Controller with the given model and views.
   * @param model the model to be used
   * @param view1 the first view to be used
   * @param view2 the second view to be used
   */
  public Controller(IReversiModel model, ReversiView view1, ReversiView view2) {
    this.model = model;
    this.views = List.of(view1, view2);
    for(ReversiView view : this.views) {
      view.addFeatureListener(this);
    }
  }

  public void go() {
    for(ReversiView view : this.views) {
      view.display(true);
    }
  }

  @Override
  public void playMove(ICell cell) {
    boolean validate = this.model.getValidMoves(this.model.getCurrentColor()).contains(cell);
    if (validate) {
      this.model.placeCurrentPlayerPiece(cell);
      for(ReversiView view : this.views) {
        view.advance();
      }
    } else {
      for(ReversiView view : this.views) {
        if(view.getFrameColor() == this.model.getCurrentColor()) {
          view.error();
        }
      }
    }
  }

  @Override
  public void pass() {
    this.model.passTurn(true);
    for(ReversiView view : this.views) {
      view.advance();
    }
  }

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
}
