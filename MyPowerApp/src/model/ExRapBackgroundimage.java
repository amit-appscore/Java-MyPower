package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the EX_RAP_BACKGROUNDIMAGE database table.
 * 
 */
@Entity
@Table(name="EX_RAP_BACKGROUNDIMAGE")
@NamedQuery(name="ExRapBackgroundimage.findAll", query="SELECT e FROM ExRapBackgroundimage e")
public class ExRapBackgroundimage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	private byte[] backgroundImage;

	private int version;

	public ExRapBackgroundimage() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getBackgroundImage() {
		return this.backgroundImage;
	}

	public void setBackgroundImage(byte[] backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}