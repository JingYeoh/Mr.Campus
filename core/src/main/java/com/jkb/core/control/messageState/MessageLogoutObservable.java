package com.jkb.core.control.messageState;

import com.jkb.core.control.messageState.dataSource.MessagesDataCallback;
import com.jkb.core.control.messageState.dataSource.MessagesDbLocalDataSource;

import java.util.List;

import jkb.mrcampus.db.entity.Messages;

/**
 * 未登录的返回结果
 * Created by JustKiddingBaby on 2016/10/25.
 */

public class MessageLogoutObservable implements MessageObservableAction {

    private MessagesDbLocalDataSource dataSource;

    public MessageLogoutObservable(MessagesDbLocalDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Messages> getAllDynamicMessage() {
        return null;
    }

    @Override
    public List<Messages> getAllUnReadDynamicMessage() {
        return null;
    }

    @Override
    public int getAllUnReadMessageCount() {
        final int count[] = new int[1];
        dataSource.getAllUnReadSystemMessageCount(new MessagesDataCallback<Integer>() {
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
        dataSource.getAllSystemMessageCount(new MessagesDataCallback<Integer>() {
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
        return 0;
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
