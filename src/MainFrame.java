import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.Query;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.Wait;

import net.lucrare.licenta.Factura;

public class MainFrame extends JFrame {

	static MainFrame frame;
	private static JTable tabel;
	JTextField textSearchFactura;
	JButton butAdaugaFactura, butStergeFactura, butPlatesteFactura;
	JPanel panelSus;
	Font f;
	
	// class constructor
	public MainFrame()
	{
		super("Facturi");
		drawGUI();
		this.setTitle("Facturi");
	}
	
	//draw GUI function
	private void drawGUI()
	{
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//buttons
		panelSus=new JPanel();
		butAdaugaFactura=new JButton("Adauga Factura");
		butStergeFactura=new JButton("Sterge Factura");
		butPlatesteFactura=new JButton("Plateste Factura");
		textSearchFactura=new JTextField(10);
		panelSus.add(textSearchFactura);
		panelSus.add(butAdaugaFactura);
		panelSus.add(butStergeFactura);
		panelSus.add(butPlatesteFactura);
		
		//font
		f=new Font("Arial",Font.BOLD,15);
		
		//set font to buttons and textField
		textSearchFactura.setFont(f);
		butAdaugaFactura.setFont(f);
		butStergeFactura.setFont(f);
		butPlatesteFactura.setFont(f);
		
		//add panel to layout
		this.add(panelSus,BorderLayout.NORTH);
		
		//full screen frame
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		//create table
		final ArrayList columnNames = new ArrayList();
		final ArrayList data = new ArrayList();
		
		String PERSISTENCE_UNIT_NAME = "persistenceIG";
		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		TypedQuery<Factura> query = em.createNamedQuery("Factura.findAll", Factura.class);
		List<Factura> results = query.getResultList();
		
		int columns = 6;
		columnNames.add("Nr Factura");
		columnNames.add("Companie");
		columnNames.add("Data Emiterii");
		columnNames.add("Data Scadenta");
		columnNames.add("Total de Plata");
		columnNames.add("Status");
		
		for(int i=0;i<results.size();i++)
		{
		Vector row = new Vector(columns);
		row.add(results.get(i).getNrFactura());
		row.add(results.get(i).getCompanie());
		row.add(results.get(i).getDataEmiterii());
		row.add(results.get(i).getDataScadenta());
		row.add(results.get(i).getTotalPlata());
		row.add(results.get(i).getStatus());
		data.add(row);
		}
		
		em.close();
		factory.close();
		
		Vector columnNamesVector = new Vector();
		Vector dataVector = new Vector();

		for (int i = 0; i < data.size(); i++) {
			Vector subArray = (Vector) data.get(i);
			Vector subVector = new Vector();
			for (int j = 0; j < subArray.size(); j++) {
				subVector.add(subArray.get(j));
			}
			dataVector.add(subVector);
		}
		
		for (int i = 0; i < columnNames.size(); i++) {
			columnNamesVector.add(columnNames.get(i));
		}
		

		tabel = new JTable(dataVector, columnNamesVector) {
			public Class getColumnClass(int column) {
				for (int row = 0; row < getRowCount(); row++) {
					Object obj = getValueAt(row, column);
					if (obj != null) {
						return obj.getClass();
					}
				}
				return Object.class;
			}

			//Set cell editable to false
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		//titlu tabel
		TitledBorder title = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black), "Facturi");
		title.setTitleJustification(TitledBorder.CENTER);
		
		final JScrollPane jsp = new JScrollPane(tabel);
		jsp.setBorder(title);
		
		// aliniere centrata a continutului tabelului
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < 6; i++)
			tabel.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

		// setare font tabel
		tabel.setFont(new Font("Arial", Font.BOLD, 16));

		// Setare rearanjare coloane pe FALSE
		tabel.getTableHeader().setReorderingAllowed(false);

		// Setare culoare Background tabel
		tabel.setBackground(new Color(255, 232, 208));

		//set Row Heigth
		for(int i=0;i<tabel.getRowCount();i++)
			 tabel.setRowHeight(32);
		
		//singura selectie
		tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//afisare format data dd-MM-yyyy
		tabel.getColumnModel().getColumn(2).setCellRenderer(new TimestampCellRenderer());
		tabel.getColumnModel().getColumn(3).setCellRenderer(new TimestampCellRenderer());
		
		//add table to frame
		this.add(jsp,BorderLayout.CENTER);
		
		//buton adaugaFactura actionListener
		butAdaugaFactura.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fc=new JFileChooser();
				fc.setCurrentDirectory(new File("/Users/GabrielTarpian/Desktop/Facultate/Eclipse_Workspace_2/LucrareLicenta/Facturi"));
				int result=fc.showOpenDialog(fc);
				if(result==JFileChooser.APPROVE_OPTION)
				{
					File selectedFile=fc.getSelectedFile();
					String path=selectedFile.getAbsolutePath();
					extractText(path);
				}
			}
		});
		
		
		butStergeFactura.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String nrFactura=getSelectedNrFactura();
				
				String PERSISTENCE_UNIT_NAME = "persistenceIG";
				EntityManagerFactory factory;
				factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
				EntityManager em = factory.createEntityManager();
				
				Query query = em.createQuery("DELETE FROM Factura f WHERE f.nrFactura = :nrFact");
				query.setParameter("nrFact", nrFactura);
				EntityTransaction et=em.getTransaction();
				et.begin();
				int r=query.executeUpdate();
				et.commit();
				//factory.close();
				//em.close();
				if(r>0)
				{
					JOptionPane.showMessageDialog(null,"Factura stearsa cu succes");
					frame.dispose();
					MainFrame.run();
				}
				else 
					{
					JOptionPane.showMessageDialog(null,"Eroare","Eroare",JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		

		//********************************************** CAUTARE ************************************************************************
		final TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(tabel.getModel());
		tabel.setRowSorter(rowSorter);
		textSearchFactura.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				String text=textSearchFactura.getText();
				if(text.trim().length()==0)
					rowSorter.setRowFilter(null);
				else
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)"+text));
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
			
				String text=textSearchFactura.getText();
				if(text.trim().length()==0)
					rowSorter.setRowFilter(null);
				else
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)"+text));
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				
				String text=textSearchFactura.getText();
				if(text.trim().length()==0)
					rowSorter.setRowFilter(null);
				else
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)"+text));
			}
	
			
		});
//*********************************************END CAUTARE*********************************************************************
		
	}
	
	//PDF Extractor
	public static void extractText(String filePath)
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
			insertIntoDatabase(numarFactura,"Orange Romania",dataFactura,dataScadenta,total,"Neplatita");
			//System.out.println(parsedText);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void insertIntoDatabase(String nr,String companie,String dataEmiterii, String dataScadenta, float total,String status)
	{
		dataEmiterii=dataEmiterii.replace('.','/');
		dataScadenta=dataScadenta.replace('.','/');
		//prepare date types
		DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
		Date date=new Date();
		String d=dateFormat.format(date);
		try
		{
			date=dateFormat.parse(dataEmiterii);
		}catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
			
		Date date2=new Date();
		String d2=dateFormat.format(date2);
		try
		{
			date2=dateFormat.parse(dataScadenta);
		}catch (ParseException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		//insert into database
		Factura f=new Factura();
		f.setNrFactura(nr);
		f.setCompanie(companie);
		f.setDataEmiterii(date);
		f.setDataScadenta(date2);
		f.setTotalPlata(total);
		f.setStatus(status);
		
		String PERSISTENCE_UNIT_NAME = "persistenceIG";
		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(f);
		et.commit();
		
		if (et != null) {
			JOptionPane.showMessageDialog(null, "Factura adaugata cu succes");
			frame.dispose();
			MainFrame.run();
		} else {
			JOptionPane.showMessageDialog(null, "Eroare", "Eroare", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	public static void TestSelenium()
	{
	WebDriverWait wait;
	WebDriver driver;
	
	File pathToBinary = new File("/Applications/Firefox.app/Contents/MacOS/firefox"); 
	FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary); FirefoxProfile firefoxProfile = new FirefoxProfile(); driver = new FirefoxDriver(ffBinary, firefoxProfile);
	// driver = new FirefoxDriver();
	
	wait = new WebDriverWait(driver, 60); 
	driver.get("https://sso.orange.ro/wp/oro?jspname=login.jsp&action=LOGINPAGE_SSO&full_page=true");
	
	String username = "gabystelistu2007";
	String password = "flavius";
	
	// type search query
	driver.findElement(By.id("login")).sendKeys(new String[] { username });
	driver.findElement(By.name("password")).sendKeys(new String[] { password });
	driver.findElement(By.id("autent")).click();
	driver.get("https://www.orange.ro/myaccount/invoice/download/lastInvoice");
	
	
	
	}
		

	// run function
	public static void run() {
		try {
			frame = new MainFrame();
			frame.setVisible(true);
			frame.pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getSelectedNrFactura()
	{
		int selIndex=tabel.getSelectedRow();
		return (String) tabel.getValueAt(selIndex,0);
	}
}
