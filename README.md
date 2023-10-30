# Reversi

A Java-based implementation of a hexagonal board game using the MVC (Model-View-Controller)
architecture.
This project provides the foundation for creating and playing a strategy game on a hexagonal grid.
Players can make moves, capture opponents' pieces, and follow the rules of the game to achieve
victory.

## Features

- Hexagonal board with the [cube coordinates system](https://www.redblobgames.com/grids/hexagons/)
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



## IPlayer Interface
```java
// TODO
```

## HumanPlayer Class
```java
// TODO
```