package model;

public class HumanPlayer implements IPlayer{
  private Color color;

  public HumanPlayer(Color color) {
    this.color = color;
  }

  @Override
  public Color getColor() {
    if(this.color.toString().equals("B")) {
      return Color.BLACK;
    } else {
      return Color.WHITE;
    }
  }
}
