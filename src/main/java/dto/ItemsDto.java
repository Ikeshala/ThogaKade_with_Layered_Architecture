package dto;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemsDto {
    private String code;
    private String description;
    private double unitPrice;
    private int quantity;
    private JFXButton btn;

    public ItemsDto(String code, String description, double unitPrice, int quantity) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
}
