package com.company;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class Ordreliste {

    //init ordrearraylist (kan gøres her pga. copy by reference)
    private ArrayList<Ordrelinje> ordreliste = new ArrayList<>();

    //constructor
    public Ordreliste(){ }

    //funktioner
    public void funktioner(Ordrearkiv ordrearkiv, Menu menu) {

        //print funktioner
        System.out.println("Hvilken funktion ønsker du at benytte?");
        System.out.println("1. Se ordrelisten");
        System.out.println("2. Tilføj ny ordre");
        System.out.println("3. Afslut ordre");
        System.out.println("4. Annuller ordre");
        System.out.println("5. Gå tilbage");

        //bool til at undersøge om brugeren angiver et korrekt svar.
        boolean korrektSvar = false;

        while(!korrektSvar) {
            //int til at sammenligne svar
            int svar = Main.intSvar();

            if (svar == 1) {
                if(ordreliste.isEmpty() == true){
                    System.out.println("Der er ikke nogen ordre.");
                    System.out.println();
                    korrektSvar = true;

                }
                else {
                    System.out.println();
                    print();
                    korrektSvar = true;
                }
            }
            else if (svar == 2){
                tilfoejOrdre(menu);
                korrektSvar = true;
            }
            else if (svar == 3){
                //if-sætningen tjekker, om der eksisterer nogle ordre i listen.
                if(ordreliste.size() > 0){
                    afslutOrdre(ordrearkiv);
                    korrektSvar = true;
                }
                else{
                    System.out.println("Der er ingen ordrer at afslutte.");
                    System.out.println();
                    korrektSvar = true;
                }

            }
            else if (svar == 4){
                //if-sætningen tjekker, om der eksisterer nogle ordre i listen.
                if(ordreliste.size() > 0) {
                    annullerOrdre();
                    korrektSvar = true;
                }
                else{
                    System.out.println("Der er  ingen ordrer at annullere.");
                    System.out.println();
                    korrektSvar = true;
                }
            }
            else if (svar == 5){
                //bryder løkken og vender direkte tilbage til funktioner
                korrektSvar = true;
            }
            else if(svar <= 0 || svar > 5) {
                System.out.println("Det indtastede valg (" + svar + ") eksisterer ikke.");
            }

        }

    }

    private void tilfoejOrdre(Menu menu) {

        //find pizza
        System.out.println("Pizzaens nummer:");

        //init pizzaNummer variable
        int pizzaNummer = 0;

        ArrayList<Integer> pizzaerKundenOensker = new ArrayList<>();
        ArrayList<String> pizzaBemaerkninger = new ArrayList<>();

        //Bool & While-løkke for at skabe flere Ordrelinjer.
        boolean kundenSkalHaveFlerePizzaer = true;
        while(kundenSkalHaveFlerePizzaer) {
            boolean korrektSvar = false;
            boolean korrektSvar2 = false;

            //while-løkke til at sikre korrekt svar.
            while (!korrektSvar) {
                pizzaNummer = Main.intSvar();

                if (pizzaNummer >= 1 && pizzaNummer <= menu.menuArrayList.size()) {
                    //opret note.
                    System.out.println("Indtast yderligere bemærkninger (Tryk ENTER, hvis der ikke er nogen):");
                    String note = Main.in.next();
                    //ændrer noten til "Ingen.", hvis der ikke indtastes noget.
                    if(note.equalsIgnoreCase("")){
                        note = "Ingen";
                    }
                    pizzaBemaerkninger.add(note);

                    korrektSvar = true;/////////

                } else if (pizzaNummer > menu.menuArrayList.size() || pizzaNummer <= 0) {
                    System.out.println("Pizza #" + pizzaNummer + " eksisterer ikke.");
                }
            }
            pizzaerKundenOensker.add(pizzaNummer);

            //kundenSkalHaveFlerePizzaer-løkke
            System.out.println("Ønsker kunden flere pizzaer?");
            System.out.println("1. Ja");
            System.out.println("2. Nej");

            //while-løkke til at sikre korrekt svar (2).
            while(!korrektSvar2) {
                int svar = Main.intSvar();
                if(svar == 1){
                    System.out.println("Pizza #:");
                    korrektSvar2 = true;
                }
                else if(svar == 2){
                    korrektSvar2 = true;
                    kundenSkalHaveFlerePizzaer = false;
                }
                else{
                    System.out.println("Det indtastede valg (" + svar + ") eksisterer ikke.");

                }
            }
        }

        //opret kunde.
        System.out.println("Kundens navn:");
        String kundensNavn = Main.in.next();
        System.out.println("Kundens telefonnummer:");
        String kundensTlfnummer = tlfnummerScan();

        Kunde kunde = new Kunde(kundensNavn, kundensTlfnummer);

        //Bestem afhentningstidspunkt via string => LocalTime (skal have en korrektsvarløkke!)
        System.out.println("Forventes færdig klokken:");
        String afhentningstidspunkt = afhentningstidspunktScan();
        LocalTime localTime = LocalTime.parse(afhentningstidspunkt, DateTimeFormatter.ofPattern("HHmm"));

        for (int i = 0; i < pizzaerKundenOensker.size(); i++) {
            //meget uelegant måde at skabe pizzaen fra arraylisten, men den letteste løsning jeg kunne regne ud.
            Pizza pizza = new Pizza(menu.menuArrayList.get(pizzaerKundenOensker.get(i) -1).getPizzaNavn(),
                    menu.menuArrayList.get(pizzaerKundenOensker.get(i) -1).getPizzaNummer(),
                    menu.menuArrayList.get(pizzaerKundenOensker.get(i) -1).getPizzaPris(),
                    menu.menuArrayList.get(pizzaerKundenOensker.get(i) -1).getPizzaBeskrivelse());
            String note = pizzaBemaerkninger.get(i);

            //skaber objektet(Ordrelinje) via den indsamlede data.
            Ordrelinje nyOrdrelinje = new Ordrelinje(pizza, kunde, note, localTime);

            //tilføjer den nye ordre til arraylisten(ordreliste).
            ordreliste.add(nyOrdrelinje);

        }
        System.out.println("[Ordren er tilføjet]");

        //sortérer listen.
        ordreliste.sort(Comparator.comparing(Ordrelinje::getLocalTime));

        //just for zeh lookz...
        System.out.println();
    }

    private void afslutOrdre(Ordrearkiv ordrearkiv){ //mangler at gemme ordre i arkiv.

        boolean korrektSvar = false;
        print();
        //if/else: Hvis der kun eksisterer én ordre, behøver brugeren ikke at træffe noget valg.
        if(ordreliste.size() > 1) {
            System.out.println("1. Afslut den øverste ordre fra køen");
            System.out.println("2. Afslut en anden ordre");
            System.out.println("3. Gå tilbage");
            while (!korrektSvar) {
                int svar = Main.intSvar();
                if (svar == 1) {

                    //++ i array
                    int menuNummerFraOrdrelinje = ordreliste.get(0).getPizza().getPizzaNummer() - 1;
                    ordrearkiv.ordrearkivListe[menuNummerFraOrdrelinje] ++;
                    ordrearkiv.gemData();

                    ordreliste.remove(0);
                    System.out.println("[Ordren er afsluttet og gemt]");
                    System.out.println();
                    korrektSvar = true;
                } else if (svar == 2) {
                    boolean korrektSvar2 = false;
                    System.out.println("Hvilket nummer i køen har ordren, du ønsker at afslutte?");
                    while (!korrektSvar2) {
                        int svar2 = Main.intSvar();
                        if (svar2 <= ordreliste.size() && svar2 > 0) {

                            //++ i array
                            int menuNummerFraOrdrelinje = ordreliste.get(svar2 - 1).getPizza().getPizzaNummer() - 1;
                            ordrearkiv.ordrearkivListe[menuNummerFraOrdrelinje] ++;
                            ordrearkiv.gemData();

                            ordreliste.remove(svar2 - 1);
                            System.out.println("[Ordren er afsluttet og gemt]");
                            System.out.println();
                            korrektSvar2 = true;
                        } else if (svar2 > ordreliste.size()) {
                            System.out.println("Den indtastede ordre: '" + svar2 + "' kan ikke afsluttes.");
                        }
                    }
                    korrektSvar = true;
                } else if(svar == 3){
                    korrektSvar = true;
                } else {
                    System.out.println("Det indtastede valg (" + svar + ") eksisterer ikke.");
                }
            }
        }
        else {
            //++ i array
            int menuNummerFraOrdrelinje = ordreliste.get(0).getPizza().getPizzaNummer() - 1;
            ordrearkiv.ordrearkivListe[menuNummerFraOrdrelinje] ++;
            ordrearkiv.gemData();

            ordreliste.remove(0);
            System.out.println("[Ordren er afsluttet og gemt]");
            System.out.println();
        }
    }

    private void annullerOrdre(){
        print();
        boolean korrektSvar = false;
        System.out.println("Hvilket nummer i køen har ordren, du ønsker at annullere?");
        System.out.println("Tast '0', hvis du ikke ønsker at annullere en ordre.");

        while(!korrektSvar){
            int svar = Main.intSvar();
            if(svar <= ordreliste.size() && svar > 0){
                ordreliste.remove(svar-1);
                System.out.println("[Ordren er annulleret]");
                System.out.println();
                korrektSvar = true;
            }
            else if(svar == 0){
                korrektSvar = true;
            }
            else{
                System.out.println("Den indtastede ordre: '" + svar + "' kan ikke annulleres");
            }
        }
    }

    //utility
    public String tlfnummerScan(){
        boolean korrektSvar = false;
        String kundensTlfnummer = "";
        while(!korrektSvar) {
            kundensTlfnummer = Main.in.next();

            if(kundensTlfnummer.length() == 8 && kundensTlfnummer.matches("^[0-9]{1,8}$")){
                korrektSvar = true;
            }
            else{
                System.out.println("Telefonnummeret skal bestå af otte cifre.");
            }
        }
        return kundensTlfnummer;
    }

    public String afhentningstidspunktScan(){
        boolean korrektSvar = false;
        String afhentningstidspunkt = "";
        while(!korrektSvar){
            afhentningstidspunkt = Main.in.next();

            if(afhentningstidspunkt.length() == 4 && afhentningstidspunkt.matches("^[0-9]{1,4}$")){
                int intTidspunkt = Integer.parseInt(afhentningstidspunkt);
                if(intTidspunkt < 2360 && intTidspunkt >= 2300 ||
                        intTidspunkt < 2260 && intTidspunkt >= 2200 ||
                        intTidspunkt < 2160 && intTidspunkt >= 2100 ||
                        intTidspunkt < 2060 && intTidspunkt >= 2000 ||
                        intTidspunkt < 1960 && intTidspunkt >= 1900 ||
                        intTidspunkt < 1860 && intTidspunkt >= 1800 ||
                        intTidspunkt < 1760 && intTidspunkt >= 1700 ||
                        intTidspunkt < 1660 && intTidspunkt >= 1600 ||
                        intTidspunkt < 1560 && intTidspunkt >= 1500 ||
                        intTidspunkt < 1460 && intTidspunkt >= 1400 ||
                        intTidspunkt < 1360 && intTidspunkt >= 1300 ||
                        intTidspunkt < 1260 && intTidspunkt >= 1200 ||
                        intTidspunkt < 1160 && intTidspunkt >= 1100 ||
                        intTidspunkt < 1060 && intTidspunkt >= 1000 ||
                        intTidspunkt < 960 && intTidspunkt >= 900 ||
                        intTidspunkt < 860 && intTidspunkt >= 800 ||
                        intTidspunkt < 760 && intTidspunkt >= 700 ||
                        intTidspunkt < 660 && intTidspunkt >= 600 ||
                        intTidspunkt < 560 && intTidspunkt >= 500 ||
                        intTidspunkt < 460 && intTidspunkt >= 400 ||
                        intTidspunkt < 360 && intTidspunkt >= 300 ||
                        intTidspunkt < 260 && intTidspunkt >= 200 ||
                        intTidspunkt < 160 && intTidspunkt >= 100 ||
                        intTidspunkt < 60 && intTidspunkt >= 0){
                    korrektSvar = true;
                }
                else{
                    System.out.println("Dette klokkeslæt (" + afhentningstidspunkt + ") eksisterer ikke.");
                }
            }
            else{
                System.out.println("Klokkeslættet må kun bestå af 4 cifre.");
            }
        }
        return afhentningstidspunkt;
    }

    public void print(){
        for (int i = 0; i < ordreliste.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + ordreliste.get(i));
        }

    }
}
