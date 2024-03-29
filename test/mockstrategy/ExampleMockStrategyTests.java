package mockstrategy;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.controller.Controller;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.player.AIPlayer;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.strategy.Strategy;
import cs3500.reversi.view.HexagonReversiView;
import cs3500.reversi.view.ReversiView;

/**
 * A JUnit 4 test class to test the initialization and functions of the controller.
 */
public class ExampleMockStrategyTests {
  private IReversiModel model;
  private StringBuilder strategyLog;

  // Initialize the model, players, and controller
  private void init() {
    this.strategyLog = new StringBuilder();
    Strategy mockStrategy = new MockStrategy(strategyLog);
    this.model = new HexagonReversi(6);
    IPlayer player1 = new AIPlayer(TokenColor.BLACK, mockStrategy, model);
    ReversiView view = new HexagonReversiView(model, TokenColor.BLACK);
    Controller controller = new Controller(model, view, player1, TokenColor.BLACK);
  }

  @Test
  public void testChooseMoveCalled() {
    init();
    Assert.assertTrue(strategyLog.toString().isEmpty());
    model.startGame();
    Assert.assertTrue(strategyLog.toString().contains("chooseMove called with filteredMoves " +
            "list of size: 6"));
  }

  // Mock Strategy class to test whether the chooseMove method is called
  private class MockStrategy implements Strategy {
    private final StringBuilder log;

    /**
     * Constructor for the mock strategy.
     * @param log the log to keep track of when methods are called.
     */
    public MockStrategy(StringBuilder log) {
      this.log = log;
    }

    @Override
    public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
      log.append("chooseMove called with filteredMoves list of size: " + filteredMoves.size());
      return new ArrayList<>();
    }
  }
}
