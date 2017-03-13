package com.ruiger.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;


/**
 * @author 睿哥
 * @version 1.0
 * @time 11:03
 * @description #
 * @since 2017/01/20
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable>
		extends PagingAndSortingRepository<T, ID> {
	boolean support(String modelType);

	Page<T> findPaged(Integer page, Integer size);
}
