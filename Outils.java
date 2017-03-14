
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;


public class Outils

/* Liste Fonctions présentes dans cette classe:
         * 
         * - findNb
         * - compter
         * - recupereBalise
         * - recupereBaliseAuto
         * - recupereBaliseOuvrante
         * - compareDeuxMots
         * - compareMotif
         * - constitueBalise
         */

{

        static Scanner sc = new Scanner(System.in);


        /* Fonction qui recherche un certain nombre de fois à partir du début 
         * Nb commence à 1 */
        public static int findNb(String chaine, String recherche, int debut, int nb)
        {
            int a = 0;
            for (int i = 0; i < nb; i++)
            {

                a = chaine.indexOf(recherche, debut);
                debut = a + 1;

            }
            return a;
        }

        // Fonction qui permet de compter le nombre d'occurence d'une chaine dans une autre
        public static int compter(String chaine, String recherche)
        {
            int a = -1;
            int i = 0;
            while (chaine.indexOf(recherche, a + 1) != -1)
            {
                a = chaine.indexOf(recherche, a + 1);
                i++;
            }
            return i;
        }


        // Fonction qui permet de récuperer le contenu d'une balise
        /*
         * SOURCE D'ERREUR : nbFermante
         * 
         * chaine : la chaine sur laquelle on doit récuperer la balise
         * baliseOuvrante : la balise ouvrante, sans le crochet ou guillemet ouvrant
         * nbOuvrante : la combien eme balise ouvrante qui va devoir etre prise
         * baliseFermante : la balise fermante, sans le crochet ou guillemet fermant
         * nbFermante : la combien eme balise fermante à partir de la balise ouvrante
         * crochet : si true ce jeu [] va être utilisé, sinon <>
         */

        public static String recupereBalise(String chaine, String baliseOuvrante, int nbOuvrante, String baliseFermante,
            int nbFermante, boolean crochet)
        {
            String crochetOuvrant = "<", crochetFermant = ">";
            if (crochet)
            {
                crochetOuvrant = "[";
                crochetFermant = "]";
            }
            int debut = findNb(chaine, crochetOuvrant + baliseOuvrante, 0, nbOuvrante);
            //System.out.println("debut : " + debut);
            chaine = chaine.substring(debut);
            debut = chaine.indexOf(crochetFermant) + 1;
            //System.out.println("debut2 : " + debut);
            chaine = chaine.substring(debut);
            int fin = findNb(chaine, crochetOuvrant + "/" + baliseFermante, 0, nbFermante);
            chaine = chaine.substring(0, fin);
            return chaine;
        }

        public static String recupereBalise(String chaine, String balise, boolean crochet)
        {
            return recupereBalise(chaine, balise, 1, balise, 1, crochet);
        }

        public static String recupereBalise(String chaine, String balise)
        {
            return recupereBalise(chaine, balise, 1, balise, 1, false);
        }

        public static String recupereBalise(String chaine, String baliseOuvrante, String baliseFermante)
        {
            return recupereBalise(chaine, baliseOuvrante, 1, baliseFermante, 1, false);
        }

        public static String recupereBalise(String chaine, String baliseOuvrante, String baliseFermante, boolean crochet)
        {
            return recupereBalise(chaine, baliseOuvrante, 1, baliseFermante, 1, crochet);
        }

        public static String recupereBalise(String chaine, String baliseOuvrante, int nbOuvrante, String baliseFermante, boolean crochet)
        {
            return recupereBalise(chaine, baliseOuvrante, nbOuvrante, baliseFermante, 1, crochet);
        }

        public static String recupereBalise(String chaine, String baliseOuvrante, int nbOuvrante, String baliseFermante)
        {
            return recupereBalise(chaine, baliseOuvrante, nbOuvrante, baliseFermante, 1, false);
        }

        public static String recupereBalise(String chaine, String balise, int nbOuvrante)
        {
            return recupereBalise(chaine, balise, nbOuvrante, balise, 1, false);
        }

        // Fonction permettant de récuperer automatiquement une balise (calcule le nombre d'ouverture et de fermeture de balise correspondante)
        public static String recupereBaliseAuto(String chaine, String baliseOuvrante, int nbOuvrante, String baliseFermante, boolean crochet)
        {
            String crochetOuvrant = "<", crochetFermant = ">";
            if (crochet)
            {
                crochetOuvrant = "[";
                crochetFermant = "]";
            }


            int nb = 1;
            int nbTotal = 0;
            boolean test = false;
            String chaine2 = "";
            while (nb != 0)
            {
                if (!test)
                {
                    nb = 0;
                    test = true;
                }
                    
                chaine2 = recupereBalise(chaine, baliseOuvrante, nbOuvrante, baliseFermante,nbTotal+1, crochet);
                nb = compter(chaine2,crochetOuvrant+baliseFermante) - compter(chaine2, crochetOuvrant+"/"+baliseFermante);
                nbTotal = compter(chaine2, crochetOuvrant + baliseFermante);

                

            } 
            

            return chaine2;
        }

        // Recupere entre dex balises ouvertes ce qu'il y'a à l'interieur

        public static String recupereBaliseOuvrante(String chaine, String baliseOuvrante, int nbOuvrante, String baliseFermante, int nbFermante, boolean crochet)
        {
            String crochetOuvrant = "<", crochetFermant = ">";
            if (crochet)
            {
                crochetOuvrant = "[";
                crochetFermant = "]";
            }
            int debut = findNb(chaine, crochetOuvrant + baliseOuvrante, 0, nbOuvrante);
            //System.out.println("debut : " + debut);
            chaine = chaine.substring(debut);
            debut = chaine.indexOf(crochetFermant) + 1;
            //System.out.println("debut2 : " + debut);
            chaine = chaine.substring(debut);
            int fin = findNb(chaine, crochetOuvrant + baliseFermante, 0, nbFermante);
            chaine = chaine.substring(0, fin);
            return chaine;
        }

        // Fonction qui permet de récuperer l'attribut dans l'ouverture d'une balise.
        public static String recupereAttributBalise(String chaine, String baliseOuvrante, int nbOuvrante, String nomAttribut, boolean crochet, boolean guillemetSimple)
        {
            String crochetOuvrant = "<", crochetFermant = ">";
            if (crochet)
            {
                crochetOuvrant = "[";
                crochetFermant = "]";
            }

            String guillemet = "\"";
            if (guillemetSimple)
            {
                guillemet = "'";
            }

            int debut = findNb(chaine, crochetOuvrant + baliseOuvrante, 0, nbOuvrante)+1;
            chaine = chaine.substring(debut);
            int fin = chaine.indexOf(crochetFermant);
            chaine = chaine.substring(0, fin);

            debut = chaine.indexOf(nomAttribut);
            chaine = chaine.substring(debut);
            debut = chaine.indexOf(guillemet)+1;
            chaine = chaine.substring(debut);
            fin = chaine.indexOf(guillemet);
            chaine = chaine.substring(0, fin);
            

            return chaine;
        }


        // Compare deux mots selon la définition d'Olivier en 1993. algo pas optimisé.
        public static int compareDeuxMots (String mot, String mot2)
        {
            String interm = mot;

            if (mot2.length() > mot.length())
            {
                
                mot = mot2;
                mot2 = interm;
            }
            int max = 0;
            int compteur = 0;
            
            for (int i = 0; i < mot2.length(); i++)
            {
                for (int i2 = 2; i2 <= mot2.length() - i; i2++)
                {
                    interm = mot2.substring(i, i2);
                    if (compareMotif(mot, interm))
                        compteur = interm.length();

                    if (compteur > max)
                    {
                        max = compteur;
                        System.out.println("interm : " + interm);
                    }
                }

                
                compteur = 0;
  

            }

            return max;
        }

        public static boolean compareMotif(String chaine, int indice, String motif){

            chaine = chaine.substring(indice);

            for (int i = 0; i < chaine.length() - motif.length() + 1; i++)
            {
                for (int i2 = 0; i2 < motif.length(); i2++)
                {
                    if (chaine.charAt(i + i2) != motif.charAt(i2))
                        break;

                    if (i2 == motif.length() - 1)
                        return true;
                    
                }

                
            }

            return false;
        }

        public static boolean compareMotif(String chaine, String motif)
        {
            return compareMotif(chaine, 0, motif);
        }

        public static String constitueBalise(String balise,String chaine){
            return constitueBalise(balise,null,null,chaine,false,false);
        }

        public static  String constitueBalise(String balise, String [] attributNames, String [] attribut, String chaine2,boolean crochet, boolean guillemetSimple){
            String guillemet = "<", guillemetFin = ">";
            String guillemetAttribut = "\""  ;
            if (crochet){
                guillemet = "[";
                guillemetFin = "]";
            }

            if (guillemetSimple)
                guillemetAttribut = "'";


            String chaine  = guillemet+balise;
            if ( attribut != null && attributNames != null && attributNames.length == attribut.length){
                for (int i = 0; i < attributNames.length; i++){
                    chaine += " "+ attributNames[i]+"="+guillemetAttribut+attribut[i]+guillemetAttribut;
                }
            }

            chaine+=guillemetFin + chaine2 + guillemet+"/"+balise+guillemetFin;
            return chaine;


        }

        public static void ecrireFichier (String adresse, String chaine){
            try{
                File ff=new File(adresse); // définir l'arborescence
                ff.createNewFile();
                FileWriter ffw=new FileWriter(ff);
                ffw.write(chaine);  // écrire une ligne dans le fichier resultat.txt
                ffw.close(); // fermer le fichier à la fin des traitements
            } catch (Exception e) {

            }
        }



        public static String lireFichier(String adresse){
            String chaine = "";
            try {
                Scanner sc = new Scanner(new File(adresse));
                while (sc.hasNextLine())
                {
                    chaine+= sc.nextLine()+"\n";
                }
                sc.close();
            }
            catch (Exception e){

            }

                

            return chaine;
        }


        public static boolean creeChemin(String dossier){
            if(!new File(dossier).exists())
            {
                    // Créer le dossier avec tous ses parents
                    new File(dossier).mkdirs();
                    return true;
         
            }
            return false;
        }

        public static void afficheTableau (String [] tab, String name){
            System.out.println("Affichage du tableau "+name+" :");
            System.out.print("{");
            for (int i  = 0 ; i < tab.length; i++){
                System.out.print(tab[i]+" | ");
            }
            System.out.println("}");
        }

        public static boolean testPresence(String adresse){
            try {
                BufferedReader in = new BufferedReader(new FileReader(adresse));
                return true;
            }
            catch (Exception e){

                return false;
            }
        }

        public static int getInt(){
            boolean continuer = true;
            int a = 0;
            while (continuer){
                try {
                    a = sc.nextInt();
                    sc.nextLine();
                    continuer = false;
                }
                catch(Exception e){
                    System.out.println("Merci de bien vouloir recommencer, un entier est attendu !");
                    sc = new Scanner (System.in);
                }
                
            }

            
            
            return a;
        }

        public static String getString(){
            String a = sc.nextLine();
            return a;
        }

        public static String getDate(){
            Calendar cal = Calendar.getInstance();
            String chaine = cal.get(Calendar.YEAR)+"_";

            String month = (1+cal.get(Calendar.MONTH))+"_";
            if (month.length() == 2)
                month = "0"+month;

            String day =  cal.get(Calendar.DAY_OF_MONTH)+" ";
            if (day.length() == 2)
               day = "0"+day;

           String hour = cal.get(Calendar.HOUR_OF_DAY)+"_";
           if (hour.length() == 2)
                hour = "0"+hour;

           String minute = cal.get(Calendar.MINUTE)+"_";
           if (minute.length() == 2)
                minute = "0"+minute;


           String seconde = cal.get(Calendar.SECOND)+"";
           if (seconde.length() == 1)
                seconde = "0"+seconde;

            chaine += month+day+hour+minute+seconde;
            return chaine;
        }

        public static Integer [] intToInteger(int [] oldArray){
            // Convertit un int[] en un Integer[]


            Integer[] newArray = new Integer[oldArray.length];
            int i = 0;
            for (int value : oldArray) {
                newArray[i++] = Integer.valueOf(value);
            }

            return newArray;

        }

        
        /*public static void main(String[] args) {
            creeChemin("Donnees/Gomuku/Games/");
            
           /* String chaine = "<Question><Type>QuestionSimple</Type><Points>2</Points><Ennonce>Quelle est le seul club français ayant gagné la champions league ?</Ennonce><Reponse>Marseille</Reponse><Casse>false</Casse></Question>";
            System.out.println(recupereBaliseAuto(chaine,"Type",1,"Type",false));
            System.out.println(constitueBalise("Bonjour","totototot"));
            ecrireFichier("resultat.f","Bonjour Jarvis !\nBonjour Jarvis\nBonjour Jarvis");
            System.out.println(lireFichier("resultat.f"));
            System.out.println(Outils.compter("Question Question","Question"));
            System.out.println(testPresence("quizz.f"));
            //testPresence("quizz.f"); 
            
        }  */
}


