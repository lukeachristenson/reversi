package modelfeatures;

import java.util.Objects;

import cs3500.reversi.model.TokenColor;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.ModelFeature;
import cs3500.reversi.player.IPlayer;
import cs3500.reversi.view.ReversiView;

/**
 * A controller for the Reversi game. This controller takes in a model and two views and allows
 * the user to play the game.
 */
public class MockModelFeature implements ModelFeature {
  private final IReversiModel model;
  private final ReversiView view;
  private final IPlayer player;
  private final StringBuilder log;

  /**
   * Constructs a Controller with the given model and views.
   *
   * @param model the model to be used
   * @param view  the view to be used
   */
  public MockModelFeature(IReversiModel model, ReversiView view, IPlayer player, StringBuilder log) {
    this.log = Objects.requireNonNull(log);
    this.model = Objects.requireNonNull(model);
    this.view = Objects.requireNonNull(view);
    this.player = Objects.requireNonNull(player);
//    view.addFeatureListener(this);
//    player.addListener(this);
  }

  /**
   * Constructs a Controller with the given model and views.
   */
  public void controllerGo() {
    this.emitMoveColor(this.model.getCurrentColor());
    log.append("controllerGo called\n");
  }



  @Override
  public void emitMoveColor(TokenColor tokenColor) {
    this.advanceFrame();
    this.player.listenForMove(tokenColor);
    this.view.listenToMove(tokenColor);
    log.append("emitMoveColor called with color:" + tokenColor.toString() + "\n");
  }

  @Override
  public void advanceFrame() {
    this.view.display(true);
    this.view.advance();
    log.append("advanceFrame called\n");
  }
}
