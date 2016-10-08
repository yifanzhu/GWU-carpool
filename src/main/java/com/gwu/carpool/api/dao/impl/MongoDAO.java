package com.gwu.carpool.api.dao.impl;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javassist.expr.NewArray;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import ch.qos.logback.core.filter.Filter;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.gwu.carpool.api.dao.CarpoolDAO;
import com.gwu.carpool.core.model.User;
import com.gwu.carpool.core.model.Event;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import static com.mongodb.client.model.Filters.*;

public class MongoDAO implements CarpoolDAO {


	static String dataBaseName = "carpool";
	static String userCollectionName = "user";
	static String eventCollectionName = "event";

	
	public MongoDAO(String dataBaseName){
		this.dataBaseName = dataBaseName;
	}

	public static void main(String[] args) {
		MongoDAO dao = new MongoDAO("carpool");
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		MongoCollection<Document> coll = db.getCollection(userCollectionName);
		coll.drop();
		coll = db.getCollection(eventCollectionName);
		coll.drop();
		User user1 = new User("1@gmail.com", "Luona Guo", "female",
				"2028180726", "123456", "good");
		User user2 = new User("2@gmail.com", "haha Guo", "female",
				"2028180733", "123456", "bad");
		User user3 = new User("3@gmail.com", "3 Guo", "female",
				"2028180712", "123456", "brilliant");
		User user4 = new User("4@gmail.com", "4 Guo", "female",
				"2028180712", "123456", "brilliant");
		
		dao.createUser(user1);
		dao.createUser(user2);
		dao.createUser(user3);
		dao.createUser(user4);
		

		Event evt1 = new Event("Let's go to Columbia Plaza", new Date(), 
				"2000 s eads st nw, VA", "500 23rd st NW, DC", user1, null, null, 
				"5", "this is description", "waiting", new Date());
		
		Event evt2 = new Event("Let's go to Columbia Plaza", new Date(), 
				"2000 s eads st nw, VA", "500 23rd st NW, DC", user2, null, null, 
				"5", "this is description", "waiting", new Date());
		
		dao.createEvent(evt1);
		dao.createEvent(evt2);
		
		dao.addPendingToEvent(evt1.getId(), user4);
		dao.addPassengerToEvent(evt1.getId(), user2);
		dao.addPassengerToEvent(evt1.getId(), user3);
		
		dao.addPendingToEvent(evt2.getId(), user4);
		dao.addPendingToEvent(evt2.getId(), user1);
		dao.addPassengerToEvent(evt2.getId(), user3);
		
		System.err.println("----------------------------------------------------------------");
		
//		List<Event> lhaha = dao.getEventsByUserIdAsDriver(user1.getId());
//		for(Event evt : lhaha){
//			System.err.println(evt.toString());
//		}
		
		//test changeEventStatus
		//dao.changeEventStatusById(evt1.getId(), "fuck");
		
		//dao.deleteUserById(user3.getId());
		//List<User> lu = dao.getAllUsers();
//		for(User usr : lu){
//			System.err.println(usr.toString());
//		}
//		System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		//System.err.println(dao.getEventByTitleAndPublishTimeAndDriverId(evt1.getTitle(), evt1.getPublishTime(), evt1.getDriver().getId()));
		
//		List<Event> le = dao.getEventsByDepartureTime(new Date());
//		for(Event evt : le){
//			System.err.println(evt.toString());
//		}
		
	}
	
	@Override
	public void createUser(User user) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		MongoCollection<Document> coll = db.getCollection(userCollectionName);
		Document doc = new Document("email",user.getEmail())
		               .append("gender", user.getGender())
		               .append("phone", user.getPhone())
		               .append("password", user.getPassword())
		               .append("reputation", user.getReputation())
		               .append("username", user.getUsername());
		coll.insertOne(doc);
		ObjectId oi = (ObjectId)doc.get("_id");
		user.setId(oi.toHexString());
		mongoClient.close();
	}
	
	public void createEvent(Event event){
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		MongoCollection<Document> coll = db.getCollection(eventCollectionName);

		Document driver = new Document("email",event.getDriver().getEmail())
				        .append("gender", event.getDriver().getGender())
				        .append("phone", event.getDriver().getPhone())
				        .append("password", event.getDriver().getPassword())
				        .append("reputation", event.getDriver().getReputation())
				        .append("username", event.getDriver().getUsername())
						.append("_id", new ObjectId(event.getDriver().getId()));
		Document doc = new Document("title",event.getTitle())
		               .append("startAddress", event.getStartAddress())
		               .append("endAddress", event.getEndAddress())
		               .append("capacity", event.getCapacity())
		               .append("description", event.getDescription())
		               .append("status", event.getStatus())
					   .append("departureTime",event.getDepartureTime())
					   .append("publishTime", event.getPublishTime())
		               .append("driver", driver);
		coll.insertOne(doc);
		ObjectId oi = (ObjectId)doc.get("_id");
		event.setId(oi.toHexString());
		mongoClient.close();

	}

	@Override
	public Optional<User> getUserByEmail(String email) {

		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		MongoCollection<Document> coll = db.getCollection(userCollectionName);
		
		try{
			Document doc = coll.find(new Document("email", email)).first();
			Optional<User> user = Optional.of(userDocToUser(doc));
			
			return user;
		}
		catch(Exception e){
			return Optional.empty();
		}
		finally{
			mongoClient.close();
		}
		
	}

	@Override
	public List<User> getAllUsers() {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		FindIterable<Document> iterable = db.getCollection(userCollectionName).find();
		List<User> result = new ArrayList<User>();
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		        result.add(userDocToUser(document));
		    }
		});
		mongoClient.close();
		return result;
	}
	
	public List<Event> getAllEvents(){
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		FindIterable<Document> iterable = db.getCollection(eventCollectionName).find();
		List<Event> result = new ArrayList<Event>();
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		        result.add(eventDocToEvent(document));
		    }
		});
		mongoClient.close();
		return result;

	}

	@Override
	public User updateUser(User user) {
		
		return null;
	}

	@Override
	public void deleteUserById(String userId) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		MongoCollection<Document> coll = db.getCollection(userCollectionName);
		
		Bson match = new Document("_id", new ObjectId(userId));
		coll.deleteOne(match);
		mongoClient.close();
	}

	@Override
	public List<Event> getEventsByUserIdAsDriver(String userId) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		MongoCollection<Document> coll = db.getCollection(eventCollectionName);
		
		Bson filter = new Document("driver._id", new ObjectId(userId));
		List<Document> ld = coll.find(filter).into(new ArrayList<Document>());
		List<Event> le = new ArrayList<Event>();
		for(Document d : ld){
			le.add(eventDocToEvent(d));
		}
		mongoClient.close();
		return le;
	}

	@Override

	public List<Event> getEventsByDepartureTime(Date date) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		MongoCollection<Document> coll = db.getCollection(eventCollectionName);
		
		Bson filter = new Document("departureTime", new Document("$lte", date));
		List<Document> ld = coll.find(filter).into(new ArrayList<Document>());
		List<Event> le = new ArrayList<Event>();
		for(Document d : ld){
			le.add(eventDocToEvent(d));
		}
		mongoClient.close();
		return le;
	}

	@Override
	public List<Event> getEventsByUserIdAsPending(String userId) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		MongoCollection<Document> coll = db.getCollection(eventCollectionName);
		
		Bson filter = new Document("pending._id", new ObjectId(userId));
		List<Document> ld = coll.find(filter).into(new ArrayList<Document>());
		List<Event> le = new ArrayList<Event>();
		for(Document d : ld){
			le.add(eventDocToEvent(d));
		}
		mongoClient.close();
		return le;
	}

	@Override
	public List<Event> getEventsByUserIdAsPassenger(String userId) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		MongoCollection<Document> coll = db.getCollection(eventCollectionName);
		
		Bson filter = new Document("passengers._id", new ObjectId(userId));
		List<Document> ld = coll.find(filter).into(new ArrayList<Document>());
		List<Event> le = new ArrayList<Event>();
		for(Document d : ld){
			le.add(eventDocToEvent(d));
		}
		mongoClient.close();
		return le;
	}

	@Override
	public void removePendingFromEvent(String evtId, User usr) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		MongoCollection<Document> coll = db.getCollection(eventCollectionName);
		
		Bson match = new Document("_id", new ObjectId(evtId));
		Bson update = new Document("pending", new Document("_id", new ObjectId(usr.getId())));
		coll.updateOne(match, new Document("$pull", update));
		
	}

	@Override
	public void addPendingToEvent(String evtId, User usr) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		MongoCollection<Document> coll = db.getCollection(eventCollectionName);
		Document doc = userToUserDoc(usr);
		coll.updateOne(new Document("_id", new ObjectId(evtId)), new Document("$addToSet", new Document("pending", doc)));
		mongoClient.close();
	}

	@Override
	public void addPassengerToEvent(String evtId, User usr) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		MongoCollection<Document> coll = db.getCollection(eventCollectionName);
		Document doc = userToUserDoc(usr);
		coll.updateOne(new Document("_id", new ObjectId(evtId)), new Document("$addToSet", new Document("passengers", doc)));
		mongoClient.close();
		
	}

	@Override
	public void changeEventStatusById(String eventId, String stat) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		MongoCollection<Document> coll = db.getCollection(eventCollectionName);
		
		coll.updateOne(new Document("_id", new ObjectId(eventId)), new Document("$set", new Document("status", stat)));
		mongoClient.close();
		
	}

	public static Document userToUserDoc(User user){
		Document doc = new Document("email",user.getEmail())
        .append("gender", user.getGender())
        .append("phone", user.getPhone())
        .append("password", user.getPassword())
        .append("reputation", user.getReputation())
        .append("_id", new ObjectId(user.getId()))
        .append("username", user.getUsername());
		return doc;
	}
	
	public static Event eventDocToEvent(Document document){
		Event evt = new Event();
    	evt.setCapacity(document.getString("capacity"));
    	evt.setDepartureTime(document.getDate("departureTime"));
    	evt.setDescription(document.getString("description"));
    	evt.setEndAddress(document.getString("endAddress"));
    	evt.setStartAddress(document.getString("startAddress"));
    	evt.setTitle(document.getString("title"));
    	ObjectId oi = (ObjectId)document.get("_id");
    	evt.setId(oi.toHexString());
    	evt.setPublishTime(document.getDate("publishTime"));
    	evt.setStatus(document.getString("status"));
    	Document docDriver = new Document();
    	docDriver = (Document)document.get("driver");
    	User driver = userDocToUser(docDriver);
    	evt.setDriver(driver);
    	ArrayList<User> passengers = new ArrayList<User>();
    	if(document.get("passengers") != null){
    		for(Document doc : (ArrayList<Document>)document.get("passengers")){
        		passengers.add(userDocToUser(doc));
        	}
    	}
    	evt.setPassengers(passengers);
    	
    	ArrayList<User> pending = new ArrayList<User>();
    	if(document.get("pending") != null){
    		for(Document doc : (ArrayList<Document>)document.get("pending")){
        		pending.add(userDocToUser(doc));
        	}
    	}
    	evt.setPending(pending);
    	//un done
    	return evt;
	}
	
	public static User userDocToUser(Document document){
		User user = new User();
    	user.setGender(document.getString("gender"));
    	user.setUsername(document.getString("username"));
    	ObjectId oi = (ObjectId)document.get("_id");
    	user.setId(oi.toHexString());
    	user.setPhone(document.getString("phone"));
    	user.setEmail(document.getString("email"));
        user.setPassword(document.getString("password"));
        user.setReputation(document.getString("reputation"));
		return user;
	}

	@Override
	public Event getEventByTitleAndPublishTimeAndDriverId(String title,
			Date publishtime, String driverId) {
		Bson filter = and(eq("title", title), eq("driver._id", new ObjectId(driverId)), eq("publishTime", publishtime));
		
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		MongoCollection<Document> coll = db.getCollection(eventCollectionName);
		
		Event evt = eventDocToEvent(coll.find(filter).first());
		mongoClient.close();
		return evt;
	}

	@Override
	public Event updateEvent(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Event> getEventsById(String id) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(dataBaseName);
		MongoCollection<Document> coll = db.getCollection(eventCollectionName);
		
		Bson filter = new Document("_id", new ObjectId(id));
		try{
			Document doc = coll.find(filter).first();
			Event evt = eventDocToEvent(doc);
			return Optional.of(evt);
		}
		catch(Exception e){
			return Optional.empty();
		}
		finally{
			mongoClient.close();
		}
		
	}

	
}