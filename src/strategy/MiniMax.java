package strategy;
import model.Color;
import model.ICell;
import model.ROModel;
import java.util.List;


public class MiniMax implements Strategy {
  private final Color color;

  public MiniMax(Color color) {
    this.color = color;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
      return null;
  }
  private int evaluateBoard(ROModel model) {
    return 0;
  }
}
