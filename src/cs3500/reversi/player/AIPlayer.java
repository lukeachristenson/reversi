package cs3500.reversi.player;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.controller.IPlayerFeature;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.ROModel;
import cs3500.reversi.model.TokenColor;
import cs3500.reversi.strategy.Strategy;

/**
 * Represents an AI player in a game of Reversi. The AI player has a color and a strategy. The AI
 * player can play a move through the controller that is a part of its listeners when it is its
 * turn. The AI player can also listen for moves from the controller.
 */
public class AIPlayer implements IPlayer {
  private final TokenColor tokenColor;
  private List<IPlayerFeature> listeners;
  private final Strategy strategy;
  private final ROModel model;

  /**
   * Constructor for an AIPlayer. Takes in a color, a strategy, and a read only model.
   *
   * @param tokenColor the color of the player.
   * @param strategy   the strategy of the player.
   */
  public AIPlayer(TokenColor tokenColor, Strategy strategy, ROModel model) {
    this.tokenColor = tokenColor;
    this.strategy = strategy;
    this.listeners = new ArrayList<>();
    this.model = model;
  }

  @Override
  public void addListener(IPlayerFeature listener) {
    this.listeners.add(listener);
  }

  @Override
  public void playMove() {
    List<ICell> moves = this.strategy.chooseMove(model, model.getValidMoves(this.tokenColor));

    if (!model.isGameOver()) {
      // If passing is the best move, the strategy returns an empty list.
      if (moves.isEmpty()) {
        for (IPlayerFeature listener : this.listeners) {
          listener.pass();
        }
      } else { // Else, play the first move of the list of moves that the strategy returns.
        try {
          // Delay before move is made.
          Thread.sleep(50);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        for (IPlayerFeature listener : this.listeners) {
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
