package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTm extends RecursiveTreeObject<CustomerTm> {
    private String id;
    private String name;
    private String address;
    private double salary;
    private JFXButton btn;
}
