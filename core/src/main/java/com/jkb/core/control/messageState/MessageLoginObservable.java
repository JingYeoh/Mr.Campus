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
    public int getAllUnReadSubscribeMessageCount() {
        final int count[] = new int[1];
        dataSource.getAllUnReadSubscribeMessageCount(user_id, new MessagesDataCallback<Integer>() {
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
    public int getAllSubscribeMessageCount() {
        final int count[] = new int[1];
        dataSource.getAllSubscribeMessageCount(user_id, new MessagesDataCallback<Integer>() {
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
    public List<Messages> getAllSubscribeMessage() {
        final Object messages[] = new Object[1];
        dataSource.getAllSubscribeMessage(user_id, new MessagesDataCallback<List<Messages>>() {
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
    public List<Messages> getAllUnReadSubscribeMessage() {
        return null;
    }

    @Override
    public int getAllUnReadFansMessageCount() {
        final int count[] = new int[1];
        dataSource.getAllUnReadFansMessageCount(user_id, new MessagesDataCallback<Integer>() {
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
        //设置消息已被读取
        messages.setIs_read(true);
        dataSource.saveMessagesToDb(messages);
    }

    @Override
    public void readMessage(int messageId) {
        dataSource.getMessageById(messageId, new MessagesDataCallback<Messages>() {
            @Override
            public void onSuccess(Messages messageObj) {
                readMessage(messageObj);
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }

    @Override
    public void readAllSubscribeMessage() {
        dataSource.getAllUnReadSubscribeMessage(user_id, new MessagesDataCallback<List<Messages>>() {
            @Override
            public void onSuccess(List<Messages> messageObj) {
                for (Messages message :
                        messageObj) {
                    message.setIs_read(true);
                    saveMessage(message);
                }
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }

    @Override
    public void readAllFansMessage() {
        dataSource.getAllUnReadFansMessage(user_id, new MessagesDataCallback<List<Messages>>() {
            @Override
            public void onSuccess(List<Messages> messageObj) {
                for (Messages message :
                        messageObj) {
                    message.setIs_read(true);
                    saveMessage(message);
                }
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }

    @Override
    public void saveMessage(Messages messages) {
        dataSource.saveMessagesToDb(messages);
    }
}
