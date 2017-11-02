package com.company;

public class Pizza {

    //attributer
    private String pizzaNavn;
    public String getPizzaNavn() {
        return pizzaNavn;
    }
    public void setPizzaNavn(String pizzaNavn) {
        this.pizzaNavn = pizzaNavn;
    }

    private int pizzaNummer;
    public int getPizzaNummer() {
        return pizzaNummer;
    }
    public void setPizzaNummer(int pizzaNummer) {
        this.pizzaNummer = pizzaNummer;
    }

    private int pizzaPris;
    public int getPizzaPris() {
        return pizzaPris;
    }
    public void setPizzaPris(int pizzaPris) {
        this.pizzaPris = pizzaPris;
    }

    private String pizzaBeskrivelse;
    public String getPizzaBeskrivelse() {
        return pizzaBeskrivelse;
    }
    public void setPizzaBeskrivelse(String pizzaBeskrivelse) {
        this.pizzaBeskrivelse = pizzaBeskrivelse;
    }

    //constructor (overload)
    public Pizza() {}

    public Pizza(String pizzaNavn, int pizzaNummer, int pizzaPris, String pizzaBeskrivelse) {
        this.pizzaNavn = pizzaNavn;
        this.pizzaNummer = pizzaNummer;
        this.pizzaPris = pizzaPris;
        this.pizzaBeskrivelse = pizzaBeskrivelse;
    }

    //override og formatering
    @Override
    public String toString(){
        return("#" + getPizzaNummer() + " " + getPizzaNavn()
                + " (" + getPizzaBeskrivelse() + ") " + getPizzaPris() + ",- ");
    }
}