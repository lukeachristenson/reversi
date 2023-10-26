import java.util.Optional;

/**
 * This class represents a game of HexReversi. It implements the IReversiModel interface.
 * HexReversi is a hexagonal version of the game Reversi.
 */
public class HexagonReversi implements IReversiModel{
  IBoard board;
  Player currentPlayer;
  int passCount;
  boolean gameStarted;
  int sideLength;

  /**
   * Constructor for a HexagonReversi model. Takes in a board, current player, pass count, and
   * whether the game has started or not.
   * @param board the board to be used.
   * @param currentPlayer the current player.
   * @param passCount the number of times a player has passed.
   * @param gameStarted whether the game has started or not.
   * @param sideLength the side length of the board.
   * @throws IllegalArgumentException if the side length is less than 3
   */
  public HexagonReversi(IBoard board, Player currentPlayer, int passCount, boolean gameStarted
          , int sideLength) throws IllegalArgumentException{
    if (gameStarted) {
      throw new IllegalStateException("Game is already started");
    }
    if (sideLength < 3) {
      throw new IllegalArgumentException("Side length must be greater than 2");
    } else {
      this.sideLength = sideLength;
    }
    this.board = new HexagonBoard(sideLength);
    this.currentPlayer = Player.BLACK;
    this.passCount = 0;
    this.initBoard(sideLength);
    this.addStartingMoves();
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
  }

  //helper to add the starting moves of each player
  private void addStartingMoves() {
    Player playerToAdd = currentPlayer;
    this.board.newCellOwner(new HexagonCell(0, -1, 1), Optional.of(playerToAdd));
    playerToAdd = playerToAdd.next();
    this.board.newCellOwner(new HexagonCell(1, -1, 0), Optional.of(playerToAdd));
    playerToAdd = playerToAdd.next();
    this.board.newCellOwner(new HexagonCell(1, 0, -1), Optional.of(playerToAdd));
    playerToAdd = playerToAdd.next();
    this.board.newCellOwner(new HexagonCell(0, 1, -1), Optional.of(playerToAdd));
    playerToAdd = playerToAdd.next();
    this.board.newCellOwner(new HexagonCell(-1, 1, 0), Optional.of(playerToAdd));
    playerToAdd = playerToAdd.next();
    this.board.newCellOwner(new HexagonCell(-1, 0, 1), Optional.of(playerToAdd));
  }

  @Override
  public void placePiece(ICell targetCell, Player player) throws IllegalStateException
          , IllegalArgumentException {
    this.gameStartedChecker();
    if (this.board.validMove(targetCell, this.currentPlayer)) {
      this.flipEverythingBetweenCells(targetCell, this.currentPlayer);
      //fixme this would handle only the target cell
//      this.board.newCellOwner(targetCell, Optional.of(this.currentPlayer));
      this.passCount = 0;
      this.currentPlayer = this.currentPlayer.next();
    } else {
      throw new IllegalStateException("Invalid move");
    }
  }

  //this helper will use newCellOwner to flip everything new to be flipped over.
  private void flipEverythingBetweenCells(ICell targetCell, Player player) {
  }

  @Override
  public void passTurn() throws IllegalStateException {
    this.gameStartedChecker();
    this.passCount++;
    if (this.passCount == Player.values().length) {
      this.gameStarted = false;
    }
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
    return this.board.getCellState(cell);
  }

  @Override
  public int getScore(Player player) throws IllegalArgumentException
          , IllegalStateException {
    this.gameStartedChecker();
    int score = 0;
    for (int x = -sideLength; x < sideLength; x++) {
      for (int y = -sideLength; y < sideLength; y++) {
        for (int z = -sideLength; z < sideLength; z++) {
          Optional<Player> cellPlayer = this.board.getCellState(new HexagonCell(x, y, z));
          if (cellPlayer.isPresent() && cellPlayer.get().equals(player)) {
            score++;
          }
        }
      }
    }
    return score;
  }

  @Override
  public Player getCurrentPlayer() throws IllegalStateException{
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
