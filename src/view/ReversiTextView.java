package view;

import model.IReversiModel;

/**
 * This class represents a text view for a game of Reversi.
 */
public class ReversiTextView implements TextView {
  private final IReversiModel model;
  private Appendable appendable;

  /**
   * Constructor for a ReversiTextView. Takes in a model.
   * @param model the model to represent as a text view.
   */
  public ReversiTextView(IReversiModel model) {
    this.model = model;
  }

  @Override
  public String toString() {
    return this.model.toString();
  }

}
