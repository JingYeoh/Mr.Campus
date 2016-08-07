package jkb.mrcampus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import jkb.mrcampus.db.dao.DaoMaster;
import jkb.mrcampus.db.dao.DaoSession;

/**
 * 菌菌的数据库类
 * 采用单例模式
 * Created by JustKiddingBaby on 2016/8/6.
 */

public class MrCampusDB {

    private static final String TAG = "MrCampusDB";
    public static final String DB_NAME = "mrCampus.db";

    private static MrCampusDB mInstance = null;

    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private Context applicationContext;

    private MrCampusDB() {
        Log.d(TAG, "初始化数据库...");
    }

    /**
     * 初始化环境
     *
     * @param applicationContext
     */
    public void initDb(Context applicationContext) {
        this.applicationContext = applicationContext;
        helper = new DaoMaster.DevOpenHelper(this.applicationContext, DB_NAME, null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    /**
     * 获得MrCampus对象
     * 采用DCL实现单例模式
     *
     * @return MrCampus单例实例对象
     */
    public static MrCampusDB getInstance() {
        if (mInstance == null) {
            synchronized (MrCampusDB.class) {
                if (mInstance == null) {
                    mInstance = new MrCampusDB();
                }
            }
        }
        return mInstance;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    /**
     * 得到daoSession对象
     *
     * @return
     */
    public DaoSession getDaoSession() {
        return daoSession;
    }
}
