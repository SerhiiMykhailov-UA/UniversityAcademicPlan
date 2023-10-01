package ua.foxminded.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ua.foxminded.dto.GroupsDto;
import ua.foxminded.entity.Groups;

@Mapper
public interface GroupsMapper {
	
	GroupsMapper INSTANCE = Mappers.getMapper(GroupsMapper.class);
	
	GroupsDto groupsToGroupsDto (Groups groups);

}
