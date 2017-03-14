import java.util.ArrayList;

public abstract class Pion {
	Case c;
	

	public Pion (Case c){
		this.c = c;
	}

	public abstract boolean isValid(Plateau p, Case  arrivee);
		// verification que le mouvement vers la case d'arivée est valide.
	

	
	public boolean verifieAriveeVide(Case arrivee){
		// Vérifie que la case d'arrivée est vide
		return arrivee.estVide();
	}

}