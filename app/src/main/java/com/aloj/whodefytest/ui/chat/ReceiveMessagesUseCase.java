package com.aloj.whodefytest.ui.chat;


import com.aloj.whodefytest.model.Message;
import com.aloj.whodefytest.util.ConfigUtils;
import com.aloj.whodefytest.util.FirebaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Use case for receiving messages in real time
 */
public class ReceiveMessagesUseCase {


    public Observable<Message> receiveMessage(final String senderName) {
        return Observable.create(new ObservableOnSubscribe<Message>() {
            @Override
            public void subscribe(final @NonNull ObservableEmitter<Message> emitter) throws Exception {
                FirebaseUtils.getMessagesReference()
                        .limitToLast(1)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                                if (iterator.hasNext()) {
                                    Message message = iterator.next().getValue(Message.class);
                                    if (message != null && message.getSenderName().equals(senderName)
                                            && message.getReceiverName().equals(ConfigUtils.CURRENT_USER_NAME)) {
                                        emitter.onNext(message);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                emitter.onComplete();
                            }
                        });
            }
        });
    }

}
