package com.bbg.open.b2b4pos.web.param;

public class SaleOrderParam extends BaseParam {
	private String loadCode;
	
	private String driverCode;

	private String machineNo;
	
	private String storeCode;

	public String getLoadCode() {
		return loadCode;
	}

	public void setLoadCode(String loadCode) {
		this.loadCode = loadCode;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	
}
