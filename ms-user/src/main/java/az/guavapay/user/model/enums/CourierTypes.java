package az.guavapay.user.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CourierTypes {

    Standard("Standard Delivery Services"),
    SameDay("Same Day Delivery"),
    OverNight("Overnight Shipping Services"),
    LuggageService("Luggage Delivery Services");

    private final String name;
}
