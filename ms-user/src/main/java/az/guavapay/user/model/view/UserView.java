package az.guavapay.user.model.view;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserView {

    private Long id;
    private String roleName;
}
