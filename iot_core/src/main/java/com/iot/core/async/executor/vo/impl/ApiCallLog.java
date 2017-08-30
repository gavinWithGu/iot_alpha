package com.iot.core.async.executor.vo.impl;

import com.iot.core.async.executor.vo.BaseExecutorVO;

public class ApiCallLog extends BaseExecutorVO {
	private static final long serialVersionUID = 4897045061767928601L;
	private String beaconId;
	private String callUserMobile;
	private String callMobileId;
	private String beaconInfoId;
	private int type;
	public String getBeaconId() {
		return beaconId;
	}

	public void setBeaconId(String beaconId) {
		this.beaconId = beaconId;
	}

	public String getCallUserMobile() {
		return callUserMobile;
	}

	public void setCallUserMobile(String callUserMobile) {
		this.callUserMobile = callUserMobile;
	}

	public String getCallMobileId() {
		return callMobileId;
	}

	public void setCallMobileId(String callMobileId) {
		this.callMobileId = callMobileId;
	}

	public String getBeaconInfoId() {
		return beaconInfoId;
	}

	public void setBeaconInfoId(String beaconInfoId) {
		this.beaconInfoId = beaconInfoId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
