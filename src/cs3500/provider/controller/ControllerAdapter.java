package cs3500.provider.controller;

import cs3500.provider.model.CubicCoordinate;
import cs3500.provider.model.ReversiModel;
import cs3500.provider.view.ViewInterface;
import cs3500.reversi.model.TokenColor;

public class ControllerAdapter implements ObserverInterface, ControllerFeatures {
  /**
   * 1. Controller stores the model and color of the player for which the controller is made.
   * 2. Controller adds itself as a subscriber to the model.
   * 3. Controller has a view(GUI)
   * 4. Controller plays the move if it is the turn of the player for which the controller is made.
   * 5. Controller updates the view(GUI) using the display method when it receives a notification
   * from the model.
   */

  private final ReversiModel model;
  private final TokenColor color;
  private final ViewInterface view;

  public ControllerAdapter(ReversiModel model, TokenColor color, ViewInterface view) {
//    System.out.println("ControllerAdapter constructor");
    this.color = color;
    this.model = model;
    this.view = view;
    this.model.subscribe(this);
    this.view.addFeatures(this);
    this.view.display();
  }

  @Override
  public void pass() {
    int token = (this.color == TokenColor.BLACK) ? 1 : 2;
    model.passMove(token);
    this.view.display();
  }

  @Override
  public void move(CubicCoordinate c) {
    int token = (this.color == TokenColor.BLACK) ? 1 : 2;
    try{
      model.flipCell(c, token);
    } catch (IllegalStateException ex) {
      System.err.println("ERROR");
      this.view.display();
    }
  }

  @Override
  public void getNotifiedItsYourPlayersMove() {
    System.out.println("ControllerAdapter getNotifiedItsYourPlayersMove");
    System.out.println("Current Move: " + this.model.getCurrentTurn());
    this.view.display();
  }
}