package net.vuega.control_plane.dto.expansionrequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.vuega.control_plane.util.ExpansionRequestFor;
import net.vuega.control_plane.util.ExpansionRequestStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpansionRequestDto {

    private Long requestId;
    private Long operatorId;
    private ExpansionRequestFor requestFor;
    private Integer count;
    private ExpansionRequestStatus status;
}