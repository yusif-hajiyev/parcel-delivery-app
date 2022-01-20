package az.guavapay.user.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ChangeCourierStatusDto {

    @NotNull(message = "The id can't be null or empty")
    private Long id;

    @NotNull(message = "The username can't be null or empty")
    private Integer status;
}
