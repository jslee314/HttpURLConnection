package com.jslee.restApiTools.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface RetrofitInterface {

    /**
     * @POST - CRUD의 Create(생성) 방식, BODY에 전송할 데이터를 담아서 서버에 생성     */


    /**
     * @GET - Read, 정보 조회용도, URL에 모두 표현 (BODY 사용x URL에 쿼리스트링 포함)      */
    @GET()
    Call<ResponseBody> getImageData(@Url String url);


    /**
     * @PUT - CRUD의 Update(변경/수정) 방식, @POST와 같이 BODY에 데이터 담아서 전송      */

    /**
     * @DELETE - CRUD의 Delete(삭제) 방식      */


//    @HEAD - 헤더 정보만 요청
//    @TRACE - 요청을 추적하여 클라이언트-서버 사이 프록시 서버에서 변경여부 확인시 사용
//    @OPTIONS - 특정 URL에 대해 지원되는 요청 메서드의 목록을 리턴, 요청URL이 *인 경우 서버 전체의 의미
}
