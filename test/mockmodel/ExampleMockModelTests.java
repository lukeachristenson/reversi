package mockmodel;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.view.BasicReversiView;
import cs3500.reversi.view.ReversiView;
import mockplayerview.MockPlayer;
import mockplayerview.MockView;

public class ExampleMockModelTests {
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
    this.model = new MockModel(modelLog);
    this.player1 = new MockPlayer(new StringBuilder());
    this.view = new BasicReversiView(model, TokenColor.BLACK);
    controller = new Controller(model, view, player1, TokenColor.BLACK);
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
  public void testEmitColorCalled() {
    this.init();
    this.model.startGame();
    Assert.assertTrue(modelLog.toString().contains("emitColor called on single listener"));
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
