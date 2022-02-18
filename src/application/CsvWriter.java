package application;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CsvWriter {

	private String texte;
	public void setTexte(String texte) {
		this.texte = texte;
	}
	public void createCsvFile(){
		File file = new File("Resultats.csv");
		// create FileWriter object with file as parameter
        FileWriter outputfile;
		try {
			outputfile = new FileWriter(file);
			
		// create CSVWriter object filewriter object as parameter
        CSVWriter writer = new CSVWriter(outputfile,CSVWriter.DEFAULT_SEPARATOR,CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.DEFAULT_ESCAPE_CHARACTER,CSVWriter.DEFAULT_LINE_END);
  
        // adding header to csv
        String[] header = { "Id", "Pays", "Nom et Prenom" };
        writer.writeNext(header, false);
        String [] champions=this.texte.split("\n");
		String [] elements;
		for(int i=1;i<=champions.length;i++){
			elements=champions[i-1].split(" ");
			String[] data = { String.valueOf(i), elements[0], elements[1]+" "+elements[2]};
	        writer.writeNext(data);
		}
		// closing writer connection
        writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
       
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("INFOS");
		alert.setHeaderText("Fichier crée");
		alert.setContentText("un fichier xlsx a bien éte crée");
		alert.showAndWait();
		
	}
}
