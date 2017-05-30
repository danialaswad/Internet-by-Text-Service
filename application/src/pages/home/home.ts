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
  smsValue = "";
  url = ""

  constructor(public navCtrl: NavController, private sms: SMSManager) {
    this.fetchSMS();
  }

  fetchSMS() {
    this.sms.fetchSMS().then((data: any) => {
      for (var sms of data.reverse()) {
        //On concatène les SMS de la liste
        this.smsValue += sms.body;
      }
    }, error => {
      console.log(error)
    })
  }

  sendSearch() {
    for (var i = 0; i < 30; i++)
      this.sms.sendSMS(this.url);
  }

}
