package com.poker.poker;

import java.util.*;

public class PokerHandEvaluator {

    // Avalia o vencedor de uma lista de jogadores
    public static Jogador avaliarVencedor(List<Jogador> jogadores) {
        Jogador vencedor = null;
        List<Integer> melhorMao = null;

        for (Jogador j : jogadores) {
            List<Integer> mao = calcularValorMaoComKickers(j.getMaoFinal());
            System.out.println(j.getNome() + " valor da mão: " + mao);

            if (melhorMao == null || compararMaos(mao, melhorMao) > 0) {
                melhorMao = mao;
                vencedor = j;
            }
        }

        return vencedor;
    }

    // Calcula a mão de poker em formato [rank, principal, kicker1, kicker2, ...]
    private static List<Integer> calcularValorMaoComKickers(List<Card> cartas) {
        Map<Integer, Integer> freqValor = new HashMap<>();
        Map<String, Integer> freqNaipe = new HashMap<>();
        List<Integer> valores = new ArrayList<>();

        for (Card c : cartas) {
            int valor = valorDaFace(c.getFace());
            freqValor.put(valor, freqValor.getOrDefault(valor, 0) + 1);
            freqNaipe.put(c.getSuit(), freqNaipe.getOrDefault(c.getSuit(), 0) + 1);
            valores.add(valor);
        }

        valores.sort(Collections.reverseOrder());

        boolean flush = freqNaipe.values().stream().anyMatch(q -> q >= 5);
        int straightHigh = verificaStraight(valores);

        List<Integer> pares = new ArrayList<>();
        List<Integer> trincas = new ArrayList<>();
        int quadra = -1;

        for (int val : freqValor.keySet()) {
            int count = freqValor.get(val);
            if (count == 4) quadra = val;
            else if (count == 3) trincas.add(val);
            else if (count == 2) pares.add(val);
        }

        pares.sort(Collections.reverseOrder());
        trincas.sort(Collections.reverseOrder());

        List<Integer> resultado = new ArrayList<>();

        if (straightHigh != -1 && flush) { // Straight flush
            resultado.add(9);
            resultado.add(straightHigh);
        } else if (quadra != -1) { // Quadra
            resultado.add(8);
            resultado.add(quadra);
            for (int v : valores) if (v != quadra) { resultado.add(v); break; }
        } else if (!trincas.isEmpty() && !pares.isEmpty()) { // Full house
            resultado.add(7);
            resultado.add(trincas.get(0));
            resultado.add(pares.get(0));
        } else if (flush) { // Flush
            resultado.add(6);
            List<Integer> topFlush = new ArrayList<>();
            String naipeFlush = freqNaipe.entrySet().stream().filter(e -> e.getValue() >= 5).findFirst().get().getKey();
            for (Card c : cartas) if (c.getSuit().equals(naipeFlush)) topFlush.add(valorDaFace(c.getFace()));
            topFlush.sort(Collections.reverseOrder());
            for (int i = 0; i < 5; i++) resultado.add(topFlush.get(i));
        } else if (straightHigh != -1) { // Straight
            resultado.add(5);
            resultado.add(straightHigh);
        } else if (!trincas.isEmpty()) { // Trinca
            resultado.add(4);
            resultado.add(trincas.get(0));
            for (int v : valores) if (v != trincas.get(0)) resultado.add(v);
        } else if (pares.size() >= 2) { // Dois pares
            resultado.add(3);
            resultado.add(pares.get(0));
            resultado.add(pares.get(1));
            for (int v : valores) if (v != pares.get(0) && v != pares.get(1)) { resultado.add(v); break; }
        } else if (pares.size() == 1) { // Par
            resultado.add(2);
            resultado.add(pares.get(0));
            for (int v : valores) if (v != pares.get(0)) resultado.add(v);
        } else { // Carta alta
            resultado.add(1);
            for (int i = 0; i < Math.min(5, valores.size()); i++) resultado.add(valores.get(i));
        }

        return resultado;
    }

    // Retorna a carta mais alta de uma straight ou -1 se não tiver
    private static int verificaStraight(List<Integer> valores) {
        Set<Integer> uniqSet = new TreeSet<>(valores);
        List<Integer> uniq = new ArrayList<>(uniqSet);
        Collections.sort(uniq);

        int count = 1;
        int lastVal = uniq.get(0);
        int maxHigh = -1;

        for (int i = 1; i < uniq.size(); i++) {
            if (uniq.get(i) == lastVal + 1) {
                count++;
                if (count >= 5) maxHigh = uniq.get(i);
            } else {
                count = 1;
            }
            lastVal = uniq.get(i);
        }

        // Checa A-2-3-4-5
        if (uniqSet.contains(14) && uniqSet.contains(2) && uniqSet.contains(3) && uniqSet.contains(4) && uniqSet.contains(5)) {
            maxHigh = 5;
        }

        return maxHigh;
    }

    // Compara duas mãos já processadas em formato [rank, principal, kicker1, kicker2,...]
    private static int compararMaos(List<Integer> mao1, List<Integer> mao2) {
        for (int i = 0; i < Math.min(mao1.size(), mao2.size()); i++) {
            int cmp = mao1.get(i).compareTo(mao2.get(i));
            if (cmp != 0) return cmp;
        }
        return 0;
    }

    // Conversão de face para valor
    private static int valorDaFace(String face) {
        return switch (face) {
            case "2" -> 2;
            case "3" -> 3;
            case "4" -> 4;
            case "5" -> 5;
            case "6" -> 6;
            case "7" -> 7;
            case "8" -> 8;
            case "9" -> 9;
            case "10" -> 10;
            case "J" -> 11;
            case "Q" -> 12;
            case "K" -> 13;
            case "A" -> 14;
            default -> 0;
        };
    }
}
