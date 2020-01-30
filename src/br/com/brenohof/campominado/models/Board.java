package br.com.brenohof.campominado.models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int rows;
    private int columns;
    private int numberOfMines;

    private List<Square> squares = new ArrayList<>();

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
                squares.add(new Square(row, column));
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
        try {
            squares.get((row * columns) + column).open();
        } catch (Exception e) {
            squares.forEach(s -> {
                if (s.isMined())
                    s.setOpened(true);
            });
            throw e;
        }
    }

    public void tag(int row, int column) {
        squares.get((row * columns) + column).switchTag();
    }

    public boolean wasObjectiveAchieved() {
        return squares.stream().allMatch(Square::wasObjectiveAchieved);
    }

    public void restart() {
        squares.forEach(Square::restart);
        shuffleMines();
    }
}
