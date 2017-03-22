package com.ruiger.service.message;

import com.ruiger.modle.business.CarPlate;
import com.ruiger.modle.message.common.Data;

/**
 * @author 睿哥
 * @version 1.0
 * @time 11:09
 * @description #
 * @since 2017/03/17
 */
public interface MessageService {

	/**
	 * 组装老司机模板消息类
	 * @return
	 */
	public Data  assembleDirver(CarPlate carPlate);
}
