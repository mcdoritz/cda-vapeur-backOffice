package com.vapeur.config;

public class Debug {
	
	/*
	 * Cette propriété active ou désactive la méthode PRLN, ce qui active ou non les messages dans la console.
	 */
	private static Boolean prln = true;
	
	public static void prln(String message) {
		if(prln) {
			System.out.println(message);
		}
			
	}
	
	public static void prln(int entier) {
		if(prln) {
			System.out.println(entier);
		}
		
	}
	
	public static void prln(float flottant) {
		if(prln) {
			System.out.println(flottant);
		}
		
	}
	
	public static void prln(Boolean boulé1) {
		if(prln) {
			System.out.println(boulé1);
		}
		
	}
	
	public static void bddSays(String crud, Boolean status, int objectId, String objectInfos) {
		switch (crud){
			case "create":
				if(status) {
					prln("Objet " + objectId + " " + objectInfos + " a été créé dans la BDD");
				}else {
					prln("Objet n'a PAS été créé dans la BDD");
				}
			break;
			
			case "read":
				if(status) {
					prln("Objet " + objectId + " " + objectInfos + " a été trouvé dans la BDD");
				}else {
					prln("Objet " + objectId + " n'a PAS été trouvé dans la BDD");
				}
			break;
			
			case "readAll":
				if(status) {
					prln("Une liste de " + objectId + " objets a été trouvé dans la BDD");
				}else {
					prln("Aucun objet trouvé dans la BDD");
				}
			break;
			
			case "update":
				if(status) {
					prln("Objet " + objectId + " " + objectInfos + " a été mis à jour dans la BDD");
				}else {
					prln("Objet " + objectId + " n'a PAS été mis à jour dans la BDD");
				}
			break;
			
			case "delete":
				if(status) {
					prln("Objet " + objectId + " a été supprimé de la BDD");
				}else {
					prln("Objet " + objectId + " n'a PAS été supprimé de la BDD");
				}
			break;
			
		}
		
		
		
	}

}
