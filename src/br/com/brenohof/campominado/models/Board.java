package br.com.brenohof.campominado.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Data
public class Board implements BiConsumer<Square, SquareEvent> {
    private final int rows;
    private final int columns;
    private final int numberOfMines;

    private List<Square> squares = new ArrayList<>();
    private Set<Consumer<Boolean>> observers = new HashSet<>();

    public void addObserver(Consumer<Boolean> observer) {
        observers.add(observer);
    }

    public void notifyObservers(Boolean result) {
        observers.forEach(o -> o.accept(result));
    }

    public Board(int rows, int columns, int numberOfMines) {
        this.rows = rows;
        this.columns = columns;
        this.numberOfMines = numberOfMines;

        generateSquares();
        populateNeighborhood();
        shuffleMines();
    }

    private void generateSquares() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Square square = new Square(row, column);
                square.addObserver(this::accept);
                squares.add(square);
            }
        }
    }

    private void populateNeighborhood() {
        for (Square s1 : squares) {
            for (Square s2 : squares)
                s1.addNeighbor(s2);
        }
    }

    private void shuffleMines() {
        int squaresMined = 0;
        do {
            int rand = (int) (Math.random() * squares.size());
            if (!squares.get(rand).isMined()) {
                squares.get(rand).setMined(true);
                squaresMined++;
            }
        } while (squaresMined < numberOfMines);
    }

    public void open(int row, int column) {
        squares.get((row * columns) + column).open();
    }

    public void tag(int row, int column) {
        squares.get((row * columns) + column).switchTag();
    }

    private void uncoverMines() {
        squares.stream().filter(Square::isMined).filter(s -> !s.isTagged()).forEach(s -> s.setOpened(true));
    }

    public boolean wasObjectiveAchieved() {
        return squares.stream().allMatch(Square::wasObjectiveAchieved);
    }

    public void restart() {
        squares.forEach(Square::restart);
        shuffleMines();
    }

    public void forEachSquare(Consumer<Square> function) {
        squares.forEach(function);
    }

    @Override
    public void accept(Square square, SquareEvent event) {
        if (event == SquareEvent.EXPLODE) {
            uncoverMines();
            notifyObservers(false);
        } else if (wasObjectiveAchieved()) {
            notifyObservers(true);
        }
    }
}
