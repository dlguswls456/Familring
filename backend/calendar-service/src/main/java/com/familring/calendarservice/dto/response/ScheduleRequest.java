package com.familring.calendarservice.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ScheduleRequest {

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Boolean hasTime;

    private Boolean hasNotification;

    private String color;

    private List<UserAttendance> attendances;
}

