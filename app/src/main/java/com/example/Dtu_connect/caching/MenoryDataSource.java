package com.example.Dtu_connect.caching;

import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;

import java.util.Observable;

import static android.provider.ContactsContract.Contacts.*;

public class MenoryDataSource {
    private Data data;

    public Observable<Data> getData() {
        return Observable.create(emitter -> {
            if (data != null) {
                emitter.onNext(data);
            }
            emitter.onComplete();
        });
    }

    public void cacheInMemory(Data data) {
        this.data = (Data) data.clone();
        
    }
}
