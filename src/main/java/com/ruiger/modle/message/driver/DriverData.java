package com.ruiger.modle.message.driver;

import com.ruiger.modle.message.common.Data;

/**
 * @author 睿哥
 * @version 1.0
 * @time 10:37
 * @description #  老司机开车消息模板
 * @since 2017/03/17
 */
public class DriverData extends Data{

	private Driver_frist driver_frist;

	private Driver_desc driver_desc;

	private Driver_link driver_link;

	private Driver_password driver_password;

	private Driver_remark driver_remark;

	public Driver_frist getDriver_frist() {
		return driver_frist;
	}

	public void setDriver_frist(Driver_frist driver_frist) {
		this.driver_frist = driver_frist;
	}

	public Driver_desc getDriver_desc() {
		return driver_desc;
	}

	public void setDriver_desc(Driver_desc driver_desc) {
		this.driver_desc = driver_desc;
	}

	public Driver_link getDriver_link() {
		return driver_link;
	}

	public void setDriver_link(Driver_link driver_link) {
		this.driver_link = driver_link;
	}

	public Driver_password getDriver_password() {
		return driver_password;
	}

	public void setDriver_password(Driver_password driver_password) {
		this.driver_password = driver_password;
	}

	public Driver_remark getDriver_remark() {
		return driver_remark;
	}

	public void setDriver_remark(Driver_remark driver_remark) {
		this.driver_remark = driver_remark;
	}
}
