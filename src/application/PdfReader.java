package application;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfReader {
	private File file;
	
	 public void setFile(File file) {
		this.file = file;
	}

	public String getResults() throws Exception{
		FileInputStream fis=new FileInputStream(file);
		PDDocument pdfDoc=PDDocument.load(fis);
		PDFTextStripper pdfTS=new PDFTextStripper();
		String texte=pdfTS.getText(pdfDoc);
		texte=texte.replaceAll("\\s{2,}","\n");
		if(texte.startsWith("\n")){
			texte=texte.replaceFirst("\n","");
		}
		//traitement
		String []tab=texte.split("\n");
		String []res=Arrays.copyOfRange(tab, 27, tab.length);
		
		for(int i=0;i<res.length;i++){
			if(res[i].matches(".*[0-9]+.*")==true ){
				res[i]="";
			}
		}
		for(int i=0;i<res.length;i++){
			if(res[i]!="" && res[i].split(" ").length<3){
				res[i]="";
			}
		}
		for(int i=0;i<res.length;i++){
			if(res[i]!="" && res[i].split(" ")[0].length()!=3){
				res[i]="";
			}
		}
		texte=String.join("\n", res);
		texte=texte.replaceAll("\\s{2,}","\n");
		pdfDoc.close();
		fis.close();
		return texte;
	}
}
