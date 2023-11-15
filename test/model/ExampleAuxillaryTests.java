package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import view.ReversiTextView;
import view.TextView;


/**
 * This class contains tests for the HexagonBoard class.d
 */
public class ExampleAuxillaryTests {
  IReversiModel basicModelThree;
  IReversiModel basicModelFour;
  IBoard riggedBoard;

  @Test
  public void testTextViewWorks() {
    basicModelThree = new HexagonReversi(3);
    basicModelFour = new HexagonReversi(4);
    TextView tv3 = new ReversiTextView(this.basicModelThree);
    TextView tv4 = new ReversiTextView(this.basicModelFour);

    Assert.assertTrue(tv3.toString().contains(
            "       - - - \n" +
            "      - B W - \n" +
            "     - W - B - \n" +
            "      - B W -   \n" +
            "       - - -     \n"));

    Assert.assertTrue(tv4.toString().contains(
            "         - - - - \n" +
            "        - - - - - \n" +
            "       - - B W - - \n" +
            "      - - W - B - - \n" +
            "       - - B W - -   \n" +
            "        - - - - -     \n" +
            "         - - - -       \n"));
  }

  @Test
  public void testCellReturnsCoordinatesCorrectly() {
    ICell hex0 = new HexagonCell(0,0,0);
    ICell hex1 = new HexagonCell(1, -1, 0);
    ICell hex2 = new HexagonCell(10, -6, -4);

    Assert.assertEquals(hex0.getCoordinates(), Arrays.asList(0,0,0));
    Assert.assertEquals(hex1.getCoordinates(), Arrays.asList(1, -1, 0));
    Assert.assertEquals(hex2.getCoordinates(), Arrays.asList(10, -6, -4));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCellCoordinatesSumNotZero() {
    ICell hex0 = new HexagonCell(0,1,0);
    ICell hex1 = new HexagonCell(-1,1,1);
  }

  @Test
  public void testPlayerNextWorks() {
    Assert.assertEquals(Color.WHITE.next(), Color.BLACK);
    Assert.assertEquals(Color.WHITE.next().next(), Color.WHITE);
    Assert.assertEquals(Color.BLACK.next(), Color.WHITE);
    Assert.assertEquals(Color.BLACK.next().next(), Color.BLACK);
  }

  @Test
  public void testPlayerDoesNotChangePlayerButReturnsNewPlayer() {
    Color p1 = Color.WHITE;
    Color p2 = p1.next();
    Assert.assertEquals(p1, Color.WHITE);
  }
}
