package sg.edu.iss.nus.pafworkshop7.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import sg.edu.iss.nus.pafworkshop7.model.Comment;
import sg.edu.iss.nus.pafworkshop7.model.Game;
import sg.edu.iss.nus.pafworkshop7.respository.CommentRepository;
import sg.edu.iss.nus.pafworkshop7.respository.GameRepository;

@Controller
public class GameController {

@Autowired
private GameRepository gRepo;

@Autowired
private CommentRepository cRepo;

@GetMapping(path="/")
public String getGameName(Model m){

 Game game= new Game();
 m.addAttribute("game", game);

    return "index";
    
}

@GetMapping(path="/search")
public String search(@ModelAttribute Game game,Model m, HttpSession session){
    String gameName= game.getName();
    List<Game> gameList=gRepo.FindGameByName(gameName);
    m.addAttribute("gameList", gameList);
    

    //Game game = new Game();

    
    String gid=gRepo.FindGidByName(gameName);

     session.setAttribute("gid", gid);
     session.setAttribute("gameList",gameList);
     m.addAttribute("gameList", gameList);
     m.addAttribute("gameName", gameName);
     
     

     return "gameSearchList";
}


@GetMapping(path="/game/{gid}")
public String getGameDetails(@PathVariable int gid, Model m, HttpSession session){
session.setAttribute("gid", gid);
    Game gameSelected= gRepo.findGameDetailsByGid(gid);
   m.addAttribute("gameSelected", gameSelected);
   System.out.println(gameSelected);

   Comment comment = new Comment();

   List<Comment> commentList= cRepo.findCommentByGid(gid);
   m.addAttribute("comment", comment);
   m.addAttribute("commentList",commentList);

   //System.out.println(commentList);

   return "gamedetails";

    }


@PostMapping(path="/post")
public String saveForm(@ModelAttribute Comment comment,HttpSession session ){
    
    Integer gid=(Integer) session.getAttribute("gid");
    cRepo.insertComment(comment,gid);
    System.out.println(comment);
    return "redirect:/game/"+gid;

}
}


    



// }
// }


    

