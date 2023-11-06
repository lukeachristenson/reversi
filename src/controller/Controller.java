package controller;

import java.awt.*;
import java.util.HashMap;
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
  private final ReversiView view;

  public Controller(IReversiModel model, ReversiView view) {
    this.model = model;
    this.view = view;
    this.view.addFeatureListener(this);
  }

  public void go() {
    this.view.display(true);
  }


  @Override
  public void playMove(CartesianPosn posn) {
    boolean validate = this.model.getValidMoves(this.model.getCurrentPlayer().getColor()).contains(this.getCell(posn));
    if (validate) {
      this.model.placeCurrentPlayerPiece(this.getCell(posn));
      this.view.advance();
    } else {
      this.view.error();
    }
    System.out.println(model.getScore(Color.BLACK) + " " + model.getScore(Color.WHITE));
  }

  @Override
  public void pass() {
    this.model.passTurn();
    this.view.advance();
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
