package com.jkb.core.control.messageState.dataSource;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.config.Config;
import com.jkb.model.utils.LogUtils;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import jkb.mrcampus.db.MrCampusDB;
import jkb.mrcampus.db.dao.DaoSession;
import jkb.mrcampus.db.dao.MessagesDao;
import jkb.mrcampus.db.entity.Messages;

/**
 * 消息数据库的本地数据来源类
 * Created by JustKiddingBaby on 2016/10/24.
 */

public class MessagesDbLocalDataSource implements MessagesDbDataSource {

    private Context mContext;
    //数据库
    private MrCampusDB mrCampusDB;
    private DaoSession daoSession;

    private MessagesDbLocalDataSource(@NonNull Context mContext) {
        this.mContext = mContext;
        mrCampusDB = MrCampusDB.getInstance();
        daoSession = mrCampusDB.getDaoSession();
    }

    private static MessagesDbLocalDataSource INSTANCE = null;

    public static MessagesDbLocalDataSource newInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new MessagesDbLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getAllUnReadMessage(MessagesDataCallback<List<Messages>> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(MessagesDao.Properties.Is_read.eq(false));
        List<Messages> messages = messagesQueryBuilder.list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages);
        }
    }

    @Override
    public void getAllMessage(MessagesDataCallback<List<Messages>> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        List<Messages> messages = messagesQueryBuilder.list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages);
        }
    }

    @Override
    public void getAllDynamicMessage(int user_id, MessagesDataCallback<List<Messages>> callback) {
        LogUtils.d(MessagesDbLocalDataSource.class, "我要查询的user_id=" + user_id);
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(MessagesDao.Properties.User_id.eq(user_id));
        messagesQueryBuilder.whereOr(
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_LIKE),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_MAKECOMMENT),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_MAKEREPLY),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_FAVORITE));
        List<Messages> messages = messagesQueryBuilder.orderDesc(MessagesDao.Properties.Id)
                .list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages);
        }
    }

    @Override
    public void getAllUnReadDynamicMessage(int user_id, MessagesDataCallback<List<Messages>> callback) {
        LogUtils.d(MessagesDbLocalDataSource.class, "我要查询的user_id=" + user_id);
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(
                MessagesDao.Properties.Is_read.eq(false),
                MessagesDao.Properties.User_id.eq(user_id));
        messagesQueryBuilder.whereOr(
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_LIKE),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_MAKECOMMENT),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_MAKEREPLY),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_FAVORITE));
        List<Messages> messages = messagesQueryBuilder.list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages);
        }
    }


    @Override
    public void getAllDynamicMessageCount(int user_id, MessagesDataCallback<Integer> callback) {
        LogUtils.d(MessagesDbLocalDataSource.class, "我要查询的user_id=" + user_id);
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(MessagesDao.Properties.User_id.eq(user_id));
        messagesQueryBuilder.whereOr(
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_LIKE),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_MAKECOMMENT),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_MAKEREPLY),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_FAVORITE));
        List<Messages> messages = messagesQueryBuilder.list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages.size());
        }
    }

    @Override
    public void getAllUnReadDynamicMessageCount(int user_id, MessagesDataCallback<Integer> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        LogUtils.d(MessagesDbLocalDataSource.class, "我要查询的user_id=" + user_id);
        messagesQueryBuilder.where(
                MessagesDao.Properties.User_id.eq(user_id),
                MessagesDao.Properties.Is_read.eq(false));
        messagesQueryBuilder.whereOr(
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_LIKE),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_MAKECOMMENT),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_MAKEREPLY),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_FAVORITE));
        List<Messages> messages = messagesQueryBuilder.list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages.size());
        }
    }

    @Override
    public void getAllUnReadMessageCount(int user_id, MessagesDataCallback<Integer> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> qb = messagesDao.queryBuilder();
        QueryBuilder<Messages> qb2 = messagesDao.queryBuilder();
        qb2.where(MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_SYSTEM),
                MessagesDao.Properties.Is_read.eq(false));
        qb.where(
                MessagesDao.Properties.User_id.eq(user_id),
                MessagesDao.Properties.Is_read.eq(false));
        qb.whereOr(
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_IN_BLACK_LIST_USER),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_IN_BLACK_LIST_DYNAMIC),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_OUT_BLACK_LIST_DYNAMIC),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_OUT_BLACK_LIST_USER),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_SUBSCRIBE),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_FAVORITE),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_PAYATTENTION),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_LIKE),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_MAKECOMMENT),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_MAKEREPLY)
        );
        List<Messages> messages = qb.list();
        List<Messages> messages2 = qb2.list();
        if (messages == null && messages2 == null) {
            callback.onDataNotAvailable();
        } else {
            int sum = 0;
            if (messages2 != null) {
                sum += messages2.size();
            }
            if (messages != null) {
                sum += messages.size();
            }
            callback.onSuccess(sum);
        }
    }

    @Override
    public void getAllUnReadSubscribeMessageCount(int user_id, MessagesDataCallback<Integer> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(
                MessagesDao.Properties.User_id.eq(user_id),
                MessagesDao.Properties.Is_read.eq(false),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_SUBSCRIBE));
        List<Messages> messages = messagesQueryBuilder.list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages.size());
        }
    }

    @Override
    public void getAllSubscribeMessageCount(int user_id, MessagesDataCallback<Integer> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(
                MessagesDao.Properties.User_id.eq(user_id),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_SUBSCRIBE));
        List<Messages> messages = messagesQueryBuilder.list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages.size());
        }
    }

    @Override
    public void getAllUnReadSubscribeMessage(int user_id, MessagesDataCallback<List<Messages>> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(
                MessagesDao.Properties.User_id.eq(user_id),
                MessagesDao.Properties.Is_read.eq(false),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_SUBSCRIBE));
        List<Messages> messages = messagesQueryBuilder.list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages);
        }
    }

    @Override
    public void getAllSubscribeMessage(int user_id, MessagesDataCallback<List<Messages>> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(
                MessagesDao.Properties.User_id.eq(user_id),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_SUBSCRIBE));
        List<Messages> messages = messagesQueryBuilder.orderDesc(MessagesDao.Properties.Id).list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages);
        }
    }

    @Override
    public void getAllUnReadFansMessageCount(int user_id, MessagesDataCallback<Integer> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(
                MessagesDao.Properties.User_id.eq(user_id),
                MessagesDao.Properties.Is_read.eq(false),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_PAYATTENTION));
        List<Messages> messages = messagesQueryBuilder.list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages.size());
        }
    }

    @Override
    public void getAllUnReadFansMessage(int user_id, MessagesDataCallback<List<Messages>> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(
                MessagesDao.Properties.User_id.eq(user_id),
                MessagesDao.Properties.Is_read.eq(false),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_PAYATTENTION));
        List<Messages> messages = messagesQueryBuilder.list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages);
        }
    }

    @Override
    public void getAllCircleMessages(int user_id, MessagesDataCallback<List<Messages>> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(
                MessagesDao.Properties.User_id.eq(user_id));
        messagesQueryBuilder.whereOr(
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_IN_BLACK_LIST_USER),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_IN_BLACK_LIST_DYNAMIC),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_OUT_BLACK_LIST_DYNAMIC),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_OUT_BLACK_LIST_USER)
        );
        List<Messages> messages = messagesQueryBuilder.orderDesc(MessagesDao.Properties.Id).list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages);
        }
    }

    @Override
    public void getAllUnReadCircleMessages(int user_id, MessagesDataCallback<List<Messages>> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(
                MessagesDao.Properties.User_id.eq(user_id),
                MessagesDao.Properties.Is_read.eq(false));
        messagesQueryBuilder.whereOr(
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_IN_BLACK_LIST_USER),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_IN_BLACK_LIST_DYNAMIC),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_OUT_BLACK_LIST_DYNAMIC),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_OUT_BLACK_LIST_USER)
        );
        List<Messages> messages = messagesQueryBuilder.list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages);
        }
    }

    @Override
    public void getAllUnReadCircleMessagesCount(int user_id, MessagesDataCallback<Integer> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(
                MessagesDao.Properties.User_id.eq(user_id),
                MessagesDao.Properties.Is_read.eq(false));
        messagesQueryBuilder.whereOr(
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_IN_BLACK_LIST_USER),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_IN_BLACK_LIST_DYNAMIC),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_OUT_BLACK_LIST_DYNAMIC),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_OUT_BLACK_LIST_USER)
        );
        List<Messages> messages = messagesQueryBuilder.list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages.size());
        }
    }

    @Override
    public void getAllCircleMessagesCount(int user_id, MessagesDataCallback<Integer> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(
                MessagesDao.Properties.User_id.eq(user_id));
        messagesQueryBuilder.whereOr(
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_IN_BLACK_LIST_USER),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_IN_BLACK_LIST_DYNAMIC),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_OUT_BLACK_LIST_DYNAMIC),
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_OUT_BLACK_LIST_USER)
        );
        List<Messages> messages = messagesQueryBuilder.list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages.size());
        }
    }

    @Override
    public void getAllSystemMessageCount(MessagesDataCallback<Integer> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_SYSTEM));
        List<Messages> messages = messagesQueryBuilder.list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages.size());
        }
    }

    @Override
    public void getAllUnReadSystemMessageCount(MessagesDataCallback<Integer> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(
                MessagesDao.Properties.Action.eq(Config.MESSAGE_ACTION_SYSTEM),
                MessagesDao.Properties.Is_read.eq(false));
        List<Messages> messages = messagesQueryBuilder.list();
        if (messages == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onSuccess(messages.size());
        }
    }

    @Override
    public void saveMessagesToDb(Messages messages) {
        daoSession.insertOrReplace(messages);
    }

    @Override
    public void getMessageById(int message_id, MessagesDataCallback<Messages> callback) {
        MessagesDao messagesDao = daoSession.getMessagesDao();
        QueryBuilder<Messages> messagesQueryBuilder = messagesDao.queryBuilder();
        messagesQueryBuilder.where(
                MessagesDao.Properties.Id.eq(message_id));
        List<Messages> list = messagesQueryBuilder.list();
        if (list != null && list.size() > 0) {
            callback.onSuccess(list.get(0));
        } else {
            callback.onDataNotAvailable();
        }
    }
}
