package com.jslee.restApiTools.data.repository;

import android.app.Application;

import com.jslee.restApiTools.data.dataSource.DataSource;
import com.jslee.restApiTools.data.dataSource.LocalDataSourceImpl;
import com.jslee.restApiTools.data.dataSource.RemoteDataSourceImpl;
import com.jslee.restApiTools.thread.AppExecutors;

public class Repository {
    private DataSource mLocalDataSource;
    private DataSource mRemoteDataSource;
    private static volatile Repository INSTANCE;

    public Repository(Application app) {

        mLocalDataSource = LocalDataSourceImpl.getInstance(AppExecutors.getInstance());
        mRemoteDataSource = RemoteDataSourceImpl.getInstance(AppExecutors.getInstance());
    }

    public static Repository getInstance(Application app) {
        if (INSTANCE == null) {
            synchronized (Repository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Repository(app);
                }
            }
        }
        return INSTANCE;
    }
}