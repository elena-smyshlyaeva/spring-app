package ru.sumbirsoft.chat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.sumbirsoft.chat.domain.Members;
import ru.sumbirsoft.chat.dto.members.ResponseMembersDto;

@Mapper(componentModel = "spring")
public interface MembersMapper {
    @Mappings({
            @Mapping(target="userId", source="members.id.userId"),
            @Mapping(target="role", source="members.role"),
            @Mapping(target="status", source="members.status")
    })
    ResponseMembersDto membersToResponseMembersDto(Members members);
}
