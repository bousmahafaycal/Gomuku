import java.util.*;

public  abstract class Plateau {

	final int  abs , ord ;
	Case [][] tab ; 
	boolean damier;

	

	public Plateau (int abs, int ord){ 
		this(abs, ord,false);
	}

	public Plateau (int abs, int ord, boolean damier){ // Dans le cas d'un plateau réctangulaire en ayant le choix d'un plateau coloré en damier..
		this.abs = abs;
		this.ord = ord;
		tab  = new Case [ord][abs];
		this.damier = damier;
		if (damier)
			colorerDamier();
		else 
			colorerBlanc();
	}

	public void colorerDamier(){
		boolean b = true;
		boolean x = true;
		for (int i = 0 ; i < tab.length ; i++){
			x = b;
			for (int i2 = 0; i2 < tab[i].length; i2++){
				tab[i][i2] = new Case (x,i2,i);
				x = !x;
			}
			b = !b;
		}
	}

	public void colorerBlanc(){
		//System.out.println("Colorer blnco");
		for (int i = 0 ; i < tab.length ; i++){
			for (int i2 = 0; i2 < tab[i].length; i2++){
				tab[i][i2] = new Case (true,i2,i);
			}
		}
	}

	

	public Case giveCase(int x, int y){
		//if (x >= abs || y>= ord)
		//	return null;
		return tab[x][y];
	}


	
	

	public boolean plateauComplet(){
		for (int i = 0 ; i < tab.length ; i++){
			for (int i2 = 0; i2 < tab[i].length; i2++){
				if (tab[i][i2].pion == null)
					return false;
			}
		}
		return true;
	}

	public abstract boolean verificationFin();
	// public abstract void compterPoints(); // On verra
	
	public ArrayList<Case> caseDispo (){
		// Renvoie toutes les cases sur lesquelles il n'y aucun pion.

		ArrayList<Case> array = new ArrayList<Case>();

		for (int i = 0; i < tab.length; i++){
			for (int i2 = 0; i2 < tab[i].length; i2++){
				if (tab[i][i2].pion == null)
					array.add(tab[i][i2]);

			}
		}

		return array;
	}
}