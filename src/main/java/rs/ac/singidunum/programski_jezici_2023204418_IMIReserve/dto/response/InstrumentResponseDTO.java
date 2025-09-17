package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.response;

import lombok.Data;

@Data
public class InstrumentResponseDTO {
    private Integer id;
    private String instrumentName;
    private String laboratory;
    private String createdAt;
    private String updatedAt;
}