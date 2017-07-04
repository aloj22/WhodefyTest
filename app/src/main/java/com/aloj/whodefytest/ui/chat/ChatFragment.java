package com.aloj.whodefytest.ui.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aloj.whodefytest.R;
import com.aloj.whodefytest.model.Contact;
import com.aloj.whodefytest.model.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment for displaying chat screen
 */
public class ChatFragment extends Fragment implements ChatView {

    @BindView(R.id.send_text)
    EditText mEditText;

    @BindView(R.id.send_button)
    ImageView mSendButton;

    @BindView(R.id.chat_recycler_view)
    RecyclerView mChatRecyclerView;

    private ChatPresenter mChatPresenter;
    private ChatAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.chat_layout, container, false);

        ButterKnife.bind(this, view);
        mChatPresenter = new ChatPresenter(this);

        configViews();

        return view;
    }

    private void configViews() {
        mSendButton.setEnabled(false);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSendButton.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChatPresenter.sendMessage(mEditText.getText().toString());
            }
        });

        mAdapter = new ChatAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        mChatRecyclerView.setLayoutManager(layoutManager);
        mChatRecyclerView.setAdapter(mAdapter);
    }

    public void onContactSelected(Contact contact) {
        mChatPresenter.onContactSelected(contact);
    }

    @Override
    public void clearChat() {
        mAdapter.clearChat();
    }

    @Override
    public void clearEditText() {
        mEditText.setText("");
    }

    @Override
    public void showContactNoSelectedMessage() {
        Toast.makeText(getContext(), R.string.select_contact, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessages(List<Message> messages) {
        mAdapter.setMessages(messages);
    }

    @Override
    public void addMessage(Message message) {
        mAdapter.addNewMessage(message);
        mChatRecyclerView.scrollToPosition(0);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }
}
