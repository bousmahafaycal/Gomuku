public class Case {
	boolean blanc;
	public int x, y;
	Pion pion = null;

	public Case (boolean b, int x, int y){
		this(b,x,y,null);
	}

	public Case (boolean b, int x, int y, Pion pion){
		blanc = b;
		this.pion = pion;
		this.x = x;
		this.y = y;
	}


	// Vérifie que la case est vide.
	public boolean estVide (){
		return (pion == null);
	}

	// Récupere un pion
	public Pion getPion(){
		return pion;
	}

	// Supprime un pion
	public void removePion (){
		pion = null;
	}

	// Met un pion dans cette case
	public void fill (Pion p){
		pion = p;
	}
}
