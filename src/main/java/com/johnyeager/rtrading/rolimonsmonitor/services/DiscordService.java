package com.johnyeager.rtrading.rolimonsmonitor.services;

import com.johnyeager.rtrading.rolimonsmonitor.model.Anuncio;
import com.johnyeager.rtrading.rolimonsmonitor.model.DiscordNotifier;
import com.johnyeager.rtrading.rolimonsmonitor.model.TradeItem;
import org.springframework.stereotype.Service;

@Service
public class DiscordService {

    public void enviarAnuncioDiscord(Anuncio anuncio) {
        // Construção do JSON do embed
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"embeds\":[{\"title\":\"🎉 Anúncio correspondente encontrado!\",\"color\":5814783,\"fields\":[");

        // Campo Username
        jsonBuilder.append("{\"name\":\"👤 Username\",\"value\":\"[").append(anuncio.getUsuario()).append("](https://www.roblox.com/users/trade)").append("\",\"inline\":true},");

        // Campo Value
        jsonBuilder.append("{\"name\":\"💰 Value\",\"value\":\"").append(anuncio.getValor()).append("\",\"inline\":true},");

        // Campo Tags
        jsonBuilder.append("{\"name\":\"🏷️ Tags\",\"value\":\"`").append(String.join(", ", anuncio.getTags())).append("`\",\"inline\":false},");

        // Campo Offering
        jsonBuilder.append("{\"name\":\"🛒 Offering\",\"value\":\"");
        for (TradeItem item : anuncio.getOffering()) {
            jsonBuilder.append("**").append(item.getNome()).append("**\\n📊 Value: `").append(item.getValor()).append("`\\n📉 RAP: `").append(item.getRap()).append("`\\n\\n");
        }
        jsonBuilder.append("\",\"inline\":false},");

        // Campo Requesting
        jsonBuilder.append("{\"name\":\"🔎 Requesting\",\"value\":\"");
        for (TradeItem item : anuncio.getRequesting()) {
            jsonBuilder.append("**").append(item.getNome()).append("**\\n📊 Value: `").append(item.getValor()).append("`\\n📉 RAP: `").append(item.getRap()).append("`\\n\\n");
        }
        jsonBuilder.append("\",\"inline\":false},");

        // Campo Trade Link
        jsonBuilder.append("{\"name\":\"🔗 Send Trade\",\"value\":\"[Click here](").append(anuncio.getTradeLink()).append(")\"}");

        jsonBuilder.append("],\"footer\":{\"text\":\"📢 Trade Notifier\"},\"timestamp\":\"").append(java.time.Instant.now().toString()).append("\"}]}");

        // Envia o JSON do embed
        DiscordNotifier.sendJsonMessage(jsonBuilder.toString());
    }
}