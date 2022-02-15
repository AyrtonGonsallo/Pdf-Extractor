package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Controller implements Initializable{
	private int id=1;
	private boolean isImport=false;
	@FXML 
	private ChoiceBox<String> format;
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
		format.getItems().add("xml");
		format.getItems().add("csv");
		
	}
	
	public void exportation(){
		String value=format.getValue();
		if(value!=null && isImport==true){
			Text text_2 = new Text("document pdf nº"+(id-1)+" exporté en "+value+"\n");
			text_2.setFill(Color.GREEN);
			text_2.setFont(Font.font("Verdana", 25));
			texte.getChildren().add(text_2);
			isImport=false;
		}
		
	}
	public void effacement(){
		id=1;
		texte.getChildren().removeAll(texte.getChildren());
		format.setValue(null);
	}
	public void importation(){
		Text text = new Text("document pdf nº"+id+" importé\n");
		text.setFill(Color.BLUE);
		text.setFont(Font.font("Verdana", 25));
		texte.getChildren().add(text);
		id++;
		isImport=true;
		
	}
}


