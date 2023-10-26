import java.util.List;

public class AbstractCell implements ICell{

  @Override
  public List<Integer> getCoordinates() {
    return null;
  }

  @Override
  public PlayerEnum.Player getPlayer() {
    return null;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean isBlack() {
    return false;
  }

  @Override
  public boolean isWhite() {
    return false;
  }

  @Override
  public boolean isPlayer(PlayerEnum.Player player) throws IllegalArgumentException {
    return false;
  }

  @Override
  public boolean isOppositePlayer(PlayerEnum.Player player) throws IllegalArgumentException {
    return false;
  }

  @Override
  public boolean isSameColor(ICell cell) {
    return false;
  }
}
