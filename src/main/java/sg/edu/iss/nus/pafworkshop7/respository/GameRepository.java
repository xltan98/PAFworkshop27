package sg.edu.iss.nus.pafworkshop7.respository;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
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

     public List<Game> FindGameByName(String gameName){

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
            String image= d.getString("image");
            

            gameList.add(new Game(gid,name,year,ranking,usersRated,url,image) );



        }

        return gameList;

    }
/*
    * db.games.find({ gid: 'gid' })
    */
    public Game findGameDetailsByGid(int gid){
        
        Criteria criteria= Criteria.where("gid").is(gid);
        Query query = Query.query(criteria);
        List<Document> game=template.find(query,Document.class,C_GAME);

        List<Game> gameList = new ArrayList<>();

        for(Document d:game){

            //Integer gid=d.getInteger("gid");
            String name=d.getString("name");
            Integer year=d.getInteger("year");
            Integer ranking=d.getInteger("ranking");
            Integer usersRated=d.getInteger("users_rated");
            String url=d.getString("url");
            String image= d.getString("image");
            

            gameList.add(new Game(gid,name,year,ranking,usersRated,url,image) );



        }

        return gameList.get(0);



    }

    public JsonObjectBuilder FindGameByNameREST(String gameName,int offset,int limit){

        Criteria criteria= Criteria.where(F_NAME).regex(gameName,"i");

        Query query = Query.query(criteria)
         .with(Sort.by(Direction.DESC,"rating"));

        query.fields()
            .exclude(C_ID)
            .exclude("year")
            .exclude("ranking")
            .exclude("users_rated")
            .exclude("url")
            .exclude("image")
            .exclude("gid");
           


        List<Document> game= template.find(query,Document.class,C_GAME);

        // List<JsonObject> gameobject =game.stream()
        //                               .map(document -> {
        //     JsonReader reader = Json.createReader(new StringReader(document.toJson()));
        //     JsonObject jsonObject = reader.readObject();
        //     reader.close();
        //     return jsonObject;
        // })
        // .collect(Collectors.toList());

        JsonArrayBuilder jsonArrayBuilder= Json.createArrayBuilder();

        for(Document g: game){
            JsonObject jsonObject =Json.createObjectBuilder(g).build();
            jsonArrayBuilder.add(jsonObject);

        }



        JsonObjectBuilder jsonObjectBuilder =Json.createObjectBuilder();

        jsonObjectBuilder.add("games",jsonArrayBuilder);
        jsonObjectBuilder.add("offset",String.valueOf(offset));
        jsonObjectBuilder.add("limit",String.valueOf(limit));
        jsonObjectBuilder.add("total",String.valueOf(game.size()));
        jsonObjectBuilder.add("timestamp",LocalDateTime.now().toString());

        // List<Game> gameList = new ArrayList<>();

        // for(Document d:game){

        //     Integer gid=d.getInteger("gid");
        //     String name=d.getString("name");
        //     Integer year=d.getInteger("year");
        //     Integer ranking=d.getInteger("ranking");
        //     Integer usersRated=d.getInteger("users_rated");
        //     String url=d.getString("url");
        //     String image= d.getString("image");
            

        //     gameList.add(new Game(gid,name,year,ranking,usersRated,url,image) );



        // }

        return jsonObjectBuilder;

    }

      public JsonObject findGameDetailsByGidREST(int gid){
        
        Criteria criteria= Criteria.where("gid").is(gid);
        Query query = Query.query(criteria);
        List<Document> game=template.find(query,Document.class,C_GAME);

List<JsonObject> gameObject = game.stream()
    .map(document -> {
        JsonReader reader = Json.createReader(new StringReader(document.toJson()));
        JsonObject jsonObject = reader.readObject();
        reader.close();
        return jsonObject;
    })
    .collect(Collectors.toList());

// List<JsonObject> simplifiedJsonObjects = new ArrayList<>();

// for (JsonObject originalJsonObject : gameObject) {
//     JsonObjectBuilder simplifiedJsonObject = Json.createObjectBuilder();
//     simplifiedJsonObject.add("_id", originalJsonObject.getString("_id"));
//     simplifiedJsonObject.add("gid", originalJsonObject.getJsonNumber("gid"));
//     simplifiedJsonObject.add("name", originalJsonObject.getString("name"));
//     simplifiedJsonObject.add("year", originalJsonObject.getJsonNumber("year"));
//     simplifiedJsonObject.add("ranking", originalJsonObject.getJsonNumber("ranking"));
//     simplifiedJsonObject.add("users_rated", originalJsonObject.getJsonNumber("users_rated"));
//     simplifiedJsonObject.add("url", originalJsonObject.getString("url"));
//     simplifiedJsonObject.add("image", originalJsonObject.getString("image"));
//     simplifiedJsonObjects.add(simplifiedJsonObject.build());
// }

return gameObject.get(0);




        }

       



    }


    

