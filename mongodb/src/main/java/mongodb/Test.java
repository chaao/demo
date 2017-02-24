package mongodb;

import org.bson.Document;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * @author lichao
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MongoCredential mongoCredential = MongoCredential.createCredential("didi", "didi", "456".toCharArray());
		MongoClient client = new MongoClient(new ServerAddress("182.92.191.75", 28000), Lists.newArrayList(mongoCredential));
		
		for (String dbName : client.listDatabaseNames()) {
			System.out.println(dbName);
		}
		
		
//		MongoDatabase db = client.getDatabase("test");
//		
//		System.out.println("collection:");
//		for (String name : db.listCollectionNames()) {
//			System.out.println(name);
//		}
//		
//		 Block<Document> printBlock = new Block<Document>() {
//	            @Override
//	            public void apply(final Document document) {
//	                System.out.println(document.toJson());
//	            }
//	        };
//
//		MongoCollection<Document> collection = db.getCollection("test");
//		collection.drop();
//		insert(collection);
		
//		collection.drop();
		System.exit(0);
	}
	
	
	public static void insert(MongoCollection<Document> collection){
//		Document doc = new Document("name", "MongoDB").append("type", "database").append("count", 1)
//				.append("info", new Document("x", 203).append("y", 102));
		Document doc = new Document("i",100);
		collection.insertOne(doc);
	}

}
