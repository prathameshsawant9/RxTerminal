package com.leo.rxterminal.ex;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.leo.rxterminal.RxTerminal;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends Activity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String NEWLINE = "\n";
    TextView _logTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _logTxtView = findViewById(R.id.logs);

        compositeDisposable.add(
                RxTerminal.execute("ping -c 4 www.google.com")
                        .delay(5, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe( this::printValue,
                                this::printError,
                                this::printCompleted));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void printError(Throwable error){
        _logTxtView.append("Error -> " + error.getMessage() + NEWLINE);
    }

    private void printValue(String value){
        _logTxtView.append(value + NEWLINE);
    }

    private void printCompleted(){
        _logTxtView.append("onCompleted");
    }
}
