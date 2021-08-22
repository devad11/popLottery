package devad.springframework.poplottery.web.controller.form;

import lombok.*;

import javax.validation.constraints.Min;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LineNumberForm {

    @NonNull
    @Min(1)
    private int noOfLines;
}
