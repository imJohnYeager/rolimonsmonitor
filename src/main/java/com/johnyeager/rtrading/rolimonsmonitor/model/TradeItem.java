package com.johnyeager.rtrading.rolimonsmonitor.model;

public class TradeItem {
    private String nome;
    private String valor;
    private String rap;

    public TradeItem(String nome, String valor, String rap) {
        this.nome = nome;
        this.valor = valor;
        this.rap = rap;
    }

    public String getNome() {
        return nome;
    }

    public String getValor() {
        return valor;
    }

    public String getRap() {
        return rap;
    }

    @Override
    public String toString() {
        return nome + " | Value: " + valor + " | RAP: " + rap;
    }
}