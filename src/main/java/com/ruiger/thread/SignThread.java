package com.ruiger.thread;

import com.ruiger.service.sign.SignService;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 签到
 *
 * @author yeyi
 * @create 2017-08-11 下午 14:51
 **/
public class SignThread {

    private Logger logger = Logger.getLogger(this.getClass());

    @Scheduled(cron = "0 25 8 * * ?")
    public void sign() throws Exception {
        try {
            SignService.Sign("");
        } catch (Exception e) {
            logger.error(e.getMessage() + "【yy】", e);
        }
        try {
            SignService.Sign("zk");
        } catch (Exception e) {
            logger.error(e.getMessage() + "【zk】", e);
        }
    }
}
