package com.poker.poker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CriaJson {
    private final JogadorBot bot;
    private final Mesa mesa;
    private final int maiorAposta;



    public CriaJson(JogadorBot bot,Mesa mesa,int maiorAposta){
        this.bot=bot;
        this.mesa=mesa;
        this.maiorAposta=maiorAposta;
    }

    public String criar()
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject root  = new JsonObject();
        // ---------------- JOGO ----------------
        root.addProperty("game", "poker");
        root.addProperty("etapa", mesa.getEtapa());

        // ---------------- CARTAS DA MESA ----------------
        root.add("mesa", cardsToJson(mesa.mao));

        // ---------------- BOT ----------------
        JsonObject botObj = new JsonObject();
        botObj.addProperty("nome", bot.getNome());
        botObj.add("cartas", cardsToJson(bot.getMao()));
        botObj.addProperty("fichas", bot.getFichas());
        botObj.addProperty("aposta_rodada", bot.getApostaRodada().get());
        root.add("bot", botObj);

        // ---------------- POT ----------------
        root.addProperty("pote", mesa.getPote());
        root.addProperty("maior_aposta", maiorAposta);

        // ---------------- AÇÕES POSSÍVEIS ----------------
        JsonArray acoes = new JsonArray();
        acoes.add("call");
        acoes.add("fold");
        root.add("acoes_possiveis", acoes);

        // ---------------- INSTRUÇÃO PARA IA ----------------
        root.addProperty("instrucao", "Você é um jogador TAG agressivo. Jogue sólido, porém com agressividade frequente.\n" +
                "\n" +
                "REGRAS DO PERFIL:\n" +
                "- Se a mão tiver força moderada ou forte (pares, overcards fortes, top pair, overpair, trinca, flush/straight draws), prefira jogar AGREDINDO.\n" +
                "- Aumente (raise) muito mais vezes do que apenas pagar. CALL é aceitável, mas RAISE deve ser considerado sempre que você enxergar valor ou pressão possível.\n" +
                "- Faça semi-blefes com bons draws (ex: flush draw, open-ended straight). Prefira aumentar nesses casos.\n" +
                "- Em dúvida entre CALL ou RAISE, prefira RAISE.\n" +
                "- Só dê fold quando a mão for realmente fraca e sem perspectiva.\n" +
                "- Considere a textura da mesa, o tamanho do pote e a força relativa da sua mão.\n" +
                "- **Regra importante sobre apostas já iguais:** se a sua aposta atual na rodada for **igual** à `maior_aposta`, isso significa que você já está igualado — **NÃO** retorne \"fold\". Nessa situação, retorne \"call\" (ou um número para raise) — nunca \"fold\" por causa dessa igualdade.\n"+
                "\n" +
                "você nao pode ficar com fichas negativas entao ,nao aposte valores que deixam a ficha menor que 0\n"+
                "TENDÊNCIA:\n" +
                "- Jogador agressivo: usa raise como principal ferramenta.\n" +
                "- Call é opção secundária.\n" +
                "- Fold é exceção.\n" +
                "\n" +
                "FORMATO DE RESPOSTA:\n" +
                "- \"call\"\n" +
                "- \"fold\"\n" +
                "- Número inteiro para raise (ex: 300)\n" +
                "\n" +
                " NUNCA RETORNE TEXTO EXPLICATIVO.");

        return gson.toJson(root);
    }

    private JsonArray cardsToJson(java.util.List<Card> cards) {
        JsonArray arr = new JsonArray();
        for (Card c : cards) {
            arr.add(c.getFace() + c.getSuit());
        }
        return arr;
    }

}
