package sg.edu.iss.nus.pafworkshop7.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.iss.nus.pafworkshop7.model.Game;
import sg.edu.iss.nus.pafworkshop7.respository.CommentRepository;
import sg.edu.iss.nus.pafworkshop7.respository.GameRepository;

@Service
public class GameService {
    @Autowired
    CommentRepository cRepo;
    @Autowired
    GameRepository gRepo;

    public String editComment(String cId,String comment,Integer rating){
        JsonObject editedcomment =cRepo.upsertComment( cId, comment, rating);
        Game game =gRepo.findGameDetailsByGid(editedcomment.getJsonNumber("gid").intValue());

        
JsonBuilderFactory factory = Json.createBuilderFactory(null);
JsonObjectBuilder builder = factory.createObjectBuilder(editedcomment);

        String modified=builder.add("gameName",game.getName()).build().toString();

        return modified;


    }
    

    public String getCommentHistory(String cid){
        JsonObject commentobject=cRepo.getCommentHistory(cid);
         Game game =gRepo.findGameDetailsByGid(commentobject.getJsonNumber("gid").intValue());

        
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder builder = factory.createObjectBuilder(commentobject);

        String modified=builder.add("gameName",game.getName()).build().toString();

        return modified;

    }
}
