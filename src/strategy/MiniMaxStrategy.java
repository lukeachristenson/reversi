//package strategy;
//
//import model.Color;
//import model.HexagonReversi;
//import model.IBoard;
//import model.ICell;
//import model.IReversiModel;
//import model.ROModel;
//
//import java.util.HashMap;
//import java.util.List;
//
//
//public class MiniMaxStrategy implements Strategy {
//  private final Color color;
//
//
//  /**
//   * function minimax(node, depth, maximizingPlayer):
//   *     if depth is 0 or node is a terminal node:
//   *         return evaluate(node)  // Evaluate the heuristic value of the node
//   *
//   *     if maximizingPlayer:
//   *         bestValue = negative infinity
//   *         for each child in node.children:
//   *             value = minimax(child, depth - 1, false)
//   *             bestValue = max(bestValue, value)
//   *         return bestValue
//   *     else:
//   *         bestValue = positive infinity
//   *         for each child in node.children:
//   *             value = minimax(child, depth - 1, true)
//   *             bestValue = min(bestValue, value)
//   *         return bestValue
//   */
//  private int minimax(Node node, int depth, boolean maximizingPlayer) {
//    if (depth == 0 || node.isTerminal()) {
//      return node.getHeuristicValue();
//    }
//
//    if (maximizingPlayer) {
//      int bestValue = Integer.MIN_VALUE;
//      for (Node child : node.getChildren()) {
//        int value = minimax(child, depth - 1, false);
//        bestValue = Math.max(bestValue, value);
//      }
//      return bestValue;
//    } else {
//      int bestValue = Integer.MAX_VALUE;
//      for (Node child : node.getChildren()) {
//        int value = minimax(child, depth - 1, true);
//        bestValue = Math.min(bestValue, value);
//      }
//      return bestValue;
//    }
//  }
//
//  public MiniMaxStrategy(Color color) {
//    this.color = color;
//  }
//
//
//  private Color getOtherColor(Color color) {
//    if (color.equals(Color.BLACK)) {
//      return Color.WHITE;
//    } else
//      return Color.BLACK;
//  }
//
//  @Override
//  public List<ICell> chooseMove(ROModel model, List<ICell> filteredMoves) {
//    return null;
//  }
//}
//
