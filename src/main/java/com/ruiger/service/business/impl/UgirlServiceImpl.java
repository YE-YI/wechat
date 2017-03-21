package com.ruiger.service.business.impl;

import com.ruiger.dao.UgirlNumRepository;
import com.ruiger.dao.UgirlRepository;
import com.ruiger.modle.business.UgirlNum;
import com.ruiger.service.business.UgirlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 睿哥
 * @version 1.0
 * @time 13:46
 * @description #
 * @since 2017/03/20
 */
@Service
public class UgirlServiceImpl implements UgirlService{

	@Autowired
	private UgirlNumRepository ugirlNumRepository;

	@Autowired
	private UgirlRepository ugirlRepository;
	/**
	 * 获取最新的7条记录
	 *
	 * @return
	 */
	@Override
	public List<UgirlNum> find8Record() {
		List<UgirlNum> list = ugirlNumRepository.findBySign(false,new Sort(Sort.Direction.ASC, "no"));
		//图文消息每天限制8个
		List sublist = list.subList(0,7);
		return sublist;
	}

	/**
	 * 保存
	 *
	 * @param ugirlNum
	 * @return
	 */
	@Override
	public int saveUgirlNum(UgirlNum ugirlNum) {
		ugirlNumRepository.save(ugirlNum);
		return 0;
	}
}
