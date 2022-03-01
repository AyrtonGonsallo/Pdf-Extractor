package application;

import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class XlsxWriter {
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
	public void createXlsxFile(){
		XSSFWorkbook workbook=new XSSFWorkbook();
		if(this.typeFichier.equalsIgnoreCase("fédération francaise")){
			XSSFSheet sheet=workbook.createSheet("Resultats_Fed_Fra");
			XSSFRow entete=sheet.createRow(0);
			entete.createCell(0).setCellValue("Nom");
			entete.createCell(1).setCellValue("Sexe : MF");
			entete.createCell(2).setCellValue("abreviation pays");
			entete.createCell(3).setCellValue("rang");
			entete.createCell(4).setCellValue("categorie de poids");
			entete.createCell(5).setCellValue("club (facultatif)");
			String [] resultatsPage=this.texte.split("\n---\n");
			int page=1;
			for(int ip=0;ip<resultatsPage.length;ip++){
				String [] champions=resultatsPage[ip].split("\n");
				for(int ic=2;ic<champions.length;ic++){
					String []elements=champions[ic].split("   ");
					if(elements.length==3){
						champions[1]=champions[1].replace("kg", "");
						champions[1]=champions[1].replace("(", "");
						champions[1]=champions[1].replace(")", "");
						champions[1]=champions[1].replaceAll("\\s{1,}","");
						XSSFRow ligne=sheet.createRow(page);
						ligne.createCell(0).setCellValue(elements[1].replaceAll("\\s{1,}",""));
						ligne.createCell(1).setCellValue(this.sexe);
						ligne.createCell(2).setCellValue("FRA");
						ligne.createCell(3).setCellValue(elements[0]);
						if(champions[1].startsWith("+")==false && champions[1].startsWith("-")==false){
							champions[1]="-"+champions[1];
						}
						ligne.createCell(4).setCellValue(champions[1]);
						ligne.createCell(5).setCellValue(elements[2]);
						page++;
					}
					
				}
				
			}
			
			try {
				FileOutputStream fos=new FileOutputStream("Resultats_Fed_Fra.xlsx");
				workbook.write(fos);
				workbook.close();
				fos.close();
				System.out.println("ecrit");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			page=0;
		}else if(this.typeFichier.equalsIgnoreCase("fédération internationale")){
			XSSFSheet sheet=workbook.createSheet("Resultats_Fed_Int");
			XSSFRow entete=sheet.createRow(0);
			entete.createCell(0).setCellValue("Nom");
			entete.createCell(1).setCellValue("Sexe : MF");
			entete.createCell(2).setCellValue("abreviation pays");
			entete.createCell(3).setCellValue("rang");
			entete.createCell(4).setCellValue("categorie de poids");
			String [] resultatsPage=this.texte.split("---\n");
			int page=1;
			for(int ip=0;ip<resultatsPage.length;ip++){
				String [] champions=resultatsPage[ip].split("\n");
				for(int ic=2;ic<champions.length;ic++){
					String []elements=champions[ic].split("   ");
					if(elements.length==3){
						XSSFRow ligne=sheet.createRow(page);
						champions[1]=champions[1].replace("kg", "");
						champions[1]=champions[1].replace("(", "");
						champions[1]=champions[1].replace(")", "");
						champions[1]=champions[1].replaceAll("\\s{1,}","");
						ligne.createCell(0).setCellValue(elements[1].replaceAll("\\s{1,}",""));
						ligne.createCell(1).setCellValue(this.sexe);
						ligne.createCell(2).setCellValue(elements[2]);
						ligne.createCell(3).setCellValue(elements[0]);
						if(champions[1].startsWith("+")==false  && champions[1].startsWith("-")==false){
							champions[1]="-"+champions[1];
						}
						ligne.createCell(4).setCellValue(champions[1]);
						page++;
					}
					
				}
				
			}
			
			try {
				FileOutputStream fos=new FileOutputStream("Resultats_Fed_Int.xlsx");
				workbook.write(fos);
				workbook.close();
				System.out.println("ecrit");
				fos.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			page=0;
		}
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("INFOS");
		alert.setHeaderText("Fichier crée");
		alert.setContentText("un fichier xlsx a bien éte crée");
		alert.showAndWait();
		
		
	}
}
