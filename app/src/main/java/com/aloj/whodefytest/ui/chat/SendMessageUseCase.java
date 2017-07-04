package com.aloj.whodefytest.ui.chat;


import com.aloj.whodefytest.model.Message;
import com.aloj.whodefytest.util.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


/**
 * Use case for sending a message
 */
public class SendMessageUseCase {


    public Observable<Message> sendMessage(final Message message) {
        return Observable.create(new ObservableOnSubscribe<Message>() {
            @Override
            public void subscribe(final @NonNull ObservableEmitter<Message> emitter) throws Exception {
                FirebaseUtils.getMessagesReference().push().setValue(message)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@android.support.annotation.NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    emitter.onNext(message);
                                    emitter.onComplete();
                                }
                            }
                        });
            }
        });
    }

}
