package cs3500.provider.controller;

import cs3500.provider.model.CubicCoordinate;
import cs3500.provider.model.ModelAdapter;
import cs3500.provider.model.Player;
import cs3500.provider.model.ReversiModel;
import cs3500.provider.view.ViewInterface;
import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.view.BasicReversiView;
import cs3500.reversi.view.ReversiView;

public class ControllerAdapter extends Controller implements ObserverInterface, ControllerFeatures {
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
  private final IReversiModel ourModel;
  private Player player;

  public ControllerAdapter(ReversiModel model, IReversiModel ourModel,
                           TokenColor color, ViewInterface view, Player player) {
    super(ourModel, new BasicReversiView(new HexagonReversi(6),
            TokenColor.BLACK), new HumanPlayer(TokenColor.BLACK), TokenColor.BLACK);

    this.color = color;
    this.model = model;
    this.ourModel = ourModel;
    this.view = view;
    this.view.addFeatures(this);
    this.player = player;
    this.view.display();
    this.model.subscribe(this);
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
      System.err.println("Exception: " + ex.getMessage());
      this.view.display();
    }
  }

  @Override
  public void getNotifiedItsYourPlayersMove() {
    int token = (this.color == TokenColor.BLACK) ? 1 : 2;
    if(this.player.getPlayerNumber() == token) {
      if (this.player.play(new ModelAdapter(this.ourModel)) != null) {
        System.out.println("AI playing move");
        this.move(this.player.play(new ModelAdapter(this.ourModel)));
      }
    }

    this.view.display();
  }
}