package com.mert.secunda_bank.models.enums;

public enum BillTypes {
    ELECTRICITY,
    WATER,
    INTERNET,
    NATURAL_GAS,
    CREDIT_CARD,
    PHONE;

    @Override
    public String toString() {
        switch (this) {
            case ELECTRICITY: return "Electricity";
            case WATER: return "Water";
            case INTERNET: return "Internet";
            case NATURAL_GAS: return "Natural Gas";
            case CREDIT_CARD: return "Credit Card";
            case PHONE: return "Phone";
            default: return super.toString();
        }
    }
}
