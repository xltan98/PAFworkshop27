package sg.edu.iss.nus.pafworkshop7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
private Integer gid;
private String user;
private Float rating;
private String cText;



    
}
