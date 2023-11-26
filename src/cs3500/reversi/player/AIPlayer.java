package cs3500.reversi.player;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.model.Color;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.strategy.Strategy;
import cs3500.reversi.view.ViewFeatures;

public class AIPlayer implements IPlayer{
  private final Color color;
  private List<ViewFeatures> listeners;
  private final Strategy strategy;

  /**
   * Constructor for a HumanPlayer. Takes in a color and makes a player who will play that color.
   * @param color the color of the player.
   * @param strategy  the strategy of the player.
   */
  public AIPlayer(Color color, Strategy strategy) {
    this.color = color;
    this.strategy = strategy;
    this.listeners = new ArrayList<>();
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public void addListener(ViewFeatures listener) {
    this.listeners.add(listener);
  }

  @Override
  public void playMove(IReversiModel model) {
    List<ICell> moves = this.strategy.chooseMove(model, model.getValidMoves(this.color));

    // If passing is the best move, the strategy returns an empty list.
    if(moves.isEmpty()) {
      for(ViewFeatures listener : this.listeners) {
        System.out.println("AI is passing");
        listener.pass();
      }
    } else { // Else, play the first move of the list of moves that the strategy returns.
      for(ViewFeatures listener : this.listeners) {
        System.out.println("AI is playing move");
        listener.playMove(moves.get(0));
      }
    }
  }

  @Override
  public void listenForMove(Color color, IReversiModel model) {
    // Play the move if it is the AI's turn.
    if(color == this.color) {
      System.out.println("AI has detected for a move");
      this.playMove(model);
    }
  }
}
