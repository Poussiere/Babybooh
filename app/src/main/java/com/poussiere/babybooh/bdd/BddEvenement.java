package com.poussiere.babybooh.bdd;

import java.util.Calendar;

/**
 * Created by poussiere on 11/10/15.
 */
public class BddEvenement {

    private int id, lum ;
    private long date ;
    private int decibels ;
    private int monstre;


    public void setId(int i)
    {this.id=i;}

    public int getId()
    {return this.id;}

    public void setLum(int l)
    {this.lum=l;}

    public int getLum()
    {return this.lum;}

    public void setDecibels (double d)
    {this.decibels=(int)Math.round(d);}

    public int getDecibels ()
    {return decibels;}

    public void setDate(long d)
    {this.date=d;}

    public long getDate()
    {return this.date;}

    public void setMonstre(int m)
    {this.monstre=m;}

    public int getMonstre()
    {return this.monstre;}
// On cree un ensemble de méthodes qui permettront de récupérer les valeurs contenues dans la date en fonction des besoins

    public int getAnnee()
    {Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(date);
        int annee = cal.get(Calendar.YEAR);
        return annee;}

    public int getMois()
    {Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(date);
        int mois = cal.get(Calendar.MONTH)+1;
        return mois;
    }

    public int getJour()
    {Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(date);
        int jour = cal.get (Calendar.DAY_OF_MONTH);
        return jour;
    }

    public int getHeure()
    {Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(date);
        int heure = cal.get (Calendar.HOUR_OF_DAY);
        return heure;}

    public int getMinutes()
    {Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(date);
        int minutes = cal.get (Calendar.MINUTE);
        return minutes;}


    // ne pas oublier pour l'affichage
    @Override
    public String toString()
    {
        return "Evenement a  "+this.getDecibels()+" decibels survenu le "+ this.getJour()+"/"+this.getMois()+"/"+this.getAnnee()+" avec "+this.getLum()+" lux "+
                " et le monstre n°"+this.getMonstre();
    }
/*

    @Override
    public String toString() {
        return ""+this.getNom()+ this.getEmail()+this.getPrenom();    }
*/


    //M?thode qui retourne le nom du monstre qui a r?veill? l'enfant pour l'?v?nement en question
    //On peut également optimiser le code en determinant quel monstre est apparu directement lorsque l'évènement se produit

   /* public int quelMonstre()
    {
        //On cree un entier qui servira a identifier le monstre
        int numMonstre;

        int heure = this.getHeure(); // On récupère l'heure de l'évenement

        if (lum<10 && heure>23 && decibels>30) // On pose la condition de l'apparition du monstre
        {numMonstre=1;}

        else numMonstre=10;

        return numMonstre;} // On retourne le numéro du monstre qui est appraru
*/

}





