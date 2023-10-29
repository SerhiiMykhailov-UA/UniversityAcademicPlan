package ua.foxminded.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.dto.GroupsDto;
import ua.foxminded.exceptions.GroupsException;
import ua.foxminded.service.GroupsService;

@Controller
@RequestMapping("/groups")
public class GroupsController {
	
	private GroupsService groupsService;

	public GroupsController(GroupsService groupsService) {
		this.groupsService = groupsService;
	}
	
	@GetMapping()
	public String getAll (Model model) {
		model.addAttribute("groupsDtoList", groupsService.getAll());
		return "groups/get_all";
	}
	
	@GetMapping("/{id}")
	public String get (@PathVariable("id") long id, Model model) throws GroupsException {
		GroupsDto groupsDto = groupsService.get(id);
		model.addAttribute("groupsDto", groupsDto);
		model.addAttribute("studentDtoList", groupsDto.getStudent());
		model.addAttribute("courseDtoList", groupsDto.getCourse());
		return "groups/get";
	}

}
