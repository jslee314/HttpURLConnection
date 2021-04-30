package com.jslee.restApiTools.data.dataSource;

import androidx.annotation.NonNull;

import com.jslee.restApiTools.thread.AppExecutors;

public class LocalDataSourceImpl  implements DataSource {
    private static volatile LocalDataSourceImpl INSTANCE;
    private AppExecutors mAppExecutors;

    private LocalDataSourceImpl(@NonNull AppExecutors mAppExecutors) {
        this.mAppExecutors = mAppExecutors;
    }

    /**
     * @내용 : DCL 방식으로 ManualIrisAnalysisLocalDataSourceImpl 인스턴스를 초기화하는 함수
     **/
    public static LocalDataSourceImpl getInstance(
            AppExecutors mAppExecutors) {
        if (INSTANCE == null) {
            synchronized (LocalDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSourceImpl(mAppExecutors);
                }
            }
        }
        return INSTANCE;
    }

}
