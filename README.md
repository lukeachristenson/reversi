# Reversi

A Java-based implementation of a hexagonal board game using the MVC (Model-View-Controller)
architecture.
This project provides the foundation for creating and playing a strategy game on a hexagonal grid.
Players can make moves, capture opponents' pieces, and follow the rules of the game to achieve
victory.`

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

## Usage of the Text View for Reversi


```java
//TODO
```


## Color Enum

```java
//TODO
```

## IPlayer Interface
```java
// TODO
```

## HumanPlayer Class
```java
// TODO
```