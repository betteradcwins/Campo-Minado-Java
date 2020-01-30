package br.com.brenohof.campominado;

import br.com.brenohof.campominado.models.Board;
import br.com.brenohof.campominado.view.BoardConsole;

public class Application {
    public static void main(String[] args) {
        new BoardConsole(new Board(10, 10, 25));
    }
}
