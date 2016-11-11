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

app.set("port", 4531);

//Hardcoded question
//should have data formatted as follows"
//'{"quizSubject":{"quizFormat": " ", "quizQuestion" : " ", "quizAnswerChoices" : " " }}';
//quizFormat string choices: "True/False, "Multiple Choice", "Short Answer"
//if shortAnswer no quizAnswe choices needed
var JSON_String = '{"math":{"quizFormat" : "True/False","quizQuestion":"Does 2+2=4?", "quizAnswerChoiceOne" : "True","quizAnswerChoiceTwo" : "False"}}';
                                 
var JSON_Object = JSON.parse(JSON_String);



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
