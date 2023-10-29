package model;
import java.util.Optional;

/**
 * This class represents a game of HexReversi. It implements the IReversiModel interface.
 * HexReversi is a hexagonal version of the game Reversi.
 */
public class HexagonReversi implements IReversiModel{
  IBoard board;
  Player currentPlayer;
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
    this.currentPlayer = Player.BLACK;
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
    this.currentPlayer = Player.BLACK;
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
    this.board.newCellOwner(new HexagonCell(-1, 1, 0), Optional.of(Player.BLACK));
    this.board.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(Player.WHITE));
    this.board.newCellOwner(new HexagonCell(1, 0, -1), Optional.of(Player.BLACK));
    this.board.newCellOwner(new HexagonCell(1, -1, 0), Optional.of(Player.WHITE));
    this.board.newCellOwner(new HexagonCell(0, -1, 1), Optional.of(Player.BLACK));
    this.board.newCellOwner(new HexagonCell(0, 1, -1), Optional.of(Player.WHITE));

    // Used for testing only
//    this.board.newCellOwner(new HexagonCell(0,0,0), Optional.empty());
//    this.board.newCellOwner(new HexagonCell(1, -1, 0), Optional.of(Player.BLACK));
//    this.board.newCellOwner(new HexagonCell(1, 0, -1), Optional.of(Player.BLACK));
//    this.board.newCellOwner(new HexagonCell(0, 1, -1), Optional.of(Player.BLACK));
//    this.board.newCellOwner(new HexagonCell(-1, 1, 0), Optional.of(Player.BLACK));
//    this.board.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(Player.BLACK));
//    this.board.newCellOwner(new HexagonCell(0, -1, 1), Optional.of(Player.BLACK));
//
//    this.board.newCellOwner(new HexagonCell(-2, 2, 0), Optional.of(Player.BLACK));
//    this.board.newCellOwner(new HexagonCell(-1, 2, -1), Optional.of(Player.WHITE));
//    this.board.newCellOwner(new HexagonCell(0, 2, -2), Optional.of(Player.BLACK));
//    this.board.newCellOwner(new HexagonCell(-2, 1, 1), Optional.of(Player.WHITE));
//    this.board.newCellOwner(new HexagonCell(-2, 0 , 2), Optional.of(Player.BLACK));
//    this.board.newCellOwner(new HexagonCell(-1, -1, 2), Optional.of(Player.WHITE));
//    this.board.newCellOwner(new HexagonCell(1, 1, -2), Optional.of(Player.WHITE));
//    this.board.newCellOwner(new HexagonCell(2, 0, -2), Optional.of(Player.BLACK));
//    this.board.newCellOwner(new HexagonCell(2, -1, -1), Optional.of(Player.BLACK));
//    this.board.newCellOwner(new HexagonCell(0, -2, 2), Optional.of(Player.BLACK));
//    this.board.newCellOwner(new HexagonCell(1, -2, 1), Optional.of(Player.WHITE));
//    this.board.newCellOwner(new HexagonCell(2, -2, 0), Optional.of(Player.WHITE));

  }

  @Override
  public void placePiece(ICell targetCell, Player player) throws IllegalStateException
          , IllegalArgumentException {
    this.gameStartedChecker();

//    System.out.println(this.board.validMove(targetCell, this.currentPlayer, ));
    if (this.board.validMove(targetCell, player, false)) {
      this.board.validMove(targetCell, player, true);
      this.board.newCellOwner(targetCell, Optional.of(player));
      this.passCount = 0;
//      this.currentPlayer = this.currentPlayer.next();
    } else {
      throw new IllegalStateException("Invalid move");
    }
  }


  @Override
  public void passTurn() throws IllegalStateException {
    if (this.passCount > Player.values().length) {
      throw new IllegalStateException("cannot pass more than " + Player.values().length
              + " times, game should be over");
    }
    this.gameStartedChecker();
    this.passCount++;
    this.currentPlayer = this.currentPlayer.next();
  }


  @Override
  public int getDimensions() throws IllegalStateException {
    this.gameStartedChecker();
    return this.sideLength;
  }

  @Override
  public Optional<Player> getCellState(ICell cell) throws IllegalArgumentException
          , IllegalStateException {
    this.gameStartedChecker();
    return this.board.getCellOccupant(cell);
  }

  @Override
  public int getScore(Player player) throws IllegalArgumentException
          , IllegalStateException {
    this.gameStartedChecker();
    return this.board.getScore(player);
  }

  @Override
  public Player getCurrentPlayer() throws IllegalStateException{
    this.gameStartedChecker();
    return this.currentPlayer;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException{
    this.gameStartedChecker();
    if (this.passCount >= Player.values().length) {
      this.gameRunning = false;
      return true;
    }

    if(this.board.validMovesLeft(Player.WHITE).isEmpty() &&
            this.board.validMovesLeft(Player.BLACK).isEmpty()) {
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

}
