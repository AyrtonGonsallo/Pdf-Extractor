package application;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfReader {
	private File file;
	
	 public void setFile(File file) {
		this.file = file;
	}

	public String getInternationalResults() throws Exception{
		FileInputStream fis=new FileInputStream(file);
		PDDocument pdfDoc=PDDocument.load(fis);
		PDFTextStripper pdfTS=new PDFTextStripper();
		String texte="";
		String pretexte="";
		for(int i=0;i<pdfDoc.getNumberOfPages();i++){
			try{
			pdfTS.setStartPage(i+1);
			pdfTS.setEndPage(i+1);
			pretexte=pdfTS.getText(pdfDoc);
			pretexte=pretexte.replaceAll("\\s{2,}","\n");
			if(pretexte.startsWith("\n")){
				pretexte=pretexte.replaceFirst("\n","");
			}
			texte+="Page nº"+i+"\n";
			//recuperer le poids
			texte+=pretexte.substring(pretexte.indexOf("kg")-5,pretexte.indexOf("kg")+3);
			texte+="\n";
			//recuperer les resultats
			texte+=pretexte.substring(pretexte.indexOf("1."),pretexte.length());
			}
			catch (Exception e) {
				System.out.println("erreur page nº"+(i+1));
			}
		}
		String []tab=texte.split("\n");
		String []res=new String [tab.length];
		for(int i=0;i<tab.length;i++){
			if(tab[i].matches("[0-9]+")==true ||tab[i].matches(".*Ippon.*")==true){
				res[i]="";
			}
			else res[i]=tab[i];
		}
		texte=String.join("\n", res);
		texte=texte.replaceAll("\\s{2,}","\n");
		//System.out.println(texte);
		String []pages=texte.split("Page");//les pages
		String []pagesRes=new String[pages.length];
		int total=0;
		for(int j=1;j<pages.length;j++){
			pagesRes[j]="";
			pagesRes[j]+="Page nº"+j+"\n";
			Pattern pattern = Pattern.compile("[0-9]+.");
			Matcher matcher = pattern.matcher(pages[j]);
			while (matcher.find())
			    total++;
			String []pagesTab=pages[j].split("\n");
			pagesRes[j]+=pagesTab[1]+"\n";
			for(int i=2;i<total+1;i++){
				
				pagesRes[j]+=pagesTab[i].replaceFirst("\\.","");
				pagesRes[j]+="    "+pagesTab[i+(total-1)].replaceFirst(",","")+"    "+pagesTab[i+2*(total-1)];
				if(i!=total){
					pagesRes[j]+="\n";
				}
			}
			total=0;
			pagesRes[j]+="---\n";
		}
		pagesRes[0]="";
		texte=String.join("", pagesRes);
		
		pdfDoc.close();
		fis.close();
		return texte;
	}
	
	public String getFrenchResults() throws Exception{
		FileInputStream fis=new FileInputStream(file);
		PDDocument pdfDoc=PDDocument.load(fis);
		PDFTextStripper pdfTS=new PDFTextStripper();
		String texte="";
		String pretexte="";
		String texte2="";
		for(int i=0;i<pdfDoc.getNumberOfPages();i++){
			try{
				pdfTS.setStartPage(i+1);
				pdfTS.setEndPage(i+1);
				pretexte=pdfTS.getText(pdfDoc);
				pretexte=pretexte.replaceAll("\\s{2,}","\n");
				if(pretexte.startsWith("\n")){
					pretexte=pretexte.replaceFirst("\n","");
				}
				texte+="Page nº"+(i+1)+" ";
				if(pretexte.indexOf("Poule")!=-1){
					texte+="(Poules)\n";
					texte+=pretexte.substring(pretexte.indexOf("kg")-4,pretexte.indexOf("kg")+2);
					//texte+="\n";
					
					String []tab=pretexte.split("\n");
					String []res=new String [tab.length];
					for(int j=0;j<tab.length;j++){
						if(tab[j].matches(".*-[0-9]+.*")==true ||tab[j].matches(".*Tirage.*")==true||tab[j].matches(".*TOURNOI.*")==true||tab[j].matches(".*NOM.*")==true||tab[j].matches(".*Tapis.*")==true||tab[j].matches(".*kg.*")==true||tab[j].matches(".*/.*")==true){
							res[j]="";
						}
						else res[j]=tab[j];
					}
					pretexte=String.join("\n",res);
					pretexte=pretexte.replaceAll("\\s{2,}","\n");
					System.out.println("avant");
					System.out.println(pretexte);
					for(int irep=0;irep<5;irep++){
						tab=pretexte.split("\n");
						for(int j=0;j<tab.length;j++){
							if((tab[j].matches("[A-Z].*")==false||tab[j].matches("X.*")==true) && tab[j].length()>1){
								tab[j].replaceFirst("\n","");
								tab[j-1]+=tab[j];
								//System.out.println(tab[j]+" devient "+tab[j-1]);
								tab[j]="";
							}
						}
						pretexte=String.join("\n",tab);
						pretexte=pretexte.replaceAll("\\s{2,}","\n");
					}
					
					pretexte=String.join("\n",tab);
					pretexte=pretexte.replaceAll("\\s{2,}","\n");
					System.out.println("resutats");
					System.out.println(pretexte);
					tab=pretexte.split("\n");
					for(int j=0;j<tab.length;j++){
						String []mots=tab[j].split(" ");
						if(mots.length>1){
							int pos=0;
							for(int j2=0;j2<mots.length;j2++){
								if(mots[j2].startsWith("(")){
									pos=j2;
								}
								else{
									mots[j2]+=" ";
								}
							}
							for(int j2=pos;j2<mots.length-1;j2++){
								mots[j2]="";
							}
							for(int j3=mots.length-1;j3>0;j3--){
								if(mots[j3].matches(".*[a-z] ")==true){
									mots[j3]+="   ";
									break;
								}
								
							}
							mots[pos-1]+="   ";
						}
						String[] mots2=new String[mots.length];
						for(int j2=0;j2<mots.length-1;j2++){
							mots2[j2+1]=mots[j2];
						}
						mots2[0]=mots[mots.length-1]+"   ";
						tab[j]=String.join("", mots2);
					}
					
					pretexte=String.join("\n",tab);
					pretexte=pretexte.replaceAll("\\s{5,}","\n");
					texte+=pretexte;
				}else if(pretexte.indexOf("CLASSEMENT")!=-1){
					texte+="(Classement)\n";
					//System.out.println("page "+(i+1)+" classement");
					texte+=pretexte.substring(pretexte.indexOf("kg")-4,pretexte.indexOf("kg")+2);
					//texte+="\n";
					//recuperer les resultats
					pretexte=pretexte.substring(pretexte.indexOf("Tirage"),pretexte.indexOf("CLASSEMENT"));
					String []tab=pretexte.split("\n");
					String []res=new String [tab.length];
					for(int j=0;j<tab.length;j++){
						if(tab[j].matches(".*Tirage.*")==true){
							res[j]="";
						}
						else{
							String []mots=tab[j].split(" ");
							mots[0]=mots[0].replace(".", "   ");
							for(int j3=mots.length-1;j3>1;j3--){
								if(mots[j3].matches(".*[a-z]")==true){
									mots[j3]+="   ";
									break;
								}
								
							}
							
							tab[j]=String.join(" ", mots);
							res[j]=tab[j];
						}
							
					}
					pretexte=String.join("\n",res);
					pretexte=pretexte.replaceAll("\\s{5,}","\n");
					texte+=pretexte;
					
				}
				texte+="\n---\n";
				texte2+=texte;
				texte="";
			}
			catch (Exception e) {
				System.out.println("erreur page nº"+(i+1));
				
				texte="";
			}
			
			
		}
		
		
		//texte=texte.replaceAll("\\s{2,}","\n");
		
		
		pdfDoc.close();
		fis.close();
		return texte2;
	}
}
