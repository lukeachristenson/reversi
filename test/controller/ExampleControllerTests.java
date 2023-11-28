package controller;

import org.junit.Test;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.view.BasicReversiView;
import cs3500.reversi.view.ReversiView;
import mockplayerview.MockPlayer;
import mockplayerview.MockView;

public class ExampleControllerTests {
  /**
   * Cases to consider:
   *  - Null model
   *  - Null view
   *  - Null player
   *  - Null color
   */

  private IReversiModel model;

  private MockView mockView;
  private MockPlayer mockPlayer;
  private IPlayer player1;
  private ReversiView view;
  private StringBuilder modelLog;
  private Controller controller;

  private void init() {
    // Initialize the model, players, and controller
    modelLog = new StringBuilder();
    this.model = new HexagonReversi(6);
    this.player1 = new MockPlayer(new StringBuilder());
    this.view = new BasicReversiView(model, TokenColor.BLACK);
  }

  @Test(expected = NullPointerException.class)
  public void testNullModel() {
    this.controller = new Controller(null, view, player1, TokenColor.BLACK);
  }

  @Test(expected = NullPointerException.class)
  public void testNullView() {
    this.controller = new Controller(model, null, player1, TokenColor.BLACK);
  }
  @Test(expected = NullPointerException.class)
  public void testNullPlayer() {
    this.controller = new Controller(null, view, null, TokenColor.BLACK);
  }

  @Test(expected = NullPointerException.class)
  public void testNullColor() {
    this.controller = new Controller(model, null, player1, null);
  }
}
