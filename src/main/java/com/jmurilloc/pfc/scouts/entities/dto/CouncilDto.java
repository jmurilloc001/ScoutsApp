package com.jmurilloc.pfc.scouts.entities.dto;

import java.util.Date;

public class CouncilDto {
    private Long id;
    private Date initialDate;
    private Date endDate;

    public CouncilDto(Long id, Date initialDate, Date endDate) {
        this.id = id;
        this.initialDate = initialDate;
        this.endDate = endDate;
    }

    public CouncilDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
