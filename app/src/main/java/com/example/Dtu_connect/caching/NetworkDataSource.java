package com.example.Dtu_connect.caching;

public class NetworkDataSource {
    public Observable<Data> getData() {
        return Observable.create(emitter -> {
            Data data = new Data();
            data.source = "network";
            emitter.onNext(data);
            emitter.onComplete();
        });
    }
}
