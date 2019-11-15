package com.citycloud.ccuap.ybhw.uuid;

import com.citycloud.ccuap.ybhw.common.HwConstant;

public class UUIDGenerator {
	private static SnowflakeIdWorker _instance;

	public static String nextId() {
		if (_instance == null) {
			_instance = new SnowflakeIdWorker(HwConstant.DATA_CENTER_ID, HwConstant.MACHINE_ID);
		}
		return _instance.nextId() + "";
	}
}
