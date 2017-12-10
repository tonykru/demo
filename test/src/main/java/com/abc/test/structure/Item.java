package com.abc.test.structure;

/**
 * 
 * @author thanh
 *
 */
public class Item {

	private static final int MAX_ARRAY_SIZE = 2147483639;
	
	private String phoneNumber;

	private String[] activationDate = new String[0];

	private String[] deActivationDate = new String[0];

	private int activationDateSize;
	
	private int deActivationDateSize;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String[] getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(String[] activationDate) {
		this.activationDate = activationDate;
	}

	public String[] getDeActivationDate() {
		return deActivationDate;
	}

	public void setDeActivationDate(String[] deActivationDate) {
		this.deActivationDate = deActivationDate;
	}

	public void addActivationDate(String date) {
		if (activationDateSize >= activationDate.length) {
			resize("ACTIVATION");
		}
		activationDate[activationDateSize++] = date;
	}
	
	public void addDeActivationDate(String date) {
		if (deActivationDateSize >= deActivationDate.length) {
			resize("");
		}
		deActivationDate[deActivationDateSize++] = date;
	}
	
	private void resize(String type) {
		
		String[] arr = null;
		if("ACTIVATION".equals(type)) {
			arr = activationDate;
		} else {
			arr = deActivationDate;
		}
		
		int newCapacity = (arr.length << 1) + 1;
		if (newCapacity - MAX_ARRAY_SIZE > 0) {
			if (arr.length == MAX_ARRAY_SIZE)
				return;
			newCapacity = MAX_ARRAY_SIZE;
		}
		
		String[] newArr = new String[newCapacity];
		for (int i = 0; i < arr.length ; i++) {
			newArr[i] = arr[i];	
		}
		if("ACTIVATION".equals(type)) {
			activationDate = newArr;
		} else {
			deActivationDate = newArr;
		}
		
	}

	public int getActivationDateSize() {
		return activationDateSize;
	}

	public void setActivationDateSize(int activationDateSize) {
		this.activationDateSize = activationDateSize;
	}

	public int getDeActivationDateSize() {
		return deActivationDateSize;
	}

	public void setDeActivationDateSize(int deActivationDateSize) {
		this.deActivationDateSize = deActivationDateSize;
	}
	
}
