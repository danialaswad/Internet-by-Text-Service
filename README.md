# Internet by Text Service (ITS)


ITS is a service that allows users to surf the internet using only SMS through our application. The application is currently developped on Android while the server is running on Java.

# New Features!
  - Users can type in an URL in the application
  - Application display a simple view (without css) of a webpage
  - Large website are trim into chunks and sent package by package to optimize the transmission duration
  - all package are compressed before send to reduce the size

You can also:
  - Jump from one page to another (on page links are clickable)
  - Click on next to receive the sequence of a webpage
<<<<<<< HEAD


=======
>>>>>>> 155000b1b2869767c4a13f285e64b8b50c70bfa6

### Tech

ITS uses a number of open source projects to work properly:

* [Android Studio] - an IDE to develop application on android
* [Intellij IDEA] - an IDE to develop programs on JAVA
* [Jenkins] - automation server to support building, deploying and automating any project.

The server needs a [usb gsm modem](https://www.amazon.fr/Hsdpa-7-2Mbps-Adaptateur-Réseau-Dongle/dp/B01K5P0WCA/ref=sr_1_2?ie=UTF8&qid=1496396243&sr=8-2&keywords=usb+gsm+dongle) that allows the application to send and receive SMS.

And of course ITS itself is open source with a [public repository][dill]
 on GitHub.

### Installation

ITS server requires [Java jdk x86](https://nodejs.org/) and [Maven](https://maven.apache.org) to run.

```sh
$ mvn clean package
```


### Dependencies (jar)

ITS is currently extended with the following dependancies. Instructions on how to use them in your own application are linked below.

| Dependency | README |
| ------ | ------ |
| SMSLib |  [github](https://github.com/tdelenikas/smslib) |
| SMSDura | [github](https://github.com/harshadura/SMS.Dura.Wrapper) |
| RXTXCOMM | [website](https://seiscode.iris.washington.edu/projects/rxtxcomm) |
| Log4j | [website](https://logging.apache.org/log4j/2.x/) |
| JSMPP | [website](https://jsmpp.org) |



### Todos

 - Implement css on application side
 - allow pictures to be send through the service
 - Integrate twitter
 - Integrate Instagram

License
----

Polytech Nice Sophia

   [Android Studio]: <https://developer.android.com/studio/index.html>
   [Intellij IDEA]: <https://www.jetbrains.com/idea/>
   [Jenkins]: <https://jenkins.io>
   [dill]: <https://github.com/danialaswad/Internet-by-Text-Service>
<<<<<<< HEAD
   [USB GSM modem]: <https://www.amazon.fr/Hsdpa-7-2Mbps-Adaptateur-Réseau-Dongle/dp/B01K5P0WCA/ref=sr_1_2?ie=UTF8&qid=1496396243&sr=8-2&keywords=usb+gsm+dongle>
   
=======
   
>>>>>>> 155000b1b2869767c4a13f285e64b8b50c70bfa6
