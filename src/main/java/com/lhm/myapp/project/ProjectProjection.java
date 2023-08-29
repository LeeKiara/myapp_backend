package com.lhm.myapp.project;


import java.util.Date;

public interface ProjectProjection {

    Long getPid();
    String getTitle();
    String getDescription();
    Date getStartDate();
    Date getEndDate();
    Long getMid();
    String getStatus();
    String getCreatorUser();
    String getImage();


}
