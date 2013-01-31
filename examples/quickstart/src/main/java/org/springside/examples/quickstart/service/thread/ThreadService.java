package org.springside.examples.quickstart.service.thread;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.quickstart.entity.Thread;
import org.springside.examples.quickstart.repository.ThreadDao;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

@Component
@Transactional(readOnly = true)
public class ThreadService {
	private ThreadDao threadDao;

	public Thread getThread(Long id) {
		return threadDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public void saveThread(Thread entity) {
		threadDao.save(entity);
	}

	@Transactional(readOnly = false)
	public void deleteThread(Long id) {
		threadDao.delete(id);
	}

	public List<Thread> getAllThread() {
		return (List<Thread>) threadDao.findAll();
	}

	public Page<Thread> getUserThread(Long userId,
			Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);
		Specification<Thread> spec = buildSpecification(userId, searchParams);

		return threadDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize,
			String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Thread> buildSpecification(Long userId,
			Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<Thread> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), Thread.class);
		return spec;
	}

	@Autowired
	public void setThreadDao(ThreadDao threadDao) {
		this.threadDao = threadDao;
	}
}
