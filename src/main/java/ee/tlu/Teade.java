package ee.tlu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "teated")
public class Teade {
	@Id
	@GeneratedValue
	@Column(name = "id")
	int id;
	@Column(name = "teade")
	String teade;
}