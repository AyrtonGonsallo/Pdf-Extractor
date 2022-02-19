package application;



import java.io.File;
import java.io.FileWriter;


import com.opencsv.CSVWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CsvWriter {

	private String texte;
	private String sexe;
	private String typeFichier;
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public void setTypeFichier(String typeFichier) {
		this.typeFichier = typeFichier;
	}
	public void setTexte(String texte) {
		this.texte = texte;
	}
	public void createCsvFile(){
		if(this.typeFichier.equalsIgnoreCase("fédération francaise")){
			File file = new File("Resultats_Fed_Fra.csv");
			FileWriter outputfile;
			try {
				outputfile = new FileWriter(file);
				CSVWriter writer = new CSVWriter(outputfile,CSVWriter.DEFAULT_SEPARATOR,CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.DEFAULT_ESCAPE_CHARACTER,CSVWriter.DEFAULT_LINE_END);
		        String[] header = { "Nom", "Sexe : MF", "abreviation pays","rang","categorie de poids","club (facultatif)" };
		        writer.writeNext(header);
				String [] resultatsPage=this.texte.split("\n---\n");
				
				for(int ip=0;ip<resultatsPage.length;ip++){
					String [] champions=resultatsPage[ip].split("\n");
					for(int ic=2;ic<champions.length;ic++){
						String []elements=champions[ic].split("   ");
						if(elements.length>1){
							String[] data = { elements[1],this.sexe,"FRA",elements[0],champions[1].replace("kg", ""),elements[2]};
					        writer.writeNext(data);
							
						}
					}
				}
			writer.close();
			}catch (Exception e) {
				// TODO: handle exception
			}	
		}else if(this.typeFichier.equalsIgnoreCase("fédération internationale")){
			File file = new File("Resultats_Fed_Int.csv");
			FileWriter outputfile;
			try {
				outputfile = new FileWriter(file);
				CSVWriter writer = new CSVWriter(outputfile,CSVWriter.DEFAULT_SEPARATOR,CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.DEFAULT_ESCAPE_CHARACTER,CSVWriter.DEFAULT_LINE_END);
		        String[] header = { "Nom", "Sexe : MF", "abreviation pays","rang","categorie de poids" };
		        writer.writeNext(header);
				String [] resultatsPage=this.texte.split("\n---\n");
				
				for(int ip=0;ip<resultatsPage.length;ip++){
					String [] champions=resultatsPage[ip].split("\n");
					for(int ic=2;ic<champions.length;ic++){
						String []elements=champions[ic].split("   ");
						if(elements.length>1){
							String[] data = { elements[1],this.sexe,elements[2],elements[0],champions[1].replace("kg", "")};
					        writer.writeNext(data);
							
						}
					}
				}
				writer.close();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("INFOS");
		alert.setHeaderText("Fichier crée");
		alert.setContentText("un fichier csv a bien éte crée");
		alert.showAndWait();
		
	}
}
