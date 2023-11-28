package modelfeatures;

import org.junit.Assert;
import org.junit.Test;

import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.view.BasicReversiView;
import cs3500.reversi.view.ReversiView;

public class ExampleModelFeaturesTests {
  private IReversiModel model;
  private MockModelFeature mockModelFeatures;
  private IPlayer player1;
  private ReversiView view;
  private StringBuilder log;

  private void init() {
    // Initialize the model, players, and controller
    log = new StringBuilder();
    this.model = new HexagonReversi(6);
    this.player1 = new HumanPlayer(TokenColor.BLACK);
    this.view = new BasicReversiView(model, TokenColor.BLACK);
    this.mockModelFeatures = new MockModelFeature(model, view, player1, log);
  }

  @Test
  public void testModelFeaturesCommunicatesWithController() {
    this.init();
    this.model.startGame();
    this.mockModelFeatures.controllerGo();
    System.out.println(log.toString());
    Assert.assertTrue(log.toString().contains("advanceFrame called"));
    Assert.assertTrue(log.toString().contains("controllerGo called"));
    Assert.assertTrue(log.toString().contains("emitMoveColor called with color:B"));
  }
}
