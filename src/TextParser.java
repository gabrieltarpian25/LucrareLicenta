import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class TextParser {

	
// ***************************************************** ORANGE ***********************************************************************
	public static void extractTextOrange(String filePath) {
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		File file = new File(filePath);

		try {
			PDFParser parser = new PDFParser(new FileInputStream(file));
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(2);
			String parsedText = pdfStripper.getText(pdDoc);
			System.out.println(parsedText);

			String lines[] = parsedText.split("\\r?\\n");
			// System.out.println("Lines[3] =" + lines[3]);

			String numarFactura = null;
			String dataFactura = null;
			String dataScadenta = null;
			String totalPlata = null;

			// parse every line
			for (int i = 0; i < lines.length; i++) {
				// Numar Factura
				if (lines[i].contains("Numar factura: ")) {
					numarFactura = lines[i].substring(15, lines[i].length()).trim();
					System.out.println("Numar Factura: " + numarFactura);
				}

				// Data Facturii
				if (lines[i].contains("Data facturii: ")) {
					dataFactura = lines[i].substring(15, lines[i].length()).trim();
					System.out.println("Data Facturii: " + dataFactura);
				}

				// Ultima zi de plata
				if (lines[i].contains("Ultima zi de plata: ")) {
					dataScadenta = lines[i].substring(20, lines[i].length()).trim();
					System.out.println("Data scadenta: " + dataScadenta);
				}

				// Total de plata
				if (lines[i].contains("Factura curenta Orange")) {
					totalPlata = lines[i + 3].trim();
					System.out.println("Total Plata: " + totalPlata);
				}
			}

			// convert totalPlata to float
			totalPlata = totalPlata.replace(",", ".");
			float total = Float.parseFloat(totalPlata);
			// System.out.println("Float total: "+total);

			DBUtil.insertIntoDatabase(numarFactura,"Telefonie", "Orange", dataFactura, dataScadenta, total, "Neplatita", filePath);
			// System.out.println(parsedText);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Eroare la citirea facturii Orange", "Eroare",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
//************************************************************************************************************************************
		
// ***************************************************** E-ON ***********************************************************************
	public static void extractTextEon(String filePath) {
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		File file = new File(filePath);

		try {
			PDFParser parser = new PDFParser(new FileInputStream(file));
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(2);
			String parsedText = pdfStripper.getText(pdDoc);
			System.out.println(parsedText);

			String lines[] = parsedText.split("\\r?\\n");
			// System.out.println("Lines[3] =" + lines[3]);

			String numarFactura = null;
			String dataFactura = null;
			String dataScadenta = null;
			String totalPlata = null;

			// parse every line
			for (int i = 0; i < lines.length; i++) {
				// Numar Factura , Data Facturii, Data Scadenta
				if (lines[i].contains("Seria Numărul facturii Perioada de facturare Data emiterii Data scadenţei")) {
					String[] details = lines[i + 1].split("EON");
					String[] details2 = details[1].split(" ");
					numarFactura = details2[1];
					dataFactura = details2[2];
					dataScadenta = details2[3];

					System.out.println("Numar Factura: " + numarFactura);
					System.out.println("Data Emiterii: " + dataFactura);
					System.out.println("Data Scadenta: " + dataScadenta);
				}

				// Total de plata
				if (lines[i].contains("Vă mulţumim!")) {
					totalPlata = lines[i - 1].trim();
					System.out.println("Total Plata (string): " + totalPlata);
				}
			}

			// convert totalPlata to float
			totalPlata = totalPlata.replace(",", ".");
			float total = Float.parseFloat(totalPlata);
			System.out.println("Total Plata (string): " + total);

			DBUtil.insertIntoDatabase(numarFactura,"Gaze Naturale", "E-ON", dataFactura, dataScadenta, total, "Neplatita", filePath);
			// System.out.println(parsedText);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Eroare la citirea facturii E-ON", "Eroare", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}
//*************************************************************************************************************************************
	
// ***************************************************** TELEKOM ***********************************************************************
	public static void extractTextTelekom(String filePath) {
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		File file = new File(filePath);

		try {
			PDFParser parser = new PDFParser(new FileInputStream(file));
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(2);
			String parsedText = pdfStripper.getText(pdDoc);

			String lines[] = parsedText.split("\\r?\\n");
			// System.out.println("Lines[3] =" + lines[3]);

			String numarFactura = null;
			String dataFactura = null;
			String dataScadenta = null;
			String totalPlata = null;

			// parse every line
			for (int i = 0; i < lines.length; i++) 
			{
				System.out.println(lines[i]);
				
				// data factura
				if(lines[i].contains("Data facturării: "))
					dataFactura = lines[i].substring(17,lines[i].length());
				// numar factura
				if(lines[i].contains("Cod facturare: "))
					numarFactura = lines[i].substring(15,lines[i].length());
				
				// total plata
				if(lines[i].contains("Total factură curentă "))
					totalPlata = lines[i].substring(22,lines[i].length());
				
				if(lines[i].contains("Total de plată până la "))
					dataScadenta = lines[i].substring(23,33);
			}

			
			// convert totalPlata to float
			totalPlata = totalPlata.replace(",", ".");
			float total = Float.parseFloat(totalPlata);
			
			System.out.println();
			System.out.println("Detalii factura Telekom: ");
			System.out.println("Numar factura: "+numarFactura+";");
			System.out.println("Data factura: "+dataFactura+";");
			System.out.println("Data scadenta: "+dataScadenta+";");
			System.out.println("Total Plata (string): " + totalPlata+";");
			System.out.println("Total Plata (float): " + total+";");

			DBUtil.insertIntoDatabase(numarFactura,"Telefonie", "Telekom", dataFactura, dataScadenta, total, "Neplatita", filePath);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Eroare la citirea facturii TELEKOM", "Eroare", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}
//*************************************************************************************************************************************
}
