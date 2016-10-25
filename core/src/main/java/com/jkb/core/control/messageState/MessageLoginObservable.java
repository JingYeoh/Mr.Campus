package com.jkb.core.control.messageState;

import com.jkb.core.control.messageState.dataSource.MessagesDataCallback;
import com.jkb.core.control.messageState.dataSource.MessagesDbLocalDataSource;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.Messages;

/**
 * 登录了的返回结果
 * Created by JustKiddingBaby on 2016/10/25.
 */

public class MessageLoginObservable implements MessageObservableAction {

    //data
    private int user_id;
    //数据来源
    private MessagesDbLocalDataSource dataSource;

    public MessageLoginObservable(MessagesDbLocalDataSource dataSource, int user_id) {
        this.dataSource = dataSource;
        this.user_id = user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public List<Messages> getAllDynamicMessage() {
        final Object messages[] = new Object[1];
        dataSource.getAllDynamicMessage(user_id, new MessagesDataCallback<List<Messages>>() {
            @Override
            public void onSuccess(List<Messages> messageObj) {
                messages[0] = messageObj;
            }

            @Override
            public void onDataNotAvailable() {
                messages[0] = new ArrayList<>();
            }
        });
        return (List<Messages>) messages[0];
    }

    @Override
    public List<Messages> getAllUnReadDynamicMessage() {
        final Object messages[] = new Object[1];
        dataSource.getAllUnReadDynamicMessage(user_id, new MessagesDataCallback<List<Messages>>() {
            @Override
            public void onSuccess(List<Messages> messageObj) {
                messages[0] = messageObj;
            }

            @Override
            public void onDataNotAvailable() {
                messages[0] = new ArrayList<>();
            }
        });
        return (List<Messages>) messages[0];
    }

    @Override
    public int getAllUnReadMessageCount() {
        final int count[] = new int[1];
        dataSource.getAllUnReadMessageCount(new MessagesDataCallback<Integer>() {
            @Override
            public void onSuccess(Integer messageObj) {
                count[0] = messageObj;
            }

            @Override
            public void onDataNotAvailable() {
                count[0] = 0;
            }
        });
        return count[0];
    }

    @Override
    public int getAllDynamicMessageCount() {
        final int count[] = new int[1];
        dataSource.getAllDynamicMessageCount(user_id, new MessagesDataCallback<Integer>() {
            @Override
            public void onSuccess(Integer messageObj) {
                count[0] = messageObj;
            }

            @Override
            public void onDataNotAvailable() {
                count[0] = 0;
            }
        });
        return count[0];
    }

    @Override
    public int getAllUnReadDynamicMessageCount() {
        final int count[] = new int[1];
        dataSource.getAllUnReadDynamicMessageCount(user_id, new MessagesDataCallback<Integer>() {
            @Override
            public void onSuccess(Integer messageObj) {
                count[0] = messageObj;
            }

            @Override
            public void onDataNotAvailable() {
                count[0] = 0;
            }
        });
        return count[0];
    }

    @Override
    public void readMessage(Messages messages) {

    }

    @Override
    public void readMessage(int messageId) {

    }

    @Override
    public void saveMessage(Messages messages) {
        dataSource.saveMessagesToDb(messages);
    }
}
