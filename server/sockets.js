var express = require('express'),
        app = express(),
        server1 = require('http').Server(app),
        server2 = require('http').Server(app),
        io1  = require('socket.io')(server1),
        io2 = require('socket.io')(server2),
        port1   = 1234,
        port2   = 1235,
        socket1 = null,
        socket2 = null;

//Listening port 1234
server1.listen(port1, function() {  
    console.log('Servidor corriendo en http://localhost:1234');
});

//Listening port 1235
server2.listen(port2, function() {  
    console.log('Servidor corriendo en http://localhost:1235');
}); 

io1.on('connection', function(socket) {
    if(socket1 == null){
        console.log("NEW SOCKET 1");
        socket1 = socket;
        socket1.emit("connected","New player socket 1");
		socket1.on('disconnect', function(socket) {
			console.log("Closing socket 2");
			if(socket2 != null){
				socket1 = socket2;
            	socket2 = null;
            	socket1.emit("warning","Waiting for another player");
			}
	    });

        socket1.on('message', function(data) {
            console.log("Sending from 1 to 2");

            if(socket2 != null){
            	socket2.emit('receive',data);
            }

        });
    }
    else{
        console.log("Match completed");
    }
    
});
   
io2.on('connection', function(socket) {
    if(socket2 == null){
        console.log("NEW SOCKET 2");
        socket2 = socket;
        socket2.emit("connected","New player socket 2");
		socket2.on('disconnect', function(socket) {
			console.log("Closing socket 2");
        	socket2 = null;
        	if(socket1 != null){
        		socket1.emit("warning","Waiting for another player");
        	}
	    });

        socket2.on('message', function(data) {
            console.log("Sending from 2 to 1");
            if(socket1 != null){
            	socket1.emit('receive',data);
            }
            
        });
    }
    else{
        console.log("Match completed");
    }
    
});