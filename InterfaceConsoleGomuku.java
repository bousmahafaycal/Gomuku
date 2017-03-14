public class InterfaceConsoleGomuku {

	static Sauvegarde sauv = new Sauvegarde("Donnees/Gomuku/save.f", "Donnees/Gomuku/Games/");

	public static void menuGomuku(){
		// Laisse l'utilisateur jouer ou revenir au menuPrincipal ou aller aux parametres
		boolean continuer = true;
		int a;
		String [] tab = {"0 - Quitter", "1 - Jouer de manière classique", "2 - Jouer à la variante","3 - Regarder les parties sauvegardées."};

		while (continuer) {
			a = menu("Menu Gomuku : ",tab,true);
			if (a == 0){
				continuer = false;
			}
			else if (a == 1)
				jouer(false);
			else if (a == 2)
				jouer(true);
			else if (a == 3)
				regarder();
			else{
				System.out.println("Merci de bien vouloir choisir un choix possible");
				System.out.println();
			}

		}
	}

	public static void regarder (){
		// Interface console pour choisir une partie .
		boolean continuer = true;
		int a ;
		String [] tab = new String[sauv.nom.size()+1];
		tab[0] = "0 - Revenir au menu précédent.";
		for (int i = 0; i < sauv.nom.size(); i++){
			tab[i+1] = (i+1)+" - "+sauv.nom.get(i);
		}

		while (continuer) {
			a = menu("Menu regarder : ",tab,true);
			if (a == 0)
				continuer = false;
			else if (a > 0 && a < tab.length)
				regarder (a-1);
			else
				System.out.println("Merci de bien vouloir choisir un choix possible.");
		}
	}

	public static void regarder (int nb){
		// interface console pour regarder une partie.
		PartieGomuku partie = new PartieGomuku(sauv,nb);
		boolean continuer = true;
		int i = 0;
		int a = 0;
		PlateauGomuku p;
		JoueurGomuku joueurActuel;
		String [] tab = {"0 - Retour au menu précédent","1 - Revenir au début de la partie", "2 - Revenir au coup précédent","3 - Aller au coup suivant","4 - Aller à la fin de la partie"};
		while (continuer) {
			if (i%2 == 1)
				joueurActuel = partie.j1;
			else
				joueurActuel = partie.j2;
			p = partie.getPlateau(i);

			affichePlateau(p);
			System.out.println();
			if (i > 0){
				System.out.println("Tour n°"+i+" : ");
				System.out.println("Le dernier coup qui a été joué est ["+partie.getCoup(i-1)[0]+";"+partie.getCoup(i-1)[1]+"] et a été joué par "+joueurActuel.pseudo);
				System.out.println();
			}

			System.out.println(partie.j1.pseudo+" : "+p.verificationAlignementJoueur(partie.j1)+" alignements");
			System.out.println(partie.j2.pseudo+" : "+p.verificationAlignementJoueur(partie.j2)+" alignements");
			System.out.println();


			a = menu("Que souhaitez vous faire ?",tab, false);
			if (a == 0)
				continuer = false;
			else if (a == 1)
				i=0;
			else if (a == 2 && i > 0)
				i--;
			else if (a == 2 && i == 0){
				System.out.println("Erreur : On est déja au début de la partie, il n'y a donc pas de coup précédent.");
				System.out.println();
			}
			else if (a == 3 && i < partie.liste.size()-1)
				i++;
			else if (a == 3 && i == partie.liste.size()-1){
				System.out.println("Erreur : On est déja à la fin de la partie, il n'y a donc pas de coup suivant.");
				System.out.println();
			}
			else if (a == 4)
				i = partie.liste.size()-1;
			else {
				System.out.println("Merci de bien vouloir choisir un choix possible.");
			}
		}
	}

	public static PlateauGomuku creePlateau (boolean variante){
		// Fonction qui crée un plateau
		boolean continuer;

		// On demande le nombre d'alignements nécéssaire si on joue à la variante.
		int nbAlignement = 0;
		if (variante){
			nbAlignement = getAlignement();
			System.out.println();
		}

		// On demande la taille du plateau
		int taille = getTaille();
		System.out.println();



		System.out.println("Merci de bien vouloir donner les informations concernant le premier joueur : ");
		JoueurGomuku j1 = getJoueur();
		System.out.println();
		System.out.println("Merci de bien vouloir donner les informations concernant le second joueur : ");
		JoueurGomuku j2 = getJoueur();


		PlateauGomuku plateau = new PlateauGomuku(taille, taille, variante, nbAlignement, j1, j2);
		//System.out.println("Plateau "+plateau);

		return plateau;
	}
	public static void parametres(){
		// Ecris les parametres de la partie (nombre de cases) dans un fichier
	}

	public static void jouer (boolean variante){
		// Lis le fichier parametre et le crée si il n'existe pas ou qu'il est illisible.
		// Créer une partie à partir de ces parametres
		// Demander les joueurs avec la fonction getJoueur()

		PlateauGomuku plateau = creePlateau(variante);
		JoueurGomuku j1 = plateau.j1;
		JoueurGomuku j2 = plateau.j2;
		JoueurGomuku joueurActuel;
		JoueurGomuku vainqueur;
		int i = 0;
		boolean continuer = true;

		System.out.println("--------------------------");
		System.out.println("Nous allons joueur au Gomuku.");
		if (variante){
			System.out.println("Il faut "+plateau.nbAlignement+" alignements pour gagner.");
			System.out.println("Si le plateau est plein sans que le nombre d'alignement requis ait été atteint,  celui qui aura fait le plus d'alignements aura gagné.");
		}

		else
			System.out.println("Le jeu continue jusqu'à ce que le plateau soit plein.");
		System.out.println();

		PartieGomuku partie = new PartieGomuku(j1,j2, plateau.abs,variante,plateau.nbAlignement);

		while (continuer){
			affichePlateau(plateau);
			System.out.println();

			if (i %2 == 0)
				joueurActuel = j1;
			else
				joueurActuel = j2;
			System.out.println("C'est à "+joueurActuel.pseudo+" de jouer : ");
			System.out.println("Tour n°"+(i+1)+" :");
			faisCoup(plateau,joueurActuel,partie);

			i++;
			if(plateau.verificationFin())
				continuer = false;
		}
		System.out.println();
		System.out.println("Plateau final : ");
		affichePlateau(plateau);
		System.out.println("--------------------------");
		System.out.println();
		System.out.println();
		System.out.println("--------------------------");
		vainqueur = plateau.recupereGagnant();
		System.out.println("La partie s'est finie au bout de "+i+" tours.");
		System.out.println(j1.pseudo+" : "+plateau.verificationAlignementJoueur(j1)+" alignements");
		System.out.println(j2.pseudo+" : "+plateau.verificationAlignementJoueur(j2)+" alignements");
		if (vainqueur != null)
			System.out.println("Le vainqueur est "+vainqueur.pseudo+".");
		else
			System.out.println("La partie est nulle.");
		System.out.println();
		System.out.println("Souhaitez vous sauvegarder la partie ? (O/n)");
		String save = Outils.getString();
		if (! save.equals("n")){
			System.out.println("La partie a été sauvegardée. Son nom est "+partie.sauvegarder(sauv));

		}else {
			System.out.println("Partie non sauvegardée.");
		}
		System.out.println("--------------------------");
		System.out.println();

	}



	public static void affichePlateau(PlateauGomuku p){
		PionGomuku pion ;
		boolean refaire = true;
		int a = p.tab[0].length;

		for (int i = -1; i < p.tab.length; i++){

			if (! refaire){
				if ((i+1)/10 == 0)
					System.out.print((i+1)+" |");
				else
					System.out.print((i+1)+"|");
				a = p.tab[i].length;
			} else
				System.out.print("   ");

			for (int i2 = 0 ; i2 < a; i2++){
				if (refaire){
					if ((i2+1)/10 == 0)
						System.out.print((i2+1)+" ");
					else
						System.out.print((i2+1));

				}else {
					if (p.tab[i][i2].pion == null)
						System.out.print("\u25FC ");
					else{
						pion = (PionGomuku) p.tab[i][i2].pion;
						if (pion.j == p.j1)
							System.out.print("\u265F ");
						else if (pion.j == p.j2)
							System.out.print("\u2659 ");
					}
				}




			}
			if (refaire)
				refaire = false;
			System.out.println("");
		}
	}


	// -------------------------------
	// -------------------------------
	// Fonctions de soutient aux fonctions principales

	public static int getTaille(){
		// Fonction demandant et retournant la taille du plateau.
		int taille = 0;
		boolean continuer = true;
		while (continuer){
			System.out.println("Donnez la taille du plateau sur lequel vous souhaitez jouer : ");
			taille = Outils.getInt();
			if (taille >= 0)
				continuer=false;
			else {
				System.out.println("Merci de donner une taille positive.");
			}
		}
		return taille;
	}

	public static int getAlignement(){
		// Fonction demandant et retournant le nombre d'alignement nécéssaire à la victoire.
		int taille = 0;
		boolean continuer = true;
		while (continuer){
			System.out.println("Donnez le nombre d'alignements nécéssaire pour obtenir une victoire : ");
			taille = Outils.getInt();
			if (taille >= 0)
				continuer=false;
			else {
				System.out.println("Merci de donner un nombre d'alignement positif.");
			}
		}
		return taille;
	}

	public static int getOrdonnee(){
		// Fonction demandant et retournant l'ordonnee.
		int ordonnee = 0;
		boolean continuer = true;
		while (continuer){
			System.out.println("Donnez l'ordonnee (commençant à 1) de la case ou vous souhaitez mettre votre pion : ");
			ordonnee = Outils.getInt();
			if (ordonnee >= 0)
				continuer=false;
			else {
				System.out.println("Merci de donner une ordonnee possible.");
			}
		}
		return ordonnee - 1;
	}

	public static int getAbcisse(){
		// Fonction demandant et retournant l'abcisse.
		int abcisse = 0;
		boolean continuer = true;
		while (continuer){
			System.out.println("Donnez l'abcisse (commençant à 1) de la case ou vous souhaitez mettre votre pion : ");
			abcisse = Outils.getInt();
			if (abcisse >= 0)
				continuer=false;
			else {
				System.out.println("Merci de donner une abcisse possible.");
			}
		}
		return abcisse - 1;
	}

	public static JoueurGomuku getJoueur(){
		// Fonction qui permet de renvoyer un JoueurGomuku en ayant déterminer si le joueur est une IA ou un humain.

		String pseudo = getPseudo();
		System.out.println();
		boolean automatique = getAutomatique();
		JoueurGomuku j = new JoueurGomuku(pseudo, automatique);
		return j;
	}


	public static boolean getAutomatique(){
		// Fonction qui renvoie un booleen pour savoir si le joueur doit etre gérer par l'ordi ou pas.
		boolean o = true;
		boolean continuer = true;
		while (continuer){
			String [] tab = {"1 - Le joueur doit être gérer par l'ordi.","2 - Le joueur sera géré par un humain."};
			int ordre = menu("Choissiez si le joueur doit etre gérer par l'ordi ou pas : ",tab, false);

			if (ordre == 1){
				continuer=false;
				o = true;
			}
			else if (ordre == 2){
				continuer = false;
				o = false;
			}
			else {
				System.out.println("Merci de choisir un choix possible.");
			}
		}
		return o;
	}

	public static String getPseudo(){
		// Fonction demandant et renvoyant le pseudo du joueur
		System.out.println("Donnez le pseudo du joueur : ");
		String question = Outils.getString();
		return question;
	}

	public static void faisCoup(PlateauGomuku p ,JoueurGomuku j, PartieGomuku partie){
		// Fonction qui va demander à l'utilisateur un coup, vérifier qu'il est possible, si ce n'est pas le cas demander à l'utilisateur un autre coup.
		// Si le joueur est automatique, alors on appelle la fonction permettant de réaliser un coup automatique.
		if (j.automatique){
			Case c  = j.mouvement(p);
			partie.ajouterCoup(c.x,c.y);
			System.out.println("Un coup automatique pour "+j.pseudo+" a été joué");
			System.out.println("Celui ci est ["+(c.x+1)+";"+(c.y+1)+"].");
			System.out.println();
			return;
		}

		boolean continuer = true;
		int abs = 0, ord =0;
		while (continuer) {
			abs = getAbcisse();
			ord = getOrdonnee();

			if (j.mouvement(p,abs,ord)){
				continuer = false;
			}
			else {
				System.out.println("Ce coup n'est pas possible, veuillez réessayer.");
			}

		}
		partie.ajouterCoup(abs,ord);
	}

	public static int menu (String phrase, String [] choix, boolean barre){
		// Fonction affichant un menu de manière structurée et renvoyant le choix de l'utilisateur.
		if (barre)
			System.out.println("--------------------------");

		System.out.println(phrase);
		for (int i = 0; i < choix.length; i++){
			System.out.println(choix[i]);
		}
		System.out.print("Votre choix : ");
		int a = Outils.getInt();
		if (barre)
			System.out.println("--------------------------");
		System.out.println();
		return a;
	}
}
