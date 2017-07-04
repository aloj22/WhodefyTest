package com.aloj.whodefytest.ui.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aloj.whodefytest.R;
import com.aloj.whodefytest.model.Message;
import com.aloj.whodefytest.util.ConfigUtils;
import com.aloj.whodefytest.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for displaying messages in chat screen
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {

    private static final int TYPE_RECEIVED_MESSAGE = 0;
    private static final int TYPE_SENT_MESSAGE = 1;


    private List<Message> mMessageList = new ArrayList<>();


    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = viewType == TYPE_RECEIVED_MESSAGE
                ? R.layout.received_message_layout
                : R.layout.sent_message_layout;
        return new MessageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.bindView(mMessageList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return mMessageList.get(position).getSenderName().equals(ConfigUtils.CURRENT_USER_NAME) ? TYPE_SENT_MESSAGE : TYPE_RECEIVED_MESSAGE;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public void clearChat() {
        mMessageList.clear();
        notifyDataSetChanged();
    }

    public void setMessages(List<Message> messages) {
        mMessageList = messages;
        notifyDataSetChanged();
    }

    public void addNewMessage(Message message) {
        mMessageList.add(message);
        notifyItemInserted(mMessageList.size());
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.message_text)
        TextView mMessageText;

        @BindView(R.id.message_time)
        TextView mMessageTime;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Message message) {
            mMessageText.setText(message.getText());
            mMessageTime.setText(Utils.formatTime(mMessageTime.getContext(), message.getTime()));
        }
    }
}
