/**
 * 
 */
package com.ppx.terminal.mvc.tool.core;

/**
 * @author mark
 * @date 2019年7月25日
 */
public class Store {
	private String storeNo;
	private String storeName;
	private String storeAddress;
	private String storeLng;
	private String storeLat;
	private String storePhone;
	
	private String initStoreNo;
	
	public String getInitStoreNo() {
		return initStoreNo;
	}

	public void setInitStoreNo(String initStoreNo) {
		this.initStoreNo = initStoreNo;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}

	public String getStoreLng() {
		return storeLng;
	}

	public void setStoreLng(String storeLng) {
		this.storeLng = storeLng;
	}

	public String getStoreLat() {
		return storeLat;
	}

	public void setStoreLat(String storeLat) {
		this.storeLat = storeLat;
	}

	public String getStorePhone() {
		return storePhone;
	}

	public void setStorePhone(String storePhone) {
		this.storePhone = storePhone;
	}

}
