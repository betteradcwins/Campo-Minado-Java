package br.com.brenohof.campominado.view;

import br.com.brenohof.campominado.exceptions.ExitRuntimeException;
import br.com.brenohof.campominado.exceptions.ExplosionRuntimeException;
import br.com.brenohof.campominado.models.Board;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class BoardConsole {
    private Board board;
    private static final Scanner scanner = new Scanner(System.in);

    public BoardConsole(Board board) {
        this.board = board;

        run();
    }

    private void run() {
        try {
            while (true) {
                gameInput();
                String answer = catchAnswer("Continue? (S/n) ");

                if ("n".equalsIgnoreCase(answer)) {
                    throw new ExitRuntimeException();
                } else {
                    board.restart();
                }
            }
        } catch (ExitRuntimeException e) {
            System.out.println("Good bye!!!");
        } finally {
            scanner.close();
        }
    }

    private String catchAnswer(String text) {
        System.out.println(text);
        String value = scanner.nextLine();

        if ("exit".equalsIgnoreCase(value)) {
            throw new ExitRuntimeException();
        }

        return value;
    }

    private void gameInput() {
        try {
            while (!board.wasObjectiveAchieved()) {
                System.out.println(board);

                String value = catchAnswer("Provide (x, y): ");

                Iterator<Integer> xy = Arrays.stream(value.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();

                value = "";
                while (!"1".equals(value) && !"2".equals(value)) {
                    value = catchAnswer("1 - Open or 2 - (Un)Tag");
                    if ("1".equals(value)) {
                        board.open(xy.next(), xy.next());
                    } else if ("2".equals(value)) {
                        board.tag(xy.next(), xy.next());
                    }
                }
            }
            System.out.println(board);
            System.out.println("Game Over! You win.");
        } catch (ExplosionRuntimeException e) {
            System.out.println(board);
            System.out.println("Game Over! You lose.");
        }
    }
}
