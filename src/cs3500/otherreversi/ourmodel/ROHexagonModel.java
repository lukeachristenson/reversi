package cs3500.otherreversi.ourmodel;

/**
 * This class represents a Hexagon Reversi model that is read-only. It is used for the view and
 * strategies and to get observations from the move.
 */
public class ROHexagonModel extends HexagonReversi {

  /**
   * Constructor for a Read Only Hexagon Reversi model. Takes in the side length of the board.
   * @param sideLength  the side length of the board.
   * @throws IllegalArgumentException if the side length is less than 3
   */
  public ROHexagonModel(int sideLength) throws IllegalArgumentException {
    super(sideLength);
  }

  /**
   * Constructor for a Read Only Hexagon Reversi model used ONLY for testing. Rigs the game by
   * passing in a hexagonal board for the game.
   * @param hexBoard rigged board.
   * @throws IllegalArgumentException if the side length is less than 3
   */
  public ROHexagonModel(IBoard hexBoard, int sideLength) throws IllegalArgumentException {
    super(hexBoard, sideLength);
  }
}
