package br.unicentro.acaddecomp;

import java.util.*;

public class Estados implements Comparable<Estados> {

	private int[] tabuleiroInicial; // tabuleiro inicial
	private int[] tabuleiroObjetivo; // tabuleiro de objetivo
	private int peso; // peso
	private Estados pai = null;

	// getters e setters
	public int[] getJogo() {
		return tabuleiroInicial;
	}

	public void setJogo(int[] jogo) {
		this.tabuleiroInicial = jogo;
	}

	public void setObjetivo(int[] objetivo) {
		this.tabuleiroObjetivo = objetivo;
	}

	public int[] getObjetivo() {
		return tabuleiroObjetivo;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public Estados getPai() {
		return pai;
	}

	public void setPai(Estados pai) {
		this.pai = pai;
	}

	// Gera os movimentos possíveis
	private Estados movimentarPecas(Estados pai, String movimento) {
		int[] jogo = pai.getJogo().clone(); // Clona o jogo do pai para o filho
		int[] objetivo = pai.tabuleiroObjetivo; // Carrega o objetivo para o
												// filho
		int posicaovazio = 0, auxiliar;
		while (jogo[posicaovazio] != 0) {
			posicaovazio = posicaovazio + 1; // Indica a posição vazia do sudoku
		}
		// Gera cada um dos movimentos, e infere nulo caso o nó acesse uma
		// posição inválida
		if (movimento == "cima") { // Para cima
			try {
				Estados novo = new Estados();
				auxiliar = jogo[posicaovazio - 3];
				jogo[posicaovazio - 3] = jogo[posicaovazio];
				jogo[posicaovazio] = auxiliar;
				novo.setJogo(jogo);
				novo.setObjetivo(objetivo);
				novo.setPai(pai);
				novo.setPeso(avaliaPeso(novo));
				return novo;
			} catch (ArrayIndexOutOfBoundsException ae) {
				return null;
			}
		} else if (movimento == "baixo") { // Para baixo
			try {
				Estados novo = new Estados();
				auxiliar = jogo[posicaovazio + 3];
				jogo[posicaovazio + 3] = jogo[posicaovazio];
				jogo[posicaovazio] = auxiliar;
				novo.setJogo(jogo);
				novo.setObjetivo(objetivo);
				novo.setPai(pai);
				novo.setPeso(avaliaPeso(novo));
				return novo;
			} catch (ArrayIndexOutOfBoundsException ae) {
				return null;
			}
		} else if (movimento == "esquerdo") { // Para esquerda
			try {
				Estados novo = new Estados();
				if (posicaovazio - 1 != 2 && posicaovazio - 1 != 5) {
					auxiliar = jogo[posicaovazio - 1];
					jogo[posicaovazio - 1] = jogo[posicaovazio];
					jogo[posicaovazio] = auxiliar;
					novo.setJogo(jogo);
					novo.setObjetivo(objetivo);
					novo.setPai(pai);
					novo.setPeso(avaliaPeso(novo));
					return novo;
				} else {
					return null;
				}
			} catch (ArrayIndexOutOfBoundsException ae) {
				return null;
			}
		} else { // Para direita
			try {
				Estados novo = new Estados();
				if (posicaovazio + 1 != 3 && posicaovazio + 1 != 6) {
					auxiliar = jogo[posicaovazio + 1];
					jogo[posicaovazio + 1] = jogo[posicaovazio];
					jogo[posicaovazio] = auxiliar;
					novo.setJogo(jogo);
					novo.setObjetivo(objetivo);
					novo.setPai(pai);
					novo.setPeso(avaliaPeso(novo));
					return novo;
				} else {
					return null;
				}
			} catch (ArrayIndexOutOfBoundsException ae) {
				return null;
			}
		}
	}

	// Compara as casas do sudoku e caso a casa do jogo não estiver na mesma
	// casa do
	// objetivo, aumenta mais um
	public static int avaliaPeso(Estados filho) {
		int dist;
		int peso = 0;
		for (int i = 0; i < filho.tabuleiroObjetivo.length; i++) {
			if (filho.tabuleiroInicial[i] != filho.tabuleiroObjetivo[i]) {
				peso++;
			}
		}
		dist = distancia(filho);
		System.out.println(dist);
		peso = peso + dist;
		return peso;
	}

	private static int distancia(Estados filho) { // Elabora uma distânca em
													// módulo das peças a serem
													// movidas
		int dist = 0;
		int aux; 
		for (int i = 0; i < 9; i++) {
			if (filho.getJogo()[i] != filho.getObjetivo()[i] && filho.getJogo()[i] == 0){
				for (int j = 0; j < 9; j++){
					if (filho.getJogo()[i] == filho.getObjetivo()[j]){
						dist = j;
					}
				}
			}
		}
		if (dist >= 0){
			return dist;
		} else {
			return dist*-1;
		}
	}

	private List<Estados> avaliaEstados(List<Estados> filhos) { // Remove os
																// filhos que
																// causam o erro
																// IndexOutOfBounds
		for (int i = 0; i < filhos.size(); i++) {
			if (filhos.get(i) == null) {
				filhos.remove(i);
			}
		}
		return filhos;
	}

	public List<Estados> gerarFilhos() { // Gera os movimentos nas 4 direções
											// possíveis do sudoku
		List<Estados> filhos = new LinkedList<>();
		filhos.add(movimentarPecas(this, "cima"));
		filhos = avaliaEstados(filhos);
		filhos.add(movimentarPecas(this, "baixo"));
		filhos = avaliaEstados(filhos);
		filhos.add(movimentarPecas(this, "esquerdo"));
		filhos = avaliaEstados(filhos);
		filhos.add(movimentarPecas(this, "direito"));
		filhos = avaliaEstados(filhos);
		System.out.println("Filhos: \n");
		for (int i = 0; i < filhos.size(); i++) {
			Estados objeto = filhos.get(i);
			System.out.println(objeto);
		}
		return filhos;
	}

	public static boolean Objetivo(Estados noatual) { // Objetivo de se zerar o
														// peso entre a lista
														// original e a de
														// objetivo
		if (noatual.getPeso() == 0) {
			return true;
		}
		return false;
	}

	public String toString() { // ToString
		return (" " + tabuleiroInicial[0] + " " + tabuleiroInicial[1] + " " + tabuleiroInicial[2] + "\n" + " "
				+ tabuleiroInicial[3] + " " + tabuleiroInicial[4] + " " + tabuleiroInicial[5] + "\n" + " "
				+ tabuleiroInicial[6] + " " + tabuleiroInicial[7] + " " + tabuleiroInicial[8] + "\n" + "Peso: " + peso
				+ "\n");
	}

	@Override
	public int compareTo(Estados o) { // Comparador necessário para a lista de
										// abertos (crescente)
		if (this.peso < o.getPeso()) {
			return -1;
		}
		if (this.peso > o.getPeso()) {
			return 1;
		}
		return 0;
	}

	public static Stack<Estados> geraCaminho(Estados filho) {
		Stack<Estados> caminho = new Stack<>();
		Estados no = filho;
		while (no.pai != null) {
			caminho.add(no);
			no = no.pai;
		}
		return caminho;
	}
}
