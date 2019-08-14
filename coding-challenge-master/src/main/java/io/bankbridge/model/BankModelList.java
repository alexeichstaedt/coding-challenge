/*
 * Changed List<BankModel> banks from public to private and created 
 * getter and setter methods for safe access and manipulation.
 */
package io.bankbridge.model;
import java.util.List;

public class BankModelList {
	
	private List<BankModel> banks;

	public List<BankModel> getBanks() {
		return banks;
	}

	public void setBanks(List<BankModel> banks) {
		this.banks = banks;
	} 
	

}
