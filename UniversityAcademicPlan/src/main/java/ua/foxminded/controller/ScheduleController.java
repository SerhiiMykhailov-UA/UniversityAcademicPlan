package ua.foxminded.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.service.ScheduleService;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {

	private ScheduleService scheduleService;

	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
	
	@GetMapping()
	public String getAll(Model model) {
		model.addAttribute("scheduleDtoList", scheduleService.getAll());
		return "schedules/get_all";
	}
	
//	@GetMapping("/{id}")
//	public String get (@PathVariable("id") long id, Model model) throws ScheduleException {
//		ScheduleDto scheduleDto = scheduleService.get(id);
//		model.addAttribute("scheduleDto", scheduleDto);
//		return "schedules/get";
//	}
	
}
