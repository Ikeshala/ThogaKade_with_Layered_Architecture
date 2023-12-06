package dto;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String orderId;
    private String date;
    private String customerId;
    private List<OrderDetailsDto> list;
}
