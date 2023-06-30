package mongoDB;

import java.math.RoundingMode;
import upperPanel.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Random;

import com.mongodb.AggregationOptions;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;

import treeTable.Node;

public class Settings {

	private DBCollection coll;
	private MongoDBClient client;
	private Database db;
	private int limit;
	private int depth;
	private String collName;

	public Settings(Database db, DBCollection coll, MongoDBClient client, int limit, int depth, String collName) {

		this.coll = coll;
		this.client = client;
		this.db = db;
		this.limit = limit;
		this.collName = collName;
		if(depth!= 0) {
			this.depth = depth;
		}
		else {
			this.depth = 101;
		}
	}


	public ArrayList<TypeOcc> mapReduceTypeOcc(DBCollection coll) {

		ArrayList<TypeOcc> typeOcc = new ArrayList<>();

		String isArray = "isArray = function (v) { " +
				"return v && typeof v === 'object' && typeof v.length === 'number' && !(v.propertyIsEnumerable('length'))};";

		String m_sub = "m_sub = function(base, value){ " +
				" for(var key in value) { " +
				"emit(base + \".\" + key, 1); " +
				"if( isArray(value[key]) || typeof value[key] == 'object'){ " + 
				"m_sub(base + \".\" + key, value[key])}}};";


		String map = "function(){"
				+ isArray 
				+ m_sub 
				+ "for(var key in this) {emit(key, 1);" +
				"if(isArray(this[key]) || typeof this[key] == 'object') "
				+ "{m_sub(key, this[key])}}} " ;

		String reduce = "function (key, values) { return Array.sum(values)};";

		MapReduceCommand mapcmd = new MapReduceCommand(coll, map, reduce,
				null, MapReduceCommand.OutputType.INLINE, null);

		MapReduceOutput cars = coll.mapReduce(mapcmd);


		for (DBObject o : cars.results()) {
			String field = o.get("_id").toString();
			String occurrences = o.get("value").toString();
			if(field.indexOf("_id.")==-1)
				typeOcc.add(new TypeOcc(field, occurrences));

		}

		return typeOcc;
	}


	public TreeMap<KeyMap, Integer> getSingleValues(String field, String type, DBCollection coll) {
		//metodo per i tipi unici

		DBObject query = new BasicDBObject(field, new BasicDBObject("$type",getIntType(type)));

		DBObject projKeys = new BasicDBObject();
		projKeys.put("_id", 0);
		projKeys.put(field, 1);

		DBCursor cursor = coll.find(query,projKeys);
		TreeMap<KeyMap, Integer> map = new TreeMap<>();

		while(cursor.hasNext()) {
			String myDoc = cursor.next().toString();
			if(myDoc.contains("$ts")) {
				myDoc = myDoc.substring(myDoc.indexOf("$ts")+6,myDoc.lastIndexOf("$")-1).replace("}", "").replace(",","").replace("\"","").trim();
			}
			else {
				myDoc = myDoc.substring(myDoc.lastIndexOf(" : ")+3,myDoc.length()).replace("}", "").replace("\"", "").trim();

			}
			KeyMap key = new KeyMap(myDoc, type);

			if(map.containsKey(key)) {
				map.put(key, map.get(key)+1);

			}
			else {
				map.put(key, 1);
			}
		}

		return map;

	}

	public TreeMap<KeyMap, Integer> getArrayValues(String field, String type, DBCollection coll,ArrayList<Object>node) {

		TreeMap<KeyMap, Integer> map = new TreeMap<>();

		String tempField = field;

		List<DBObject> pipeline = new ArrayList<>();
		DBObject match = new BasicDBObject("$match",new BasicDBObject(field,new BasicDBObject("$exists",1)));

		pipeline.add(match);

		for(int i = 0; i<node.size(); i++) {
			Node n = (Node) node.get(i);
			String t = (String) n.getValueAt(1);
			String f1 = (String) n.getValueAt(0);

			if(t.indexOf("Array")!= -1) {
				if(node.get(i+1) != null) {
					Node arrayNode = (Node) node.get(i+1);
					String f2 = (String) arrayNode.getValueAt(0);

					try{
						int index = Integer.parseInt(f2.replace(f1, "").replace(".", ""));


						DBObject project = new BasicDBObject("$project",new BasicDBObject(f1,
								new BasicDBObject("$arrayElemAt",Arrays.asList("$"+f1,index))));
						pipeline.add(project);

						field = field.replace(f2, f1);

						for(int j = i; j<node.size(); j++) {
							Node n1 = (Node) node.get(j);
							String f = (String) n1.getValueAt(0);
							String t1 = (String) n1.getValueAt(1);
							String occ = (String) n1.getValueAt(2);
							String op = (String) n1.getValueAt(3);


							f=f.replace(f2, f1);
							n1.setUserObject(new Object[] {f,t1,occ,op});
						}
					}
					catch (NumberFormatException ex) {
						System.out.println("Error");
					}

				}

			}
		}

		DBObject project = new BasicDBObject("$project", new BasicDBObject("field","$"+field).append("_id", 0));

		pipeline.add(project);

		AggregationOptions options = AggregationOptions.builder().outputMode(AggregationOptions.OutputMode.CURSOR).build(); 
		Iterator<DBObject> it = coll.aggregate(pipeline,options);

		while(it.hasNext()) {

			String myDoc = it.next().toString();

			if(myDoc.contains("{ \"field\" : [")) {

			}

			if(myDoc.contains("$ts")) {
				myDoc = myDoc.substring(myDoc.indexOf("$ts")+6,myDoc.lastIndexOf("$")-1).replace("}", "").replace(",","").replace("\"","").trim();
				KeyMap key = new KeyMap(myDoc, type);
				if(map.containsKey(key)) {
					map.put(key, map.get(key)+1);

				}
				else {
					map.put(key, 1);
				}
			}

			else {

				myDoc = myDoc.substring(myDoc.lastIndexOf(" : ")+3,myDoc.length()).replace("}", "").replace("\"","").trim();

				KeyMap key = new KeyMap(myDoc, type);
				if(map.containsKey(key)) {
					map.put(key, map.get(key)+1);

				}
				else {
					map.put(key, 1);
				}

			}
		}
		return map;
	}

	public TreeMap<KeyMap, Integer> getMultipleValues(String field, String type, DBCollection coll) {

		String [] types = type.split(",");

		TreeMap<KeyMap, Integer> map = new TreeMap<>();

		for(int i = 0; i<types.length; i++) {

			DBObject query = new BasicDBObject(field, new BasicDBObject("$type",getIntType(types[i])));
			DBObject projKeys = new BasicDBObject();
			projKeys.put("_id", 0);
			projKeys.put(field, 1);

			DBCursor cursor = coll.find(query,projKeys);

			while(cursor.hasNext()) {
				String myDoc = cursor.next().toString();

				if(myDoc.contains("$ts")) {
					myDoc = myDoc.substring(myDoc.indexOf("$ts")+6,myDoc.lastIndexOf("$")-1).replace("}", "").replace(",","").replace("\"","").trim();
				}
				else{
					myDoc = myDoc.substring(myDoc.lastIndexOf(" : ")+3,myDoc.length()).replace("}", "").replace("\"","").trim();
				}
				if(types[i].indexOf("Object") != -1 && types[i].length()==6) {

					myDoc = "{ " + field + " }";
				}

				KeyMap key = new KeyMap(myDoc, types[i]);

				if(map.containsKey(key)) {

					map.put(key, map.get(key)+1);

				}
				else {

					map.put(key, 1);
				}
			}			
		}

		return map;

	}

	public TreeMap<KeyMap, Integer> getMultipleValuesArray(String field, String type,DBCollection coll, int numberOfOccurrences, ArrayList<Object> node) {

		TreeMap<KeyMap, Integer> map = new TreeMap<>();

		String tempField = field;

		Node n = (Node)node.get(node.size()-1);
		String t = (String)n.getValueAt(1);

		List<DBObject> pipeline = new ArrayList<>();
		DBObject match = new BasicDBObject("$match",new BasicDBObject(field,new BasicDBObject("$exists",1)));

		pipeline.add(match);

		for(int i = 0; i<node.size(); i++) {
			Node tempNode = (Node) node.get(i);
			String typeTempNode = (String) tempNode.getValueAt(1);
			String f1 = (String) tempNode.getValueAt(0);

			if(typeTempNode.indexOf("Array")!= -1 && i+1<node.size()) {
				if(node.get(i+1) != null) {
					Node arrayNode = (Node) node.get(i+1);
					String f2 = (String) arrayNode.getValueAt(0);

					int index = Integer.parseInt(f2.replace(f1, "").replace(".", ""));

					DBObject project = new BasicDBObject("$project",new BasicDBObject(f1,
							new BasicDBObject("$arrayElemAt",Arrays.asList("$"+f1,index))));
					pipeline.add(project);

					field = field.replace(f2, f1);
					System.out.println(field);

					for(int j = i; j<node.size(); j++) {
						Node n1 = (Node) node.get(j);
						String f = (String) n1.getValueAt(0);
						String t1 = (String) n1.getValueAt(1);
						String occ = (String) n1.getValueAt(2);
						String op = (String) n1.getValueAt(3);

						f=f.replace(f2, f1);
						n1.setUserObject(new Object[] {f,t1,occ,op});
					}

				}

			}
		}

		DBObject project = new BasicDBObject("$project", new BasicDBObject("field","$"+field).append("_id", 0));

		pipeline.add(project);

		AggregationOutput output = coll.aggregate(pipeline);

		Iterator<DBObject> it = output.results().iterator();

		while(it.hasNext()) {


			String myDoc = it.next().toString();

			boolean isDouble = true;
			boolean isInt = true;
			String tp = "";

			if(type.contains("Int32") && type.contains("Int64")) {
				tp = type;
				myDoc = myDoc.substring(myDoc.indexOf("{ \"field\" : ")+11).replace("}", "").trim();
			}

			else {

				if(type.contains("Date") && myDoc.contains("{ \"field\" : { \"$date\"")) {
					myDoc = myDoc.substring(myDoc.lastIndexOf(" : ")+3,myDoc.length()).replace("}", "").replace("\"","").trim();
					tp = "Date";
				}

				else if(type.contains("RegExp") && myDoc.contains("{ \"field\" : { \"$regex\"")) {
					myDoc = myDoc.substring(myDoc.indexOf("{ \"field\" : { \"$regex\"")+25,myDoc.length()).replace("}", "").trim();
					tp = "RegExp";
				}

				else if(type.contains("BinData") && myDoc.contains("{ \"field\" : { \"$uuid\"")) {
					myDoc = myDoc.substring(myDoc.indexOf("{ \"field\" : { \"$uuid\"")+24,myDoc.length()).replace("}", "").trim();
					tp = "BinData";
				}
				else if(type.contains("Timestamp") && myDoc.contains("{ \"field\" : { \"$ts\"")) {
					myDoc = myDoc.substring(myDoc.indexOf("{ \"field\" : { \"$ts\"")+21,myDoc.lastIndexOf("$")-1).replace("}", "").replace(",","").trim();
					tp = "Timestamp";
				}
				else if(type.contains("Object") && myDoc.contains("{ \"field\" : {")) {
					tp = "Object";
					myDoc = "{" + tempField + "}";
				}
				else if(type.contains("Array") && myDoc.contains("{ \"field\" : [")) {
					tp = "Array";
					myDoc = "[" + tempField + "]";
				}

				else {
					myDoc = myDoc.substring(myDoc.lastIndexOf(" : ")+3,myDoc.length()).replace("}", "").trim();

					if(type.contains("String") && myDoc.charAt(0) == '"') {
						myDoc=myDoc.replace("\"", "");
						tp = "String";
					}
					else if(type.contains("Boolean") && myDoc.equals("true") || myDoc.equals("false")) {
						tp = "Boolean";
					}
					else if(type.contains("Null") && myDoc.equals("null")) {
						tp = "Null";
					}
					else {
						try{
							int n1 = Integer.parseInt(myDoc);

						}
						catch(NumberFormatException ex) {
							isInt=false;
						}
						try{
							Double n1 = Double.parseDouble(myDoc);
						}
						catch(NumberFormatException ex) {
							isDouble = false;
						}
						if (!isInt && isDouble && type.contains("Double")) {
							tp = "Double";
						}
						else if(type.contains("Int32")&& isInt) {
							tp = "Int32";
						}
						else if(type.contains("Int64") && !type.contains("Int32") && isInt) {
							tp = "Int64";
						}
						else {
							tp = type;
							myDoc = myDoc.substring(myDoc.indexOf("{ \"field\" : ")+11).replace("}", "").trim();

						}
					}
				}
			}
			KeyMap key = new KeyMap(myDoc, tp);
			if(map.containsKey(key)) {
				map.put(key, map.get(key)+1);

			}

			else {
				map.put(key, 1);
			}

		}
		return map;

	}


	//		else {
	//
	//			String [] typesArray = type.split(",");
	//			ArrayList<String> types = new ArrayList<>();
	//			boolean isArray = false;
	//			int count = 0;
	//
	//			for(int i = 0; i<typesArray.length; i++) {
	//
	//				if(typesArray[i] != null) {
	//					if(typesArray[i].indexOf("Array")!=-1) {
	//						isArray = true;
	//					}
	//					if(typesArray[i].indexOf("Array")==-1) {
	//						types.add(typesArray[i]);
	//					}
	//				}
	//			}
	//
	//			if(isArray) {	
	//				types.add("Array");
	//			}
	//
	//			for(int i=0; i<types.size()-1; i++) {
	//				String actualType = types.get(i);
	//
	//				DBObject query = new BasicDBObject(field, new BasicDBObject("$type",getIntType(actualType)));
	//
	//				DBObject projKeys = new BasicDBObject();
	//				projKeys.put("_id", 0);
	//				projKeys.put(field, 1);
	//
	//				DBCursor cursor = coll.find(query,projKeys);
	//
	//				while(cursor.hasNext()) {
	//					String myDoc = cursor.next().toString();
	//
	//					System.out.println(myDoc);
	//
	//					int index = myDoc.indexOf(field)+field.length()+4;
	//
	//					if(actualType.equals("Object") && myDoc.substring(index, index+1).equals("{"))  {
	//						String f = "{" + field + "}";
	//						String tp = "Object";
	//						KeyMap key = new KeyMap(f, tp);
	//						if(map.containsKey(key)) {
	//							map.put(key, map.get(key)+1);
	//							count = count +1;
	//						}
	//						else {
	//							map.put(key, 1);
	//							count = count + 1;
	//						}
	//					}
	//
	//
	//					else if(myDoc.substring(index, index+1).equals("[")) {
	//						String f = "[" + field + "]";
	//						String tp = "Array";
	//						KeyMap key = new KeyMap(f, tp);
	//						if(map.containsKey(key)) {
	//							map.put(key, map.get(key)+1);
	//							count = count +1;
	//						}
	//						else {
	//							map.put(key, 1);
	//							count = count + 1;
	//						}
	//					}
	//					else {
	//
	//						myDoc = myDoc.substring(myDoc.lastIndexOf(" : ")+3,myDoc.length()).replace("}", "").trim();
	//
	//						KeyMap key = new KeyMap(myDoc, actualType);
	//
	//						if(map.containsKey(key)) {
	//							map.put(key, map.get(key)+1);
	//							count = count +1;
	//						}
	//						else {
	//							map.put(key, 1);
	//							count = count +1;
	//						}
	//
	//					}
	//				}
	//			}
	//
	//			while(count != numberOfOccurrences) {
	//				String f = "[" + field + "]";
	//				String tp = "Array";
	//				KeyMap key = new KeyMap(f, tp);
	//				if(map.containsKey(key)) {
	//					map.put(key, map.get(key)+1);
	//					count = count +1;
	//				}
	//				else {
	//					map.put(key, 1);
	//					count = count +1;
	//
	//				}
	//
	//			}
	//		}


	public TreeMap<KeyMap, Integer> getArrayAndObject(String field, String type, DBCollection coll,ArrayList<Object>node) {
		return null;

	}



	public int countAllDocuments() {

		int count = this.coll.find().count();
		return count;
	}

	public DBCollection createTempCollection() {
		Random r = new Random();

		ArrayList<String> collections = this.db.getCollectionsName();

		DBCollection coll1 = this.db.getDb().getCollection(this.collName + "temp123456789");

		while(collections.contains(coll1.getName())) {
			int randomNumber = r.nextInt(99999999);
			coll1 = this.db.getDb().getCollection(this.collName + "temp123456789" + randomNumber);
		}

		DBCursor cursor = this.coll.find().sort(new BasicDBObject("_id",-1)).limit(this.limit);

		while(cursor.hasNext()) {
			coll1.insert(cursor.next());
		}

		return coll1;
	}

	public String fieldsRate(String fieldOccString, int documentsNumber) {

		double fieldOcc = Double.parseDouble(fieldOccString);
		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.CEILING);

		double rate = (fieldOcc/documentsNumber) * 100;

		String rateString = String.valueOf(df.format(rate)) + "%";

		return rateString;
	}

	public List<String[]> getFieldType(DBCollection coll) {

		int totalDocuments = 0;

		if(limit != 0 && limit<countAllDocuments()) {
			totalDocuments = limit;
		}
		else {
			totalDocuments = countAllDocuments();
		}

		try{
			String bsontype = "bsontype = function (a) { "+
					"if (Object.prototype.toString.call(a) === '[object BSON]') return \"Object\"; " +
					"else if(Object.prototype.toString.call(a) === '[object Number]') return \"Number\";  " + 
					"else return Object.prototype.toString.call(a).slice(8, -1)};";

			String m_subObject = "m_subObject = function(base, value)" + 
					"{ " + 
					"count = 1;" +
					"for(var key in value){ " +

					"emit(base + \".\" + key, { \"type\": bsontype(value[key]), \"count\":1});"

					+ "if(count < depth) {" 	
					+ "if(isArray(value[key]) || typeof value[key] == 'object')"
					+ "{m_sub(base + \".\" + key, value[key])};};}};";


			String m_subArray = "m_subArray = function(base, value)"
					+ "{ "
					+ "count = 1;"
					+ "for(var key in value){ " 
					+ "{" 

					+"emit(base + \".\" + key, { \"type\": bsontype(value[key]), \"count\":1});"

					+ "if(count < depth)"  
					+ "if(isArray(value[key])) "
					+ "{m_sub(base + \".\" + key, value[key])} "
					+ "else if(typeof value[key] == 'object') {"
					+ "{m_sub(base + \".\" + key, value[key])}}}}};" ;


			String m_sub = "m_sub = function(base, value) " +
					"{ " +
					"count = count+1; " + 
					"for(var key in value) { " +
					"emit(base + \".\" + key, { \"type\": bsontype(value[key]), \"count\":1});" +
					"if(count < depth) { " +
					"if(isArray(value[key])) {m_sub(base + \".\" + key, value[key])} " +
					"else if(typeof value[key] == 'object'){ m_sub(base + \".\" + key, value[key]);}}} " +
					"};";

			String map = "function(){"
					+ "var count = 0;"
					+ "var depth = " + depth + ";"
					+ bsontype 
					+ m_sub 
					+ m_subObject
					+ m_subArray 
					+ "isArray = function (v) {return v && typeof v === 'object' && typeof v.length === 'number' && !(v.propertyIsEnumerable('length'))};"
					+ "for(var key in this) {"
					+ "emit(key, {\"type\":bsontype(this[key]), \"count\":1});" +
					"if(isArray(this[key])) {m_subArray(key, this[key])} " 
					+ " else if (typeof this[key] == \"object\"){m_subObject(key, this[key])}}}";

			String reduce = "function(key, stuff){ " +
					"reduceVal = {type:'', count:0};" +
					"var array = [];" + 
					"var sum = 0;" +
					"for(var idx =0; idx < stuff.length; idx++) {" +
					"sum += stuff[idx].count;" + 

					"if(array.indexOf(stuff[idx].type) == -1)" +
					"array.push(stuff[idx].type);}" +

					"reduceVal.count=sum;" +
					"var x = array.toString();" +
					"x = Array.from( new Set(x.split(','))).toString();" +
					"reduceVal.type = x;" + 
					"return reduceVal; }";

			MapReduceCommand mapcmd = null;

			mapcmd = new MapReduceCommand(coll, map, reduce,
					null, MapReduceCommand.OutputType.INLINE, null);


			mapcmd.setSort(new BasicDBObject("_id", -1));

			MapReduceOutput cars = coll.mapReduce(mapcmd);

			List<String[]> list = new ArrayList<>();	

			for (DBObject o : cars.results()) {
				String field = o.get("_id").toString();
				DBObject obj = (DBObject) o.get("value");
				String type = obj.get("type").toString();
				String occurrences = obj.get("count").toString();

				double occDouble = Double.parseDouble(occurrences);
				int occInt = (int) occDouble;
				occurrences = String.valueOf(occInt);

				if(type.indexOf("Function") == -1 && !field.equals("_id.isObjectId") && !field.equals("_id.str")) {

					if(type.indexOf("Number") != -1) {

						DBObject queryInt = new BasicDBObject(field, new BasicDBObject("$type",16));
						DBObject docInt = coll.findOne(queryInt);
						if(docInt != null) 
							type = type.concat(",Int32");

						else {
						}

						DBObject queryDouble = new BasicDBObject(field, new BasicDBObject("$type",1));
						DBObject docDouble = coll.findOne(queryDouble);
						if(docDouble != null) 
							type = type.concat(",Double");


						DBObject queryLong = new BasicDBObject(field, new BasicDBObject("$type",18));
						DBObject docLong = coll.findOne(queryLong);
						if(docLong != null) 
							type = type.concat(",Int64");

						type = type.replace(",NumberLong", "").trim();
						type = type.replace("NumberLong,", "").trim();
						type = type.replace(",Number", "").trim();
						type = type.replace("Number,", "").trim();

					}

					String occurrenceRate = fieldsRate(occurrences,totalDocuments);
					if(Double.parseDouble(occurrences)!=0) {
						if(!type.contains("Number")) {
							list.add(new String[]{field,type,occurrences,occurrenceRate});
						}
					}
				}
			}

			return list;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}	
	public MongoDBClient getClient() {
		return client;
	}


	public DBCollection getColl() {
		return coll;
	}

	public void deleteTemp() {

		String name = this.coll.getName()+"temp123456789";

		this.db.getDb().getCollection(name).drop();
	}

	public int getIntType(String type) {
		switch (type) {
		case "MaxKey":
			return 127;
		case "MinKey":
			return -1;
		case "Double": 
			return 1;
		case "String":
			return 2;
		case "Object":
			return 3;
		case "ObjectId":
			return 7;
		case "Boolean":	
			return 8;
		case "Date":
			return 9;
		case "Null":
			return 10;
		case "Int32":	
			return 16;
		case "Timestamp":	
			return 17;
		case "Int64":
			return 18;
		case "RegExp":
			return 11;
		case "BinData":	
			return 5;
		default:
			return 2;
		}

	}


}

