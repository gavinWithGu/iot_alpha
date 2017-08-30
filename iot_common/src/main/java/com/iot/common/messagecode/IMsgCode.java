package com.iot.common.messagecode;

public interface IMsgCode {

	public static final int MSGCODE = 0;

	/** ******************** success ********************** */
	public static final int BASE_MSGCODE_SUCCESS = MSGCODE + 10000;

	public static final int MSGCODE_SUCCESS_ADD = BASE_MSGCODE_SUCCESS + 1;

	public static final int MSGCODE_SUCCESS_DELETE = BASE_MSGCODE_SUCCESS + 2;

	public static final int MSGCODE_SUCCESS_UPDATE = BASE_MSGCODE_SUCCESS + 3;

	public static final int MSGCODE_SUCCESS_QUERY = BASE_MSGCODE_SUCCESS + 4;

	/** ******************** error ********************** */

	/** ******************** common **************** */
	public static final int BASE_MSGCODE_ERROR = MSGCODE + 20000;
	public static final int MSGCODE_ERROR_ADD = BASE_MSGCODE_ERROR + 1;
	public static final int MSGCODE_ERROR_DELETE = BASE_MSGCODE_ERROR + 2;
	public static final int MSGCODE_ERROR_UPDATE = BASE_MSGCODE_ERROR + 3;
	public static final int MSGCODE_ERROR_QUERY = BASE_MSGCODE_ERROR + 4;
	public static final int MSGCODE_ERROR_VALIDATE = BASE_MSGCODE_ERROR + 5;
}
