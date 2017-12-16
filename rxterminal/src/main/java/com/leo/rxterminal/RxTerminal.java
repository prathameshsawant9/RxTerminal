package com.leo.rxterminal;

import android.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * Created by prathamesh on 12/16/17.
 */

public final class RxTerminal {
    public static Flowable<String> execute(final String command){
        return Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {

                    try{
                        String _current = "";
                        Process terminal = Runtime.getRuntime().exec(command);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
                        while ((_current = reader.readLine()) != null)
                            emitter.onNext(_current);

                        terminal.waitFor();
                        terminal.destroy();
                        emitter.onComplete();
                    }
                    catch (Throwable throwable){
                        emitter.onError(throwable);
                    }
            }
        },BackpressureStrategy.BUFFER);
    }

    public static Flowable<Pair<Process,String>> executeAndEmitReference(final String command){
        return Flowable.create(new FlowableOnSubscribe<Pair<Process, String>>() {
            @Override
            public void subscribe(FlowableEmitter<Pair<Process,String>> emitter) throws Exception {

                try{
                    String _current = "";
                    Process terminal = Runtime.getRuntime().exec(command);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
                    while ((_current = reader.readLine()) != null)
                        emitter.onNext(new Pair<>(terminal,_current));

                    terminal.waitFor();
                    terminal.destroy();
                    emitter.onComplete();
                }
                catch (Throwable throwable){
                    emitter.onError(throwable);
                }
            }
        },BackpressureStrategy.BUFFER);
    }
}
