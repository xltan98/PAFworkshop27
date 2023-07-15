package sg.edu.iss.nus.pafworkshop7.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.iss.nus.pafworkshop7.model.Comment;
import sg.edu.iss.nus.pafworkshop7.model.Game;
import sg.edu.iss.nus.pafworkshop7.respository.CommentRepository;
import sg.edu.iss.nus.pafworkshop7.respository.GameRepository;
import sg.edu.iss.nus.pafworkshop7.service.GameService;

@RestController

public class GameRestController {

    @Autowired
    GameRepository gRepo;
    @Autowired
    CommentRepository cRepo;

    @Autowired
    GameService gSvc;

    @GetMapping("/game")
    public ResponseEntity<JsonObject> getGameList(@RequestParam String gameName,int limit,int offset){
        JsonObjectBuilder gameList =gRepo.FindGameByNameREST(gameName,offset,limit);

        return ResponseEntity.ok().body(gameList.build());
    }

    @GetMapping("/gamedetail/{gid}")
    public ResponseEntity<String> getGameDetailsREST(@PathVariable int gid){
        JsonObject game =gRepo.findGameDetailsByGidREST(gid);

        return ResponseEntity.ok().body(game.toString());
    }

    @PostMapping("/review/{cId}")
    public ResponseEntity<String>postCommentREST(@PathVariable String cId,@RequestParam Integer rating,String comment){
    //    String result= cRepo.upsertComment(cId, comment, rating).toString();
      String result= gSvc.editComment(cId, comment, rating);


        return ResponseEntity.ok().body(result);
    }

     @GetMapping("/review/{cId}/history")
    public ResponseEntity<String>historyCommentREST(@PathVariable String cId){
   String result=gSvc.getCommentHistory(cId);


        return ResponseEntity.ok().body(result);
    }


    
}
