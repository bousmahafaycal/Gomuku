public class PionGomuku extends Pion{
	Joueur j;

	public PionGomuku (Case c, Joueur j){
		super(c);
		this.j = j;
	}

	public boolean isValid(Plateau p, Case  arrivee){
		// verification que la case d'arrivée est vide, pas très utile surtout pour que l'héritage fonctionne.
		return verifieAriveeVide(arrivee);
		
	}
}