package com.jslee.restApiTools.retrofit;

import com.jslee.restApiTools.data.Comments;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface RetrofitService {

    /**
     * @POST=========================================================================
     * - CRUD의 Create(생성) 방식, BODY에 전송할 데이터를 담아서 서버에 생성     */


    /**
     * @GET=========================================================================
     * - Read, 정보 조회용도, URL에 모두 표현 (BODY 사용x URL에 쿼리스트링 포함)      */

    // Call<ResponseBody>
    // Retrofit으로 받아온 원시데이터를 가공없이 출력하기
    @GET()
    Call<ResponseBody> getImageData(@Url String url);

    // https://jsonplaceholder.typicode.com/comments 100개 데이터 모두 조회
    @GET("comments")
    Call<List<Comments>> getComments();

    /**
     * @Path -  동적인 URI를 가능하게 해주는 Annotation
     *       -  @GET(EndPoint), EndPoint에서 중괄호'{ }'로 감싸진 변수에 매핑되도록 알려주는 역할
     */
    //
    // https://jsonplaceholder.typicode.com/posts?userId=10
    @GET("comments/{id}")
    Call<Comments> getComment(@Path("id") String id);


    /**
     * @Query - URI에 파라메터(쿼리스트링)를 추가해서 보낼 수 있도록 해주는 Annotation
     *       -  URI에 파라메터(쿼리)를 추가해서 원하는 데이터를 조회할 수 있는 기능
     */
    // 100개 중 userId가 10인것만 조회
    //  https://jsonplaceholder.typicode.com/posts?userId=입력값
    @GET("comments")
    Call<List<Comments>> getCommentsByPostId(@Query("postId") String postId);

    // 100개 중 userId가 10이면서 id가 96인것 조회 (다중 쿼리일 경우 &구분자 사용 해줌)
    //  : https://jsonplaceholder.typicode.com/posts?userId=입력값&id=입력값
    @GET("comments")
    Call<List<Comments>> getCommentsByPostIdAndId(
            @Query("postId") String postId,
            @Query("id") String id
    );

    /**
     * @PUT=========================================================================
     * - CRUD의 Update(변경/수정) 방식, @POST와 같이 BODY에 데이터 담아서 전송      */



    /**
     * @DELETE =========================================================================
     * - CRUD의 Delete(삭제) 방식      */


//    @HEAD - 헤더 정보만 요청
//    @TRACE - 요청을 추적하여 클라이언트-서버 사이 프록시 서버에서 변경여부 확인시 사용
//    @OPTIONS - 특정 URL에 대해 지원되는 요청 메서드의 목록을 리턴, 요청URL이 *인 경우 서버 전체의 의미
}
