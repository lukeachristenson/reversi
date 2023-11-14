package strategy;

import model.Color;
import model.HexagonReversi;
import model.IBoard;
import model.ICell;
import model.IReversiModel;
import model.ROModel;

import java.util.HashMap;
import java.util.List;


public class MiniMaxStrategyV2 implements Strategy {
  private final Color color;

  public MiniMaxStrategyV2(Color color) {
    this.color = color;
  }

  @Override
  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
    //for each cell in the filtered list of moves try the move
    for(ICell cell : filteredMoves) {
      IBoard board = model.createBoardCopy();
      IReversiModel modelCopy = new HexagonReversi(board, model.getDimensions());
      modelCopy.placeCurrentPlayerPiece(cell);
      //try every opponent response to your move,
      for (ICell otherCell : model.getValidMoves(getOtherColor(color))) {
        IReversiModel newModelCopy = new HexagonReversi(board, model.getDimensions());
        newModelCopy.placePiece(otherCell, modelCopy.getCurrentPlayer());

        int curScore = modelCopy.getScore(color);
        if (curScore > )

      }

    }
  }



  private Color getOtherColor(Color color) {
    if (color.equals(Color.BLACK)) {
      return Color.WHITE;
    }
    return Color.BLACK;
  }


}
