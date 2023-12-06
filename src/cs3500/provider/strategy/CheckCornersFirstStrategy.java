//package cs3500.provider.strategy;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import model.CubicCoordinate;
//import model.ReadonlyReversiModel;
//import model.ReversiModel;
//import strategy.AvoidCornerNeighborStrategy;
//import strategy.ReversiStrategy;
//
///**
// * This strategy will first play in available corners. If no available corners,
// * then delegate to the AvoidCornerNeighborStrategy.
// */
//public class CheckCornersFirstStrategy implements ReversiStrategy {
//
//  @Override
//  public CubicCoordinate chooseCoord(ReadonlyReversiModel model, int player) {
//
//    List<CubicCoordinate> allCorners = getAllCorners(model);
//
//    List<CubicCoordinate> corners = new ArrayList<>();
//    // if there is a valid move that is a corner, play that
//    for (CubicCoordinate coord : model.getBoard().keySet()) {
//      if (model.isMoveValid(coord, player) && allCorners.contains(coord)) {
//        ReversiModel clone = model.clone();
//        clone.flipCell(coord, player);
//        corners.add(coord);
//      }
//    }
//    if (!corners.isEmpty()) {
//      return corners.get(0);
//    }
//
//    // if there are no valid corner moves, default to the AvoidCornerNeighborStrategy
//    return new AvoidCornerNeighborStrategy().chooseCoord(model, player);
//  }
//
//
//  private List<CubicCoordinate> getAllCorners(ReadonlyReversiModel model) {
//    List<CubicCoordinate> allCorners = new ArrayList<CubicCoordinate>();
//
//    int maxCoordValue = model.getSideLength() - 1;
//
//    for (CubicCoordinate coord : model.getBoard().keySet()) {
//
//      if ((coord.getX() == maxCoordValue
//              || coord.getY() == maxCoordValue
//              || coord.getZ() == maxCoordValue) && (coord.getX() == 0
//              || coord.getY() == 0
//              || coord.getZ() == 0)) {
//        allCorners.add(coord);
//      }
//
//    }
//    return allCorners;
//  }
//}
