/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbi_atm;

/**
 *
 * @author HP
 */
public class Model {
    private long account_no;
    private String acc_name;
    private long debit_card_no;
    private String card_pin;   // ✅ camelCase
    private int amount;

    public long getAccount_no() {
        return account_no;
    }

    public void setAccount_no(long account_no) {
        this.account_no = account_no;
    }

    public String getAcc_name() {
        return acc_name;
    }

    public void setAcc_name(String acc_name) {
        this.acc_name = acc_name;
    }

    public long getDebit_card_no() {
        return debit_card_no;
    }

    public void setDebit_card_no(long debit_card_no) {
        this.debit_card_no = debit_card_no;
    }

    public String getCardPin() {   // ✅ cleaner getter
        return card_pin;
    }

    public void setCardPin(String card_pin) {   // ✅ cleaner setter
        this.card_pin = card_pin;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}