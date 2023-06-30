<h3 align = "center">Welcome to MongoGlass! </h3>
<h4 align = "center"> Your Schema analyzer for MongoDB! </h4>
<h5 align = "center"> by Paolodocet </h5>
 

1- What is MongoGlass? 

* MongoGlass is a tool written in Java to help you to understand the shape of your MongoDB schema;
* MongoGlass finds all databases on your mongo instance;
* MongoGlass finds all collections for each database;
* MongoGlass finds all fields of your collection;
* MongoGlass finds all occurrences for each field;
* MongoGlass recognizes fields with more types.

2- How to install?

* No installation needed. MongoGlass needs just JRE/JDK 8 (http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) and MongoDB 3.2+ installed on your machine.

3- How to run?

* Connect to a MongoDB instance (replica set or sharded cluster supported);
* Double click on the MongoGlass icon;
* Enjoy youself!

4- How does it work?

* MongoGlass uses map/reduce operations to discover a MongoDB collection schema. Then you can analyze occurrences for each field. 

5- It's slow to analyze collection, what can I do?

* MongoGlass uses map-reduce to analyze your schema. It needs a complete scan of your schema at all depth level.  
  Unfortunately map-reduce operations are quite slow, but (at present) they are the only way to analyze an unknown schema. 

6- Can I improve tool's performances?
 
* Yes, you can! 
* You can analyze a limited number of documents and/or set a maximum depth;
* You can also create a sharded cluster: in this way you can have amazing performances!

7- How can I limit the number of documents to analyze?

* MongoGlass provides a <i>'Settings menu'</i> where you can limit the number of documents to analyze to the first 'n' documents.
When you set a limited number of documents to analyze, a "temp_collection" with the first 'n' documents of the original collection is created and 
MongoGlass works on <i>"temp_collection".</i>
You can also set the maximum depth to analyze inserting a number between 1 and 100 (that is the maximum nested level supported by MongoDB). 

8- Do I have to delete this "temp_collection" on my own after analisys?

* No, you don't. MongoGlass saves <i>"temp_collection"</i> and deletes it automatically. 

9- What is sharding?

* Sharding is the process of writing data across different servers to distribute the read and write load and data storage requirements.
  All of those different servers create a Sharded Cluster. 	
	
10- How can I create a sharded cluster?

* It is really simple: MongoDB documentation has excellent information -> https://docs.mongodb.org/manual/tutorial/deploy-shard-cluster/

11- How many documents can I save on each shard?

* Each shard consists of some chunks, which are contiguous ranges of shard key values within a particular shard. Documents are saved into chunks.
Chunks have a default size of 64 MB, but you can reduce or increase it.
Note that if you reduce the default size, tool's performances will improve, but it may take time for all chunks to split to the new size.

12- How can I modify chunk size in a sharded cluster?

* You can see MongoDB documentation -> https://docs.mongodb.org/manual/tutorial/modify-chunk-size-in-sharded-cluster/

13- Can I create a sharded cluster on a single machine to improve tool's performances?

* Yes, you can create a single machine cluster to improve map/reduce performances. 
For a MongoDB collection with 501.513 documents, if you create a single machine cluster with 6 shards of 7 chunks of 64MB each, tool's execution time
will be fewer than half of an execution on a single machine without sharding; and MongoGlass' performances will be better if you use 16 MB chunks, but
it take some time for all chunks migration. 

14- How about a sharded cluster on multiple machines? 

* If you run MongoGlass on a multiple machines cluster, you will have very good performances; in this case, each server uses its own CPU to execute 
map/reduce. With 501.513 documents, a two machines cluster improves tool's execution time of 81% passing from 7 minutes on a single machine without 
sharding to 1 minutes and a half.

15- Which are supported S.O.?
	
* MongoGlass has been tested on:
* Windows 7 ultimate;
* Windows 8;
* Windows 8.1;
* Windows 10.
* It needs to be tested on other machines.

16- Which are libraries used by MongoGlass?
 * mongo-java-driver-2.14.1;
 * swingx-all-1.6.4;
 * JTattoo-1.6.11;
 * ini4j-0.5.1;
 * guava-18.0;
 * commons-lang3-3.4;
 * jfreechart-1.0.19;
 * jcommon-1.0.23

<h3 align="center">EXAMPLES</h3>


You will also find a simple 'dataset.json' file where there are some model design mistakes.                                                             
You can import it in mongoDB from prompt using (if you have set mongodb system variable, otherwise run command from "Z:\path\to\MongoDB\bin"):           
    
    > mongoimport --db <dbName> --collection <collName> --file "Z:\path\to\file\example.json"                                                                     

Then you can see how MongoGlass works:                                                                                                                  

 * birth, is both a Date type (8 times) and a String (1 time). - Is this a data entry mistake?                                                           
 * _id, is an ObjectId 2 times and an Int32 8 times. - Are you sure to save '_id' field as an Int32? You must be carefull to handle on your own each document _id in       order to avoid duplications.                                                      
 * age, is 6 times a Null type. - Why don't you remove this field when you don't need it instead of setting it as Null? MongoDB is schemaless, if you don't need a         field, it is useless to insert on your database.                              
 * nationality, is 3 times "American" and 1 time "american" - Datas are redundant. Which is the form of 'A(a)merican' do you want to use?                
 * title, is present just 1 time. - Why? Are you sure you need it?                                                                                       
   
   
Are there any other modelling mistakes? Are you able to find them using MongoGlass?                                                                         

<h3 align ="center"> ISSUES AND FUTURE UPDATES </h3>
                                                                                                                                                         
* Change the path for objects and arrays in tree table: instead of showing all path (e.g. object_1_Name.object_2_Name.field), show just the fieldName; 
* Two fields with the same name, but with different types are shown in the same stacked bar with different colors (e.g. string vs boolean or int32 vs int64);
* ................................................................


<h3 align="center">LEGAL TERMS </h3>

 I believe MongoGlass can't corrupt your datas but if I were you I would not use it in a production environment.                                         
 In no event I shall be liable for any loss or damage to revenues, profits, or goodwill or other special, incident, indirect or consequential damages    
 of any kind resulting from its performance or failure.                                                                                                  
