# Reversi
A Java-based implementation of a hexagonal board game using the MVC (Model-View-Controller)
architecture.
This project provides the foundation for creating and playing a strategy game on a hexagonal grid.
Players can make moves, capture opponents' pieces, and follow the rules of the game to achieve
victory.

## Contents
1. [Features](#Features)
2. [Usage of the Reversi Model](#Usage-of-the-Reversi-Model)
3. [Game Board Representation](#Game-Board-Representation)

## Features

- Hexagonal board with the [cube coordinates system](https://www.redblobgames.com/grids/hexagons/#coordinates-cube)
- MVC architecture for a structured and modular design
- Player interactions, move validations, and piece capturing
- Customizable game rules and win conditions

## Usage of the Reversi Model

To use the Reversi model in your Java application, follow these steps:

1. **Import** the necessary classes and interfaces:

```java
import model.HexagonReversi;
import model.IReversiModel;
import view.ReversiTextView;
import view.TextView;
```

2. **Create** an instance of the Reversi model, specifying the dimensions of the board.
   For example, to create a Hexagon Reversi game with a board of dimensions 6:
```java
IReversiModel model = new HexagonReversi(6);
```

3. **Interaction Methods**
   1. Place a piece on a specific `ICell` with a given `Color`:
   ```java
    model.placePiece(ICell targetCell, Color color);
   ```
   2. Pass the current player's turn:
   ```java
    model.passTurn();
   ```
4. **Observation Methods**:
   1. Get a player's score using a `Color` Instance:
   ```java
    int score = model.getScore(Color color);
   ```
   2. Get a player's score using an `IPlayer` Instance:
   ```java
    int score = model.getScore(IPlayer player);
   ```
   3. Get the current player's `Color`:
   ```java
    Color currentPlayerColor = model.getCurrentColor();
   ```
   4. Get the current player:
   ```java
    IPlayer currentPlayer = model.getCurrentPlayer();
   ```
   5. Determine if the game is over:
   ```java
    boolean isGameOver = model.isGameOver();
   ```
   6. Get the number of consecutive passes made in the game:
   ```java
    int passCount = model.getPassCount();
   ```

## Usage of the Text-based View for Reversi
### TextView Interface

The `TextView` interface is a marker interface designed for text-based views in the Reversi game. 
It defines a single method, `toString()`, which returns a formatted string representing the game's 
current state. Developers can implement this interface to create custom text-based views that 
effectively communicate the game's progress through text interfaces.

### ReversiTextView Class
The `ReversiTextView` class implements the `TextView` interface, providing a text-based view of the 
Reversi game state. It takes an `IReversiModel` instance and utilizes its `toString()` method to 
present the current game state in a textual format. This class enables the visualization of the 
Reversi game's progress through text, making it user-friendly and suitable for text-based 
interfaces.

To use the view for **Reversi**, follow these steps:
1. Import the necessary classes and interfaces in your Java application:
```java
import model.HexagonReversi;
import model.IReversiModel;
import view.ReversiTextView;
import view.TextView;
```
2. Create an instance of the `ReversiTextView` class, which implements the `TextView` interface. 
   This will allow you to visualize the game's state in a text-based format:
```java
IReversiModel model = new HexagonReversi(6); // Create a Reversi model (replace with your chosen board size)
TextView textView = new ReversiTextView(model); // Create a text view for the model
```
3. Use the `toString()` method provided by the `TextView` interface to generate a rendered string 
   that displays the current state of the Reversi game:
```java
String gameState = textView.toString(); // Retrieve the rendered game state as a string
System.out.println(gameState); // Display the game state in your console or application
```

## Color Enum
The `Color` enum is a key component of the Reversi game, representing the two players, Black and 
White. This enum simplifies the management of player colors and transitions between them during 
the game. It consists of two constants, `BLACK` and `WHITE`, corresponding to the players. 
Additionally, the `Color` enum provides a `next()` method for easily determining the next player 
in the sequence, facilitating turn alternation. To enhance convenience, it overrides the 
`toString()` method to offer a representation of player colors for the board, 
returning "B" for Black and "W" for White. 

To use the `Color` enum in your Java application for the Reversi game, follow these steps:

1. Import the necessary classes and enums in your Java application:
```java
import model.Color;
```

2. Create an instance of the `Color` enum to represent a player's color in the Reversi game:
```java
Color playerColor = Color.BLACK; // Replace with either Color.BLACK or Color.WHITE
```

3. Call the `toString()` method provided by the Color enum to get a representation of the 
player's color, as seen on the `Board`:
```java
String colorString = playerColor.toString(); // Retrieve the player's color as a string ("B" for Black or "W" for White)
System.out.println("Player's color: " + colorString); // Display the player's color in your console or application
```

4. Call the `next()` method to alternate between the available player colors:
```java
Color nextPlayer = playerColor.next(); // Use next() to switch to the next player
System.out.println("Next player's color: " + nextPlayer.toString());
```


## Representing Players in the Game

### IPlayer Interface
The `IPlayer` interface is a marker interface that includes a single method, `getColor()`, 
used to retrieve the color associated with a player's token. This marker interface serves as a 
foundational structure for representing both Human and AI players within the Reversi game.

### HumanPlayer Class
The `HumanPlayer` class is a concrete implementation of the `IPlayer` interface, 
designed to represent human players in the Reversi game. It takes the player's color as a parameter
upon instantiation, allowing you to specify whether the player is assigned the color black (B) or 
white (W). The class implements the `getColor()` method, which returns the corresponding `Color` 
enum based on the player's assigned color. This class serves as a means to create and manage human
players within the Reversi game, enabling them to participate in the gameplay with their designated 
color.

To use the `HumanPlayer` class in your Java application for the Reversi game, follow these steps:

1. Import the necessary classes and interfaces in your Java application:

```java
import model.HumanPlayer;
import model.IPlayer;
import model.Color;
```

2. Create an instance of the HumanPlayer class to represent a human player in the Reversi game. 
3. Specify the player's color (either `Color.BLACK` for Black or `Color.WHITE` for White) upon 
instantiation:
```java
IPlayer player = new HumanPlayer(Color.BLACK); // Replace with the desired player color
```

4. Use the `getColor()` method provided by the HumanPlayer class to obtain the player's color 
in the form of a Color enum. This method converts the assigned string ("B" or "W") to the 
corresponding Color value:
```java
Color playerColor = player.getColor(); // Retrieve the player's color as a Color enum (Color.BLACK or Color.WHITE)
System.out.println("Player's color: " + playerColor.toString()); // Display the player's color in your console or application
```

## Game Board Representation
### IBoard Interface
The `IBoard` interface is used to represent a board for a game of **Reversi**. 
It defines essential methods for managing the game board in Reversi. 
These methods include adding and querying cell ownership, checking move validity, rendering the 
board state, retrieving player scores, and listing valid moves. It serves as a foundational 
structure for custom board management classes, playing a central role in the Reversi game.

### HexagonBoard Class
To use the `HexagonBoard` class in your Java application for the HexReversi game, follow these steps:

1. Import the necessary classes and interfaces in your Java application:
```java
import model.HexagonBoard;
import model.IBoard;
import model.ICell;
import model.Color;
import java.util.Optional;
import java.util.List;
```

2. Create an instance of the HexagonBoard class to represent the game board. Specify the side 
length of the hexagonal grid upon instantiation:
```java
IBoard board = new HexagonBoard(6); // Replace with your chosen side length
```

3. **Methods used for board management**:
   1. Add an `ICell` to the board and specify its owner, which will be an `Optional<Color>`.
   ```java
   ICell cell = new HexagonCell(0, 0, 0); // Replace with the desired cell coordinates
   Optional<Color> playerColor = Optional.of(Color.BLACK); // Replace with the player's color
   board.newCellOwner(cell, playerColor);
   ```
   
   2. The `validMove` method is used to check the validity of a move on the game board. 
   It considers the selected `ICell`, the player's `Color`, and a `boolean` flag to flip tokens.
   If the move is valid, it returns true; otherwise, it returns false. If the flip parameter is true
   , it also updates the board accordingly.
   ```java
   // Example Usage:
   ICell moveCell = new HexagonCell(1, -1, 0); // Replace with the cell where you want to make a move
   Color playerColor = Color.WHITE; // Replace with the player's color
   boolean isMoveValid = board.validMove(moveCell, playerColor, true); // Check and apply the move if valid
   ```

4. **Observation Methods**
   1. The `getCellOccupant` method allows you to retrieve the current owner of a specified cell 
   on the game board. It returns an optional color to indicate the cell's ownership or empty status.
   ```java
   // Example Usage:
   ICell cellToCheck = new HexagonCell(1, -1, 0); // Replace with the cell you want to check
   Optional<Color> occupantColor = board.getCellOccupant(cellToCheck);
   ```
   2. The `toString` method renders the current state of the game board as a formatted string, 
   providing a visual representation of the board's layout and cell ownership.
   ```java
   // Example Usage:
   String boardState = board.toString(); // Get the current state of the game board as a string
   System.out.println(boardState); // Display the board in your console or application
   ```
   3.The `validMovesLeft` method lists the valid moves available to a given player on the game board. 
   It returns a list of cells where the player can make valid moves.
   ```java
   // Example Usage:
   Color playerColor = Color.WHITE; // Replace with the player's color
   List<ICell> validMoves = board.validMovesLeft(playerColor); // Get the list of valid moves for the player
   ```

[![License: WTFPL](https://img.shields.io/badge/License-WTFPL-brightgreen.svg)](http://www.wtfpl.net/about/)