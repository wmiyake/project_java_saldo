package com.fluxocaixacontrol.fluxo_caixa_control.domain.model;

public enum TipoLancamento {

    CREDITO("Crédito", 1 ),
    DEBITO("Débito", -1);

    private final String descricao;
    private final int multiplicador;

    TipoLancamento(String descricao, int multiplicador){
        this.descricao = descricao;
        this.multiplicador = multiplicador;
    }

    public int getMultiplicador() { return multiplicador; }
}
