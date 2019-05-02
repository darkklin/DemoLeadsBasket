package libry;

import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;


import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

// Download Driver - http://mongodb.github.io/mongo-java-driver/3.0/driver/getting-started/installation-guide/
public class DatabaseMongoDB {
	
	MongoClient mongoClient = null;
	MongoDatabase db = null;

	public void updateQuary() {
		
		try {
			MongoCredential credential = MongoCredential.createCredential("lbtestadmin", "admin", "".toCharArray());
			mongoClient = new MongoClient(new ServerAddress("", 27017), Arrays.asList(credential));
			db = mongoClient.getDatabase( "leadsbasket_db" );

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		
		try {
			MongoCollection<Document> collection = db.getCollection("leads");

			Bson filter = new Document("phone_number", "+9720528895514");
			Bson newValue = new Document("phone_number", 31415926);
			Bson updateOperationDocument = new Document("$set", newValue);
			collection.updateOne(filter, updateOperationDocument);
	
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
	}
}
