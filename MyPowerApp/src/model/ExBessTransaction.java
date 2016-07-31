package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the EX_BESS_TRANSACTIONS database table.
 * 
 */
@Entity
@Table(name="EX_BESS_TRANSACTIONS")
@NamedQuery(name="ExBessTransaction.findAll", query="SELECT e FROM ExBessTransaction e")
public class ExBessTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Lob
	private String inputJSON;

	@Lob
	private String operationName;

	@Lob
	private String outputJSON;

	@Lob
	private String remarks;

	public ExBessTransaction() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInputJSON() {
		return this.inputJSON;
	}

	public void setInputJSON(String inputJSON) {
		this.inputJSON = inputJSON;
	}

	public String getOperationName() {
		return this.operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOutputJSON() {
		return this.outputJSON;
	}

	public void setOutputJSON(String outputJSON) {
		this.outputJSON = outputJSON;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}