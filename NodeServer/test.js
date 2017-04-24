var Curl = require( 'node-libcurl' ).Curl;
var querystring = require('querystring');
var mode = 0;

/* This is the required test for our nodejs server.
   To run, just use the command "nodejs test.js" on
   akka.

   NOTE: The async nature sometimes causes these to be
         run out of order. To solve this, change mode
	 above to individually test. (1 - run, 2 - run,
	 3 - run should definately work.)

*/


/*** TEST NEW PROFILE ***/  
function testNewProfile() {
    var curl = new Curl();
    curl.setOpt( 'URL', 'http://akka.d.umn.edu:8457/registerStudent/' );

    var data = {"email":"test@rest.com",
	        "password":"test",
	        "username":"test",
	        "fullName":"Tester",
	        "city":"St. Paul",
	        "state":"MN",
	        "gradTerm":"Spring",
	        "gradYear":"2018",
	        "major":"CS",
	        "photo":""
 	       };

    data = querystring.stringify(data);
    curl.setOpt( Curl.option.POSTFIELDS, data);
    curl.setOpt( Curl.option.HTTPHEADER, ['User-Agent: node-libcurl/1.0'] );
    curl.setOpt( Curl.option.VERBOSE, true);

    curl.on( 'end', function( statusCode, body, headers ) {

        console.log("\ncreateProfile POST test results:\n");
        console.log('Return Status: '+ statusCode );
        console.log('Response: ' + body);
        console.log('Total time: '+ this.getInfo( 'TOTAL_TIME' ) );
    
        this.close();
    });

    curl.on( 'error', function ( err, errCode ) {

        console.log("Error occured in new profile test...");

        this.close();
    });

    curl.perform();
}
/*** END NEW PROFILE TEST ***/



/*** LOGIN TEST  ***/
function testLogin() {
    curl = new Curl();

    curl.setOpt( 'URL', 'http://akka.d.umn.edu:8457/login/test@rest.com/test' );
    curl.setOpt( 'FOLLOWLOCATION', true );

    curl.on( 'end', function( statusCode, body, headers ) {

        console.log("\nlogin GET test results:\n");
        console.log('Return Status: '+ statusCode );
        console.log('Response: ' + body);
        console.log('Total time: '+ this.getInfo( 'TOTAL_TIME' ) );

        this.close();
    });

    curl.on( 'error', function ( err, errCode ) {

    //do something

        this.close();
    });

    curl.perform();
}
/*** END LOGIN TEST  ***/

/*** DELETE TEST  ***/
function testDelete() {
    curl = new Curl();

    curl.setOpt( 'URL', 'http://akka.d.umn.edu:8457/deleteProfile/test@rest.com' );
    curl.setOpt( 'FOLLOWLOCATION', false );

    curl.on( 'end', function( statusCode, body, headers ) {

        console.log("\ndeleteProfile DELETE test results:\n");
        console.info("Return status: " + statusCode );
        console.info("Response: " + body );
        console.info("Total time: " + this.getInfo( 'TOTAL_TIME' ) );

        this.close();
    });

    curl.on( 'error', function ( err, errCode ) {

        console.log("Something weird happened in delete...");

        this.close();
    });

    curl.perform();
}
/*** END DELETE TEST ***/

/* "main"  */
if(mode === 0) {
    testNewProfile();
    testLogin();
    testDelete();
} else if(mode ===1) {
    testNewProfile();
} else if(mode ===2) {
    testLogin();
} else if(mode ===3) {
    testDelete();
}
