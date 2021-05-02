# restApi 3가지 방법(HttpUrlConnection, Okhttp, Retrofit)으로 구현
HttpUrlConnection, Okhttp, Retrofit을 사용하여 restApi 통신 연습
각 통신 방법별로 CRUD (post, get, put, delete)를 구현하여 Retrofit의 편리함을 느껴보자 :)


## HttpUrlConnection
java.net에 포함된 클래스로 별도의 라이브러리 추가 필요 없음
원하는 방식으로 커스텀하여 사용할수 있어 자유도가 높지만, 직접 구현해야하는 것들이 많음


## Okhttp


[블로그 설명 보러가기](https://jade314.tistory.com/entry/Android-Library-OKHttp-http)


## Retrofit을 사용하여 restApi 통신 연습
annotation 방식으로 구현이 쉬움
동기적(Synchronously)으로 경우할때는 Android 에서는 MainThread(UI Thread) 에서 네트워크 통신을 할 수 없도록 되어 있다.
비동기적(Asynchronously) 으로 구현할 경우는 자체적으로 백그라운드 스레드를 타기 때문에 그냥 구현해주면 된다.





mvvm모델로 구현(activity-viewModel-repository-datasource)
databinding, livedata 사용
비동기처리는 E














