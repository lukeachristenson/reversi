package cs3500.reversi.model;

public interface ModelFeature {
  void emitMoveColor(TokenColor tokenColor);
  void advanceFrame();
}
