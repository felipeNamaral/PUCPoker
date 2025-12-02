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
        root.addProperty("instrucao",
                "Você é um bot TAG agressivo. Prefira raise; call é secundário;voce pode blefar aumentado o valor  " +
                        "fold só com mão fraca e sem draw. Com força média/forte ou bons draws: raise.,se sua aposta for igual a maior_aposta nunca de fold " +
                        "Se sua aposta atual já for igual à maior_aposta, nunca retorne fold—use call ou raise. " +
                        "Nunca aposte mais do que suas fichas. Responda apenas: call, fold ou número para raise ex: '350'.NUNCA retorne texto explicativo");

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
