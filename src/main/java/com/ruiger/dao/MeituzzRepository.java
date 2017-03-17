package com.ruiger.dao;

import com.ruiger.modle.business.Meituzz;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author 睿哥
 * @version 1.0
 * @time 10:15
 * @description #
 * @since 2016/12/23
 */
public interface MeituzzRepository extends MongoRepository<Meituzz, String> {

	public List<Meituzz> findByKey(Integer key);

}

