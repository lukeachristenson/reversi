package player;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.strategy.GreedyStrat;
import cs3500.reversi.view.BasicReversiView;
import cs3500.reversi.view.ReversiView;

/**
 * A JUnit 4 test class that tests communications. It tests communications in the following
 * directions:
 * <p> Model -> Controller -> View </p>
 * <p> Model -> Controller -> Player </p>
 */
public class ExampleMockPlayerTests {
  private IReversiModel model;
  private StringBuilder log;

  // Initialize the model, players, and controller
  private void init() {
    // Initialize the model, players, and controller
    log = new StringBuilder();
    this.model = new HexagonReversi(6);
    IPlayer player1 = new MockPlayer(TokenColor.BLACK, new GreedyStrat(TokenColor.BLACK),
            log, model);
    ReversiView view = new BasicReversiView(model, TokenColor.BLACK);
    Controller controller = new Controller(model, view, player1, TokenColor.BLACK);
  }

  @Test
  public void testListenForMoveBeingCalled() {
    this.init();
    this.model.startGame();
    System.out.println(this.log.toString());
    Assert.assertTrue(log.toString().contains("listenForMove called with color: B"));
  }

  @Test
  public void testPlayMoveBeingCalled() {
    this.init();
    this.model.startGame();
    System.out.println(this.log.toString());
    Assert.assertTrue(log.toString().contains("playMove called with moves list of size: 6"));
  }

}
