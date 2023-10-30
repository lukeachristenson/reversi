package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class represents a game of HexReversi. It implements the IReversiModel interface.
 * HexReversi is a hexagonal version of the game Reversi.
 */
public class HexagonReversi implements IReversiModel{
  IBoard board;
  Color currentColor;
  IPlayer currentPlayer;
  int passCount;
  boolean gameRunning;
  int sideLength;

  /**
   * Constructor for a HexagonReversi model. Takes in a board, current player, pass count, and
   * whether the game has started or not.
   * @param sideLength the side length of the board.
   * @throws IllegalArgumentException if the side length is less than 3
   */
  public HexagonReversi(int sideLength) throws IllegalArgumentException{
    if (gameRunning) {
      throw new IllegalStateException("Game is already started");
    }
    if (sideLength < 3) {
      throw new IllegalArgumentException("Side length must be greater than 2");
    } else {
      this.sideLength = sideLength;
    }

    this.gameRunning = true;
    this.currentColor = Color.BLACK;
    this.currentPlayer = new HumanPlayer(Color.BLACK);
    this.passCount = 0;
    this.board = this.initBoard(sideLength-1); // rings excluding the center cell = sideLength - 1
    this.addStartingMoves();
  }
  /**
   * Constructor for a HexagonReversi model used ONLY for testing. Rigs the game by
   * passing in a hexagonal board for the game.
   * @param hexBoard rigged board.
   * @throws IllegalArgumentException if the side length is less than 3
   */
  public HexagonReversi(IBoard hexBoard, int sideLength) throws IllegalArgumentException{
    if (gameRunning) {
      throw new IllegalStateException("Game is already started");
    }
    if (sideLength < 3) {
      throw new IllegalArgumentException("Side length must be greater than 2");
    } else {
      this.sideLength = sideLength;
    }

    this.gameRunning = true;
    this.currentColor = Color.BLACK;
    this.passCount = 0;
    this.board = hexBoard;
  }

  //helper to check if the game is started before running any other command.
  private void gameStartedChecker() throws IllegalStateException{
    if (!gameRunning) {
      throw new IllegalStateException("Game is not started");
    }
  }

  //helper to initialize a board based on sideLength
  private IBoard initBoard(int rings) throws IllegalStateException{
    IBoard hexReturn = new HexagonBoard(rings + 1);
    for (int q = -rings; q <= rings; q++) {
      int r1 = Math.max(-rings, -q - rings);
      int r2 = Math.min(rings, -q + rings);

      for (int r = r1; r <= r2; r++) {
        HexagonCell hp = new HexagonCell(q, r, -q-r);
        hexReturn.newCellOwner(hp, Optional.empty());
      }
    }
    return hexReturn;
  }

  //helper to add the starting moves of each player
  private void addStartingMoves() {
    this.board.newCellOwner(new HexagonCell(-1, 1, 0), Optional.of(Color.BLACK));
    this.board.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(Color.WHITE));
    this.board.newCellOwner(new HexagonCell(1, 0, -1), Optional.of(Color.BLACK));
    this.board.newCellOwner(new HexagonCell(1, -1, 0), Optional.of(Color.WHITE));
    this.board.newCellOwner(new HexagonCell(0, -1, 1), Optional.of(Color.BLACK));
    this.board.newCellOwner(new HexagonCell(0, 1, -1), Optional.of(Color.WHITE));
  }

  @Override
  public void placePiece(ICell targetCell, Color color) throws IllegalStateException
          , IllegalArgumentException {
    this.gameStartedChecker();

//    System.out.println(this.board.validMove(targetCell, this.currentPlayer, ));
    if (this.board.validMove(targetCell, color, false)) {
      this.board.validMove(targetCell, color, true);
      this.board.newCellOwner(targetCell, Optional.of(color));
      this.passCount = 0;
//      this.currentPlayer = this.currentPlayer.next();
    } else {
      throw new IllegalStateException("Invalid move");
    }
  }


  @Override
  public void passTurn() throws IllegalStateException {
    if (this.passCount > Color.values().length) {
      throw new IllegalStateException("cannot pass more than " + Color.values().length
              + " times, game should be over");
    }
    this.gameStartedChecker();
    this.passCount++;
    this.currentColor = this.currentColor.next();
    this.currentPlayer = new HumanPlayer(this.currentColor);
  }


  @Override
  public int getDimensions() throws IllegalStateException {
    this.gameStartedChecker();
    return this.sideLength;
  }

  @Override
  public Optional<Color> getCellState(ICell cell) throws IllegalArgumentException
          , IllegalStateException {
    this.gameStartedChecker();
    return this.board.getCellOccupant(cell);
  }

  @Override
  public int getScore(Color color) throws IllegalArgumentException
          , IllegalStateException {
    this.gameStartedChecker();
    return this.board.getScore(color);
  }

  @Override
  public int getScore(IPlayer player) throws IllegalArgumentException
          , IllegalStateException {
    this.gameStartedChecker();
    return this.board.getScore(player.getColor());
  }

  @Override
  public Color getCurrentColor() throws IllegalStateException{
    this.gameStartedChecker();
    return this.currentPlayer.getColor();
  }

  @Override
  public boolean isGameOver() throws IllegalStateException{
    this.gameStartedChecker();
    if (this.passCount >= Color.values().length) {
      this.gameRunning = false;
      return true;
    }

    if(this.board.validMovesLeft(Color.WHITE).isEmpty() &&
            this.board.validMovesLeft(Color.BLACK).isEmpty()) {
      this.gameRunning = false;
      return true;
    }

    return false;
  }

  @Override
    public int getPassCount() {
    return this.passCount;
    }


  @Override
  public String toString() {
    return this.board.toString();
  }

  @Override
  public IPlayer getCurrentPlayer() {
    return new HumanPlayer(this.currentPlayer.getColor());
  }

}
