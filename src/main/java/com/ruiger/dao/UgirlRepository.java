package com.ruiger.dao;

import com.ruiger.modle.business.Ugirls;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author 睿哥
 * @version 1.0
 * @time 19:45
 * @description #
 * @since 2017/03/19
 */
public interface UgirlRepository extends MongoRepository<Ugirls, String> {

	/**
	 * 根据number 查找ugirl
	 * @param no
	 * @return
	 */
	List<Ugirls> findByNo(String no);

}
