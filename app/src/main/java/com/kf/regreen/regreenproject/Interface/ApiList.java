package com.kf.regreen.regreenproject.Interface;

import com.kf.regreen.regreenproject.Model.UserDetails;
import com.kf.regreen.regreenproject.Utils.RestApi;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by admin on 26/12/2017.
 */

public interface ApiList {


    /****************************************************************************POST METHODS*******************************************************************************************************************/

    @Multipart
    @POST(RestApi.METHODS.USER_REGISTER)
    Call<ResponseBody> registeruser(@Part(RestApi.PARAMETERS.REG_USER_FIRST_NAME) RequestBody first_name,
                                    @Part(RestApi.PARAMETERS.REG_USER_EMAIL) RequestBody email,
                                    @Part(RestApi.PARAMETERS.REG_USER_PHONE) RequestBody mobile_no,
                                    @Part(RestApi.PARAMETERS.REG_USER_PWD) RequestBody password,
                                    @Part(RestApi.PARAMETERS.REG_USER_GENDER) RequestBody gender,
                                    @Part(RestApi.PARAMETERS.REG_CITY) RequestBody city,
                                    @Part(RestApi.PARAMETERS.DEVICE_TOKEN) RequestBody device_token,
                                    @Part(RestApi.PARAMETERS.DEVICE_TYPE) RequestBody device_type,
                                    @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST(RestApi.METHODS.USER_LOGIN)
    Call<ResponseBody> loginuser(@Field(RestApi.PARAMETERS.REG_USER_PHONE) String phone_no,
                                 @Field(RestApi.PARAMETERS.REG_USER_PWD) String password,
                                 @Field(RestApi.PARAMETERS.DEVICE_TOKEN) String device_token,
                                 @Field(RestApi.PARAMETERS.DEVICE_TYPE) String device_type);


    @FormUrlEncoded
    @POST(RestApi.METHODS.SOCIAL_USER_LOGIN)
    Call<ResponseBody> loginsocialuser(@Field(RestApi.PARAMETERS.SOCIAL_PROVIDER_ID) String social_provider_id,
                                       @Field(RestApi.PARAMETERS.REG_USER_EMAIL) String email,
                                       @Field(RestApi.PARAMETERS.REG_USER_FIRST_NAME) String first_name,
                                       @Field(RestApi.PARAMETERS.SOCIAL_PROVIDER) int social_provider,
                                       @Field(RestApi.PARAMETERS.DEVICE_TOKEN) String device_token,
                                       @Field(RestApi.PARAMETERS.DEVICE_TYPE) String device_type);

    @Multipart
    @POST(RestApi.METHODS.USER_PROFILE_UPDATE)
    Call<ResponseBody> profielUpdate(@Part(RestApi.PARAMETERS.REG_USER_ID) RequestBody user_id,
                                     @Part(RestApi.PARAMETERS.REG_USER_FIRST_NAME) RequestBody first_name,
                                     @Part(RestApi.PARAMETERS.REG_USER_EMAIL) RequestBody email,
                                     @Part(RestApi.PARAMETERS.REG_USER_PHONE) RequestBody mobile_no,
                                     @Part(RestApi.PARAMETERS.REG_USER_GENDER) RequestBody gender,
                                     @Part(RestApi.PARAMETERS.REG_CITY) RequestBody city,
                                     @Part MultipartBody.Part image);
//    @Part(RestApi.PARAMETERS.DEVICE_TYPE) RequestBody device_type,
    //@Part(RestApi.PARAMETERS.DEVICE_TOKEN) RequestBody device_token,


    @FormUrlEncoded
    @POST(RestApi.METHODS.FORGOTPASSWORD)
    Call<ResponseBody> forgotpassword(@Field(RestApi.PARAMETERS.REG_USER_EMAIL) String emailormobile,
                                      @Field(RestApi.PARAMETERS.DEVICE_TOKEN) String device_token,
                                      @Field(RestApi.PARAMETERS.DEVICE_TYPE) String device_type);


    @FormUrlEncoded
    @POST(RestApi.METHODS.CHANGEPASSWORD)
    Call<ResponseBody> changepassword(@Field(RestApi.PARAMETERS.REG_USER_ID) String user_id,
                                      @Field(RestApi.PARAMETERS.REG_USER_OLD_PWD) String old_password,
                                      @Field(RestApi.PARAMETERS.REG_USER_NEW_PWD) String new_password,
                                      @Field(RestApi.PARAMETERS.DEVICE_TOKEN) String device_token,
                                      @Field(RestApi.PARAMETERS.DEVICE_TYPE) String device_type);


    @FormUrlEncoded
    @POST(RestApi.METHODS.ARTICLE_LIST)
    Call<ResponseBody> getArticleList(@Field(RestApi.PARAMETERS.USER_ID) String s);

    @FormUrlEncoded
    @POST(RestApi.METHODS.UPVOTE)
    Call<ResponseBody> upvote(@Field(RestApi.PARAMETERS.ARTICLE_ID) String s, @Field(RestApi.PARAMETERS.USER_ID_UPVOTE) String s1);


    @FormUrlEncoded
    @POST(RestApi.METHODS.RESEND_OTP)
    Call<ResponseBody> resendOtp(@Field(RestApi.PARAMETERS.USER_ID) String user_id);

    @FormUrlEncoded
    @POST(RestApi.METHODS.VERIFY_USER)
    Call<ResponseBody> verifyUser(@Field(RestApi.PARAMETERS.USER_ID) String user_id, @Field(RestApi.PARAMETERS.OTP) String otp);

    @POST(RestApi.METHODS.MAINTENANCE_SCR)
    Call<ResponseBody> maintenance();

    @POST(RestApi.METHODS.NURSERY_LIST)
    Call<ResponseBody> nurseryList();

    @POST(RestApi.METHODS.NURSERY_PLANTS_LIST)
    Call<ResponseBody> plantList();

    @FormUrlEncoded
    @POST(RestApi.METHODS.PLANTS_CATEGORIES)
    Call<ResponseBody> plantsListByNursery(@Field(RestApi.PARAMETERS.NURSERY_ID) String nurseryid);

    @FormUrlEncoded
    @POST(RestApi.METHODS.PLANTS_LIST)
    Call<ResponseBody> plantList(@Field(RestApi.PARAMETERS.PLANT_CAT_ID1) String category_id,
                                 @Field(RestApi.PARAMETERS.NURSERY_ID) String nurseryid);


    @POST(RestApi.METHODS.HOME_QUOTE_MESSAGE)
    Call<ResponseBody> getQuoteMsg();

    @POST(RestApi.METHODS.EXPERTLIST)
    Call<ResponseBody> getExpertsList();

    @FormUrlEncoded
    @POST(RestApi.METHODS.USERLIST)
    Call<ResponseBody> getUserList(@Field(RestApi.PARAMETERS.USER_EQ_E_ID) String expertid,
                                   @Field(RestApi.PARAMETERS.USER_EQ_STATUS) int status,
                                   @Field(RestApi.PARAMETERS.USER_EQ_TYPE) int type);


    @FormUrlEncoded
    @POST(RestApi.METHODS.ADDTOFAQ)
    Call<ResponseBody> addFaq(@Field(RestApi.PARAMETERS.USER_EQ_ID) String questionid);

    @FormUrlEncoded
    @POST(RestApi.METHODS.ADDTOSPAM)
    Call<ResponseBody> addSpam(@Field(RestApi.PARAMETERS.USER_EQ_ID) String questionid);

    @FormUrlEncoded
    @POST(RestApi.METHODS.EXPERTFAQLIST)
    Call<ResponseBody> getFaq(@Field(RestApi.PARAMETERS.USER_EQ_E_ID) String expertid);


/*    @POST("/upload_multi_files/MultiPartUpload.php")
    Call<ResponseBody> uploadMultiFile(@Body RequestBody file);*/

    //    @Multipart
    @POST(RestApi.METHODS.EXPERT_QUESTION)
    Call<ResponseBody> expertQuestionUpload(@Body RequestBody file);

   /* @Multipart
    @POST(RestApi.METHODS.EXPERT_QUESTION)
    Call<ResponseBody> expertQuestionUpload(@Part(RestApi.PARAMETERS.EXPERT_USER_ID) RequestBody user_id,
                                            @Part(RestApi.PARAMETERS.EXPERT_ID) RequestBody expert_id,
                                            @Part(RestApi.PARAMETERS.EXPERT_NAME) RequestBody title,
                                            @Part(RestApi.PARAMETERS.EXPERT_DESC) RequestBody description,
                                            @Part MultipartBody.Part image);*/

    @FormUrlEncoded
    @POST(RestApi.METHODS.SAVEANSWER)
    Call<ResponseBody> saveAnswer(@Field(RestApi.PARAMETERS.USER_EQ_ID) String questionid,
                                  @Field(RestApi.PARAMETERS.USER_EQ_ANSWER) String answer);


    @FormUrlEncoded
    @POST(RestApi.METHODS.GET_BILL)
    Call<ResponseBody> getBill(@Field(RestApi.PARAMETERS.USER_ID) String user_id);


    @FormUrlEncoded
    @POST(RestApi.METHODS.QR_CODE)
    Call<ResponseBody> checkDiscount(@Field(RestApi.PARAMETERS.USER_ID) String user_id, @Field(RestApi.PARAMETERS.GBILL_REWARDS) String reward_points);


    @FormUrlEncoded
    @POST(RestApi.METHODS.CHECK_WELCOME_DISCOUNT)
    Call<ResponseBody> checkWelcomeDiscount(@Field(RestApi.PARAMETERS.USER_ID) String user_id);

    @Multipart
    @POST(RestApi.METHODS.ADD_BILL)
    Call<ResponseBody> uploadbill(@Part(RestApi.PARAMETERS.USER_ID) RequestBody s, @Part MultipartBody.Part image,
                                  @Part(RestApi.PARAMETERS.BILL_AMT) RequestBody amount, @Part(RestApi.PARAMETERS.NURSERY_NAME) RequestBody nursery_name);


}
