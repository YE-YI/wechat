package com.ruiger.service.business;

import com.ruiger.modle.business.UgirlNum;

import java.util.List;

/**
 * @author 睿哥
 * @version 1.0
 * @time 13:44
 * @description #
 * @since 2017/03/20
 */
public interface UgirlService {

	/**
	 * 获取最新的8条记录
	 * @return
	 */
	public List<UgirlNum> find8Record();

	/**
	 * 保存
	 * @param ugirlNum
	 * @return
	 */
	public int saveUgirlNum(UgirlNum ugirlNum);
}
