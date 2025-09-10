package com.johnyeager.rtrading.rolimonsmonitor.services;

import com.johnyeager.rtrading.rolimonsmonitor.model.Anuncio;
import com.johnyeager.rtrading.rolimonsmonitor.model.FiltrosAnuncio;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RolimonsService {

    private final Set<String> anunciosProcessados = new HashSet<>();
    private final FiltrosAnuncio filtros;
    private final WebDriverManager webDriverManager;
    private final AnuncioProcessor anuncioProcessor;
    private final DiscordService discordService;

    public RolimonsService(FiltrosAnuncio filtros, WebDriverManager webDriverManager,
                           AnuncioProcessor anuncioProcessor,
                           DiscordService discordService) {
        this.filtros = filtros;
        this.webDriverManager = webDriverManager;
        this.anuncioProcessor = anuncioProcessor;
        this.discordService = discordService;
    }

    @Scheduled(fixedRate = 5000)
    public void verificarAnuncios() {
        System.out.println("🚀 Executando verificação...");

        WebDriver driver = webDriverManager.getDriver();
        try {
            driver.get("https://www.rolimons.com/trades");
            Thread.sleep(5000);

            List<WebElement> elementosAnuncios = driver.findElements(
                    By.cssSelector("div.shadow_md_15.mix_item"));

            System.out.println("🔹 Encontrados " + elementosAnuncios.size() + " anúncios");

            for (WebElement elementoAnuncio : elementosAnuncios) {
                processarElementoAnuncio(elementoAnuncio);
            }

        } catch (Exception e) {
            System.err.println("❌ Erro na verificação: " + e.getMessage());
            webDriverManager.restartDriver();
        }
    }

    private void processarElementoAnuncio(WebElement elementoAnuncio) {
        try {
            Anuncio anuncio = anuncioProcessor.processarAnuncio(elementoAnuncio, filtros);

            // Verifica se o anúncio já foi processado neste loop antes de tentar validar
            if (anuncio != null && anunciosProcessados.contains(anuncio.getId())) {
                System.out.println("⏭️ Anúncio já processado: " + anuncio.getId());
                return;
            }

            if (anuncio != null && anuncioProcessor.validarAnuncio(anuncio, filtros)) {
                discordService.enviarAnuncioDiscord(anuncio);
                anunciosProcessados.add(anuncio.getId()); // Adiciona o ID ao cache somente após a validação
                System.out.println("✅ Anúncio enviado: " + anuncio.getUsuario());

                if (anunciosProcessados.size() > 200) {
                    Iterator<String> it = anunciosProcessados.iterator();
                    if (it.hasNext()) {
                        it.next();
                        it.remove();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("⚠ Erro ao processar elemento do anúncio: " + e.getMessage());
        }
    }
}