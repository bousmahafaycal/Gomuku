import java.util.*;

public class JoueurGomuku extends Joueur {
	boolean automatique ;

	public JoueurGomuku(String pseudo){
		this(pseudo, false);
	}

	public JoueurGomuku (String pseudo , boolean automatique){
		super(pseudo);
		this.automatique = automatique;

	}

	public Case  mouvement (PlateauGomuku p) { // mouvementAuto(Plateau p){
		// Joue un mouvement automatique et aléatoire dans le plateau p si cela est possible
		ArrayList<Case> array = p.caseDispo();
		if (array.size() == 0)
			return null;
		int a = (int)( Math.random()*array.size()); 
		PionGomuku pion = new PionGomuku (array.get(a),this);
		array.get(a).pion = pion;
		return array.get(a);
	}

	public boolean mouvement (PlateauGomuku p, int abs, int ord){
		// Joue un mouvement d'un joueur si cela est possible.
		if (ord >= p.tab.length)
			return false;

		if (abs >= p.tab[0].length)
			return false;

		PionGomuku pion = new PionGomuku (p.tab[ord][abs],this);

		return p.ajouterPion(pion,p.tab[ord][abs]);
	}

	public String sauvegarder(){
		String chaine = "";
		chaine += super.sauvegarder();
		chaine += Outils.constitueBalise("Automatique",String.valueOf(automatique));
		chaine = Outils.constitueBalise("JoueurGomuku",chaine);
		return chaine;
	}

	public void ouvrir (String chaine){
		automatique = Boolean.parseBoolean(Outils.recupereBaliseAuto(chaine,"Automatique",1,"Automatique",false));
		super.ouvrir(chaine);
	}

}