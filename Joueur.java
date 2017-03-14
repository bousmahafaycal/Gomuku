public class Joueur{
	String pseudo;

	public Joueur(String pseudo){
		this.pseudo = pseudo;
	}

	public String sauvegarder(){
		return Outils.constitueBalise("Pseudo",pseudo);
	}

	public void ouvrir(String chaine){
		pseudo = Outils.recupereBaliseAuto(chaine,"Pseudo",1,"Pseudo",false);
	}

}