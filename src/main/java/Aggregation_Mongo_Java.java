import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class Aggregation_Mongo_Java {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Creating a Mongo client 
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 ); 
		System.out.println("Created Mongo Connection successfully");
		MongoDatabase db = mongoClient.getDatabase("youtube");
		System.out.println("Get database is successful");


		MongoCollection<Document> collection = db.getCollection("channels");
		System.out.println("Database Connected");
		
		Block<Document> printBlock = document -> System.out.println(document.toJson());
		
		//Bson having =  Aggregates.match(new Document("Subscribers", new Document("$gte", 3500000)));
		Bson group = Aggregates.group("$" + "Genre", Accumulators.sum("totalSubs", "$" + "Subscribers"));
		Bson project = Aggregates.project(Projections.fields(Projections.excludeId(), Projections.include("totalSubs"), Projections.computed("Genre", "$_id")));
		Bson sort = Aggregates.sort(Sorts.descending("Subscribers"));
		Bson limit = Aggregates.limit(3);


		collection.aggregate(Arrays.asList(sort)).forEach(printBlock);
		 System.out.println("Collection Aggregrated.");
		
		/*
		 * collection.aggregate(Arrays.asList( Aggregates.match(Filters.eq("Genre",
		 * "Comedy")), Aggregates.count())).forEach(printBlock);
		 *
		 */

	}

}
