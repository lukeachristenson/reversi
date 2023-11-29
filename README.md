# Hexagonal Reversi Java Implementation

## Overview

This Java codebase is an implementation of the classic Reversi game played on a hexagonal grid. It
follows the MVC (Model-View-Controller) architecture and offers a platform for playing Reversi in a
hexagonal variant. This README serves as a guide for understanding the purpose, structure, and
components of this codebase.

## Quick Start

To get started with this codebase and play a game of Hexagonal Reversi, follow these steps:

```java
import model.HexagonReversi;
import model.IReversiModel;
import mockplayerview.ReversiTextView;
import mockplayerview.TextView;

public class Main {
  public static void main(String[] args) {
    IReversiModel model = aHexagonReversi(6); // Specify the number of sides for the game board.
    TextView textView = aReversiTextView(model); // Provide the model for rendering.
    System.out.println(textView.toString()); // Display the game state in the console or your application.
  }
}
```

## Features

- Hexagonal board with
  the [cube coordinates system](https://www.redblobgames.com/grids/hexagons/#coordinates-cube)
- MVC architecture for a structured and modular design
- Player interactions, move validations, and piece capturing
- Customizable game rules and win conditions

## Class Invariant(s)

1. One of the recognized class invariants for the game was the fact that the sideLength of the board
   cannot be less than 3. This is due to the fact that a standard game of hexagon reversi cannot
   have less than 3 sides since there
   would be no valid move that can be played by either colored token.

2. Another invariant that is enforced in the code pertains to the
   [cube coordinates system](https://www.redblobgames.com/grids/hexagons/#coordinates-cube). An
   IllegalArgumentException is thrown if the sum of the coordinates that are passed into the
   constructor of the HexagonCell is not zero(**i.e. q+r+s != 0**).

3. The player passed into the `placePiece` method cannot be null since the model cannot place a null
   or empty
   player into a cell.

## Key Components

### Game Model

The `HexagonReversi` class is the core game model that implements the `IReversiModel` interface,
responsible for managing game mechanics, including player moves, turn passing, scoring, and game
state. It is built on top of the HexagonalBoard class,
representing the game board.

### Text-based View for Reversi

The `TextView` interface serves as a marker interface for text-based views in the Reversi game. The
`ReversiTextView` class implements this interface and provides a text-based representation of the
game
state. It uses the IReversiModel instance to render the game state in text format.

### Game Board Representation

IBoard Interface: Represents the game board and defines methods for cell ownership, move validation,
rendering, score retrieval, and listing valid moves.

### HexagonBoard Class:

An implementation of the IBoard interface, representing the hexagonal game board. It is initialized
with a specified side length. Key methods include newCellOwner, validMove, getCellOccupant,
toString, and validMovesLeft.

### Coordinate System

![Cube Coordinate System](images/cubeCoordinatesSystem.jpg)

The [cube coordinates system](https://www.redblobgames.com/grids/hexagons/#coordinates-cube)
from [RedBlobGames](https://www.redblobgames.com/) was used to represent the location of each cell
in the Hexagonal grid for the game. One advantage of using this system
was that it was easy to check if a given cell was within the bounds of the grid and the fact that
the sum of the coordinates of the cell is always 0 made it easy to check if the coordinates are
valid
and within a hexagonal shape or not.

## Key Subcomponents

### Representing Players in the Game

#### IPlayer Interface:

`IPlayer` is a marker interface for player representation. It includes a method to retrieve the
player's tokenColor.
An IPlayer can be a human player or an AI(**not implemented yet**).
A concrete class for Human players, called HumanPlayer is made which represents Human Players. The
only field that the `HumanPlayer` class has is a `Color`.

#### HumanPlayer Class:

A concrete implementation of `IPlayer`, representing human players. It allows specifying the
player's
tokenColor upon instantiation, either `Color.BLACK` or `Color.WHITE`.

#### Color Enum:

The Color enum represents the two players, Black (B) and White (W). It simplifies player management
and provides a next() method for alternating turns.

### Representing Cells in the Board

#### ICell Interface:

An `ICell` represents the location of a cell in the game board grid. This interface was made with
the
intention of code extensibility so that the code can be extended to a new format of game board with
a game cell. The ICell interface has only one method, `getCoordinates()` which returns a list of
coordinates of the `ICell`.

#### HexagonCell Class:

The `HexagonCell` class implements the `ICell` interface and it represents the location of a cell in
a `HexagonBoard`. The class is configured to work with a game of `Hexagon Reversi`. The cell's
coordinates are based on the [cube coordinates system](#coordinate-system) mentioned above.
It has an invariant that limits the coordinates such that their sum is 0.

## Source Organization

```
├── src
│   └── cs3500
│       └── reversi
│           ├── META-INF
│           │   └── MANIFEST.MF
│           ├── Reversi.java
│           ├── TestingReversiRunner.java
│           ├── controller
│           │   ├── Controller.java
│           │   ├── ModelFeatures.java
│           │   └── PlayerFeatures.java
│           ├── model
│           │   ├── HexagonBoard.java
│           │   ├── HexagonCell.java
│           │   ├── HexagonReversi.java
│           │   ├── IBoard.java
│           │   ├── ICell.java
│           │   ├── IReversiModel.java
│           │   ├── ROHexagonModel.java
│           │   ├── ROModel.java
│           │   └── TokenColor.java
│           ├── player
│           │   ├── AIPlayer.java
│           │   ├── HumanPlayer.java
│           │   └── IPlayer.java
│           ├── strategy
│           │   ├── AvoidEdgesStrat.java
│           │   ├── ChooseCornersStrat.java
│           │   ├── GreedyStrat.java
│           │   ├── MiniMaxStrategy.java
│           │   ├── OurAlgorithmStrat.java
│           │   ├── RandomStrat.java
│           │   ├── SandwichStrat.java
│           │   ├── Strategy.java
│           │   └── UpperLeftStrat.java
│           └── view
│               ├── BasicReversiView.java
│               ├── CartesianPosn.java
│               ├── CoordUtilities.java
│               ├── ReversiPanel.java
│               ├── ReversiTextView.java
│               ├── ReversiView.java
│               └── TextView.java
├── strategy-transcript.txt
└── test
    ├── controller
    │   └── ExampleControllerTests.java
    ├── mockmodel
    │   ├── ExampleMockModelTests.java
    │   └── MockModel.java
    ├── mockplayerview
    │   ├── ExampleMockPlayerViewTests.java
    │   ├── MockPlayer.java
    │   └── MockView.java
    ├── mockstrategy
    │   └── ExampleMockStrategyTests.java
    ├── model
    │   ├── ExampleATests.java
    │   ├── ExampleAuxillaryTests.java
    │   ├── ExampleBoardTests.java
    │   ├── ExampleModelTests.java
    │   └── MockModel.java
    ├── player
    │   ├── ExampleMockPlayerTests.java
    │   └── MockPlayer.java
    └── strategy
        ├── AvoidEdgesTests.java
        ├── ChooseCornersTests.java
        ├── GreedyStrategyTests.java
        ├── MinimaxTests.java
        └── UpperLeftStrategyTests.java
```

## Changes for Part 2

- Removed a method called `placePiece(Color tokenColor, ICell cell)` from the model that
  gave excessive control on changing the game state irrespective of the current player,
  going against the rules of the game.
- Added a method called `placeCurrentPiece(ICell cell)` that places **only** the current player's
  piece in the cell if the move is valid. This limits the model's visibility and external control
  over the game state.
- Added a method called `getCellsFlipped(ICell cell)` that returns the number of cells that will
  be flipped if the current player places a piece in the given cell.
- Added a method called `getScore()` that returns the score of the current player.
- Made a read-only interface for the model so that the mockplayerview cannot make changes to the game state.

## Graphical View

The mockplayerview for this game is made in an interface called `ReversiView`. The interface is
further implemented in the concrete class `BasicReversiView` which extends the `JFrame` class to use
[Swing](https://docs.oracle.com/javase/tutorial/uiswing/components/index.html) components and
features. The mockplayerview works with
a [JPanel](https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/javax/swing/JPanel.html) class
called `ReversiPanel` which is responsible for rendering the game board on the mockplayerview(frame).

### ReversiPanel Class

The `ReversiPanel` class extends the `JPanel` class and renders the game board. Furthermore, it
handles mouse and keyboard inputs using `MouseEventListener` and `KeyAdapeter`
interfaces respectively.

### CartesianPosn

The `CartesianPosn` class represents a cartesian coordinate on the mockplayerview. The class is used to get
the cartesian coordinates of the center of the cells on the panel using its cube coordinates.
It has two fields, `x` and `y` which represent the x and y coordinates of the cartesian coordinate
in doubles.

### Mouse Inputs

The `ReversiPanel` class handles mouse inputs using the `MouseEventListener` interface. A cell on
the board is highlighted in <span style="tokenColor:cyan">**cyan**</span> when the mouse clicks it and
the same cell is deselected when one of four things happen:

- The mouse clicks on the same cell again.
- The mouse clicks on a different cell.
- The mouse clicks outside the board
- A key is pressed on the keyboard(*Note: nothing changes on the board in this case
  since a modelfeatures has not been made for the final version*).

The highlighted cell's coordinates in the
[cube coordinate system](https://www.redblobgames.com/grids/hexagons/#coordinates-cube)
are printed in the console when a cell is highlighted.

### Keyboard Inputs

The `ReversiPanel` class handles keyboard inputs using the `KeyAdapter` interface. The following
keys are used to control the game:

- `Space`: Places the current player's piece in the highlighted cell if the move is valid.
- `P` or `p`: Passes the turn to the next player.

## Changes for Part 2

- Removed a method called `placePiece(Color tokenColor, ICell cell)` from the model that
  gave excessive control on changing the game state irrespective of the current player,
  going against the rules of the game.
- Added a method called `placeCurrentPiece(ICell cell)` that places **only** the current player's
  piece in the cell if the move is valid. This limits the model's visibility and external control
  over the game state.
- Added a method called `getCellsFlipped(ICell cell)` that returns the number of cells that will
  be flipped if the current player places a piece in the given cell.
- Added a method called `getScore()` that returns the score of the current player.
- Made a read-only interface for the model so that the mockplayerview cannot make changes to the game state.

## Graphical View

The mockplayerview for this game is made in an interface called `ReversiView`. The interface is
further implemented in the concrete class `BasicReversiView` which extends the `JFrame` class to use
[Swing](https://docs.oracle.com/javase/tutorial/uiswing/components/index.html) components and
features. The mockplayerview works with
a [JPanel](https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/javax/swing/JPanel.html) class
called `ReversiPanel` which is responsible for rendering the game board on the mockplayerview(frame).

### ReversiPanel Class

The `ReversiPanel` class extends the `JPanel` class and renders the game board. Furthermore, it
handles mouse and keyboard inputs using `MouseEventListener` and `KeyAdapeter`
interfaces respectively.

### CartesianPosn

The `CartesianPosn` class represents a cartesian coordinate on the mockplayerview. The class is used to get
the cartesian coordinates of the center of the cells on the panel using its cube coordinates.
It has two fields, `x` and `y` which represent the x and y coordinates of the cartesian coordinate
in doubles.

### Mouse Inputs

The `ReversiPanel` class handles mouse inputs using the `MouseEventListener` interface. A cell on
the board is highlighted in <span style="tokenColor:cyan">**cyan**</span> when the mouse clicks it and
the same cell is deselected when one of four things happen:

- The mouse clicks on the same cell again.
- The mouse clicks on a different cell.
- The mouse clicks outside the board
- A key is pressed on the keyboard(*Note: nothing changes on the board in this case
  since a modelfeatures has not been made for the final version*).

The highlighted cell's coordinates in the
[cube coordinate system](https://www.redblobgames.com/grids/hexagons/#coordinates-cube)
are printed in the console when a cell is highlighted.

### Keyboard Inputs

The `ReversiPanel` class handles keyboard inputs using the `KeyAdapter` interface. The following
keys are used to control the game:

- `Space`: Places the current player's piece in the highlighted cell if the move is valid.
- `P` or `p`: Passes the turn to the next player.

## Strategy

For this assignment, we implemented 6 different "strategies" which were essentially algorithms to
recommend moves within a game of a reversi, given a board state. The strategies were implemented
using the Strategy design pattern. The method shared by all strategies in the strategy interface
is called chooseMove. chooseMove takes in a read only model to ensure that strategies cannot change
the game state. chooseMove also takes in a list of ICell's which are "filtered moves" the reason
for this is so it is possible to give one strategy a list of moves to apply its logic to and then
returning a refined list. This approach allows the usage of multiple strategies "sandwiched"
together. The strategies are as follows:

### UpperLeftStrat

This strategy returns the uppermost and leftmost position that is valid or among the
list of moves passed into it.

### GreedyStrat

This strategy implements the greedy algorithm and aims to maximize the strategy player's gain in the
given move, i.e., it aims to conquer the most number of cells in the move. If two moves are deemed
to be equal, then this strategy returns the uppermost leftmost move amongst the equal moves using
the UpperLeftStrat.

## Extra Credit Strategies

### Sandwich

We implemented the class sandwich which is a strategy that aims to sandwich other strategies
together.

### AvoidEdges

This strategy aims to avoid the edges(cells adjacent to a corner cell) in the board.

### ChooseCorners

This Strategy aims to choose corners which give a positional advantage in the game because whoever
claims a corner will keep that corner.

### OurAlgorithm

This strategy uses a combination of other strategies. First, it considers every possible move, then
it considers the most logical opponent responses to that move as determined by the opponent using a
combination of strategies. The strategy searches for whichever move it can make, that will lead to
the opponents next best move being the lowest. It also ensures that if it can play a winning move,
that it will do so.

### MiniMaxStrategy

This strategy is inspired by the [minimax algorithm](https://en.wikipedia.org/wiki/Minimax) to
optimize the player's score gain against the opponent. Additionally, it avoids moves that will lead
the player to have a chance at winnning in the next move and aims to increase its score gain.
This strategy analyzes all the possible move combinations starting from the strategy player's moves
to the subsequent opponent's moves, assigning a weight to each possible move the strategy player
while doing so.

# Checklist

- [ ] Make a command line configurator for the game.
- [ ] Update the main method.
- [ ] Clean up the code.
- [ ] Update the README file.


# Changes for Part 3
There were some changes that were made to the project from part 2 in order to accomodate the
implementation of a controller and to accommodate communications between the model, view, and
controller. These changes include:
- The model contains a `startGame()` method that initializes the game and starts the game. This was
  done according to the requirements of the assignment with the intention of stopping the controller
  from performing any actions before the game has started.
- The model now contains a list of listeners(Controllers), one for each player in the game. This change
  was made so that the listener pattern could be implemented and so that the model could communicate with
  the multiple controllers in the game the color of the current player. To enable adding listeners to the model,
  the model now contains a method called `addListener(Controller controller)` which adds a controller to the
  list of controllers.
- All instances of a `Player` which are equivalent to the current `IPlayer` interface were removed from
  the model to completely get rid of the notion that a model needs to store the player information. Instead, the model
  now stores the color of the current player and keeps changing it after every move or pass. This was done since
  the controller is now responsible for storing the player information and the model only needs to know the color
  that plays next.
- Bugs related to the logic of legal moves in edge cases in the model were fixed.

# New Classes with Part 3
## Controller
Controller is a class that facilitates communication between the model and the view. Through the
features interfaces that it uses, when certain events occur in the view, the controller can call
features that update the models game state and then this change is echoed from the model to the view
through another features interface. 

## IModelFeature interface and ModelFeatures Class
The IModelFeatures interface is a single-interface that is implemented by the concrete class ModelFeatures.
This interface contains all the methods that the model can call through the controller to notify the player
and views of the current color of the game.

## IPlayerFeature interface and PlayerFeatures interface
The IPlayerFeature interface is a single-interface implemented by the concrete class PlayerFeatures.
The interface contains all the methods that the player can call through the controller to notify the model
of the move that the player wants to play. The class implementation contains all the methods that 
the player and view can call through the controller. The player
calls these methods to play its move when the player is an AIPlayer(using the strategy)
and the view calls the method through the controller when the player is a HumanPlayer(using mouse and keyboard
inputs).

## Commmand Line Configurator Commands

- `--size` or `-s`: Specifies the size of the board. The default value is 6.
- `--player1` or `-p1`: Specifies the strategy for player 1(Black). The default value is Human.
- `--player2` or `-p2`: Specifies the strategy for player 2(White). The default value is Human.
- The strategies(arguments for `--player1` and `--player2`) are as follows:
    - `h`: Human Player
    - `g`: Greedy Strategy
    - `u`: Upper Left Strategy
    - `a`: Avoid Edges Strategy
    - `cc`: Choose Corners Strategy
    - `mm`: MiniMax Strategy
    - `oa`: Our Algorithm Strategy
    - `r` : Random Strategy
    - `san1`: Sandwich Strategy comprising Greedy, Avoid Edges, and Choose Corners Strategies
    - `san2`: Sandwich Strategy comprising Minimax and Greedy strategies.
    - `san3`: Sandwich Strategy comprising Minimax, Greedy and Avoid Edges strategies.
    - `san4`: Sandwich Strategy comprising Minimax, Greedy, Avoid Edges and Choose Corners
      strategies.