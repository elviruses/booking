package com.elvir.backend.service.appointment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeSlot {

    private LocalDateTime startTime;
    private long duration;

    public TimeSlot(LocalDateTime startTime, long duration) {
        this.startTime = startTime;
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        return this.startTime.plusMinutes(this.duration);
    }
}
