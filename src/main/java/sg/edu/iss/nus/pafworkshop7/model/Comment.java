package sg.edu.iss.nus.pafworkshop7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
private String cId;
private Integer gid;
private String user;
private Integer rating;
private String cText;



    
}
