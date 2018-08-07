package br.unicentro.acaddecomp;

import java.util.*;

public class Heuristica {

	Stack<Estados> caminho; //Pilha de caminho encontrado
	Estados no; //Necessário para a pilha
	int i; // Idem
	
	public boolean buscaHeuristica(List<Estados> abertos, List<Estados> fechados, Estados inicial) {
		Estados noAtual;
		// Lista dos filhos
		List<Estados> filhos;
		abertos.add(inicial);
		while (fechados.size() < 1) {
			// Pega o nó mais a esquerda da lista
			noAtual = abertos.get(0);
			abertos.remove(0);
			System.out.println("No Atual: \n" + noAtual);
			// Caso objetivo atingido, ele para e retorna true
			if (Estados.Objetivo(noAtual) == true) {
				int j = 1;
				caminho = Estados.geraCaminho(noAtual);
				System.out.println("Caminho: \n");
				while (caminho.size()>0){ //Gera o caminho encontrado
					i = caminho.size();
					no = caminho.get(i-1);
					System.out.println(j + "º nível\n" + no);
					j++;
					caminho.remove(i-1);
				}
				return true;
			} else {
				filhos = noAtual.gerarFilhos();
				for (int i = 0; i < filhos.size(); i++) {
					boolean hasAberto = false, hasFechado = false;
					Estados objeto;
					// Gera os filhos do nó atual
					objeto = filhos.get(i);
					// Adiciona todos os nós filhos
					abertos.add(objeto);
					// Verifica se os nós não estão duplicados na lista de
					// abertos
					// ou se já não pertencem a de fechados
					for (int j = 0; j < abertos.size(); j++) {
						hasAberto = avaliaFilhoAberto(abertos.get(j), objeto);
						for (int k = 0; k < fechados.size(); k++) {
							hasFechado = avaliaFilhoFechado(fechados.get(k), objeto);
							if (hasAberto == true && hasFechado == true) {
								abertos.remove(objeto);
							}
						}
					}
				}
				// Ordena a lista dos filhos que possuem menor peso
				Collections.sort(abertos); 
				// adiciona o nó atual a lista de fechados
				fechados.add(noAtual);
				System.out.println("Nós abertos: " + abertos.size());
				System.out.println("Nós fechados: " + fechados.size() + "\n");
			}
		}
		return false;
	}

	// Avalia se o filho objeto já não pertence a lista de abertos
	private boolean avaliaFilhoAberto(Estados aberto, Estados filho) {
		int iguais = 0;
		for (int i = 0; i < 9; i++) {
			if (aberto.getJogo()[i] == filho.getJogo()[i]) {
				iguais++;
			}
		}
		if (iguais == 9) {
			return true;
		} else {
			return false;
		}
	}

	// Avalia se o filho objeto já não pertence a lista de fechados
	private boolean avaliaFilhoFechado(Estados fechado, Estados filho) {
		int iguais = 0;
		for (int i = 0; i < 9; i++) {
			if (fechado.getJogo()[i] == filho.getJogo()[i]) {
				iguais++;
			}
		}
		if (iguais == 9) {
			return true;
		} else {
			return false;
		}
	}
}
