package ua.foxminded.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ua.foxminded.dto.UsersDto;
import ua.foxminded.exceptions.UsersException;
import ua.foxminded.security.UsersDetails;
import ua.foxminded.service.UsersService;

@Controller
public class UserPageController {

	private final UsersService usersService;
		
	public UserPageController(UsersService usersService) {
		this.usersService = usersService;
	}

	@GetMapping()
	public String start() {
		return "index";
	}
	
	@GetMapping("/showUserPage")
	public String showUserInfo(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsersDetails details = (UsersDetails) authentication.getPrincipal();
		model.addAttribute("usersDetails", details);
		UsersDto usersDto;
		String linkPage = "showuserpage/";
		try {
			usersDto = usersService.getByNickName(details.getUsername());
			model.addAttribute("usersDto", usersDto);
			linkPage = linkPage + usersDto.getUserType().getUserType();
			System.out.println(linkPage);
		} catch (UsersException e) {
			e.printStackTrace();
		}
		List<UsersDto> usersDtoList = usersService.getAll().stream()
				.sorted(Comparator.comparingLong(UsersDto::getId)).collect(Collectors.toList());
		model.addAttribute("usersDtoList", usersDtoList);

		return linkPage;
	}

}
