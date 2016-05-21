import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.hibernate.jpa.HibernatePersistenceProvider;



import net.lucrare.licenta.Factura;

public class MainClass {
	
	
/*
	public static void testHibernate()
	{
		String PERSISTENCE_UNIT_NAME = "persistenceIG";
		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		
		TypedQuery<Factura> query = em.createNamedQuery("Factura.findAll", Factura.class);
		List<Factura> results = query.getResultList();
		System.out.println("Result named query size: " + results.size());
	}
*/
	
	public static void main(String args[])
	{
		MainFrame.run();
		
		//TextParser.extractTextOrange("/Users/GabrielTarpian/Desktop/Facultate/Eclipse_Workspace_2/LucrareLicenta/Facturi/Orange-Noiembrie2015.pdf");
		//Companies.run();
		//LoginDetailsFrame.run();
		//MainFrame.TestSelenium();
		//MainFrame.extractText();
		//MainClass.testHibernate();
		
	}

}
