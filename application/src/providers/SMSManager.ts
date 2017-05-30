import {Injectable, Component} from "@angular/core";
import {AndroidPermissions} from "@ionic-native/android-permissions";

const telephone = '+33699220707';

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

    let filter = {address: telephone, maxCount:1000};

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
        console.log("SMS fetch error : ", error);
      });

    });
  }

  sendSMS(url) {
    let dest = telephone;
    window.SMS.sendSMS(dest, url, data => {
      console.log(data)
    }, error => {
      console.log(error)
    });
  }

}
