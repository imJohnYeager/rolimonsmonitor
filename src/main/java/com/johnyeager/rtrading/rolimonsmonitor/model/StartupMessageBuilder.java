package com.johnyeager.rtrading.rolimonsmonitor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StartupMessageBuilder {

    // Map para nomes legíveis das tags
    private static final Map<String, String> TAG_NAMES = Map.of(
            "any", "Any",
            "demand", "Demand",
            "rares", "Rares",
            "wishlist", "Wishlist",
            "robux", "Robux",
            "upgrade", "Upgrade",
            "downgrade", "Downgrade",
            "adds", "Adds",
            "projecteds", "Projecteds"
    );

    public static String build(FiltrosAnuncio filtros) {
        StringBuilder msg = new StringBuilder();
        msg.append("🚀 RoliMonitor Iniciado!\\n");
        msg.append("💰 Range de Value: ").append(filtros.getMinValue())
                .append(" - ").append(filtros.getMaxValue()).append("\\n");

        // Pega apenas as tags aceitas (true)
        List<String> acceptedTags = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : filtros.getTags().entrySet()) {
            if (Boolean.TRUE.equals(entry.getValue())) {
                String legibleName = TAG_NAMES.getOrDefault(entry.getKey(), entry.getKey());
                acceptedTags.add(legibleName);
            }
        }

        if (!acceptedTags.isEmpty()) {
            msg.append("🏷️ Tags aceitas:\\n");
            for (String t : acceptedTags) {
                msg.append("- ").append(t).append("\\n");
            }
        } else {
            msg.append("🏷️ Nenhuma tag marcada como obrigatória\\n");
        }

        return msg.toString();
    }
}
