package org.springside.examples.quickstart.web.thread;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.examples.quickstart.entity.Thread;
import org.springside.examples.quickstart.entity.User;
import org.springside.examples.quickstart.service.account.ShiroDbRealm.ShiroUser;
import org.springside.examples.quickstart.service.thread.ThreadService;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;

/**
 * Thread管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /thread/ Create page : GET /thread/create Create action :
 * POST /thread/create Update page : GET /thread/update/{id} Update action :
 * POST /thread/update Delete action : GET /thread/delete/{id}
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/thread")
public class ThreadController {

	private static final String PAGE_SIZE = "10";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private ThreadService threadService;

	@RequestMapping(value = "")
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		Long userId = getCurrentUserId();

		Page<Thread> threads = threadService.getUserThread(userId,
				searchParams, pageNumber, pageSize, sortType);
		model.addAttribute("threads", threads);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets
				.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "thread/threadList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("thread", new Thread());
		model.addAttribute("action", "create");
		return "thread/threadForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Thread newThread,
			RedirectAttributes redirectAttributes) {
		User user = new User(getCurrentUserId());
		newThread.setUser(user);

		threadService.saveThread(newThread);
		redirectAttributes.addFlashAttribute("message", "创建任务成功");
		return "redirect:/thread/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("thread", threadService.getThread(id));
		model.addAttribute("action", "update");
		return "thread/threadForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("thread") Thread thread,
			RedirectAttributes redirectAttributes) {
		threadService.saveThread(thread);
		redirectAttributes.addFlashAttribute("message", "更新任务成功");
		return "redirect:/thread/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		threadService.deleteThread(id);
		redirectAttributes.addFlashAttribute("message", "删除任务成功");
		return "redirect:/thread/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Thread对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute()
	public void getThread(
			@RequestParam(value = "id", required = false) Long id, Model model) {
		if (id != null) {
			model.addAttribute("thread", threadService.getThread(id));
		}
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
}
