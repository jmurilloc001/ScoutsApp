package com.jmurilloc.pfc.scouts.entities.dto;

public class PostDto {
    private Long id;
    private String type;
    private String description;
    private AffiliateDto affiliateDto;
    private String title;
    private String email;
    private Integer tlf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AffiliateDto getAffiliateDto() {
        return affiliateDto;
    }

    public void setAffiliateDto(AffiliateDto affiliateDto) {
        this.affiliateDto = affiliateDto;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTlf() {
        return tlf;
    }

    public void setTlf(Integer tlf) {
        this.tlf = tlf;
    }
}
