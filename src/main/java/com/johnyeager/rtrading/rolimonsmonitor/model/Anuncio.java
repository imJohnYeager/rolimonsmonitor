package com.johnyeager.rtrading.rolimonsmonitor.model;

import java.util.List;

public class Anuncio {
    private String id;
    private String usuario;
    private int valor;
    private List<String> tags;
    private List<TradeItem> offering;
    private List<TradeItem> requesting;
    private String tradeLink;

    public Anuncio(String id, String usuario, int valor, List<String> tags,
                   List<TradeItem> offering, List<TradeItem> requesting, String tradeLink) {
        this.id = id;
        this.usuario = usuario;
        this.valor = valor;
        this.tags = tags;
        this.offering = offering;
        this.requesting = requesting;
        this.tradeLink = tradeLink;
    }

    public String getId() { return id; }
    public String getUsuario() { return usuario; }
    public int getValor() { return valor; }
    public List<String> getTags() { return tags; }
    public List<TradeItem> getOffering() { return offering; }
    public List<TradeItem> getRequesting() { return requesting; }
    public String getTradeLink() { return tradeLink; }
}