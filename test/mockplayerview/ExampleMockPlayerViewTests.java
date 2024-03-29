package mockplayerview;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.view.ReversiView;

/**
 * A JUnit 4 test class that tests communications. It tests communication in the following
 * directions:
 * <p> Model -> Controller -> View </p>
 * <p> Model -> Controller -> Player </p>
 */
public class ExampleMockPlayerViewTests {
  private IReversiModel model;
  private MockView mockView;
  private MockPlayer mockPlayer;
  private StringBuilder playerLog;
  private StringBuilder viewLog;

  // Initialize the model, players, and controller
  private void init() {
    // Initialize the model, players, and controller
    playerLog = new StringBuilder();
    viewLog = new StringBuilder();
    this.model = new HexagonReversi(6);
    IPlayer player1 = new MockPlayer(playerLog);
    ReversiView view = new MockView(viewLog);
    Controller controller = new Controller(model, view, player1, TokenColor.BLACK);
  }


  @Test
  public void testPlayerFeatureAddListenerCalled() {
    this.init();
    this.model.startGame();
    Assert.assertTrue(playerLog.toString().contains("Player: addListener called"));
  }

  @Test
  public void testPlayerListenForMoveCalled() {
    this.init();
    this.model.startGame();
    System.out.println(playerLog.toString());
    Assert.assertTrue(playerLog.toString().contains("Player: listenForMove called"));
  }

  @Test
  public void testPlayMoveCalled() {
    this.init();
    this.model.startGame();
    System.out.println(playerLog.toString());
    Assert.assertTrue(playerLog.toString().contains("Player: playMove called"));
  }


  @Test
  public void testViewAddFeatureListenerCalled() {
    this.init();
    this.model.startGame();
    Assert.assertTrue(viewLog.toString().contains("View: addFeatureListener called"));
  }

  @Test
  public void testViewDisplayCalled() {
    this.init();
    this.model.startGame();
    System.out.println(viewLog.toString());
    Assert.assertTrue(viewLog.toString().contains("View: display called with show: true"));
  }

  @Test
  public void testViewAdvanceCalled() {
    this.init();
    this.model.startGame();
    Assert.assertTrue(viewLog.toString().contains("View: advance called"));
  }

  @Test
  public void testListenToMoveCalled() {
    this.init();
    this.model.startGame();
    Assert.assertTrue(viewLog.toString().contains("View: listenToMove called with color: B"));
  }
}
