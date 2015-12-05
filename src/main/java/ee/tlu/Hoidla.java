package ee.tlu;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
public class Hoidla {
	private List < Teade > hoida;
	public Hoidla() {
		setHoida(teatedAndmebaasist());
	}
	public List < Teade > getHoida() {
		return hoida;
	}
	public void setHoida(List < Teade > hoida) {
		this.hoida = hoida;
	}
	public List < Teade > teatedAndmebaasist() {
		Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		SessionFactory vabrik = new Configuration().configure().buildSessionFactory();
		Session session = vabrik.openSession();@SuppressWarnings("unchecked")
		List < Teade > teatedAndmebaasist = session.createCriteria(Teade.class).list();
		vabrik.close();
		return teatedAndmebaasist;
	}
	public static boolean eemaldaTeade(int id) {
		Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		boolean vastus;
		SessionFactory vabrik = new Configuration().configure().buildSessionFactory();
		Session session = vabrik.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			Teade teade = (Teade) session.get(Teade.class, id);
			session.delete(teade);
			vastus = true;
		} catch (Exception e) {
			vastus = false;
		}
		transaction.commit();
		vabrik.close();
		return vastus;
	}
}