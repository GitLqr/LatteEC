package com.lqr.latte.ec.database;


import android.content.Context;

import org.greenrobot.greendao.database.Database;

public class DatabaseManager {

    private DaoSession mDaoSession = null;
    private UserProfileDao mUserProfileDao = null;

    private DatabaseManager() {
    }

    private static final class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    public static DatabaseManager getInstance() {
        return Holder.INSTANCE;
    }

    public DatabaseManager init(Context context) {
        initDao(context);
        return this;
    }

    private void initDao(Context context) {
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context, "latte_ec.db");
        final Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mUserProfileDao = mDaoSession.getUserProfileDao();
    }

    public final UserProfileDao getUserProfileDao() {
        return mUserProfileDao;
    }
}
