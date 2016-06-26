import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class CardDetailsFrame extends JFrame {

	static CardDetailsFrame frame;
	
	JLabel labelNumarCard,labelDataExp,labelCIV,labelNume;
	JTextField textNumarCard, textDataExp,textCIV, textNume;
	JButton butOK,butSterge;
	
	// class constructor
	public CardDetailsFrame()
	{
		super("Detalii Card");
		drawGUI();
	}
	
	// ***************************************************** Draw GUI
	private void drawGUI()
	{
		// Properties for the frame
		this.setLayout(new GridLayout(0,2));
		this.setLocation(300, 300);
		this.setSize(300,100);
		
		// initialize the frame components
		labelNumarCard = new JLabel("Număr Card: ");
		labelDataExp =  new JLabel("Data Expirării(MM/YYYY): ");
		labelCIV = new JLabel("Codul CIV");
		labelNume = new JLabel("Titular Card");
		textNumarCard = new JTextField(20);
		textDataExp = new JTextField(10);
		textCIV = new JTextField(5);
		textNume = new JTextField(30);
		butOK = new JButton("Introduceți datele");
		butSterge = new JButton("Șterge");
		
		labelNumarCard.setAlignmentX(CENTER_ALIGNMENT);
		
		// add elements to frame
		this.add(labelNumarCard);
		this.add(textNumarCard);
		this.add(labelDataExp);
		this.add(textDataExp);
		this.add(labelCIV);
		this.add(textCIV);
		this.add(labelNume);
		this.add(textNume);
		this.add(butOK);
		this.add(butSterge);
		
		//************************************************** button OK is pressed ***********************************************
		butOK.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// get texts from textfields
				String numarCard = textNumarCard.getText().trim();
				String dataExp = textDataExp.getText().trim();
				String CIV = textCIV.getText().trim();
				String nume = textNume.getText().trim();
				
				PrintWriter writer = null;
				try {
					writer = new PrintWriter("./Details/detaliiCard.txt", "UTF-8");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				writer.println(numarCard);
				writer.println(dataExp);
				writer.println(CIV);
				writer.println(nume);
				writer.close();
				
				if(MainFrame.findCardDetails() == true)
				{
					JOptionPane.showMessageDialog(null,"Date adăugate cu succes");
					frame.dispose();
					String selectedCompany = MainFrame.company;
					if(selectedCompany.equals("Orange"))
					{
						Payment.payOrange();
					}
					if(selectedCompany.equals("Eon"))
					{
						Payment.payEon();
					}
				}
			}
		});
		//*************************************************************************************************************************
	}
	// ***************************************************** End of Draw GUI
	
	// ***************************************************** Run
		public static void run() {
			try {
				frame = new CardDetailsFrame();
				frame.setVisible(true);
				frame.pack();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	// ***************************************************** End of Run
}
