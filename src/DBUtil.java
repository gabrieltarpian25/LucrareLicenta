import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

import net.lucrare.licenta.Factura;


public class DBUtil {
	
	static Connection conn;
	static String driver="com.mysql.jdbc.Driver";
	static String url="jdbc:mysql://localhost:3306/lucrarelicenta";
	static String user="gt";
	static String password="parola";

	public static Connection getConnection() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn=DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(conn!=null)
		{
			System.out.println("Conectare Reusita");
		}
		else
			System.out.println("Conectare Esuata");
		return conn;
	}
	
	
	public static void insertIntoDatabase(String nr,String utilitate,String companie,String dataEmiterii, String dataScadenta, float total,String status,String link)
	{
		// check if bill already exists
		int noOfBills = MainFrame.getNumberOfBills();
		for(int i=0;i<noOfBills;i++)
		{
			String billNumber = MainFrame.getBillNumber(i);
			
			if(nr.equals(billNumber))
			{
				// bill exists
				JOptionPane.showMessageDialog(null, "Factură deja existentă", "Eroare", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
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
		f.setUtilitate(utilitate);
		f.setCompanie(companie);
		f.setDataEmiterii(date);
		f.setDataScadenta(date2);
		f.setTotalPlata(total);
		f.setStatus(status);
		f.setLink(link);
		
		String PERSISTENCE_UNIT_NAME = "persistenceIG";
		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(f);
		et.commit();
		
		if (et != null) {
			JOptionPane.showMessageDialog(null, "Factură adăugată cu succes","Succes",JOptionPane.INFORMATION_MESSAGE);
			MainFrame.getInstance().dispose();
			MainFrame.run();
		} else {
			JOptionPane.showMessageDialog(null, "Eroare la introducerea facturii", "Eroare", JOptionPane.ERROR_MESSAGE);
		}

	}
}