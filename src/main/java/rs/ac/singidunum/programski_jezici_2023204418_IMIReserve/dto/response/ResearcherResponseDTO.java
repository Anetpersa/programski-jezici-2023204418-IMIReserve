package rs.ac.singidunum.programski_jezici_2023204418_IMIReserve.dto.response;

import lombok.Data;

@Data
public class ResearcherResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String createdAt;
    private String updatedAt;
}