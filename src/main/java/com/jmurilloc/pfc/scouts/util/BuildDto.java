package com.jmurilloc.pfc.scouts.util;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.Council;
import com.jmurilloc.pfc.scouts.entities.Role;
import com.jmurilloc.pfc.scouts.entities.User;
import com.jmurilloc.pfc.scouts.entities.dto.AffiliateDto;
import com.jmurilloc.pfc.scouts.entities.dto.CouncilDto;
import com.jmurilloc.pfc.scouts.entities.dto.RoleDto;
import com.jmurilloc.pfc.scouts.entities.dto.UserDto;
import com.jmurilloc.pfc.scouts.exceptions.CouncilDtoException;

import java.util.ArrayList;
import java.util.List;

public abstract class BuildDto {

    private BuildDto(){

    }
    public static AffiliateDto buildAffiliateDto(Affiliate affiliate){
        AffiliateDto affiliateDto = new AffiliateDto(affiliate.getId(), affiliate.getName(), affiliate.getLastname(),affiliate.getInscripcionDate(),affiliate.getSeccion());
        affiliateDto.setBirthday(
                (affiliate.getBirthday() != null) ? affiliate.getBirthday() : null
        );
        return affiliateDto;
    }
    public static UserDto builUserDto(User user){
        UserDto dto = new UserDto(user.getUsername());

        dto.setId(user.getId());

        if (user.getAffiliate() != null){
            dto.setName(user.getAffiliate().getName());
            dto.setLastname(user.getAffiliate().getLastname());
        }
        List<RoleDto> roleDtos = new ArrayList<>();
        user.getRoles().forEach(role -> roleDtos.add(BuildDto.buildRoleDto(role)));
        dto.setRoles(roleDtos);
        dto.setEnabled(user.isEnabled());

        return dto;
    }
    public static RoleDto buildRoleDto(Role role){
        RoleDto roleDto = new RoleDto();
        if (role.getName() != null){
            roleDto.setName(role.getName());
        }
        return roleDto;
    }
    public static CouncilDto buildCouncilDto(Council council){
        CouncilDto councilDto = new CouncilDto();
        try {
            councilDto.setInitialDate(council.getFechaInicio());
            councilDto.setEndDate(council.getFechaFin());
            councilDto.setId(council.getId());
        } catch (CouncilDtoException e) {
            throw new CouncilDtoException("No se ha podido crear el ConcilDto");
        }
        return councilDto;
    }
}
