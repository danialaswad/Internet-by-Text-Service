import {Injectable, Component} from "@angular/core";
import {AndroidPermissions} from "@ionic-native/android-permissions";

declare var window: any;

@Component({
  providers: [AndroidPermissions]
})

@Injectable()
export class SMSManager {

  constructor(private permissions: AndroidPermissions) {

  }

  fetchSMS() {
    console.log("DFIREZREZ");

    var filter = {address: '+33628760946'};

    return new Promise((resolve, reject) => {
      this.permissions.requestPermission(this.permissions.PERMISSION.READ_SMS).then(() => {
        if (window.SMS) window.SMS.listSMS(filter, data => {
          console.log(data);
          setTimeout(() => {
            resolve(data);
          }, 0)
        }, error => {
          reject(error);
        });
      }, error => {
        console.log("NO");
      });

    });
  }

  sendSMS(url) {
    let dest = '+33628760946';
    window.SMS.sendSMS(dest, url, data => {
      console.log(data)
    }, error => {
      console.log(error)
    });
  }

}
