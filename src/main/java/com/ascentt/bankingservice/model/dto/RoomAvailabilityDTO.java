package com.ascentt.bankingservice.model.dto;

import java.time.LocalDate;

public class RoomAvailabilityDTO {
    private Long roomId;
    private LocalDate startDate;
    private LocalDate endDate;

    public RoomAvailabilityDTO() {
    }

    public RoomAvailabilityDTO(Long roomId, LocalDate startDate, LocalDate endDate) {
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
