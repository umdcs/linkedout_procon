//Express is a framework to deal with HTTP requests and REST API calls
var express = require('express');
var bodyParser = require('body-parser');
var app = express();

// ///////////// IMPORTANT Port Information //////////////
// The port may be determined by where we are running our code. The CS machine
//     lempo.d.umn.edu
// is set to run your node server on TLS/SSL port 4532 (or your assigned port + 1). It
// then redirects all traffic internally to your actual port, which is 4531. Your server
// now needs to respond to port 4531. You can still run your node server on your local
// machine under port 4531, but access to it will differ on the Android side.
//
// If you want to test locally, you can still use localhost:4531 if you're running it on
// your own machine, but if you want to access the version of your server running on lempo,
// you will need to access
//    https://lempo.d.umn.edu:4532
//
// Again, my server routes all your traffic through SSL/TLS so it IS encrypted. However, it
// redirects internally on lempo to your actual port number 4531.

/**** UPDATE Spring 2017 ******
 *  I'm leaving the above just for documentations sake. Currently the port below is
 *  set to 8457 and will run on akka.
 *  
 *  
 *  Should look into 'forever' as well as 'screens'
*/

app.set("port", 8457);

var JSON_ArrayString;
var JSON_ArrayObject;

// Parallel arrays to store user data
var arrayOfStudents = {
    usernames: [],
    passwords: [],
    names: [],
    emails: [],
    cities: [],
    states: [],
    gradTerms: [],
    gradYears: [],
    majors: [],
    studentPhotos: [],
    skillList: []
};

/* Example skillList Object Entry:
   var listObject = 
   { skill: [],
     desc: [],
     how: []
   }
*/


//Hardcoded questions Array with two questions
//var JSON_ArrayStringH = '{"quizzes":[{"quizFormat" : "Multiple Choice","quizQuestion":"2+2=?", "quizAnswer" : "4", "choiceList":[{"choice" : "1"},{"choice" : "2"}, {"choice" : "3"}, {"choice" : "4"}]}, {"quizFormat" : "Multiple Choice","quizQuestion":"3+3=?", "quizAnswer" : "6", "choiceList":[{"choice" : "2"},{"choice" : "3"}, {"choice" : "4"}, {"choice" : "6"}]}, {"quizFormat" : "Multiple Choice","quizQuestion":"1+1=?", "quizAnswer" : "6", "choiceList":[{"choice" : "2"}, {"choice" : "4"}, {"choice" : "6"}]}, {"quizFormat" : "Multiple Choice","quizQuestion":"7+3=?", "quizAnswer" : "6", "choiceList":[{"choice" : "2"},{"choice" : "3"}, {"choice" : "6"}, {"choice" : "4"}, {"choice" : "6"}]}, {"quizFormat" : "Multiple Choice","quizQuestion":"8-2=?", "quizAnswer" : "6", "choiceList":[{"choice" : "2"},{"choice" : "3"}, {"choice" : "4"}, {"choice" : "6"}, {"choice" : "6"}]} ]}';
//const defaulyJSON = JSON_ArrayStringH;


app.use(bodyParser.urlencoded({   // support encoded bodies
    extended: true
}));

// Had to up the limit to allow photos to be stored
app.use(bodyParser.json({limit: '10mb', type: 'application/json'}));  // support json encoded bodies

// This dashboard is used for debugging, obviously it should not
// display passwords etc. if it is planned to be used other than for prototyping
app.get('/', function (req, res) {
    res.write('<HTML><HEAD></HEAD><BODY><H1>This is a basic Node.js server for the LinkedOut Application</H1></BODY></HTML>');
    res.write("Emails: "+JSON.stringify(arrayOfStudents.emails) +"\nPasswords: "+JSON.stringify(arrayOfStudents.passwords)+"\nSkills: "+JSON.stringify(arrayOfStudents.skillList) );
    res.end();
});

//REST API Get, Gets the hardcoded string that is declared above *****ARRAY****
app.get('/arrayQuizData', function(req, res){
  console.log(JSON.stringify(JSON_ArrayObject));
  res.send(JSON.stringify(JSON_ArrayObject));
});

/////////////////////////////may use later
 /*
  '{"quizzes":{}}' was the default json object in my previous project
  if(JSON.stringify(temp)!=='{"quizzes":{}}'){
        var tempString = JSON.stringify(JSON_Object);
        tempString = tempString.substr(0, tempString.length - 1);
        tempString = tempString + ',' + JSON.stringify(temp).substr(1, JSON.stringify(temp).length);
        JSON_Object = JSON.parse(tempString);
    }
    res.send(JSON.stringify(JSON_Object));
    */
////////////////////////////////////////////////////////
//REST API Post, not completed, nor is it functional, ignore what is in here
app.post('/arrayQuizData', function (req, res) {
  if(!req.body) return res.sendStatus(400);


  console.log("POST " + JSON.stringify(req.body));
  JSON_ArrayString = JSON.stringify(req.body);
  console.log(JSON_ArrayString);
  JSON_ArrayObject = JSON.parse(JSON_ArrayString);
  console.log("Fully transferred to node server");

});

/** Register Student uri **
    To store a new user's info on the server, the POST request must
    be accompanied by a JSONObject with each member listed below.
    If the EMAIL is in use, this uri returns an error message.

    Also, the blank listObject is needed to keep the arrays parallel.
*/
app.post('/registerStudent' , function(request, response) {
    console.log("POST request - registerStudent");
    if(!request.body) return response.sendStatus(400);
    
    var email = request.body.email;
    
    if(arrayOfStudents.emails.indexOf(email) === -1 ) { 
        arrayOfStudents.usernames.push(request.body.username);
        arrayOfStudents.passwords.push(request.body.password);
        arrayOfStudents.names.push(request.body.fullName);
        arrayOfStudents.emails.push(email);
        arrayOfStudents.cities.push(request.body.city);
        arrayOfStudents.states.push(request.body.state);
        arrayOfStudents.gradTerms.push(request.body.gradTerm);
        arrayOfStudents.gradYears.push(request.body.gradYear);
        arrayOfStudents.majors.push(request.body.major);
	arrayOfStudents.studentPhotos.push(request.body.photo);

	// Initialize the skill list for the user at the same time,
	// so as to keep the indicies aligned...
	var emptyList = {
	    skill:[],
	    desc:[],
	    how:[]
	}

	// Use this if limiting to x Skills...
	// Initialize it to "" to be able to
	// easily splice later...
	var x = 5;
	for(x; x>0; x--){
	    emptyList.skill.push("");
	    emptyList.desc.push("");
	    emptyList.how.push("");
	}
	
	arrayOfStudents.skillList.push(emptyList);
	
	response.write("Student profile created!");
//        console.log(JSON.stringify(arrayOfStudents) );
        response.end();
    }
    else {
        response.write({"ERROR":"Email is in use..."});
        response.end();
    }
});


/* This POST uri will update the skills for a user.
   The user's email is used to find the index.
   If no skill is assigned, the JSONObject should
   contain "" for that skill.
*/
app.post('/addSkill', function(request, response) {
    console.log("addSkills");
    if(!request.body) return response.sendStatus(400);

    // Find the associated user skillList object
    var index = arrayOfStudents.emails.indexOf(request.body.email);
    var skillObject = arrayOfStudents.skillList[index];
    
    // Add all the skills, should be "" if no skill for that 'slot' yet
    //Skill 1
    skillObject.skill.splice(0, 1, request.body.skillName1);
    skillObject.desc.splice(0, 1, request.body.skillDesc1);
    skillObject.how.splice(0, 1, request.body.skillHow1);
    //Skill 2
    skillObject.skill.splice(1, 1, request.body.skillName2);
    skillObject.desc.splice(1, 1, request.body.skillDesc2);
    skillObject.how.splice(1, 1, request.body.skillHow2);
    //Skill 3
    skillObject.skill.splice(2, 1, request.body.skillName3);
    skillObject.desc.splice(2, 1, request.body.skillDesc3);
    skillObject.how.splice(2, 1, request.body.skillHow3);
    //Skill 4
    skillObject.skill.splice(3, 1, request.body.skillName4);
    skillObject.desc.splice(3, 1, request.body.skillDesc4);
    skillObject.how.splice(3, 1, request.body.skillHow4);
    //Skill 5
    skillObject.skill.splice(4, 1, request.body.skillName5);
    skillObject.desc.splice(4, 1, request.body.skillDesc5);
    skillObject.how.splice(4, 1, request.body.skillHow5);
    // Done adding skills!
    
    response.write("Skills added:");
    response.end();
    console.log(JSON.stringify(skillObject));
});

/* POST to update saved setting of a user. Uses the old email to
 * determine the index.
 */
app.post('/updatePrefs' , function(request, response) {
    console.log("POST request - updatePrefs");
    if(!request.body) return response.sendStatus(400);
    
    var oldEmail = request.body.oldEmail;
    var proIndex = arrayOfStudents.emails.indexOf(oldEmail);
    
    if( proIndex !== -1 ) { 
        arrayOfStudents.usernames.splice(proIndex, 1, request.body.username);
        arrayOfStudents.passwords.splice(proIndex, 1, request.body.password);
        arrayOfStudents.names.splice(proIndex, 1, request.body.fullName);
        arrayOfStudents.emails.splice(proIndex, 1, request.body.email);
        arrayOfStudents.cities.splice(proIndex, 1, request.body.city);
        arrayOfStudents.states.splice(proIndex, 1, request.body.state);
        arrayOfStudents.gradTerms.splice(proIndex, 1, request.body.gradTerm);
        arrayOfStudents.gradYears.splice(proIndex, 1, request.body.gradYear);
        arrayOfStudents.majors.splice(proIndex, 1, request.body.major);
	arrayOfStudents.studentPhotos.splice(proIndex, 1, request.body.photo);

	response.write("Student profile updated!");
        response.end();
    }
    else {
        response.write({"ERROR":"Bad email"});
        response.end();
    }
});

/* GET uri for retireving student data
 * @params request.body.username is the username to 
 *         retirve data for.
 *
 * @returns JSONArray containing user data
 */
app.get('/login/:p0/:p1', function(request, response) {
    console.log("GET request - login");

    if(!request.body) {
	console.log("No body!\n");
	response.end();
    }
    
    var email = request.params.p0;
    var password = request.params.p1;
    console.log(JSON.stringify(request.params) );
    console.log(email + " " + password);
/*    	var logstr = '';
	for(var elemName in request.body) {
	    logstr = logstr + "[" + elemName + ": " + request.body[elemName] + "] ";
	}
    
    console.log("Debug: " + logstr); */
        
    var profileIndex = arrayOfStudents.emails.indexOf(email);
    var skillObject = arrayOfStudents.skillList[profileIndex];
    var returnObject;

    // Username is not in use...
    if(profileIndex === -1) {
	returnObject = {"ERROR":"Email not in database!"};
        response.write(JSON.stringify(returnObject)) ;
	response.end();		      
    }

    // Password does not match stored password!
    else if(password !== arrayOfStudents.passwords[profileIndex]) {
	returnObject = {"ERROR":"Incorrect email or password."};
	response.write(JSON.stringify(returnObject));
	response.end();
    }

    // If no return errors by this point, send back a full user profile
    else {
        returnObject = {"username": arrayOfStudents.usernames[profileIndex],
			"password": arrayOfStudents.passwords[profileIndex],
			"fullName": arrayOfStudents.names[profileIndex],
			"email": arrayOfStudents.emails[profileIndex],
		  	"city": arrayOfStudents.cities[profileIndex],
			"state": arrayOfStudents.states[profileIndex],
			"gradTerm": arrayOfStudents.gradTerms[profileIndex],
			"gradYear": arrayOfStudents.gradYears[profileIndex],
			"major": arrayOfStudents.majors[profileIndex],
			"photo": arrayOfStudents.studentPhotos[profileIndex],

			// Skill stuff...
			"skill1": skillObject.skill[0],
			"obtained1": skillObject.how[0],
			"description1": skillObject.desc[0],
			"skill2": skillObject.skill[1],
			"obtained2": skillObject.how[1],
			"description2": skillObject.desc[1],
			"skill3": skillObject.skill[2],
			"obtained3": skillObject.how[2],
			"description3": skillObject.desc[2],
			"skill4": skillObject.skill[3],
			"obtained4": skillObject.how[3],
			"description4": skillObject.desc[3],
			"skill5": skillObject.skill[4],
			"obtained5": skillObject.how[4],
			"description5": skillObject.desc[4]
		       };
       response.write(JSON.stringify(returnObject) );
	console.log("Returning JSON object with user data!");
	console.log("Skills: " + JSON.stringify(skillObject));
       response.end();
    }
    
//    console.log(JSON.stringify(returnObject) );
});

/* Takes in an email and deletes the associated profile
 *
 * @param email - the email to delete the profile of
 *
 * uses splice() method to remove, reference can be found here:
 * https://www.w3schools.com/jsref/jsref_splice.asp
 *
 * For some reason, it will only delete if a get request... Fix if possible!!
 */
app.get('/deleteProfile/:email', function (req, res){
    var email = req.params.email;
    var index = arrayOfStudents.emails.indexOf(email);
    console.log("Deleting profile: " +email+".\n");
    // Verify the email is part of a valid profile
    if(index === -1) {
	res.write({"ERROR":"Email not in database!"});
	res.end();
    }
    else {
	arrayOfStudents.usernames.splice(index, 1);
	arrayOfStudents.emails.splice(index, 1);
	arrayOfStudents.passwords.splice(index, 1);
	arrayOfStudents.names.splice(index, 1);
	arrayOfStudents.cities.splice(index, 1);
	arrayOfStudents.states.splice(index, 1);
	arrayOfStudents.gradTerms.splice(index, 1);
	arrayOfStudents.gradYears.splice(index, 1);
	arrayOfStudents.majors.splice(index, 1);
	arrayOfStudents.studentPhotos.splice(index, 1);
	
	res.send("Profile successfully deleted!");
    }

});

app.use(function(req, res, next) {
  res.status(404).send('404 page not found');
});

app.listen(app.get("port"), function () {
    console.log('LinkedOut Node server listening on port: ', app.get("port"));   
});
