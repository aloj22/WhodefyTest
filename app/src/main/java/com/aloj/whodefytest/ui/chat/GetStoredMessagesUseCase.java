package com.aloj.whodefytest.ui.chat;


import com.aloj.whodefytest.model.Message;
import com.aloj.whodefytest.util.ConfigUtils;
import com.aloj.whodefytest.util.FirebaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


/**
 * Use case for retrieving messages stored in server
 */
public class GetStoredMessagesUseCase {

    private static final int MESSAGES_TO_LOAD = 10;


    public Observable<List<Message>> getStoredMessages(final String contactName) {
        return Observable.create(new ObservableOnSubscribe<List<Message>>() {
            @Override
            public void subscribe(final @NonNull ObservableEmitter<List<Message>> emitter) throws Exception {
                FirebaseUtils.getMessagesReference()
                        .limitToLast(MESSAGES_TO_LOAD)
                        .orderByChild("time")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                ArrayList<Message> storedMessages = new ArrayList<>();

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Message message = snapshot.getValue(Message.class);
                                    if (isConversationMessage(message, contactName)) {
                                        storedMessages.add(message);
                                    }
                                }

                                emitter.onNext(storedMessages);
                                emitter.onComplete();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                emitter.onComplete();
                            }
                        });
            }
        });
    }

    private boolean isConversationMessage(Message message, String contactName) {
        return (message.getSenderName().equals(contactName) && message.getReceiverName().equals(ConfigUtils.CURRENT_USER_NAME))
                || (message.getSenderName().equals(ConfigUtils.CURRENT_USER_NAME) && message.getReceiverName().equals(contactName));
    }

}
