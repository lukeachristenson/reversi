package controller;

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
    boolean validity = this.model.getValidMoves(this.model.getCurrentPlayer().getColor()).contains(getCell(posn));

    if(validity) {
      this.model.placeCurrentPlayerPiece(getCell(posn));
    } else {
      this.view.error();
    }
  }


  private ICell getCell(CartesianPosn posn) {
    // fixme - implement this method to convert a posn to an ICell properly.
    return null;
  }

  @Override
  public void quit() {
    System.exit(0);
  }
}
