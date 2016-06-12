import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.lucrare.licenta.Factura;

public class Rapoarte extends JFrame {
	
	// frame
	static Rapoarte frame;
	
	// JLabels
	JLabel labelTelefonie,    labelTelefonie2016,    labelTelefonie2015,
		   labelGazeNaturale, labelGazeNaturale2016, labelGazeNaturale2015;
	
	// JLabels for values
	JLabel labelTelefonieValue,    labelTelefonie2016Value,    labelTelefonie2015Value,
	       labelGazeNaturaleValue, labelGazeNaturale2016Value, labelGazeNaturale2015Value;
	
	public Rapoarte() throws ParseException
	{
		super("Rapoarte");
		drawGUI();
	}
	
	// ********************************************************************************************************** draw GUI
	private void drawGUI() throws ParseException
	{
		// properties for the frame
		this.setLayout(new GridLayout(0,2));
		this.setLocation(500, 300);
		
		// initialize elements
		labelTelefonie2015 = new JLabel("Total telefonie 2015: ");
		labelTelefonie2016 = new JLabel("Total telefonie 2016: ");
		labelTelefonie= new JLabel("Total telefonie: ");
		
		labelGazeNaturale2015= new JLabel("Gaze naturale 2015: ");
		labelGazeNaturale2016 = new JLabel("Gaze naturale 2016: ");
		labelGazeNaturale = new JLabel("Gaze naturale: ");
		
		labelTelefonie2015Value = new JLabel("0 lei");
		labelTelefonie2016Value = new JLabel("0 lei");
		labelTelefonieValue = new JLabel("0 lei");
		
		labelGazeNaturale2015Value = new JLabel("0 lei");
		labelGazeNaturale2016Value = new JLabel("0 lei");
		labelGazeNaturaleValue = new JLabel("0 lei");
		
		Font f = new Font("Arial",Font.ITALIC, 20);
		Font f2 = new Font("Arial",Font.BOLD, 20);
		
		labelTelefonie2015.setFont(f2);
		labelTelefonie2016.setFont(f2);
		labelTelefonie.setFont(f2);
		labelGazeNaturale2015Value.setFont(f);
		labelGazeNaturale2016Value.setFont(f);
		labelGazeNaturaleValue.setFont(f);
		labelGazeNaturale2015.setFont(f2);
		labelGazeNaturale2016.setFont(f2);
		labelGazeNaturale.setFont(f2);
		labelTelefonie2015Value.setFont(f);
		labelTelefonie2016Value.setFont(f);
		labelTelefonieValue.setFont(f);
		
		this.add(labelTelefonie2015);
		this.add(labelTelefonie2015Value);
		this.add(labelTelefonie2016);
		this.add(labelTelefonie2016Value);
		this.add(labelTelefonie);
		this.add(labelTelefonieValue);
		
		this.add(labelGazeNaturale2015);
		this.add(labelGazeNaturale2015Value);
		this.add(labelGazeNaturale2016);
		this.add(labelGazeNaturale2016Value);
		this.add(labelGazeNaturale);
		this.add(labelGazeNaturaleValue);
		
		// create entity manager
		String PERSISTENCE_UNIT_NAME = "persistenceIG";
		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		double telefonie = 0;
		double telefonie2015 = 0;
		double telefonie2016 = 0;
		
		double gazeNat = 0;
		double gazeNat2015 = 0;
		double gazeNat2016 = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d = sdf.parse("01/01/2016");
		
		// query
		Query query = em.createQuery("SELECT f FROM Factura f WHERE f.status = 'Platita'");
		List<Factura> results = query.getResultList();
		for(int i=0; i<results.size();i++)
		{
			if(results.get(i).getUtilitate().equals("Telefonie"))
			{
				telefonie = telefonie + results.get(i).getTotalPlata();
				
				if(results.get(i).getDataEmiterii().before(d))
					telefonie2015 = telefonie2015 + results.get(i).getTotalPlata();
				
				if(results.get(i).getDataEmiterii().after(d))
					telefonie2016 = telefonie2016 + results.get(i).getTotalPlata();
			}
			
			if(results.get(i).getUtilitate().equals("Gaze Naturale"))
			{
				gazeNat = gazeNat + results.get(i).getTotalPlata();
				
				if(results.get(i).getDataEmiterii().before(d))
					gazeNat2015 = gazeNat2015 + results.get(i).getTotalPlata();
				
				if(results.get(i).getDataEmiterii().after(d))
					gazeNat2016 = gazeNat2016 + results.get(i).getTotalPlata();
			}
		}
		
		em.close();
		factory.close();
		
		NumberFormat formatter = new DecimalFormat("#0.00");     
		
		labelTelefonieValue.setText(formatter.format(telefonie));
		labelGazeNaturaleValue.setText(formatter.format(gazeNat));
		
		labelTelefonie2015Value.setText(formatter.format(telefonie2015));
		labelTelefonie2016Value.setText(formatter.format(telefonie2016));
		
		labelGazeNaturale2015Value.setText(formatter.format(gazeNat2015));
		labelGazeNaturale2016Value.setText(formatter.format(gazeNat2016));
	}
	
	// ***************************************************** Run ************************************************************************
				public static void run() {
					try {
						frame = new Rapoarte();
						frame.setVisible(true);
						frame.pack();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		// ********************************************************************************************************************************

}
