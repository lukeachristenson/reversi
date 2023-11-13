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

public class Controller implements ViewFeatures {
  private final IReversiModel model;
  private final List<ReversiView> view;

  public Controller(IReversiModel model, ReversiView view1, ReversiView view2) {
    this.model = model;
    this.view = List.of(view1, view2);
    for(ReversiView view : this.view) {
      view.addFeatureListener(this);
    }
  }

  public void go() {
    for(ReversiView view : this.view) {
      view.display(true);
    }
  }


  @Override
  public void playMove(ICell cell) {
    boolean validate = this.model.getValidMoves(this.model.getCurrentColor()).contains(cell);
    if (validate) {
      this.model.placeCurrentPlayerPiece(cell);
      for(ReversiView view : this.view) {
        view.advance();
      }
    } else {
      for(ReversiView view : this.view) {
        if(view.getFrameColor() == this.model.getCurrentColor()) {
          view.error();
        }
      }
    }
  }

  @Override
  public void pass() {
    this.model.passTurn();
    for(ReversiView view : this.view) {
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
