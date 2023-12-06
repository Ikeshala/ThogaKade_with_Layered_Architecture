package dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {
    private String orderId;
    private String itemCode;
    private int quantity;
    private double unitPrice;
}
