package az.guavapay.auth.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserView {

    private Long id;
    private String roleName;
}
