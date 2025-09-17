package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.request;

import lombok.Data;

@Data
public class ReservationDTO {
    private Integer researcherId;
    private Integer instrumentId;
    private String startTime;
    private String endTime;
    private String parameter;
    private String uuid;
}