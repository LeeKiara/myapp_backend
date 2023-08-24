package com.lhm.myapp.task;


import com.lhm.myapp.auth.entity.Member;

import java.time.LocalDateTime;
import java.util.Date;

public interface TaskMemberProjection {

    Long getTid();
    String getTitle();
    String getDescription();
    Date getStartDate();
    Date getEndDate();
    Long getMid();
    String getStatus();
    String getUsername();

}
