package com.ylg.myretrofittest;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface Api {

    //post请求，如果有参数需要添加 @FormUrlEncoded注解，即和@Field配合使用
    @FormUrlEncoded
    @POST("api/comments.163")
    Call<Object> postDataCall(@Field("format") String format);

    @GET("api/rand.music")
    Call<ResponseBody> getTestData(@Query("sort") String sort, @Query("format") String format);

    @GET("api/rand.music")
    Call<Data<Info>> getJsonData(@Query("sort") String sort, @Query("format") String format);

    @Multipart
    @POST("user/followers")
    Call<ResponseBody> getPartMapData(@PartMap Map<String, MultipartBody.Part> map);

    @Multipart
    @POST("user/followers")
    Call<ResponseBody> getPartData(@Part("name") RequestBody name, @Part MultipartBody.Part file);

    @Streaming
    @POST("gists/public")
    Call<ResponseBody> getStreamingBig();

    @Headers({"phone-type:android","version:1.1.1"})
    @GET("user/emails")
    Call<ResponseBody> getHeadersData();

    @GET("user/emails")
    Call<ResponseBody> getHeaderData(@Header("token") String token);

    @GET
    Call<ResponseBody> getUrlData(@Url String url, @Query("id") long id);

    @GET("orgs/{id}")
    Call<ResponseBody> getPathData(@Query("name") String name, @Path("id") long id);

    @HTTP(method = "GET", path = "user/keys", hasBody = false)
    Call<ResponseBody> getHttpData();

    @POST("user/emials")
    Call<ResponseBody> getBodyData(@Body Response response);

    @FormUrlEncoded
    @POST("user/emials")
    Call<ResponseBody> getPostData3(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("user/emails")
    Call<ResponseBody> getPostData2(@Field("name") String name, @Field("sex") String sex);

    @POST("user/emails")
    Call<ResponseBody> getPostData();

    @GET("user")
    Call<ResponseBody> getData3(@QueryMap Map<String, Object> map);

    @GET("user")
    Call<ResponseBody> getData2(@Query("id") long id, @Query("name") String name);

    @GET("user")
    Call<ResponseBody> getData();
}
