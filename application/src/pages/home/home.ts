import {Component} from "@angular/core";
import {NavController} from "ionic-angular";
import {SMSManager} from "../../providers/SMSManager";


declare var window: any;

@Component({
  selector: 'page-home',
  templateUrl: 'home.html',
  providers: [SMSManager]
})

export class HomePage {

  websiteContent = "AZAZZAAZ";
  smsValue="";
  url=""

  constructor(public navCtrl: NavController, private sms: SMSManager) {
    this.fetchSMS();
  }

  fetchSMS() {
    this.sms.fetchSMS().then((data:any) => {
      for(var sms of data.reverse()){
        this.smsValue+=sms.body;
      }
    }, error => {
      console.log(error)
    })
  }

  sendSearch(){
    console.log(this.url);
   // this.smsValue=this.url;
  }

}
