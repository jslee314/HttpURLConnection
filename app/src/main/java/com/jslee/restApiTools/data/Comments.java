package com.jslee.restApiTools.data;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * https://jsonplaceholder.typicode.com/posts/1/comments
 *
 *     "postId": 1,
 *     "id": 5,
 *     "name": "vero eaque aliquid doloribus et culpa",
 *     "email": "Hayden@althea.biz",
 *     "body": "harum non quasi et ratione\ntempore iure ex voluptates in ratione\nharum architecto fugit inventore cupiditate\nvoluptates magni quo et
 */

@Getter
@Setter
public class Comments {

    @SerializedName("postId")
    private int postId;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("body")
    private String body;


//    // toString()을 Override 해주지 않으면 객체 주소값을 출력함
//    @Override
//    public String toString() {
//        return "PostResult{" +
//                "postId=" + postId +
//                ", id=" + id +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", body='" + body + '\'' +
//                '}';
//    }


}
