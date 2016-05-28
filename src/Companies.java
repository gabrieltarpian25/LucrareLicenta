import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.sikuli.script.FindFailed;

public class Companies extends JFrame {

	static Companies frame;
	
	JButton butOrange,butEon,butDigi,butEnel,butTelekom,butElectrica;
	
	static String selectedCompany = null;
	
	public Companies()
	{
		super("Alege companie");
		drawGUI();
	}
	
	// *************************************************************** draw GUI *********************************************************
	private void drawGUI()
	{
		// properties for the frame
		this.setLayout(new GridLayout(0,3));
		this.setLocation(500,300);
		
		// load the Orange Image
		ImageIcon imageIcon = new ImageIcon("./Logos/orange.png"); 
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back
		// create Orange button
		butOrange = new JButton();
		butOrange.setIcon(imageIcon);
		butOrange.setPreferredSize(new Dimension(120,120));
		
		// load the Eon Image
		imageIcon = new ImageIcon("./Logos/eon.gif");
		image = imageIcon.getImage();
		newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back
		// create Eon button
		butEon = new JButton();
		butEon.setIcon(imageIcon);
		butEon.setPreferredSize(new Dimension(120,120));
		
		// load the DIGI Image
		imageIcon = new ImageIcon("./Logos/digi.png");
		image = imageIcon.getImage();
		newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back
		// create DIGI button
		butDigi = new JButton();
		butDigi.setIcon(imageIcon);
		butDigi.setPreferredSize(new Dimension(120,120));

		// load the ENEL Image
		imageIcon = new ImageIcon("./Logos/enel.gif");
		image = imageIcon.getImage();
		newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back
		// create ENEL button
		butEnel = new JButton();
		butEnel.setIcon(imageIcon);
		butEnel.setPreferredSize(new Dimension(120, 120));
		
		// load the TELEKOM Image
		imageIcon = new ImageIcon("./Logos/telekom.png");
		image = imageIcon.getImage();
		newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back
		// create ENEL button
		butTelekom = new JButton();
		butTelekom.setIcon(imageIcon);
		butTelekom.setPreferredSize(new Dimension(120, 120));
		
		// load the TELEKOM Image
		imageIcon = new ImageIcon("./Logos/electrica.jpg");
		image = imageIcon.getImage();
		newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back
		// create ENEL button
		butElectrica = new JButton();
		butElectrica.setIcon(imageIcon);
		butElectrica.setPreferredSize(new Dimension(120, 120));
		
		//add components to frame
		this.add(butOrange);
		this.add(butEon);
		this.add(butDigi);
		this.add(butEnel);
		this.add(butTelekom);
		this.add(butElectrica);

		// ************* button ORANGE is clicked *************************************
		butOrange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				selectedCompany = "Orange";
				
				// option "Add Bill" is selected
				if(MainFrame.boGetAdaugaFactura() == true)
				{
					frame.dispose();
					JFileChooser fc = new JFileChooser();
					fc.setCurrentDirectory(new File("./Facturi"));
					int result = fc.showOpenDialog(fc);
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fc.getSelectedFile();
						String path = selectedFile.getAbsolutePath();
						TextParser.extractTextOrange(path);
					}
					
					MainFrame.resetFlags();
				}
				
				// option "Download bill" is selected
				if(MainFrame.boGetDownloadFactura() == true)
				{
					frame.dispose();
					if(MainFrame.findLoginDetails(selectedCompany) == true)
					{
						try {
							DownloadBill.downloadOrange();
							//MainFrame.boDownloadFactura = false;
						} catch (AWTException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else LoginDetailsFrame.run();
				}
			}
		});
		//******************************************************************************		
		
		// ************* button E-ON is clicked *************************************
		butEon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				selectedCompany = "Eon";
				
				// option "Add Bill" is selected
				if(MainFrame.boGetAdaugaFactura() == true)
				{
					frame.dispose();
					JFileChooser fc = new JFileChooser();
					fc.setCurrentDirectory(new File("./Facturi"));
					int result = fc.showOpenDialog(fc);
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fc.getSelectedFile();
						String path = selectedFile.getAbsolutePath();
						TextParser.extractTextEon(path);
					}
					
					MainFrame.resetFlags();
				}
				
				// option "Download bill" is selected
				if(MainFrame.boGetDownloadFactura() == true)
				{
					frame.dispose();
					if(MainFrame.findLoginDetails(selectedCompany) == true)
					{
						try {
							DownloadBill.downloadEon();
							//MainFrame.boDownloadFactura = false;
						} catch (AWTException | FindFailed e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else LoginDetailsFrame.run();
				}	
			}});
		
		// ************* button TELEKOM is clicked *************************************
		butTelekom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				selectedCompany = "Telekom";

				// option "Add Bill" is selected
				if (MainFrame.boGetAdaugaFactura() == true) {
					frame.dispose();
					JFileChooser fc = new JFileChooser();
					fc.setCurrentDirectory(new File("./Facturi"));
					int result = fc.showOpenDialog(fc);
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fc.getSelectedFile();
						String path = selectedFile.getAbsolutePath();
						TextParser.extractTextTelekom(path);
					}

					MainFrame.resetFlags();
				}

				// option "Download bill" is selected
				if (MainFrame.boGetDownloadFactura() == true) {
					frame.dispose();
					if (MainFrame.findLoginDetails(selectedCompany) == true) {
						try {
							DownloadBill.downloadTelekom();
							// MainFrame.boDownloadFactura = false;
						} catch (AWTException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else
						LoginDetailsFrame.run();
				}
			}
		});
	}
	// **********************************************************************************************************************************
	
	
	// ************ get selected company ****************************
	private String getSelectedCompany()
	{
		return this.selectedCompany;
	}
	// ****************************************************************
	
	// ***************************************************** Run ************************************************************************
			public static void run() {
				try {
					frame = new Companies();
					frame.setVisible(true);
					frame.pack();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	// ********************************************************************************************************************************
}
