import java.util.ArrayList ;
public class Sauvegarde {
	// Classe permettant de gérer la sauegarde de fichiers de manière à ce qu'ils ne s'écrasent pas.

	//ArrayList<String> adresses = new ArrayList<String> ();
	ArrayList<String> nom  = new ArrayList<String>();
	String adresse, adresseDossier;


	public Sauvegarde (String adresse,String adresseDossier){
		Outils.creeChemin(adresseDossier);
		this.adresse = adresse;
		this.adresseDossier = adresseDossier;
		lire();
	}

	public void sauvegarder(){
		// Cette fonction sauvegarde cet objet dans un fichier.
		String chaine = Outils.constitueBalise("AdresseDossier",adresseDossier)+"\n";
		for (int i = 0; i < nom.size(); i++){
			//adresses.add(Outils.recupereBaliseAuto(chaine,"Adresse",(i+1),"Adresse",false));
			//chaine += Outils.constitueBalise("Adresse",adresse.get(i));
			chaine += Outils.constitueBalise("Nom", nom.get(i))+"\n";
		}
		Outils.ecrireFichier(adresse,chaine);
	}
	
	public void lire(){
		// Cette fonction lit le fichier de sauvegarde qui tient le registre de tout ce qui est nécéssaire et le charge dans cet objet.

		// Tester la presence du fichier
		if (! Outils.testPresence(adresse))
			return;
		String chaine = Outils.lireFichier(adresse);
		adresseDossier = Outils.recupereBaliseAuto(chaine,"AdresseDossier",1,"AdresseDossier",false);
		int nb = Outils.compter(chaine,"<Nom");
		for (int i = 0; i < nb; i++){
			//adresses.add(Outils.recupereBaliseAuto(chaine,"Adresse",(i+1),"Adresse",false));
			nom.add(Outils.recupereBaliseAuto(chaine,"Nom",(i+1),"Nom",false));
		}
	}

	public boolean ajouter (String name){
		// Cette fonction ajoute un nom de sauvegarde si celui-ci existe.

		//System.out.println("ajouter");
		if (!verification(name))
			return false;
		//System.out.println("ajouter 2");
		nom.add(name);
		sauvegarder();
		return true;
	}

	public boolean verification(String name){
		// Cette fonction permet de vérifier si le nom existe déja ou pas.

		//for (int i = 0; i < nom.size() ;i++)
			//System.out.println(i+" : "+nom.get(i));
		//System.out.println("size : "+nom.size());
		//System.out.println("contains "+nom.contains(name));

		return ! nom.contains(name);

	}

	public boolean supprimer (String name){
		// Cette fonction permet de supprimer un nom de sauvegarde, return true si cela a été fait, false sinon.
		for (int i = 0; i < nom.size(); i++){
			if (nom.get(i).equals(name)){
				nom.remove(i);
				sauvegarder();
				return true;
			}
			
		}
		return false;
		
	}


	public String getAdresseDossier (){
		return adresseDossier;
	}






}