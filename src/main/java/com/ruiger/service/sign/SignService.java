package com.ruiger.service.sign;

import com.ruiger.service.core.impl.WechatServiceImpl;
import com.ruiger.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 公司签到打卡上班
 *
 * @author yeyi
 * @create 2017-08-09 上午 10:58
 **/
public class SignService {
    private static Logger logger = Logger.getLogger(SignService.class);

    private static String URL = "http://192.168.0.231/Check_Dowith.asp";

    private static String DEFALUT_NAME = "yy";

    /**
     * 弘智科技sign
     * @return
     * @throws Exception
     */
    public static String Sign(String name) throws Exception {
        if(StringUtils.isBlank(name)){
            name = DEFALUT_NAME;
        }
        //封装请求头
        Map headers = new HashMap();
        headers.put("'Origin'","192.168.0.231");
        headers.put("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");
        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.put("Accept-Encoding", "gzip,deflate");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        headers.put("Host", "192.168.0.231");
        headers.put("content-type", "charset=gb2312");
        headers.put("Cookie", "ASPSESSIONIDQQTACSAT=CBNNLIAANJAFIPAALIKPLOFP");
        headers.put("Referer", "http://192.168.0.231/index.asp");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("Cache-Control", "max-age=0");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
//        headers.put("Content-Length", "44");

        Map args = new LinkedHashMap();
        args.put("yhm",name);
        args.put("mm",name);
        args.put("bzsx","");
        args.put("sxsc","");
        args.put("Submit","签到");
        String html = HttpUtil.executeUrl(URL,headers,args);
        // 从网页中抽取数据信息
        logger.info("签到返回数据：" + "======================");
        logger.info(html);
        return html;
    }

}
