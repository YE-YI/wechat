package com.ruiger.dao;

import com.ruiger.modle.business.CarPlate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 睿哥
 * @version 1.0
 * @time 17:07
 * @description # 处理车牌号
 * @since 2017/03/21
 */
public interface CarPlateRepository extends MongoRepository<CarPlate, String> {

	/**
	 * 根据车牌号获取信息
	 * @param keyWord
	 * @return
	 */
	CarPlate findByKeyWord(String keyWord);
}
