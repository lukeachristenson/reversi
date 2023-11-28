package cs3500.reversi.player;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.controller.PlayerFeatures;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.strategy.Strategy;

public class AIPlayer implements IPlayer {
  private final TokenColor tokenColor;
  private List<PlayerFeatures> listeners;
  private final Strategy strategy;
  private final ROModel model;

  /**
   * Constructor for a HumanPlayer. Takes in a color and makes a player who will play that color.
   *
   * @param tokenColor    the color of the player.
   * @param strategy the strategy of the player.
   */
  public AIPlayer(TokenColor tokenColor, Strategy strategy, ROModel model) {
    this.tokenColor = tokenColor;
    this.strategy = strategy;
    this.listeners = new ArrayList<>();
    this.model = model;
  }

  @Override
  public void addListener(PlayerFeatures listener) {
    this.listeners.add(listener);
  }

  @Override
  public void playMove() {
    List<ICell> moves = this.strategy.chooseMove(model, model.getValidMoves(this.tokenColor));

    if (!model.isGameOver()) {
      // If passing is the best move, the strategy returns an empty list.
      if (moves.isEmpty()) {
        for (PlayerFeatures listener : this.listeners) {
          listener.pass();
        }
      } else { // Else, play the first move of the list of moves that the strategy returns.
        try {
          // Introduce a delay of half a second (500 milliseconds) before making a move.
          Thread.sleep(50);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        for (PlayerFeatures listener : this.listeners) {
          listener.playMove(moves.get(0));
        }
      }
    }
  }

  @Override
  public void listenForMove(TokenColor tokenColor) {
    // Play the move if it is the AI's turn.
    if (tokenColor.equals(this.tokenColor)) {
      this.playMove();
    }
  }
}
