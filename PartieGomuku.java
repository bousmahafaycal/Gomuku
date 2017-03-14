import java.util.ArrayList;
public class PartieGomuku {
	// Classe qui permet de tenir à jour l'historique des coups d'une partie.

	ArrayList<PlateauGomuku> liste = new ArrayList<PlateauGomuku> () ;
	ArrayList<Integer[]>listeCoup = new ArrayList<Integer[]>();
	JoueurGomuku j1, j2;
	int nbAlignement;
	boolean variante;
	int taille;

	public PartieGomuku (Sauvegarde sauv, int a ){



		if (a >= 0 && a < sauv.nom.size())
			lire(sauv,sauv.nom.get(a));

		
	}

	public PartieGomuku(JoueurGomuku j1, JoueurGomuku j2, int taille, boolean variante){
		this (j1,j2,taille,variante,1);
	}

	public PartieGomuku(JoueurGomuku j1, JoueurGomuku j2, int taille, boolean variante, int nbAlignement){
		this.j1 = j1;
		this.j2 = j2;
		this.nbAlignement = nbAlignement;
		this.variante = variante;
		this.taille = taille;

		

	}


	public String sauvegarder(Sauvegarde sauv){
		// Sauvegarde j1 et j2, 
		String chaine = Outils.constitueBalise("NbAlignement",String.valueOf(nbAlignement))+"\n";
		chaine += Outils.constitueBalise("Taille",String.valueOf(taille))+"\n";
		chaine += Outils.constitueBalise("Variante",String.valueOf(variante))+"\n";
		chaine += j1.sauvegarder()+"\n";
		chaine += j2.sauvegarder()+"\n";
		String chaine2;

		for (int i = 0; i < listeCoup.size(); i++){
			chaine2 = "";
			chaine2 += Outils.constitueBalise("Abs",String.valueOf(listeCoup.get(i)[0]));
			chaine2 += Outils.constitueBalise("Ord",String.valueOf(listeCoup.get(i)[1]));
			chaine2 = Outils.constitueBalise("Coup",chaine2);
			chaine += chaine2 + "\n";
		}


		String nom = Outils.getDate()+ " " +j1.pseudo+ "_"+j2.pseudo;
		boolean continuer = true;
		int i = 0;
		String nom2 = nom;
		while (continuer) {
			if (i == 0)
				continuer = ! sauv.ajouter(nom);
			else {
				nom2 = nom + " ("+i+")";
				continuer = ! sauv.ajouter(nom2);
			}

			i++;
			
		}
		
		Outils.ecrireFichier(sauv.getAdresseDossier()+nom2, chaine);
		return nom;
	}

	public boolean lire (Sauvegarde sauv, String nom){
		// Lire et ouvrir une partie.
		if (! Outils.testPresence(sauv.getAdresseDossier()+nom))
			return false;
		String chaine = Outils.lireFichier(sauv.getAdresseDossier()+nom);
		variante = Boolean.parseBoolean(Outils.recupereBaliseAuto(chaine,"Variante",1,"Variante",false));
		taille = Integer.parseInt(Outils.recupereBaliseAuto(chaine,"Taille",1,"Taille",false));
		nbAlignement = Integer.parseInt(Outils.recupereBaliseAuto(chaine,"NbAlignement",1,"NbAlignement",false));

		String joueur = Outils.recupereBaliseAuto(chaine,"JoueurGomuku",1,"JoueurGomuku",false);
		j1 = new JoueurGomuku("toto");
		j1.ouvrir(joueur);

		joueur = Outils.recupereBaliseAuto(chaine,"JoueurGomuku",2,"JoueurGomuku",false);
		j2 = new JoueurGomuku("toto");
		j2.ouvrir(joueur);


		int a = Outils.compter(chaine,"<Coup");
		Integer coo[] ;
		String chaine2;

		for (int i  = 0 ; i< a ; i++){
			chaine2 = Outils.recupereBaliseAuto(chaine,"Coup",(i+1),"Coup",false);
			coo = new Integer[2];
			coo[0] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Abs",1,"Abs",false));
			coo[1] = Integer.valueOf(Outils.recupereBaliseAuto(chaine2,"Ord",1,"Ord",false));
			listeCoup.add(coo);

		}

		// Lire les coups.
		construirePlateaux();

		return true;
	}

	public void ajouterCoup(int abs, int ord){
		// Ajoute et crée le plateau correspondant.
		//int tab[] = {abs,ord};
		Integer [] tab2 = {abs,ord};
		listeCoup.add(tab2);
		construirePlateau(listeCoup.size()-1);

	}

	public PlateauGomuku construirePlateau(int coup){
		// Construit un plaateau jusqu'au coup n°coup, coup doit etre compris entre 0 inclus et listeCoup.size() exclu.
		PlateauGomuku p = new PlateauGomuku(taille, taille, variante, nbAlignement, j1, j2);
		if (coup < 0 || coup >= listeCoup.size())
			return null;
		JoueurGomuku joueurActuel;

		//System.out.println("Coup :" +coup);

		for (int i = 0; i <= coup; i++){
			if (i%2 == 0)
				joueurActuel = j1;
			else 
				joueurActuel = j2;
			//System.out.println("0 : "+listeCoup.get(i)[0]+", 1 : "+listeCoup.get(i)[1]);
			//System.out.println("Joueur actuel : "+joueurActuel.pseudo);
			joueurActuel.mouvement(p,listeCoup.get(i)[0],listeCoup.get(i)[1]);
		}
		return p;
	}


	public void construirePlateaux(){
		// A partir de la liste des coups, construire la liste de plateau.
		//System.out.println("Liste coup .size "+listeCoup.size());
		PlateauGomuku p = new PlateauGomuku(taille, taille, variante, nbAlignement, j1, j2);
		liste.add(p);
		for (int i =  0;  i < listeCoup.size(); i++){
			liste.add(construirePlateau(i));
		}
	}

	public PlateauGomuku getPlateau(int coup){
		// Renvoie le plateau Gomuku tel qu'il est avant le coup n°coup jouer par les blancs ou les noirs.
		//System.out.println("Liste size : "+liste.size()+" coup = "+coup);
		return liste.get(coup);
	}

	public Integer[] getCoup (int coup){
		Integer[] tab = listeCoup.get(coup);
		tab[0]++;
		tab[1]++;
		return tab;
	}

}