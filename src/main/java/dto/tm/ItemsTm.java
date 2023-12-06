package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemsTm extends RecursiveTreeObject<ItemsTm> {
    private String code;
    private String description;
    private double unitPrice;
    private int quantity;
    private JFXButton btn;
}
