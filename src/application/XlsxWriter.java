package application;

import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class XlsxWriter {
	private String texte;
	public void setTexte(String texte) {
		this.texte = texte;
	}
	public void createXlsxFile(){
		XSSFWorkbook workbook=new XSSFWorkbook();
		XSSFSheet sheet=workbook.createSheet("Resultats");
		XSSFRow entete=sheet.createRow(0);
		entete.createCell(0).setCellValue("Id");
		entete.createCell(1).setCellValue("Pays");
		entete.createCell(2).setCellValue("Nom et Prenom");
		String [] champions=this.texte.split("\n");
		String [] elements;
		for(int i=1;i<=champions.length;i++){
			elements=champions[i-1].split(" ");
			XSSFRow ligne=sheet.createRow(i);
			ligne.createCell(0).setCellValue(i);
			ligne.createCell(1).setCellValue(elements[0]);
			ligne.createCell(2).setCellValue(elements[1]+" "+elements[2]);
		}
		
		try {
			FileOutputStream fos=new FileOutputStream("Resultats.xlsx");
			workbook.write(fos);
			workbook.close();
			fos.close();
		} catch (Exception e) {
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
