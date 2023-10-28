

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
    HexagonBoard hexagonBoard = new HexagonBoard(3);
    // Populate the board with some cells and players
//    hexagonBoard.newCellOwner(, Optional.empty());
    hexagonBoard.newCellOwner(new HexagonCell(0,0,0), Optional.empty());
    hexagonBoard.newCellOwner(new HexagonCell(1, -1, 0), Optional.of(Player.BLACK));
    hexagonBoard.newCellOwner(new HexagonCell(1, 0, -1), Optional.of(Player.BLACK));
    hexagonBoard.newCellOwner(new HexagonCell(0, 1, -1), Optional.empty());
    hexagonBoard.newCellOwner(new HexagonCell(-1, 1, 0), Optional.empty());
    hexagonBoard.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(Player.BLACK));
    hexagonBoard.newCellOwner(new HexagonCell(0, -1, 1), Optional.of(Player.BLACK));

    hexagonBoard.newCellOwner(new HexagonCell(-2, 2, 0), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(-1, 2, -1), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(0, 2, -2), Optional.of(Player.WHITE));

    hexagonBoard.newCellOwner(new HexagonCell(-2, 1, 1), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(-2, 0 , 2), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(-1, -1, 2), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(1, 1, -2), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(2, 0, -2), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(2, -1, -1), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(0, -2, 2), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(1, -2, 1), Optional.of(Player.WHITE));
    hexagonBoard.newCellOwner(new HexagonCell(2, -2, 0), Optional.of(Player.WHITE));

    System.out.println(hexagonBoard.validMove(new HexagonCell(0, 0, 0), Player.WHITE));

    System.out.println(hexagonBoard.toString());
  }

  @Test
  public void test() {
    IReversiModel model = new HexagonReversi(6);
    TextView t1 = new ReversiTextView(model);

    System.out.println(t1.toString());
  }
}
