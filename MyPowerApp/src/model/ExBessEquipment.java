package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the EX_BESS_EQUIPMENT database table.
 * 
 */
@Entity
@Table(name="EX_BESS_EQUIPMENT")
@NamedQuery(name="ExBessEquipment.findAll", query="SELECT e FROM ExBessEquipment e")
public class ExBessEquipment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	private String barcode;

	@Lob
	private String batteryBrand;

	@Lob
	private String batteryModel;

	@Lob
	private String inverterBrand;

	@Lob
	private String inverterModel;

	public ExBessEquipment() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBatteryBrand() {
		return this.batteryBrand;
	}

	public void setBatteryBrand(String batteryBrand) {
		this.batteryBrand = batteryBrand;
	}

	public String getBatteryModel() {
		return this.batteryModel;
	}

	public void setBatteryModel(String batteryModel) {
		this.batteryModel = batteryModel;
	}

	public String getInverterBrand() {
		return this.inverterBrand;
	}

	public void setInverterBrand(String inverterBrand) {
		this.inverterBrand = inverterBrand;
	}

	public String getInverterModel() {
		return this.inverterModel;
	}

	public void setInverterModel(String inverterModel) {
		this.inverterModel = inverterModel;
	}

}