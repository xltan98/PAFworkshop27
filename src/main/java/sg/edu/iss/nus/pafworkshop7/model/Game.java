package sg.edu.iss.nus.pafworkshop7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Game {
    Integer gid;
    String name;
    Integer year;
    Integer ranking;
    Integer usersRated;
    String url;
    String image;
    
}
