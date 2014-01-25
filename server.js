var fs = require('fs');
var http = require('http');
var url = require('url') ;

var arDrone = require('ar-drone');
var client = arDrone.createClient();
//var client = arDrone.createClient({ip: '192.168.43.250'});

http.createServer(function (req, res) {
  var queryObject = url.parse(req.url,true).query;
  console.log(queryObject.command);


  if (queryObject.command == 'takeoff')
  {
	client.takeoff();
  }

  if (queryObject.command == 'land')
  {
	client.land();
  }

  if (queryObject.command == 'flipleft')
  {
   client.animate('flipLeft', 15);
  }

  if (queryObject.command == 'flipright')
  {
   client.animate('flipRight', 15);
  }

  if (queryObject.command == 'flipright')
  {
   client.animate('flipRight', 15);
  }

  if (queryObject.command == 'wave')
  {
   client.animate('wave', 15);
  }

  if (queryObject.command == 'dance')
  {
   client.animate('vzDance', 15);
  }

  if (queryObject.command == 'up')
  {
client
  .after(1000, function() {
    this.up(0.5);
  })
  .after(1500, function() {
    this.stop();
  });
  }

  if (queryObject.command == 'down')
  {
client
  .after(1000, function() {
    this.down(0.5);
  })
  .after(1500, function() {
    this.stop();
  });
  }

  if (queryObject.command == 'front')
  {
client
  .after(1000, function() {
    this.front(0.5);
  })
  .after(1000, function() {
    this.stop();
  });
  }

  if (queryObject.command == 'back')
  {
client
  .after(1000, function() {
    this.back(0.5);
  })
  .after(1000, function() {
    this.stop();
  });
  }



  res.writeHead(200);
  res.end('Send a command to the A.R. Drone using the following querystring syntax:  http://host/?command=xxx');
}).listen(8080);


