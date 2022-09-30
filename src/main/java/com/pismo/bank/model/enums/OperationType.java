package com.pismo.bank.model.enums;

public enum OperationType {
    COMPRA_A_VISTA(1),
    COMPRA_PARCELADA(2),
    SAQUE(3),
    PAGAMENTO(4);


    private final int valor;
    OperationType(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return this.valor;
    }

    public static OperationType fromId(Long id) {
        for (OperationType type : values()) {
            if (type.getValor() == id) {
                return type;
            }
        }
        return null;
    }
}
