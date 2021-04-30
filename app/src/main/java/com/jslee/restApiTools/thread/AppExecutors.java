/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jslee.restApiTools.thread;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lombok.Getter;

/**
 * Executors
 * : 스레드를 직접적으로 다루는 가장 최상위 API이다.
 * Java에선 java.util.concurrent.Executors와 java.util.concurrent.ExecutorService를 제공하며 이를 이용하면 간단하게 스레드 풀을 생성하여 병렬 처리 할 수 있다.
 * Executors를 사용하면 개발자가 직접 스레드를 만들 필요가 없다는 것이다.
 * Executors가 Factory Method를 제공함으로 ExecutorService Instance를 얻는 것은 메서드 호출만 하면 된다.
 * Runnable처럼 Executors는 Callable을 제공한다.
 * Executors의 리턴값은 Future를 이용해서 받을 수 있다.
 *
 *
 **/
/**
* @내용 :  Application 에서 사용하는 쓰레드들을 쓰레드 풀로 관리하는 클래스
 *  * MainThread: 1개, DiskIOThread : 1개, NetworkIOThread: 3개
* @수정 :
* @버젼 : 0.0.0
* @최초작성일 : 2021-04-30 오후 1:02
* @작성자 : 이재선
**/
public class AppExecutors {

    private static final int THREAD_COUNT = 3;

    @Getter private final Executor diskThread;
    @Getter private final Executor networkThread;
    @Getter private final Executor taskThread;
    @Getter private final Executor mainThread;

    @VisibleForTesting
    private AppExecutors(Executor diskThread,
                         Executor networkThread,
                         Executor taskThread,
                         Executor mainThread) {
        this.diskThread = diskThread;
        this.networkThread = networkThread;
        this.taskThread = taskThread;
        this.mainThread = mainThread;
    }

    private AppExecutors() {
        this(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new MainThreadExecutor());
    }

    private static class SingleToneHolder {
        static final AppExecutors instance = new AppExecutors();
    }

    public static AppExecutors getInstance() {
        return SingleToneHolder.instance;
    }

       private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
