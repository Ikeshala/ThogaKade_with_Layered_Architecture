package entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Orders {
    private String id;
    private String date;
    private String customerId;
}
