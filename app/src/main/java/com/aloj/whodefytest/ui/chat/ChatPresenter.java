package com.aloj.whodefytest.ui.chat;


import android.support.annotation.Nullable;

import com.aloj.whodefytest.model.Contact;
import com.aloj.whodefytest.model.Message;
import com.aloj.whodefytest.util.ConfigUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter for displaying messages in chat screen
 */
public class ChatPresenter {

    @Nullable
    private ChatView mView;

    @Nullable
    private Contact mCurrentChatContact;

    @Nullable
    private Disposable mRealTimeMessagingDisposable = null;

    private SendMessageUseCase mSendMessageUseCase;
    private GetStoredMessagesUseCase mGetStoredMessagesUseCase;
    private ReceiveMessagesUseCase mReceiveMessagesUseCase;

    public ChatPresenter(@Nullable ChatView view) {
        mView = view;
        mSendMessageUseCase = new SendMessageUseCase();
        mGetStoredMessagesUseCase = new GetStoredMessagesUseCase();
        mReceiveMessagesUseCase = new ReceiveMessagesUseCase();
    }

    public void sendMessage(String text) {
        if (mCurrentChatContact != null) {
            Message message = new Message(text, mCurrentChatContact.getName(), System.currentTimeMillis(), ConfigUtils.CURRENT_USER_NAME);
            mSendMessageUseCase.sendMessage(message)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnNext(new Consumer<Message>() {
                        @Override
                        public void accept(@NonNull Message message) throws Exception {
                            if (mView != null) {
                                mView.clearEditText();
                                mView.addMessage(message);
                            }
                        }
                    })
                    .doOnError(new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            if (mView != null) {
                                mView.showToast(throwable.getMessage());
                            }
                        }
                    })
                    .subscribe();
        } else if (mView != null) {
            mView.showContactNoSelectedMessage();
        }
    }

    public void onContactSelected(Contact contact) {
        mCurrentChatContact = contact;
        if (mView != null) {
            mView.clearChat();
            mView.clearEditText();
        }

        if (mCurrentChatContact != null) {
            mGetStoredMessagesUseCase.getStoredMessages(mCurrentChatContact.getName())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnNext(new Consumer<List<Message>>() {
                        @Override
                        public void accept(@NonNull List<Message> messages) throws Exception {
                            if (mView != null) {
                                mView.showMessages(messages);
                            }
                        }
                    })
                    .doOnError(new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            if (mView != null) {
                                mView.showToast(throwable.getMessage());
                            }
                        }
                    }).subscribe();

            if (mRealTimeMessagingDisposable != null && !mRealTimeMessagingDisposable.isDisposed()) {
                mRealTimeMessagingDisposable.dispose();
            }

            mRealTimeMessagingDisposable = mReceiveMessagesUseCase.receiveMessage(mCurrentChatContact.getName())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnNext(new Consumer<Message>() {
                        @Override
                        public void accept(@NonNull Message message) throws Exception {
                            if (mView != null) {
                                mView.addMessage(message);
                            }
                        }
                    })
                    .doOnError(new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            if (mView != null) {
                                mView.showToast(throwable.getMessage());
                            }
                        }
                    }).subscribe();
        }
    }
}
