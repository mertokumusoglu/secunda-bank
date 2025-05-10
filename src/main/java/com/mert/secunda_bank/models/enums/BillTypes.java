package com.mert.secunda_bank.models.enums;

public enum BillTypes {
    ELECTRICITY,
    WATER,
    INTERNET,
    NATURAL_GAS,
    LOAN_DEBT,
    PHONE;

    @Override
    public String toString() {
        switch (this) {
            case ELECTRICITY: return "Electricity";
            case WATER: return "Water";
            case INTERNET: return "Internet";
            case NATURAL_GAS: return "Natural Gas";
            case LOAN_DEBT: return "Loan Debt";
            case PHONE: return "Phone";
            default: return super.toString();
        }
    }
}
