package strategy;

import java.util.List;
import java.util.Optional;

import model.ICell;
import model.IReversiModel;
import model.ROModel;

public interface Strategy {
  List<ICell> chooseMove(ROModel model, Optional<List<ICell>> filteredMoves);
}