package com.example.Dtu_connect.caching;

import android.provider.ContactsContract;

import java.util.Observable;

public class DiskDataSource {
    private ContactsContract.Contacts.Data data;

    public Observable getData() {
        return Observable(emitter -> {
            if (data != null) {
                emitter.onNext(data);
            }
            emitter.onComplete();
        });
    }

    public void saveToDisk(Data data) {
        this.data = data.clone();
        this.data.source = "disk";
    }

}
