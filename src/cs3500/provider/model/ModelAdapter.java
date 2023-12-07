package cs3500.provider.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import cs3500.provider.controller.ObserverInterface;
import cs3500.reversi.model.HexagonCell;
import cs3500.reversi.model.HexagonReversi;
import cs3500.reversi.model.ICell;
import cs3500.reversi.model.IReversiModel;
import cs3500.reversi.model.TokenColor;

/**
 * A class that adapts a reversi.model to a provider.model.
 */

public class ModelAdapter implements ReversiModel {
  private IReversiModel model;
  private List<ObserverInterface> observers;
  private int currentPlayer;

  public ModelAdapter(IReversiModel model) {
//    System.out.println("ModelAdapter constructor");
    this.model = model;
    this.observers = new ArrayList<>();
  }

  @Override
  public void startGame(int sideLength, int p) {
    this.model = new HexagonReversi(sideLength);
    this.model.startGame();
//    System.out.println("ModelAdapter startGame");
    this.notifyObserverTurn();
  }

  @Override
  public void flipCell(CubicCoordinate c, int p) {
    TokenColor moveColor = playerConverter(p);
    TokenColor currentPlayer = this.model.getCurrentColor();
    //checks that it is the players turn to move
    if (!moveColor.equals(currentPlayer)) {
      throw new IllegalStateException("not player " + p + "'s turn");
    }
    try {
      this.model.placeCurrentPlayerPiece(new HexagonCell(c.getX(), c.getY(), c.getZ()));
    } catch (IllegalStateException e) {
      throw new IllegalStateException("Game has not started yet");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Coordinate is not on the board");
    }
    this.notifyObserverTurn();
  }

  @Override
  public void passMove(int p) {
    try {
      this.model.passTurn(true);
    } catch (IllegalStateException e) {
      throw new IllegalStateException("Game has not started yet");
    }
    this.notifyObserverTurn();
  }

  @Override
  public void notifyObserverTurn() {
    System.out.println("ModelAdapter notifyObserverTurn: " + this.getCurrentTurn());
    for(ObserverInterface observer : this.observers) {
      observer.getNotifiedItsYourPlayersMove();
    }
  }

  @Override
  public void subscribe(ObserverInterface observer) {
    Objects.nonNull(observer);
    this.observers.add(observer);
//    System.out.println("ModelAdapter subscribe");
  }

  @Override
  public LinkedHashMap<CubicCoordinate, Cell> getBoard() {
    Map<ICell, Optional<TokenColor>> board = this.model.createBoardCopy().getPositionsMapCopy();
    LinkedHashMap<CubicCoordinate, Cell> convertedBoard = new LinkedHashMap<>();

    for (Map.Entry<ICell, Optional<TokenColor>> entry : board.entrySet()) {
      ICell iCell = entry.getKey();
      Optional<TokenColor> optionalTokenColor = entry.getValue();

      // Convert ICell to CubicCoordinate
      CubicCoordinate cubicCoordinate = ICellToCubicCoordinate(iCell);

      // Determine the color and create the corresponding BasicCell
      int colorCode;
      if (optionalTokenColor.isPresent()) {
        colorCode = tokenColorToInt(optionalTokenColor.get());
      } else {
        colorCode = 0; // Assuming 0 represents an empty cell
      }
      Cell cell = new BasicCell(colorCode);

      // Add to the converted board
      convertedBoard.put(cubicCoordinate, cell);
    }

    return convertedBoard;
  }


  private CubicCoordinate ICellToCubicCoordinate(ICell icell) {
    List<Integer> intList = icell.getCoordinates();
    return new CubicCoordinate(intList.get(0),
            intList.get(1),
            intList.get(2));
  }

  //converts a tokenColor into an int that represents a player color
  private int tokenColorToInt(TokenColor tokenColor) {
    if (tokenColor == TokenColor.BLACK) {
      return 1;
    } else if (tokenColor == TokenColor.WHITE) {
      return 2;
    } else {
      return 0;
    }
  }

  @Override
  public boolean isGameOver() {
    return this.model.isGameOver();
  }

  @Override
  public int getScore(int p) {
    TokenColor moveColor = playerConverter(p);
    return this.model.getScore(moveColor);
  }

  @Override
  public int getSideLength() {
    return this.model.getDimensions();
  }

  //converts players stored as int values to a player stored as a tokenColor.
  private TokenColor playerConverter(int playerTurn) {
    if (playerTurn == 1) {
      return TokenColor.BLACK;
    } else if (playerTurn == 2) {
      return TokenColor.WHITE;
    } else {
      throw new IllegalArgumentException("Invalid player");
    }
  }

  @Override
  public boolean isMoveValid(CubicCoordinate c, int playerTurn) {
    TokenColor moveColor = playerConverter(playerTurn);
    ICell cell = new HexagonCell(c.getX(),c.getY(),c.getZ());
    List<ICell> validMoves = this.model.getValidMoves(moveColor);
    return validMoves.contains(cell);
  }

  @Override
  public ReversiModel clone() {
    IReversiModel modelClone = new HexagonReversi(this.model.createBoardCopy(),
            this.model.getDimensions());
    return new ModelAdapter(modelClone);
  }

  @Override
  public List<CubicCoordinate> getAvailableMoves(int p) {
    TokenColor color = (p == 0) ? TokenColor.BLACK : TokenColor.WHITE;
    List<CubicCoordinate> ret = new ArrayList<>();
    for (ICell cell : this.model.getValidMoves(color)) {
      ret.add(new CubicCoordinate(cell.getCoordinates().get(0),
              cell.getCoordinates().get(1),
              cell.getCoordinates().get(2)));
    }
    return ret;
  }

  @Override
  public int getCurrentTurn() {
    return (this.model.getCurrentColor().equals(TokenColor.BLACK))? 1 : 2;
  }
}
