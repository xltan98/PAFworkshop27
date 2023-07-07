package sg.edu.iss.nus.pafworkshop7.respository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import sg.edu.iss.nus.pafworkshop7.model.Game;

@Repository
public class GameRepository {

    public static final String F_NAME="name";
    public static final String C_GAME="game";
    public static final String C_GID="gid";
    public static final String C_ID="_id";

    @Autowired
    private MongoTemplate template;

    public String FindGidByName(String name){

        Criteria criteria= Criteria.where(F_NAME).regex(name,"i");

        Query query = Query.query(criteria);
        query.fields()
        .include(C_GID)
        .exclude(C_ID);

        List<String> gidList= template.find(query,String.class,C_GAME);

        return gidList.get(0);

    }

     public Game FindGameByName(String gameName){

        Criteria criteria= Criteria.where(F_NAME).regex(gameName,"i");

        Query query = Query.query(criteria);
        query.fields()
        .exclude(C_ID);

        List<Document> game= template.find(query,Document.class,C_GAME);
        List<Game> gameList = new ArrayList<>();

        for(Document d:game){

            Integer gid=d.getInteger("gid");
            String name=d.getString("name");
            Integer year=d.getInteger("year");
            Integer ranking=d.getInteger("ranking");
            Integer usersRated=d.getInteger("users_rated");
            String url=d.getString("url");
            String image= d.getString("url");
            

            gameList.add(new Game(gid,name,year,ranking,usersRated,url,image) );



        }

        return gameList.get(0);

    }


    
}
