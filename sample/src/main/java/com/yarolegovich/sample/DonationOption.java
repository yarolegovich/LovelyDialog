package com.yarolegovich.sample;

import androidx.annotation.NonNull;

/**
 * Created by yarolegovich on 17.04.2016.
 */
public class DonationOption {

    public final String description;
    public final String amount;

    public DonationOption(String amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return "DonationOption{" +
                "description='" + description + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
