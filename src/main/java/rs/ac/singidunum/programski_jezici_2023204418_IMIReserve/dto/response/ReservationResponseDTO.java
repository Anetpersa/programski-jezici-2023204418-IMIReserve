package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.response;

import lombok.Data;
@Data
public class ReservationResponseDTO {
    private Integer id;
    private Integer researcherId;
    private String researcherName;
    private Integer instrumentId;
    private String instrumentName;
    private String parameter;
    private String startTime;
    private String endTime;
    private String uuid;
    private String createdAt;
    private String updatedAt;
}