package org.springside.examples.quickstart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.quickstart.entity.Thread;

public interface ThreadDao extends PagingAndSortingRepository<Thread, Long>, JpaSpecificationExecutor<Thread> {

	Page<Thread> findByUserId(Long id, Pageable pageRequest);

	@Modifying
	@Query("delete from Thread thread where thread.user.id=?1")
	void deleteByUserId(Long id);
	
}
