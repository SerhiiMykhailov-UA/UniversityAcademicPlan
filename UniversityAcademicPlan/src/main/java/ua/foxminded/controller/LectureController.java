package ua.foxminded.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.dto.LectureDto;
import ua.foxminded.exceptions.LectureException;
import ua.foxminded.service.LectureService;

@Controller
@RequestMapping("/lectures")
public class LectureController {

	private LectureService lectureService;

	public LectureController(LectureService lectureService) {
		this.lectureService = lectureService;
	}
	
	@GetMapping()
	public String getAll (Model model) {
		model.addAttribute("lectureDtoList", lectureService.getAll());
		return "lectures/get_all";
	}
	
	@GetMapping("/{id}")
	public String get (@PathVariable("id") long id, Model model) throws LectureException {
		LectureDto lectureDto = lectureService.get(id);
		model.addAttribute("lectureDto", lectureDto);
		model.addAttribute("courseDto", lectureDto.getCourse());
		return "lectures/get";
	}
}
