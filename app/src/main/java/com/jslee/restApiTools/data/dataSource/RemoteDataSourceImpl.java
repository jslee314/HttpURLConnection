package com.jslee.restApiTools.data.dataSource;

import com.jslee.restApiTools.thread.AppExecutors;

public class RemoteDataSourceImpl  implements DataSource{
    private static volatile RemoteDataSourceImpl INSTANCE;
    private AppExecutors mAppExecutors;

    private RemoteDataSourceImpl(AppExecutors appExecutors) {
        this.mAppExecutors = appExecutors;
    }

    public static RemoteDataSourceImpl getInstance(AppExecutors mAppExecutors) {
        if (INSTANCE == null) {
            synchronized (RemoteDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RemoteDataSourceImpl(mAppExecutors);
                }
            }
        }
        return INSTANCE;
    }

}
