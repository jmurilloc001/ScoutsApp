package com.jmurilloc.pfc.scouts.util;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.User;
import com.jmurilloc.pfc.scouts.entities.dto.AffiliateDto;
import com.jmurilloc.pfc.scouts.entities.dto.UserDto;

public class BuildDto {

    private BuildDto(){

    }
    public static AffiliateDto buildAffiliateDto(Affiliate affiliate){
        AffiliateDto affiliateDto = new AffiliateDto(affiliate.getName(), affiliate.getLastname(),affiliate.getInscripcionDate(),affiliate.getSeccion());
        affiliateDto.setUsername(
                (affiliateDto.getUsername() != null) ? affiliate.getUser().getUsername() : "No hay ningún usuario asociado"
        );
        affiliateDto.setBirthday(
                (affiliate.getBirthday() != null) ? affiliate.getBirthday() : null
        );
        return affiliateDto;
    }
    public static UserDto builUserDto(User user){
        UserDto dto = new UserDto(user.getUsername());

        if (user.getAffiliate() != null){
            dto.setName(user.getAffiliate().getName());
            dto.setLastname(user.getAffiliate().getLastname());
        }
        dto.setRoles(user.getRoles());
        dto.setEnabled(user.isEnabled());

        return dto;
    }
}
