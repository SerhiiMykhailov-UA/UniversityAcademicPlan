package ua.foxminded.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.foxminded.dto.LocationDto;
import ua.foxminded.exceptions.LocationException;
import ua.foxminded.service.LocationService;

@Controller
@RequestMapping("/locations")
public class LocationController {
	
	private LocationService locationService;
	
	
	public LocationController(LocationService locationService) {
		this.locationService = locationService;
	}

	@GetMapping()
	public String getAll (Model model) {
		model.addAttribute("locationDtoList", locationService.getAll());
		return "locations/get_all";
	}
	
	@GetMapping("/{id}")
	public String get (@PathVariable("id") long id, Model model) throws LocationException {
		LocationDto locationDto = locationService.get(id);
		model.addAttribute("locationDto", locationDto);
		model.addAttribute("courseDtoList", locationDto.getCourse());
		return "locations/get";
	}

}
