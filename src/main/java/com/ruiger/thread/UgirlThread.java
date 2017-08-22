package com.ruiger.thread;

import com.ruiger.modle.business.UgirlNum;
import com.ruiger.service.business.UgirlService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 睿哥
 * @version 1.0
 * @time 17:59
 * @description #
 * @since 2017/03/20
 */
@Component
public class UgirlThread {
	private static Logger logger = Logger.getLogger(UgirlThread.class);

	@Autowired
	private UgirlService ugirlService;


	//每日早上9点 下午16点 定时将7条数据设置为历史
	@Scheduled(cron="0 0 9,16 * * ?")
	public void do_job(){
		List<UgirlNum> list = ugirlService.find8Record();
		for(UgirlNum num:list){
			num.setSign(true);
			ugirlService.saveUgirlNum(num);
		}

	}

}
