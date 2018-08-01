package libry;

import java.util.Arrays;
import java.util.Collection;

import org.bson.Document;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

// Download Driver - http://mongodb.github.io/mongo-java-driver/3.0/driver/getting-started/installation-guide/
public class DatabaseMongoDB {
	
	MongoClient mongoClient = null;
	MongoDatabase db = null;

	public void updateQuary() {
		
		try {
			MongoCredential credential = MongoCredential.createCredential("lbtestadmin", "admin", "TTmQX6c4Cc4xUBaX".toCharArray());
			mongoClient = new MongoClient(new ServerAddress("52.213.82.43", 27017), Arrays.asList(credential));
			db = mongoClient.getDatabase( "leadsbasket_db" );

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		
		try {
			MongoCollection<Document> collection = db.getCollection("leads");
			Document newDocument = new Document();
			newDocument.put("phone_number", "31415926");
			BasicDBObject searchQuary = new BasicDBObject().append("phone_number", "+9720528895514");
			collection.replaceOne(searchQuary, newDocument);
	
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
	}
}