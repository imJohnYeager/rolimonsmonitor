package com.johnyeager.rtrading.rolimonsmonitor.services;

import com.johnyeager.rtrading.rolimonsmonitor.model.Anuncio;
import com.johnyeager.rtrading.rolimonsmonitor.model.FiltrosAnuncio;
import com.johnyeager.rtrading.rolimonsmonitor.model.TradeItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AnuncioProcessor {

    private static final Map<String, String> TAG_NAMES = Map.of(
            "tradetagany", "Any",
            "tradetagdemand", "Demand",
            "tradetagrares", "Rares",
            "tradetagwishlist", "Wishlist",
            "tradetagrobux", "Robux",
            "tradetagupgrade", "Upgrade",
            "tradetagdowngrade", "Downgrade",
            "tradetagadds", "Adds",
            "tradetagprojecteds", "Projecteds"
    );

    // Adicionamos um Set para armazenar os IDs dos anúncios já processados
    private final Set<String> anunciosProcessados = new HashSet<>();

    public Anuncio processarAnuncio(WebElement elementoAnuncio, FiltrosAnuncio filtros) {
        String anuncioId = "";

        try {
            anuncioId = extrairId(elementoAnuncio);

            // Verifica se o anúncio já foi processado neste loop
            if (anunciosProcessados.contains(anuncioId)) {
                System.out.println("⏭️ Anúncio já processado: " + anuncioId);
                return null;
            }

            System.out.println("📋 Analisando anúncio ID: " + anuncioId);

            String usuario = extrairUsuario(elementoAnuncio);
            System.out.println("👤 Usuário: " + usuario);

            int valor = extrairValor(elementoAnuncio);
            System.out.println("💰 Valor encontrado: " + valor);

            List<String> tags = extrairTags(elementoAnuncio);
            System.out.println("🏷️ Tags encontradas: " + tags);

            List<TradeItem> offering = extrairOffering(elementoAnuncio);
            System.out.println("🎁 Items no offering: " + offering.size());

            List<TradeItem> requesting = extrairRequesting(elementoAnuncio);
            System.out.println("🎁 Items no requesting: " + requesting.size());

            String tradeLink = extrairTradeLink(elementoAnuncio);

            // Adiciona o ID ao cache após o processamento
            anunciosProcessados.add(anuncioId);

            return new Anuncio(anuncioId, usuario, valor, tags, offering, requesting, tradeLink);

        } catch (Exception e) {
            System.out.println("❌ Erro ao processar anúncio " + anuncioId + ": " + e.getMessage());
            return null;
        }
    }

    private String extrairId(WebElement anuncio) {
        try {
            return anuncio.findElement(By.cssSelector("a.send_trade_button"))
                    .getAttribute("href");
        } catch (Exception e) {
            System.out.println("⚠ Não foi possível extrair ID do anúncio. Retornando valor padrão.");
            return "ID_NAO_ENCONTRADO";
        }
    }

    private String extrairUsuario(WebElement anuncio) {
        try {
            return anuncio.findElement(By.cssSelector("a.ad_creator_name"))
                    .getText().trim();
        } catch (Exception e) {
            System.out.println("⚠ Não foi possível extrair usuário. Retornando valor padrão.");
            return "USUARIO_DESCONHECIDO";
        }
    }

    private int extrairValor(WebElement anuncio) {
        try {
            List<WebElement> valueElements = anuncio.findElements(
                    By.xpath(".//div[contains(@class,'stat_value') and not(contains(text(),'-'))]"));

            for (WebElement valueEl : valueElements) {
                String val = valueEl.getText().replace(".", "").replace(",", "").trim();
                if (!val.isEmpty() && !val.equals("-")) {
                    try {
                        return Integer.parseInt(val);
                    } catch (NumberFormatException ex) {
                        // continua
                    }
                }
            }
            System.out.println("⚠ Não foi possível extrair valor. Retornando -1.");
            return -1;
        } catch (Exception e) {
            System.out.println("⚠ Erro inesperado ao extrair valor. Retornando -1.");
            return -1;
        }
    }

    private List<String> extrairTags(WebElement anuncio) {
        List<String> tags = new ArrayList<>();
        try {
            WebElement requestSide = anuncio.findElement(By.cssSelector("div.ad_side_right"));
            List<WebElement> tagImgs = requestSide.findElements(By.cssSelector("img.ad_item_img"));

            for (int i = 0; i < tagImgs.size(); i++) {
                WebElement img = tagImgs.get(i);
                String dataSrc = img.getAttribute("data-src");

                if (dataSrc == null || dataSrc.contains("empty_trade_slot")) {
                    continue;
                }

                for (Map.Entry<String, String> entry : TAG_NAMES.entrySet()) {
                    if (dataSrc.toLowerCase().contains(entry.getKey().toLowerCase())) {
                        tags.add(entry.getValue());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("⚠ Erro ao extrair tags. Retornando lista vazia.");
        }
        return tags;
    }

    private List<TradeItem> extrairOffering(WebElement anuncio) {
        List<TradeItem> items = new ArrayList<>();
        try {
            WebElement offerSide = anuncio.findElement(By.cssSelector("div.ad_side_left"));
            List<WebElement> offerImgs = offerSide.findElements(By.cssSelector("img.ad_item_img"));

            for (WebElement itemEl : offerImgs) {
                String tooltip = itemEl.getAttribute("data-original-title");
                if (tooltip != null && !tooltip.isEmpty()) {
                    String[] parts = tooltip.split("<br>");
                    String name = parts[0].trim();
                    String value = parts.length > 1 ? parts[1].replace("Value", "").trim() : "";
                    String rap = parts.length > 2 ? parts[2].replace("RAP", "").trim() : "";
                    items.add(new TradeItem(name, value, rap));
                }
            }
        } catch (Exception e) {
            System.out.println("⚠ Erro ao extrair offering. Retornando lista vazia.");
        }
        return items;
    }

    private List<TradeItem> extrairRequesting(WebElement anuncio) {
        List<TradeItem> items = new ArrayList<>();
        try {
            WebElement requestSide = anuncio.findElement(By.cssSelector("div.ad_side_right"));
            List<WebElement> requestImgs = requestSide.findElements(By.cssSelector("img.ad_item_img"));

            for (WebElement itemEl : requestImgs) {
                String tooltip = itemEl.getAttribute("data-original-title");
                if (tooltip != null && !tooltip.isEmpty()) {
                    String[] parts = tooltip.split("<br>");
                    String name = parts[0].trim();
                    String value = parts.length > 1 ? parts[1].replace("Value", "").trim() : "";
                    String rap = parts.length > 2 ? parts[2].replace("RAP", "").trim() : "";
                    items.add(new TradeItem(name, value, rap));
                }
            }
        } catch (Exception e) {
            System.out.println("⚠ Erro ao extrair requesting. Retornando lista vazia.");
        }
        return items;
    }

    private String extrairTradeLink(WebElement anuncio) {
        try {
            return anuncio.findElement(By.cssSelector("a.send_trade_button")).getAttribute("href");
        } catch (Exception e) {
            System.out.println("⚠ Não foi possível extrair link de trade. Retornando valor padrão.");
            return "https://www.rolimons.com/trades";
        }
    }

    public boolean validarAnuncio(Anuncio anuncio, FiltrosAnuncio filtros) {
        if (anuncio == null) {
            System.out.println("❌ Anúncio é nulo");
            return false;
        }

        if (anuncio.getValor() < filtros.getMinValue() || anuncio.getValor() > filtros.getMaxValue()) {
            System.out.println("❌ Fora do range: " + anuncio.getValor() + " (Range: " + filtros.getMinValue() + "-" + filtros.getMaxValue() + ")");
            return false;
        }

        if (filtros.getMinOfferingItems() != null && anuncio.getOffering().size() < filtros.getMinOfferingItems()) {
            System.out.println("❌ Poucos itens no Offering. Esperado: " + filtros.getMinOfferingItems() + ", Encontrado: " + anuncio.getOffering().size());
            return false;
        }
        if (filtros.getMaxOfferingItems() != null && anuncio.getOffering().size() > filtros.getMaxOfferingItems()) {
            System.out.println("❌ Muitos itens no Offering. Esperado: " + filtros.getMaxOfferingItems() + ", Encontrado: " + anuncio.getOffering().size());
            return false;
        }

        if (filtros.getMinRequestingItems() != null && anuncio.getRequesting().size() < filtros.getMinRequestingItems()) {
            System.out.println("❌ Poucos itens no Requesting. Esperado: " + filtros.getMinRequestingItems() + ", Encontrado: " + anuncio.getRequesting().size());
            return false;
        }
        if (filtros.getMaxRequestingItems() != null && anuncio.getRequesting().size() > filtros.getMaxRequestingItems()) {
            System.out.println("❌ Muitos itens no Requesting. Esperado: " + filtros.getMaxRequestingItems() + ", Encontrado: " + anuncio.getRequesting().size());
            return false;
        }

        Map<String, Boolean> filtrosTags = filtros.getTags();
        System.out.println("🔍 Verificando tags...");
        for (Map.Entry<String, Boolean> entry : filtrosTags.entrySet()) {
            String tagKey = "tradetag" + entry.getKey();
            String tagName = TAG_NAMES.get(tagKey);
            if (Boolean.FALSE.equals(entry.getValue())) {
                if (anuncio.getTags().contains(tagName)) {
                    System.out.println("❌ Tag proibida encontrada: " + tagName);
                    return false;
                }
            }
            if (Boolean.TRUE.equals(entry.getValue())) {
                if (!anuncio.getTags().contains(tagName)) {
                    System.out.println("❌ Tag obrigatória não encontrada: " + tagName);
                    return false;
                }
            }
        }

        List<String> offeringNamesFilter = filtros.getOfferingItemsFilter();
        if (!offeringNamesFilter.isEmpty()) {
            boolean found = false;
            for (TradeItem item : anuncio.getOffering()) {
                if (offeringNamesFilter.stream().anyMatch(filterName -> item.getNome().toLowerCase().contains(filterName.toLowerCase()))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("❌ Item do Offering obrigatório não encontrado.");
                return false;
            }
        }

        List<String> requestingNamesFilter = filtros.getRequestingItemsFilter();
        if (!requestingNamesFilter.isEmpty()) {
            boolean found = false;
            for (TradeItem item : anuncio.getRequesting()) {
                if (requestingNamesFilter.stream().anyMatch(filterName -> item.getNome().toLowerCase().contains(filterName.toLowerCase()))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("❌ Item do Requesting obrigatório não encontrado.");
                return false;
            }
        }

        System.out.println("✅ Anúncio validado com sucesso!");
        return true;
    }

    // Método agendado para limpar o cache a cada 1 hora (3.600.000 ms)
    @Scheduled(fixedRate = 3600000)
    public void limparCacheDeAnuncios() {
        System.out.println("Limpeza de cache de anúncios processados...");
        anunciosProcessados.clear();
        System.out.println("Cache de anúncios limpo.");
    }
}