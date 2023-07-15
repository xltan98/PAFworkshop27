package sg.edu.iss.nus.pafworkshop7.model;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditedComment {

    String comment;
    Integer rating;
    LocalDateTime date;
    
}
