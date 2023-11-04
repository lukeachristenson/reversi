package view;

public interface ReversiView {

  void addFeatureListener(ViewFeatures features);

  void display(boolean show);

  // Update board.
  void advance();

  void error();
}
