# Rx2Terminal (Sample + Library)
A sample project which demonstrates wrapping of "Android Terminal" code efficiently using RxJava2. This project does not cover all aspects of accessing a Android Terminal. This is just a starter project which demonstrates how easy it is to manage work with RxJava2.

Example : 
command to execute "ping -c 4 www.google.com"

In above case the output is thrown every one sec, for which if it had been implemented with Asyntask, it would require a CallBack Interface that is attached to caller Class as well as AsyncTask Class, making the code much complicated.
But with RxJava, the intermediate response of the command can be emitted "FlowableEmitter<String> emitter"

and the final result would look like 

```java
RxTerminal.execute("ping -c 4 www.google.com")
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe( this::printValue,
                      this::printError,
                      this::printCompleted));
```

By
Prathamesh Sawant.
