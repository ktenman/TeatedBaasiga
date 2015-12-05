package ee.tlu;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
public class TeateLisaja {
	public TeateLisaja(String string) {
		Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		SessionFactory vabrik = new Configuration().configure().buildSessionFactory();
		Session session = vabrik.openSession();
		Transaction t1 = session.beginTransaction();
		Teade teade = new Teade();
		teade.teade = string;
		session.save(teade);
		t1.commit();
		vabrik.close();
	}
	public static void main(String[] args){
		new TeateLisaja("test");
	}
}