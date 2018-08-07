package br.unicentro.acaddecomp;

import java.util.*;

public class Principal {
	public static void main(String[] args) {
		List<Estados> abertos = new ArrayList<>();
		List<Estados> fechados = new ArrayList<>();
		int[] jogo = {1, 6, 3, 8, 0, 4, 7, 2, 5};
		int[] objetivo = {1, 2, 3, 8, 0, 4, 7, 6, 5};
		Estados sudoku = new Estados();
		Heuristica heuristica = new Heuristica();
		sudoku.setJogo(jogo);
		sudoku.setObjetivo(objetivo);
		sudoku.setPeso(Estados.avaliaPeso(sudoku));
//		sudoku.gerarFilhos();
		System.out.println(heuristica.buscaHeuristica(abertos, fechados, sudoku));
	}
}
