//Express is a framework to deal with HTTP requests and REST API calls
var express = require('express');
var bodyParser = require('body-parser');
var app = express();


//Hardcoded question
//should have data formatted as follows:
//'{"quizSubject":{"quizFormat": " ", "quizQuestion" : " ", "quizAnswerChoices" : " " }}';
//quizFormat string choices: "True/False, "Multiple Choice", "Short Answer"
//if shortAnswer no quizAnswe choices needed
var JSON_String = '{"math":{"quizFormat" : "Multiple Choice","quizQuestion":"2+2=?", "quizAnswerChoiceOne" : "1","quizAnswerChoiceTwo" : "2", "quizAnswerChoiceThree" : "3", "quizAnswerChoiceFour" : "4"}}';
                                 
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
  /*var temp = JSON_Object;

  var question = req.body.quiz.question;
  var answer = req.body.quiz.answer;
  var quizName;

  JSON_String = '{"quiz":{}}';

  JSON_Object = JSON.parse(JSON_String);
  JSON_Object["quiz"]["question"] = question;
  JSON_Object["quiz"]["answer"] = answer;

  if(JSON.stringify(temp)!=='{}'){
    //TODO
  }
  res.send(JSON.stringify(JSON_Object));*/
});

app.delete('/', function (req, res){

});

app.listen(app.get("port"), function () {
    console.log('LinkedOut Node server listening on port: ', app.get("port"));
});

app.use(function(req, res, next) {
  res.status(404).send('404 page not found');
});
