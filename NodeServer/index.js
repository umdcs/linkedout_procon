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

app.set("port", 4321);

//Hardcoded questions Array with two questions
var JSON_ArrayString = '{"quizzes":[{"quizFormat" : "Multiple Choice","quizQuestion":"2+2=?", "quizAnswer" : "4", "choiceList":[{"choice" : "1"},{"choice" : "2"}, {"choice" : "3"}, {"choice" : "4"}]}, {"quizFormat" : "Multiple Choice","quizQuestion":"3+3=?", "quizAnswer" : "6", "choiceList":[{"choice" : "2"},{"choice" : "3"}, {"choice" : "4"}, {"choice" : "6"}]}, {"quizFormat" : "Multiple Choice","quizQuestion":"1+1=?", "quizAnswer" : "6", "choiceList":[{"choice" : "2"}, {"choice" : "4"}, {"choice" : "6"}]}, {"quizFormat" : "Multiple Choice","quizQuestion":"7+3=?", "quizAnswer" : "6", "choiceList":[{"choice" : "2"},{"choice" : "3"}, {"choice" : "6"}, {"choice" : "4"}, {"choice" : "6"}]}, {"quizFormat" : "Multiple Choice","quizQuestion":"8-2=?", "quizAnswer" : "6", "choiceList":[{"choice" : "2"},{"choice" : "3"}, {"choice" : "4"}, {"choice" : "6"}, {"choice" : "6"}]} ]}';
                      
var JSON_String = '{"quizzes": {"quizFormat" : "Multiple Choice","quizQuestion":"2+2=?", "quizAnswer" : "4", "answerChoiceOne" : "1", "answerChoiceTwo" : "2", "answerChoiceThree" : "3", "answerChoiceFour" : "4"}}';      
                           
var JSON_ArrayObject = JSON.parse(JSON_ArrayString);
var JSON_Object = JSON.parse(JSON_String);


app.use(bodyParser.urlencoded({   // support encoded bodies
    extended: true
}));
app.use(bodyParser.json());  // support json encoded bodies

app.get('/', function (req, res) {
  res.send('<HTML><HEAD></HEAD><BODY><H1>This is a basic Node.js server for the LinkedOut Application</H1></BODY></HTML>');
});


//REST API Get, Gets the hardcoded string that is declared above *****ARRAY****
app.get('/arrayQuizData', function(req, res){
  console.log(JSON.stringify(JSON_ArrayObject));
  res.send(JSON.stringify(JSON_ArrayObject));
});

//REST API Get, Gets the hardcoded string that is declared above
app.get('/quizData', function(req, res){
  console.log(JSON.stringify(JSON_Object));
  res.send(JSON.stringify(JSON_Object));
});


//REST API Post, not completed, nor is it functional, ignore what is in here
app.post('/quizData', function (req, res) {
  if(!req.body) return res.sendStatus(400);

  console.log("POST " + JSON.stringify(req.body));
});

app.delete('/', function (req, res){

});

app.use(function(req, res, next) {
  res.status(404).send('404 page not found');
});

app.listen(app.get("port"), function () {
    console.log('LinkedOut Node server listening on port: ', app.get("port"));
});
