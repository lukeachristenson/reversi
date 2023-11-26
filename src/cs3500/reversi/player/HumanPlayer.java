package cs3500.reversi.player;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.View;

import cs3500.reversi.model.Color;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.view.ViewFeatures;

/**
 * A class to represent a human player.
 */
public class HumanPlayer implements IPlayer {
  private final Color color;
  private  List<ViewFeatures> listeners;

  /**
   * Constructor for a HumanPlayer. Takes in a color and makes a player who will play that color.
   *
   * @param color the color of the player.
   */
  public HumanPlayer(Color color) {
    this.color = color;
    this.listeners = new ArrayList<>();
  }

  @Override
  public Color getColor() {
    if (this.color.toString().equals("B")) {
      return Color.BLACK;
    } else {
      return Color.WHITE;
    }
  }

  @Override
  public void addListener(ViewFeatures listener) {
    this.listeners.add(listener);
  }

  @Override
  public void playMove(IReversiModel model) {
    // Do nothing
  }

  @Override
  public void listenForMove(Color color, IReversiModel model) {
    // Do nothing
  }
}
