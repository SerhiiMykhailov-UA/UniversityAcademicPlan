package ua.foxminded.mapper;

import org.mapstruct.Mapper;

import ua.foxminded.dto.GroupsDto;
import ua.foxminded.entity.Groups;

@Mapper(componentModel = "spring")
public interface GroupsMapper {
	
	GroupsDto groupsToGroupsDto (Groups groups);
	
	Groups groupsDtoToGroups (GroupsDto groupsDto);

	
}
