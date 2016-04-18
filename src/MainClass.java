import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.hibernate.jpa.HibernatePersistenceProvider;



import net.lucrare.licenta.Factura;

public class MainClass {
	
	
	
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
	
	// Tarpian Gabriel Lucian
	public static void main(String args[])
	{
		//MainFrame.TestSelenium();
		MainFrame.run();
		//MainFrame.extractText();
		//MainClass.testHibernate();
		
	}

}
