package ua.foxminded.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import ua.foxminded.dto.GroupsDto;
import ua.foxminded.entity.Groups;

@Mapper(componentModel = "spring")
public interface GroupsMapper {
	
	GroupsDto groupsToGroupsDto (Groups groups, @Context CycleAvoidingMappingContext context);
	
	Groups groupsDtoToGroups (GroupsDto groupsDto, @Context CycleAvoidingMappingContext context);

	
}
