//Express is a framework to deal with HTTP requests and REST API calls
var express = require('express');
var bodyParser = require('body-parser');
var app = express();


//Hardcoded question
//should have data formatted as follows"
//'{"quizSubject":{"quizFormat": " ", "quizQuestion" : " ", "quizAnswerChoices" : " " }}';
//quizFormat string choices: "True/False, "Multiple Choice", "Short Answer"
//if shortAnswer no quizAnswe choices needed
var JSON_String = '{"math":{"quizFormat" : "True/False","quizQuestion":"Does 2+2=4?", "quizAnswerChoiceOne" : "True","quizAnswerChoiceTwo" : "False"}}';
                                 
var JSON_Object = JSON.parse(JSON_String);

app.set("port", 4321);

app.use(bodyParser.urlencoded({   // support encoded bodies
    extended: true
}));
app.use(bodyParser.json());  // support json encoded bodies

app.get('/', function (req, res) {
  res.send('<HTML><HEAD></HEAD><BODY><H1>This is a basic Node.js server for the LinkedOut Application</H1></BODY></HTML>');
});


//REST API Get, Gets the hardcoded string that is declared above
app.get('/quizData', function(req, res){
  console.log(JSON.stringify(JSON_Object));
  res.send(JSON.stringify(JSON_Object));
});


//REST API Post, not completed, nor is it functional, ignore what is in here
app.post('/quizData', function (req, res) {
  if(!req.body) return res.sendStatus(400);

  //console.log("POST " + res.send());
});

app.delete('/', function (req, res){

});

app.use(function(req, res, next) {
  res.status(404).send('404 page not found');
});

app.listen(app.get("port"), function () {
    console.log('LinkedOut Node server listening on port: ', app.get("port"));
});
