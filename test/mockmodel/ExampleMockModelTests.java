package mockmodel;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.view.BasicReversiView;
import cs3500.reversi.view.ReversiView;
import mockplayerview.MockPlayer;
import mockplayerview.MockView;

/**
 * A JUnit 4 test class that tests communications in the following directions:
 * <p> Player -> Controller -> Model </p>
 * <p> View -> Controller -> Model </p>
 * <p> </p>
 * Tests whether the model methods are being called in the right order and whether they are
 * being called at all when the player and view communicate with the controller about a move.
 * Furthermore, test methods in this class test whether the actions are being separated between
 * the times when the game is just set up and when the game is actually started.
 */
public class ExampleMockModelTests {
  private IReversiModel model;

  private MockView mockView;
  private MockPlayer mockPlayer;
  private StringBuilder modelLog;

  private void init() {
    // Initialize the model, players, and controller
    modelLog = new StringBuilder();
    this.model = new MockModel(modelLog);
    IPlayer player1 = new MockPlayer(new StringBuilder());
    ReversiView view = new BasicReversiView(model, TokenColor.BLACK);
    Controller controller = new Controller(model, view, player1, TokenColor.BLACK);
  }


  // Pre Start Game - Set Up Board
  @Test
  public void testCreateBoardCopyCalledByView() {
    this.init();
    this.model.startGame();
    Assert.assertTrue(modelLog.toString().contains("createBoardCopy called"));
  }

  @Test
  public void testAddListenersCalled() {
    this.init();
    this.model.startGame();
    Assert.assertTrue(modelLog.toString().contains("addListener called to add a ModelFeature"));
  }

  @Test
  public void testStartGameCalled() {
    this.init();
    this.model.startGame();
    Assert.assertTrue(modelLog.toString().contains("startGame called"));
  }

  // Post Start Game - The game begins.

  @Test
  public void testEmitMessageCalled() {
    this.init();
    this.model.startGame();
    Assert.assertTrue(modelLog.toString().contains("emitMessage called"));
  }

  @Test
  public void testNotifyPlayerMoveCalled() {
    this.init();
    this.model.startGame();
    Assert.assertTrue(modelLog.toString().contains("notifyPlayerMove called on single listener"));
  }

  @Test
  public void testGetCurrentColorCalled() {
    this.init();
    this.model.startGame();
    Assert.assertTrue(modelLog.toString().contains("getCurrentColor called"));
  }

  @Test
  public void testIsGameOverCalled() {
    this.init();
    this.model.startGame();
    Assert.assertTrue(modelLog.toString().contains("isGameOver called"));
  }
}
