import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class TextParser {

	
// ***************************************************** Orange ***********************************************************************
		public static void extractTextOrange(String filePath)
		{
			PDFTextStripper pdfStripper=null;
			PDDocument pdDoc=null;
			COSDocument cosDoc=null;
			File file=new File(filePath);
			
			try
			{
				PDFParser parser=new PDFParser(new FileInputStream(file));
				parser.parse();
				cosDoc=parser.getDocument();
				pdfStripper=new PDFTextStripper();
				pdDoc=new PDDocument(cosDoc);
				pdfStripper.setStartPage(1);
				pdfStripper.setEndPage(2);
				String parsedText=pdfStripper.getText(pdDoc);
				System.out.println(parsedText);
				
				int indexUltimaZiPlata=parsedText.indexOf("Ultima zi de plata: ")+20;
				int indexStartNumarFactura=parsedText.indexOf("Numar factura: ")+15;
				int indexStopNumarFactura=parsedText.indexOf("Referinta interna")-2;
				int indexDataFactura=parsedText.indexOf("Data facturii: ")+15;
				int indexStartTotalPlata=parsedText.indexOf("Total de plata (lei)")+21;
				int indexStopTotalPlata=0;
				
				int i=indexStartTotalPlata;
				boolean gasit=false;
				while(gasit==false)
				{
					String c=parsedText.substring(i,i+1);
					System.out.println(i+"-"+c);
					if(c.equals("0") == false && c.equals("1") == false && c.equals("2") == false && c.equals("3") == false && 
							c.equals("4") == false && c.equals("5") == false && c.equals("6") == false && c.equals("7") == false &&
							c.equals("8") == false && c.equals("9") == false && c.equals(",") == false )
						gasit=true;
					i++;
				}
				indexStopTotalPlata=i;
		
				String numarFactura=parsedText.substring(indexStartNumarFactura,indexStopNumarFactura);
				String dataFactura=parsedText.substring(indexDataFactura,indexDataFactura+10);
				String dataScadenta=parsedText.substring(indexUltimaZiPlata, indexUltimaZiPlata+10);
				String totalPlata=parsedText.substring(indexStartTotalPlata,indexStopTotalPlata);
				
				System.out.println("Numar Factura: "+numarFactura);//numar factura
				System.out.println("Data Factura: "+dataFactura);//data factura
				System.out.println("Data Scadenta: "+dataScadenta);//ultima zi de plata
				System.out.println("Total de Plata: "+totalPlata);//total de plata
				totalPlata=totalPlata.replace(',','.');
				float total=Float.parseFloat(totalPlata);
				DBUtil.insertIntoDatabase(numarFactura,"Orange Romania",dataFactura,dataScadenta,total,"Neplatita");
				//System.out.println(parsedText);
			}catch(IOException e)
			{
				e.printStackTrace();
			}
		}
//*****************************************************************************************************************************************
}
