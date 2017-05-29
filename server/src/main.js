const gsm = require('../node_modules/gsm2');
const Message  = require('../node_modules/gsm2/pdu');


const modem = new gsm.Modem('/dev/gsm-modem');

modem.open(function(err){

    modem.on('message', function(message){
        console.log('gsm modem received sms message',message);
    });

    modem.on('ring', function(number){
        cnsole.log('gsm modem have a phone call from %s', number);
    });

    var message = new Message('+8618510100102', 'Hello GSM Modem!');
    modem.sendSMS(message, function(err){
        console.log('gsm modem: message sent!');
    });

    modem.getSMS(function(err, messages){
        console.log(messages);
    });

    modem.delSMS(function(err){
        console.log('all messages was deleted');
    });

    modem.call('+8618510100102', function(err){
        //err: busy or hangup
        setTimeout(function(){
            modem.hangup();
        }, 3000);
    });


    modem.GPRS('apn', 'user', 'pass', function(err, request){

        request.get('http://api.lsong.org/ip', function(err, response){
            console.log(err, response);
        })

    });

});