package sg.edu.iss.nus.pafworkshop7.respository;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import sg.edu.iss.nus.pafworkshop7.model.Comment;
import sg.edu.iss.nus.pafworkshop7.model.EditedComment;

@Repository
public class CommentRepository {

@Autowired
private MongoTemplate template;

public List<Comment> findCommentByGid(Integer gid){
    Criteria criteria = Criteria.where("gid").is(gid);

    Query query = Query.query(criteria)
                .with(Sort.by(Direction.DESC,"rating"))
                .limit(5);

    List<Document> docs= template.find(query,Document.class,"comment");

    List<Comment> commentList = new ArrayList<>();
    for(Document d:docs){
        String user=d.getString("user");
        Integer rating= d.getInteger("rating");
        String comment=d.getString("c_text");

    commentList.add(new Comment("",gid,user,rating,comment));


    }
    
    return commentList;

}

public ObjectId insertComment(Comment comment,Integer gid){
    Document doc = new Document();
    String cId = UUID.randomUUID().toString().substring(0, 8);
    comment.setCId(cId);
    doc.put("c_id",comment.getCId());
    doc.put("gid", gid);
    doc.put("user",comment.getUser());
    doc.put("rating",comment.getRating());
    doc.put("c_text",comment.getCText());

    

    Document newDoc = template.insert(doc,"comment");
    return newDoc.getObjectId("_id");
    
}

public JsonObject upsertComment(String cId,String comment, Integer rating){
    Criteria criteria = Criteria.where("c_id").is(cId);

    EditedComment editedComment = new EditedComment();
    editedComment.setComment(comment);
    editedComment.setRating(rating);
    editedComment.setDate(LocalDateTime.now());

    Query query1 = Query.query(criteria);
    Update updateOps= new Update()
    .set("c_text",comment)
    .set("rating",rating)
    .push("edited",editedComment);
    // .push("edited").each(List.of(comment,rating,LocalDateTime.now().toString()));

    UpdateResult updateResult= template.upsert(query1,updateOps,"comment");

    Criteria criteria1= Criteria.where("c_id").is(cId);

    //field checker
    Query query2 = Query.query(criteria1);
    List<Document> game2=template.find(query2,Document.class,"comment");

        Query query = Query.query(criteria1);
        query.fields()
        .exclude("edited")
        .exclude("_id");
        List<Document> game=template.find(query,Document.class,"comment");

// List<JsonObject> gameObject = game.stream()
//     .map(document -> {
//         JsonReader reader = Json.createReader(new StringReader(document.toJson()));
//         JsonObject jsonObject = reader.readObject();
//         reader.close();
//         return jsonObject;
//     })
//     .collect(Collectors.toList());


List<JsonObject> gameObject = game.stream()
    .map(document -> {
        JsonReader reader = Json.createReader(new StringReader(document.toJson()));
        JsonObject jsonObject = reader.readObject();
        reader.close();

        boolean hasEditedField = game2.get(0).containsKey("edited");
        
        // Create a new JsonObjectBuilder
        JsonObjectBuilder builder = Json.createObjectBuilder();
        // Add all existing key-value pairs to the builder
        jsonObject.entrySet().forEach(entry -> builder.add(entry.getKey(), entry.getValue()));
        // Add the new key-value pair to the builder
        builder.add("editedPresent", hasEditedField);
        // Build the modified JsonObject
        JsonObject modifiedJsonObject = builder.build();
        
        return modifiedJsonObject;
    })
    .collect(Collectors.toList());

    return gameObject.get(0);
}

public JsonObject getCommentHistory(String cId){

     Criteria criteria1= Criteria.where("c_id").is(cId);

    //field checker
   ;

        Query query = Query.query(criteria1);
        query.fields()
        .exclude("_id");

        List<Document> game=template.find(query,Document.class,"comment");

List<JsonObject> gameObject = game.stream()
    .map(document -> {
        JsonReader reader = Json.createReader(new StringReader(document.toJson()));
        JsonObject jsonObject = reader.readObject();
        reader.close();
        return jsonObject;
    })
    .collect(Collectors.toList());

    return gameObject.get(0);



}










    
    
}
