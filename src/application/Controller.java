package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

public class Controller implements Initializable{
	private int id=1;
	private PdfReader pdfReader=new PdfReader();
	private XlsxWriter writer=new XlsxWriter();
	private CsvWriter csvWriter=new CsvWriter();
	private boolean isImport=false;
	@FXML 
	private ChoiceBox<String> format;
	@FXML 
	private ChoiceBox<String> typeFichier;
	@FXML 
	private ChoiceBox<String> sexe;
	@FXML
	private Button importer;
	@FXML
	private Button exporter;
	@FXML
	private Button effacer;
	@FXML
	private TextFlow texte;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		format.getItems().add("xlsx");
		format.getItems().add("csv");
		typeFichier.getItems().add("fédération francaise");
		typeFichier.getItems().add("fédération internationale");
		sexe.getItems().add("M");
		sexe.getItems().add("F");
		
	}
	
	public void exportation(){
		String value=format.getValue();
		if(value!=null && isImport==true){
			Text text_1 = new Text("Document pdf nº"+(id-1)+" exporté en "+value+"\n");
			text_1.setFill(Color.GREEN);
			text_1.setFont(Font.font("Verdana", 25));
			texte.getChildren().add(text_1);
			
			if(value.equalsIgnoreCase("xlsx") && isImport==true){
				writer.createXlsxFile();
				writer.setTexte(null);
				writer.setSexe(null);
				writer.setTypeFichier(null);
				isImport=false;
			}
			else if(value.equalsIgnoreCase("csv") && isImport==true){
				csvWriter.createCsvFile();
				csvWriter.setTexte(null);
				csvWriter.setSexe(null);
				csvWriter.setTypeFichier(null);
				isImport=false;
			}
		}
		
		
	}
	public void effacement(){
		id=1;
		texte.getChildren().removeAll(texte.getChildren());
		format.setValue(null);
		typeFichier.setValue(null);
		sexe.setValue(null);
	}
	public void importation(){
		FileChooser fc=new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("PDF file","*pdf"));
		File file=fc.showOpenDialog(Main.getPrimaryStage());
		if(file!=null && typeFichier.getValue()!=null && sexe.getValue()!=null){
			Text text = new Text("Document pdf nº"+id+" importé\n");
			text.setFill(Color.BLUE);
			text.setFont(Font.font("Verdana", 25));
			texte.getChildren().add(text);
			Text text2 = new Text("Titre: "+file.getName()+"\n");
			text2.setFill(Color.RED);
			text2.setFont(Font.font("Verdana", 20));
			texte.getChildren().add(text2);
			pdfReader.setFile(file);
			id++;
			isImport=true;
			String res="";
			Text text_2; 
			try {
				if(this.typeFichier.getValue().equalsIgnoreCase("fédération francaise")){
					Text text4 = new Text("Document de la federation francaise\n");
					text4.setFill(Color.BLUE);
					text4.setFont(Font.font("Verdana", 15));
					texte.getChildren().add(text4);
					res = pdfReader.getFrenchResults();
				}else if(this.typeFichier.getValue().equalsIgnoreCase("fédération internationale")){
					Text text4 = new Text("Document de la federation internationale\n");
					text4.setFill(Color.BLUE);
					text4.setFont(Font.font("Verdana", 15));
					texte.getChildren().add(text4);
					res = pdfReader.getFrenchResults();
					res = pdfReader.getInternationalResults();
				}
				Text text_3 = new Text("Traitement:\n");
				text_3.setFill(Color.ORANGE);
				text_3.setFont(Font.font("Verdana", 15));
				texte.getChildren().add(text_3);
				csvWriter.setTexte(res);
				csvWriter.setSexe(this.sexe.getValue());
				csvWriter.setTypeFichier(this.typeFichier.getValue());
				writer.setTexte(res);
				writer.setSexe(this.sexe.getValue());
				writer.setTypeFichier(this.typeFichier.getValue());
				text_2 = new Text(res+"\n");
				text_2.setFill(Color.BLACK);
				text_2.setFont(Font.font("Verdana", 10));
				texte.getChildren().add(text_2);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pdfReader.setFile(null);
		}else{
			Text text5 = new Text("Erreur:\n-Type de fichier ou sexe non saisit\n-Fichier non uploadé\n");
			text5.setFill(Color.RED);
			text5.setFont(Font.font("Verdana", 10));
			texte.getChildren().add(text5);
		}
		
		
		
	}
}


