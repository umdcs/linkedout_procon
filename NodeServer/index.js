//Express is a framework to deal with HTTP requests and REST API calls
var express = require('express');
var bodyParser = require('body-parser');
var app = express();

var JSON_String = '{}';
var JSON_Object = JSON.parse(JSON_String);

app.set("port", 4321);

app.use(bodyParser.urlencoded({   // support encoded bodies
    extended: true
}));
app.use(bodyParser.json());  // support json encoded bodies

app.get('/', function (req, res) {
  res.send('<HTML><HEAD></HEAD><BODY><H1>This is a basic Node.js server for the LinkedOut Application</H1></BODY></HTML>');
});

app.get('/quizData', function(req, res){
  res.send(JSON.stringify(JSON_Object));
});

app.post('/quizData', function (req, res) {
  if(!req.body) return res.sendStatus(400);
  var temp = JSON_Object;

  var question = req.body.quiz.question;
  var answer = req.body.quiz.answer;

  JSON_String = '{"quiz":{}}';

  JSON_Object = JSON.parse(JSON_String);
  JSON_Object["quiz"]["question"] = question;
  JSON_Object["quiz"]["answer"] = answer;

  if(JSON.stringify(temp)!=='{}'){
    //TODO
  }
  res.send(JSON.stringify(JSON_Object));
});

app.delete('/', function (req, res){

});

app.listen(app.get("port"), function () {
    console.log('LinkedOut Node server listening on port: ', app.get("port"));
});

app.use(function(req, res, next) {
  res.status(404).send('404 page not found');
});
