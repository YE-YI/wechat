package com.ruiger.service.message.impl;

import com.ruiger.modle.business.CarPlate;
import com.ruiger.modle.message.common.Data;
import com.ruiger.modle.message.driver.*;
import com.ruiger.service.message.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.UnsupportedEncodingException;

/**
 * @author 睿哥
 * @version 1.0
 * @time 11:15
 * @description #
 * @since 2017/03/17
 */
@Service
public class MessageServiceImpl implements MessageService{

	@Value("${com.ruiger.copyright.sponsor}")
	public String  sponsor;

	/**
	 * 组装老司机模板消息类
	 *
	 * @return
	 */
	@Override
	public Data assembleDirver(CarPlate carPlate) {
		DriverData driverData = new DriverData();
		Driver_frist driver_frist = new Driver_frist();
		Driver_desc driver_desc = new Driver_desc();
		Driver_link driver_link = new Driver_link();
		Driver_password driver_password = new Driver_password();
		Driver_remark driver_remark = new Driver_remark();
		Driver_sponsor driver_sponsor = new Driver_sponsor();

		if(carPlate != null){
			driver_frist.setValue(carPlate.getTitle());
			driver_frist.setColor("#173177");
			driver_desc.setValue(carPlate.getKeyWord());
			driver_desc.setColor("#173177");
			driver_link.setValue(carPlate.getLink());
			driver_link.setColor("#173177");
			driver_password.setValue(carPlate.getPassword());
			driver_password.setColor("#173177");
			driver_remark.setValue(carPlate.getDescription());
			driver_remark.setColor("#173177");

		}else {
			driver_frist.setValue("老司机飙车，请系好安全带");
			driver_frist.setColor("#173177");
			driver_desc.setValue("闫盼盼VIP收费视频合集");
			driver_desc.setColor("#173177");
			driver_link.setValue("whttps://pan.baidu.com/s/1hskNkQo");
			driver_link.setColor("#173177");
			driver_password.setValue("r4jr");
			driver_password.setColor("#173177");
			driver_remark.setValue("财主一定要打赏点的话，给这个收破烂的转点money吧。【微信号:yy583260221】");
			driver_remark.setColor("#173177");
		}
		try {
			driver_sponsor.setValue(new String(sponsor.getBytes("ISO8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		driver_sponsor.setColor("#ff0000");
		driverData.setDriver_frist(driver_frist);
		driverData.setDriver_desc(driver_desc);
		driverData.setDriver_link(driver_link);
		driverData.setDriver_password(driver_password);
		driverData.setDriver_remark(driver_remark);
		driverData.setDriver_sponsor(driver_sponsor);

		return driverData;
	}
}
