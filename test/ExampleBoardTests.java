

import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;
import java.util.Optional;

import model.HexagonBoard;
import model.HexagonCell;
import model.HexagonReversi;
import model.IReversiModel;
import model.Player;
import view.ReversiTextView;
import view.TextView;


public class ExampleBoardTests {

  @Test
  public void testHexagonBoardToString() {
    HexagonBoard hexagonBoard = new HexagonBoard(2);
    // Populate the board with some cells and players
    hexagonBoard.newCellOwner(new HexagonCell(0, 0, 0), Optional.of(Player.BLACK));
    hexagonBoard.newCellOwner(new HexagonCell(1, -1, 0), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(1, 0, -1), Optional.of(Player.BLACK));
    hexagonBoard.newCellOwner(new HexagonCell(0, 1, -1), Optional.empty());
    hexagonBoard.newCellOwner(new HexagonCell(-1, 1, 0), Optional.of(Player.BLACK));
    hexagonBoard.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(0, -1, 1), Optional.of(Player.BLACK));
    hexagonBoard.newCellOwner(new HexagonCell(0, -1, 1), Optional.of(Player.BLACK));

    System.out.println(hexagonBoard.toString());
  }
}
