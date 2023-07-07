package sg.edu.iss.nus.pafworkshop7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import sg.edu.iss.nus.pafworkshop7.model.Comment;
import sg.edu.iss.nus.pafworkshop7.model.Game;
import sg.edu.iss.nus.pafworkshop7.respository.GameRepository;

@Controller
public class GameController {

@Autowired
private GameRepository gRepo;

@GetMapping(path="/")
public String getGameName(Model m){

 Game game= new Game();
 m.addAttribute("game", game);

    return "index";
    
}

@PostMapping(path="/form")
public String form(@ModelAttribute String gameName,Model m, HttpSession session){
    Game searchGame=gRepo.FindGameByName(gameName);
    m.addAttribute("searchGame", searchGame);

    Comment comment = new Comment();
    String gid=gRepo.FindGidByName(gameName);

     session.setAttribute("gid", gid);
     m.addAttribute("comment", comment);
     

     return "form";
}

// @PostMapping(path="/complete")
// public String saveForm(@ModelAttribute Comment comment){

    



}


    

