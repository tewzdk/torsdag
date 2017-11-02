package com.company;

public class Kunde {

    //attributer
    private String navn;
    public String getNavn() {
        return navn;
    }

    private String tlfnummer;
    public String getTlfnummer(){return tlfnummer;}

    //constructor (overload)
    public Kunde(){}

    public Kunde(String navn, String tlfnummer){
        this.navn = navn;
        this.tlfnummer = tlfnummer;
    }

    //override og formatering
    @Override
    public String toString(){
        return("Kunde: " + getNavn() + " (" + getTlfnummer() + ")");
    }
}
