//
// This file, mongoDBFunctions.js is treated like a Node.js module.
//
// 

var mongojs = require("mongojs");           //mongo wrapper
var url = 'mongodb://localhost:27017/my_database_name'; //URL: this is for test purposes
var collections = ['documents']; //Array of known collections

var assert = require('assert');


module.exports = function() {
    mongodb = mongojs(url, collections); //creation of the mongo connection
    console.log("Connected to Mongo DB - all functions are now active.");

    /** ********************************************************************
     * printDatabase - Prints the whole collection, for debugging purposes.
     * @param collectionName - the name of the collection
     * @param callback - need to provide a function to return the data
     */
    mongodb.printDatabase = function(collectionName, callback) {
	
	// 
	// Collection look ups with find return a MongoDB 'cursor'. More info can be found here
	// https://docs.mongodb.com/v3.2/reference/glossary/#term-cursor
	// 
        var cursor = mongodb.collection(collectionName).find(function(err, docs) {
		    
		if(err || !docs) {
		    console.log("Cannot print database or database is empty\n");
		}
		else {
		    console.log(collectionName, docs);
		    callback(docs);
		}
	    });
    };


    mongodb.insertQuizData = function(quizData) {

        mongodb.collection('documents').save({quiz: quizData}, function (err, result) {
		if(err || !result) console.log ("Quiz not saved in database.");
		else console.log("Inserted a quiz into the documents collection.");
	    });
    };

    return mongodb;
}