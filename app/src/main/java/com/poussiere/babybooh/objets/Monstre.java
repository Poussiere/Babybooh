package com.poussiere.babybooh.objets;

import java.util.Random;

/**
 * Created by poussiere on 1/8/16.
 *
 * CLASSE A SUPPRIMER ou mettre dans cette classe les méthodes du type monstreEnString qui sont écrites plusieurs fois inutilement pour tableau de chasse et écoute'
 *
 *
 *
 */
public abstract class Monstre {


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Méthode pour savoir quel monstre à réveillé l'enfant à partir de tous les parametres rentrant en compte
    public static int quelMonstre(int lum, int heure, double decibels, long difference, int nbReveils) {

        int monstre=0;
        Random rand = new Random();
        int nombreAleatoire=0;


        ////////////////Monstres du soir

        if (heure>20 ||  heure<8)

        {
            if (nbReveils>3) monstre=11; // Si reveillé 3 fois alors pouponnator (11)
            else if (decibels >=80) monstre=1; // Si cri très fort alors Dédé la terreur (1)
            else if (decibels>60 && decibels<80) {
                if (lum < 5 && difference > 3600) {
                    nombreAleatoire = rand.nextInt(3 - 1 + 1) + 1;
                    switch (nombreAleatoire) {
                        case 1:
                            monstre = 4;
                            break;
                        case 2:
                            monstre = 8;
                            break;
                        case 3:
                            monstre = 9;
                            break;
                    }
                } else if (lum < 5) {
                    nombreAleatoire = rand.nextInt(2 - 1 + 1) + 1;
                    switch (nombreAleatoire) {
                        case 1:
                            monstre = 8;
                            break;
                        case 2:
                            monstre = 9;
                            break;
                    }
                } else monstre = 9;
            }
            else if(lum>20) {
                if (nbReveils > 3600) {
                    nombreAleatoire = rand.nextInt(4 - 1 + 1) + 1;
                    switch (nombreAleatoire) {
                        case 1:
                            monstre = 3;
                            break;
                        case 2:
                            monstre = 4;
                            break;
                        case 3:
                            monstre = 6;
                            break;
                        case 4:
                            monstre = 7;
                            break;
                    }
                } else {
                    nombreAleatoire = rand.nextInt(3 - 1 + 1) + 1;
                    switch (nombreAleatoire) {
                        case 1:
                            monstre = 3;
                            break;
                        case 2:
                            monstre = 6;
                            break;
                        case 3:
                            monstre = 7;
                            break;

                    }
                }
            }
            else if (lum>4 && lum<11 && decibels<40) {
                if (difference > 3600) {
                    nombreAleatoire = rand.nextInt(3 - 1 + 1) + 1;
                    switch (nombreAleatoire) {
                        case 1:
                            monstre = 4;
                            break;
                        case 2:
                            monstre = 7;
                            break;
                        case 3:
                            monstre = 10;
                            break;
                    }
                } else {
                    nombreAleatoire = rand.nextInt(2 - 1 + 1) + 1;
                    switch (nombreAleatoire) {
                        case 1:
                            monstre = 7;
                            break;
                        case 2:
                            monstre = 10;
                            break;
                    }

                }
            }
            else if (lum>0 && lum<6 ) {
                nombreAleatoire = rand.nextInt(2 - 1 + 1) + 1;
                switch (nombreAleatoire) {
                    case 1:
                        monstre = 7;
                        break;
                    case 2:
                        monstre = 8;
                        break;
                }
            }

            else if (lum==0) {
                nombreAleatoire = rand.nextInt(3 - 1 + 1) + 1;
                switch (nombreAleatoire) {
                    case 1:
                        monstre = 7;
                        break;
                    case 2:
                        monstre = 8;
                        break;
                    case 3:
                        monstre = 12;
                        break;
                }
            }

            else if (difference>3600) {
                nombreAleatoire = rand.nextInt(2 - 1 + 1) + 1;
                switch (nombreAleatoire) {
                    case 1:
                        monstre = 4;
                        break;
                    case 2:
                        monstre = 7;
                        break;
                }

            }

            else monstre = 7;


        }

        //////////Monstres de la journée

        if (heure>7 && heure<21)
        {

            if (nbReveils>3) monstre=11;
            else if (decibels >=80) monstre=1;
            else if (lum==0 && decibels >40) monstre=12;
            else if (decibels>60 && decibels<80)
            {
                if ((heure>=8 && heure<=9) || (heure>=12 && heure<=14) || (heure>=16 && heure<=17) || (heure>=20 && heure<=21))
                {
                    nombreAleatoire = rand.nextInt(2 - 1 + 1) + 1;
                    switch (nombreAleatoire) {
                        case 1:
                            monstre = 5;
                            break;
                        case 2:
                            monstre = 9;
                            break;
                    }
                }
                else monstre=9;


            }
            else if ((heure>=8 && heure<=9) || (heure>=12 && heure<=14) || (heure>=16 && heure<=17) || (heure>=20 && heure<=21)) {
                if (lum > 5 && decibels > 40) {
                    nombreAleatoire = rand.nextInt(2 - 1 + 1) + 1;
                    switch (nombreAleatoire) {
                        case 1:
                            monstre = 5;
                            break;
                        case 2:
                            monstre = 6;
                            break;
                    }
                } else {
                    nombreAleatoire = rand.nextInt(3 - 1 + 1) + 1;
                    switch (nombreAleatoire) {
                        case 1:
                            monstre = 2;
                            break;
                        case 2:
                            monstre = 5;
                            break;
                        case 3:
                            monstre = 7;
                    }
                }
            }

            else if (lum>5) {
                nombreAleatoire = rand.nextInt(3 - 1 + 1) + 1;
                switch (nombreAleatoire) {
                    case 1:
                        monstre = 2;
                        break;
                    case 2:
                        monstre = 6;
                        break;
                    case 3:
                        monstre = 7;
                }
            }

            else {
                nombreAleatoire = rand.nextInt(2 - 1 + 1) + 1;
                switch (nombreAleatoire) {
                    case 1:
                        monstre = 2;
                        break;
                    case 2:
                        monstre = 7;
                        break;
                }
            }

        }

        return monstre;
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Méthode pour dire quel monstre en String à partir du int (pour la gestion des shared preference qui disent si le monstre a été débloqué ou non
    //Créer une méthode dans monstre pour mettre cette methode qui sert egalement dans recyclerViwAdapter et ChasseFullScreenActivity

    public static String quelMonstreString (int numeroMonstre)
    {
        String monstreEnString = null;
        switch (numeroMonstre)
        {
            case 1:
                monstreEnString="monstre1";
                break;

            case 2:
                monstreEnString="monstre2";
                break;

            case 3:
                monstreEnString="monstre3";
                break;

            case 4:
                monstreEnString="monstre4";
                break;

            case 5:
                monstreEnString="monstre5";
                break;

            case 6:
                monstreEnString="monstre6";
                break;

            case 7:
                monstreEnString="monstre7";
                break;

            case 8:
                monstreEnString="monstre8";
                break;

            case 9:
                monstreEnString="monstre9";
                break;

            case 10:
                monstreEnString="monstre10";
                break;

            case 11:
                monstreEnString="monstre11";
                break;

            case 12:
                monstreEnString="monstre12";
                break;
        }
        return monstreEnString;
    }





}
