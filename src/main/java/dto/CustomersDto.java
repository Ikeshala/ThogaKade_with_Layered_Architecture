package dto;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomersDto {
    private String id;
    private String name;
    private String address;
    private double salary;
    private JFXButton btn;

    public CustomersDto(String id, String name, String address, double salary) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }
}
