import java.util.*;

public class PlateauGomuku extends Plateau{
	boolean variante ;
	int nbAlignement;
	JoueurGomuku j1, j2;
	int lonAlignement = 5;


	

	public PlateauGomuku(int abs, int ord, boolean variante, int nbAlignement, JoueurGomuku j1, JoueurGomuku j2){ 
		super(abs, ord);
		//System.out.println("In the PlateauGomuku");
		this.variante = variante;
		this.nbAlignement = nbAlignement;
		this.j1 = j1;
		this.j2 = j2;
		//System.out.println("Colorer");
	}

	public boolean verificationFin() {
		// Fonction vérifiant si la partie est terminée.
		if (!variante){
			return plateauComplet();
		}else {
			if (plateauComplet())
				return true;
			return verificationAlignement();
		}
	}

	public boolean verificationAlignement(){
		// Fonction verifiant si un joueur a atteint le nombre d'alignement requis.
		if (verificationAlignementJoueur(j1) == nbAlignement)
			return true;
		if (verificationAlignementJoueur(j2) == nbAlignement)
			return true;	
		return false;	
	}

	public JoueurGomuku recupereGagnant(){
		// Renvoie le joueur ayant le plus d'alignements et null si c'est égal.

		int a = verificationAlignementJoueur(j1);
		int b = verificationAlignementJoueur(j2);
		//System.out.println("J1 : "+a+" J2 : "+b);
		if (a == b)
			return null;
		else if (a > b)
			return j1;
		return j2;
	}

	public int verificationAlignementJoueur(Joueur j){
		// Fonction retournant le nombre d'alignements de 5 pions d'un joueur
		int nbAlignement = 0;
		int compteurLigne = 0, compteurColonne = 0, compteurDiagGauche = 0, compteurDiagDroite = 0 ;
		PionGomuku pion ;
		for (int i = 0; i < tab.length; i++){
			compteurLigne = 0;
			compteurColonne = 0;
			//System.out.println("nbAlignement : "+nbAlignement);
			for (int i2 = 0; i2 < tab[i].length; i2++){

				// Verification des lignes
				if (tab[i][i2].pion == null){
					compteurLigne = 0;
				}
				else{
					pion = (PionGomuku) tab[i][i2].pion ;
					if (pion.j == j)
						compteurLigne++;
					else
						compteurLigne = 0;
				}

				if (compteurLigne >= lonAlignement){
					//System.out.println("Alignement ligne : ["+i+";"+i2+"]");
					nbAlignement++;
				}


				// Verification des colonnes
				if (tab[i2][i].pion == null){
					compteurColonne = 0;
				}
				else{
					pion = (PionGomuku) tab[i2][i].pion ;
					if (pion.j == j)
						compteurColonne++;
					else
						compteurColonne = 0;
				}

				if (compteurColonne >= lonAlignement){
					//System.out.println("Alignement colonne : ["+i2+";"+i+"]");
					nbAlignement++;
				}

				compteurDiagGauche = 0;
				compteurDiagDroite = 0;
				// Verification des diagonales
				for (int i3 = 0; i3 < lonAlignement ; i3++){

					// Verification de la diagonale remontante vers la gauche
					if (i -i3 >= 0 && i2 -i3 >= 0){ // Pour empecher des out of range
						if (tab[i-i3][i2-i3].pion == null){
							compteurDiagGauche = 0;
						}
						else{
							pion = (PionGomuku) tab[i-i3][i2-i3].pion ;
							if (pion.j == j){
								compteurDiagGauche++;
								//System.out.println("Cômpteur diag gauche : "+compteurDiagGauche + " pos : "+(i-i3)+","+(i2-i3));
							}
							else
								compteurDiagGauche = 0;
						}

						if (compteurDiagGauche >= lonAlignement){
							//System.out.println("Alignement diagonale gauche : ["+(i-i3)+";"+(i2-i3)+"]");
							nbAlignement++;
						}

					}else {
						compteurDiagGauche = 0;
					}


					// Verification de la diagonale remontante vers la droite
					if (i - i3 >= 0 && i2 + i3 < tab[i].length){ // Pour empecher des out of range
						if (tab[i-i3][i2+i3].pion == null){
							compteurDiagDroite = 0;
						}
						else{
							pion = (PionGomuku) tab[i-i3][i2+i3].pion ;
							if (pion.j==j){
								compteurDiagDroite++;
								//System.out.println("Cômpteur diag droite : "+compteurDiagDroite);
							}
							else
								compteurDiagDroite = 0;
						}

						if (compteurDiagDroite >= lonAlignement){
							//System.out.println("Alignement diagonale droite : ["+(i-i3)+";"+(i2+i3)+"]");
							nbAlignement++;
						}

					}else {
						compteurDiagDroite = 0;
					}
				}

			}
		} 
		return nbAlignement; 
	}

	public boolean ajouterPion(Pion p, int x, int y){
		// Fonction qui ajoutera un pion p aux coordonnées x,y en effectuant une verification
		return ajouterPion(p,tab[y][x]);
	}

	public boolean ajouterPion(Pion p, Case c){
		// Fonction qui ajoutera un pion p à la case en effectuant une verification
		if (! verificationPlacement(c))
			return false;
		c.pion = p;
		return true;
	}

	public boolean verificationPlacement(Case c){
		// Verifie que le placement est bien possible (la case est vide)
		if (c.pion == null)
			return true;
		return false;
	}


	



}