import java.util.Optional;

/**
 * This class represents a game of HexReversi. It implements the IReversiModel interface.
 * HexReversi is a hexagonal version of the game Reversi
 */
public class HexReversi implements IReversiModel{
  IBoard board;
  PlayerEnum.Player currentPlayer;
  int passCount;
  boolean gameStarted;
  int sideLength;

  /**
   * Constructs a HexReversi game object.
   */
  public HexReversi() {
    this.board = new HexagonBoard(sideLength);
    this.currentPlayer = PlayerEnum.Player.BLACK;
    this.passCount = 0;
  }

  @Override
  public void startGame(int sideLength) {
    if (sideLength < 1) {
      throw new IllegalArgumentException("Side length must be greater than 0");
    } else {
      this.sideLength = sideLength;
    }
    if (gameStarted) {
      throw new IllegalStateException("Game is already started");
    }
    this.initBoard(sideLength);
  }

  //helper to check if the game is started before running any other command.
  private void gameStartedChecker() throws IllegalStateException{
    if (!gameStarted) {
      throw new IllegalStateException("Game is not started");
    }
  }

  //helper to initialize a board based on sideLength
  private void initBoard(int sideLength) throws IllegalStateException{
    this.gameStartedChecker();
    for (int x = -sideLength; x < sideLength; x++) {
      for (int y = -sideLength; y < sideLength; y++) {
        for (int z = -sideLength; z < sideLength; z++) {
          this.board.newCellOwner(new HexagonCell(x,y,z), Optional.empty());
        }
      }
    }
    this.addStartingMoves();
  }

  //todo
  // make this helper add the starting moves to the board
  private void addStartingMoves(){}

  @Override
  public void placePiece(ICell targetCell, PlayerEnum.Player player) throws IllegalStateException
          , IllegalArgumentException {
    this.gameStartedChecker();
    if (this.board.validMove(targetCell, this.currentPlayer)) {
      this.board.newCellOwner(targetCell, Optional.of(this.currentPlayer));
      this.passCount = 0;
      this.currentPlayer = this.currentPlayer.next();
    } else {
      throw new IllegalStateException("Invalid move");
    }
  }

  @Override
  public int getDimensions() throws IllegalStateException {
    this.gameStartedChecker();
    return this.sideLength;
  }

  @Override
  public Optional<PlayerEnum.Player> getCellState(ICell cell) throws IllegalArgumentException
          , IllegalStateException {
    this.gameStartedChecker();
    return this.board.getCellState(cell);
  }

  @Override
  public int getScore(PlayerEnum.Player player) throws IllegalArgumentException
          , IllegalStateException {
    this.gameStartedChecker();
    int score = 0;
    for (int x = -sideLength; x < sideLength; x++) {
      for (int y = -sideLength; y < sideLength; y++) {
        for (int z = -sideLength; z < sideLength; z++) {
          Optional<PlayerEnum.Player> cellPlayer = this.board.getCellState(new HexagonCell(x, y, z));
          if (cellPlayer.isPresent() && cellPlayer.get().equals(player)) {
            score++;
          }
        }
      }
    }
    return score;
  }

  @Override
  public PlayerEnum.Player getCurrentPlayer() throws IllegalStateException{
    this.gameStartedChecker();
    return this.currentPlayer;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException{
    this.gameStartedChecker();
    for (int x = -sideLength; x < sideLength; x++) {
      for (int y = -sideLength; y < sideLength; y++) {
        for (int z = -sideLength; z < sideLength; z++) {
          if (this.board.validMove(new HexagonCell(x,y,z), this.currentPlayer)) {
            return false;
          }
        }
      }
    }
    return true;
  }
}
