package com.book.donation.apicalls.api;

import com.book.donation.apicalls.model.AddToWishlistResBean;
import com.book.donation.apicalls.model.AskHelpNowResBean;
import com.book.donation.apicalls.model.AskHelpSubCategoryResBean;
import com.book.donation.apicalls.model.BookTypeResBean;
import com.book.donation.apicalls.model.CategoryResBean;
import com.book.donation.apicalls.model.CityResBean;
import com.book.donation.apicalls.model.ClaimAcceptResBean;
import com.book.donation.apicalls.model.ClaimResBean;
import com.book.donation.apicalls.model.ClaimedPersonListResBean;
import com.book.donation.apicalls.model.ChatListResBean;
import com.book.donation.apicalls.model.DonatItemDasListResBean;
import com.book.donation.apicalls.model.DonatNowResBean;
import com.book.donation.apicalls.model.EventResBean;
import com.book.donation.apicalls.model.HappyDataResBean;
import com.book.donation.apicalls.model.HappySectionResBean;
import com.book.donation.apicalls.model.ItemDeleteResBean;
import com.book.donation.apicalls.model.ItemReceivedResBean;
import com.book.donation.apicalls.model.LoginResBean;
import com.book.donation.apicalls.model.MyClaimItemListResBean;
import com.book.donation.apicalls.model.MyHelpRequestResBean;
import com.book.donation.apicalls.model.RecentItemsResBean;
import com.book.donation.apicalls.model.RequestDetailsResBean;
import com.book.donation.apicalls.model.ResponseResBean;
import com.book.donation.apicalls.model.SearchItemResBean;
import com.book.donation.apicalls.model.StateResBean;
import com.book.donation.apicalls.model.SubmitLikeThumbResBean;
import com.book.donation.apicalls.model.SupportQueryResBean;
import com.book.donation.apicalls.model.ThreadIdResBean;
import com.book.donation.apicalls.model.messenger.MyResponse;
import com.book.donation.apicalls.model.NotificationResBean;
import com.book.donation.apicalls.model.ProductDetailsResBean;
import com.book.donation.apicalls.model.ProductResBean;
import com.book.donation.apicalls.model.ProfileResBean;
import com.book.donation.apicalls.model.RegisterResBean;
import com.book.donation.apicalls.model.RejectClaimResBean;
import com.book.donation.apicalls.model.SubCategoryResBean;
import com.book.donation.apicalls.model.UpdateProfileResBean;
import com.book.donation.apicalls.model.VerifyOtpResBean;
import com.book.donation.apicalls.model.WishlistResBean;
import com.book.donation.apicalls.model.messenger.Sender;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    /*Login Api */
    @FormUrlEncoded
    @POST("send-login-otp")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<LoginResBean> login(@Field("email") String email,
                             @Field("role_id") String role_id,
                             @Field("device_key") String device_key);

    /*Verify OTP Api */
    @FormUrlEncoded
    @POST("send-verify-otp")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<VerifyOtpResBean> verifyOTP(@Field("email") String email,
                                     @Field("otp") String otp);

    /*State Api */
    @POST("state")
    Call<StateResBean> getStates();

    /*City api*/
    @FormUrlEncoded
    @POST("city")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<CityResBean> getCities(@Field("state_id") String state_id);

    /*Add User*/
    @Multipart
    @POST("register")
    Call<RegisterResBean> registration(@Part("name") RequestBody user_name,
                                    @Part("gender") RequestBody gender,
                                    @Part("mobile") RequestBody mobile,
                                    @Part("email") RequestBody email,
                                    @Part("business_organization") RequestBody business_organization,
                                    @Part("registration_no") RequestBody registration_no,
                                    @Part("organization_name") RequestBody organization_name,
                                    @Part("website") RequestBody website_link,
                                    @Part("state") RequestBody state,
                                    @Part("city") RequestBody city,
                                    @Part("address") RequestBody address,
                                    @Part("pincode") RequestBody pincode,
                                    @Part MultipartBody.Part user_image,
                                    @Part("device_id") RequestBody device_id);

    /*Category Api */
    @FormUrlEncoded
    @POST("category")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<CategoryResBean> getCategories(@Field("type") String user_id);

    /*Sub Category Api */
    @FormUrlEncoded
    @POST("sub-category")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<SubCategoryResBean> getSubCategories(@Field("user_id") String user_id,
                                              @Field("categoryid") String category_id,
                                              @Field("book_category") String book_category,
                                              @Field("author_name") String author_name,
                                              @Field("isbn") String isbn,
                                              @Field("type") String type,
                                              @Field("state_id") String state_id,
                                              @Field("city_id") String city_id,
                                              @Field("pincode") String pincode);

    /*Prdoucts Api */
    @FormUrlEncoded
    @POST("subcategory/products")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<ProductResBean> getProducts(@Field("user_id") String user_id,
                                     @Field("subcategoryid") String sub_category_id,
                                     @Field("type") String type,
                                     @Field("state_id") String state_id,
                                     @Field("city_id") String city_id);

    /*Wishlist Api */
    @FormUrlEncoded
    @POST("wishlist")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<WishlistResBean> getWishlist(@Field("userid") String user_id,
                                      @Field("type") String type);

    /*AddToWishList Api */
    @FormUrlEncoded
    @POST("add_to_wishlist")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<AddToWishlistResBean> addToWishList(@Field("product_id") String product_id,
                                             @Field("userid") String user_id,
                                             @Field("type") String type);

    /*MyProfile Api */
    @FormUrlEncoded
    @POST("myprofile")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<ProfileResBean> getProfileInfo(@Field("userid") String user_id);

    /*Update User*/
    @Multipart
    @POST("update-myprofile")
    Call<UpdateProfileResBean> updateProfileInfo(@Part("userid") RequestBody user_id,
                                                 @Part("name") RequestBody user_name,
                                                 @Part("mobile") RequestBody mobile,
                                                 @Part("email") RequestBody email,
                                                 @Part("organization_name") RequestBody organization_name,
                                                 @Part("pincode") RequestBody pincode,
                                                 @Part MultipartBody.Part user_image);

    /*Add Donate Item*/
    @Multipart
    @POST("donatenow")
    Call<DonatNowResBean> addDonateItem(@Part("name") RequestBody name,
                                        @Part("user_id") RequestBody user_id,
                                        @Part("mobile_no") RequestBody mobile_no,
                                        @Part("email") RequestBody email,
                                        @Part("state_id") RequestBody state_id,
                                        @Part("city_id") RequestBody city_id,
                                        @Part("address") RequestBody address,
                                        @Part("pincode") RequestBody pincode,
                                        @Part("item_name") RequestBody item_name,
                                        @Part("item_donate") RequestBody item_donote,
                                        @Part("category_id") RequestBody category_id,
                                        @Part("subcategory_id") RequestBody subcategory_id,
                                        @Part("item_condition") RequestBody item_condition,
                                        @Part("description") RequestBody description,
                                        @Part("isbn") RequestBody isbn,
                                        @Part("author") RequestBody author,
                                        @Part("book_category_id") RequestBody book_category_id,
                                        @Part("book_category") RequestBody book_category,
                                        @Part("grade") RequestBody grade,
                                        @Part("university") RequestBody university,
                                        @Part MultipartBody.Part user_image);

    /*Event Api */
    @POST("event")
    Call<EventResBean> getEvents();

    /*Prdouct Details Api */
    @FormUrlEncoded
    @POST("subcategory/single-product")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<ProductDetailsResBean> getProductDetails(@Field("user_id") String user_id,
                                                  @Field("proid") String product_id,
                                                  @Field("type") String type);
    @FormUrlEncoded
    @POST("subcategory/single-product")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<RequestDetailsResBean> getRequestDetails(@Field("user_id") String user_id,
                                                  @Field("proid") String product_id,
                                                  @Field("type") String type);

    /*Prdouct Claim Api */
    @FormUrlEncoded
    @POST("claim")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<ClaimResBean> claimProduct(@Field("user_id") String user_id,
                                    @Field("product_id") String product_id);

    /* Get MyDontaedItemList Api */
    @FormUrlEncoded
    @POST("donateditemlist")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<DonatItemDasListResBean> getMyDonatedItemDasList(@Field("user_id") String user_id);

    /* Delete ItemOrRequest Api */
    @FormUrlEncoded
    @POST("delete_ADlist")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<ItemDeleteResBean> deleteItems(@Field("product_id") String productId,
                                        @Field("type") String type);

    /*get ClaimedPersonList*/
    @FormUrlEncoded
    @POST("claimpersonlist")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<ClaimedPersonListResBean> getClaimedPersonList(@Field("user_id") String user_id,
                                                        @Field("product_id") String product_id);

    /*get ClaimedPersonList*/
    @FormUrlEncoded
    @POST("askclaimpersonlist")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<ClaimedPersonListResBean> getAskClaimedPersonList(@Field("user_id") String user_id,
                                                        @Field("product_id") String product_id);

    /*get ClaimedAcceptList*/
    @FormUrlEncoded
    @POST("claimaccept")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<ClaimAcceptResBean> claimAccept(@Field("claim_by") String claim_by,
                                         @Field("claim_id") String claim_id);

    /*get RequestClaimedAcceptList*/
    @FormUrlEncoded
    @POST("claim-request-accept")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<ClaimAcceptResBean> requestClaimAccept(@Field("claim_by") String claim_by,
                                         @Field("claim_id") String claim_id);

    /* Get MyClaimItemList Api */
    @FormUrlEncoded
    @POST("myclaims")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<MyClaimItemListResBean> getMyClaimItemList(@Field("user_id") String user_id);

    /* Call ItemReceived Api */
    @FormUrlEncoded
    @POST("item_recieved")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<ItemReceivedResBean> callItemReceived(@Field("claim_by") String claimBy,
                                               @Field("claim_id") String claimId);

    /* Call RequestReceived Api */
    @FormUrlEncoded
    @POST("request_item_recieved")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<ItemReceivedResBean> callRequestReceived(@Field("claim_by") String claimBy,
                                               @Field("claim_id") String claimId);

    /* RejectClaim Api */
    @FormUrlEncoded
    @POST("rejectclaim")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<RejectClaimResBean> getRejectClaim(@Field("user_id") String user_id,
                                            @Field("product_id") String product_id,
                                            @Field("type") String type);

    /*AskHelpNow Api */
    @FormUrlEncoded
    @POST("askhelp")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<AskHelpNowResBean> addAskHelp(@Field("user_id") String user_id,
                                       @Field("user_name") String user_name,
                                       @Field("mobile_no") String mobile_no,
                                       @Field("email") String email,
                                       @Field("address") String address,
                                       @Field("pincode") String pincode,
                                       @Field("category_id") String category_id,
                                       @Field("subcategory_id") String subcategory_id,
                                       @Field("description") String description,
                                       @Field("item_name") String item_name,
                                       @Field("isbn") String isbn,
                                       @Field("author") String author,
                                       @Field("book_category_id") String book_category_id,
                                       @Field("book_category") String book_category,
                                       @Field("first_name") String first_name,
                                       @Field("last_name") String last_name,
                                       @Field("student_level") String student_level,
                                       @Field("student_id") String student_id,
                                       @Field("student_phone") String student_phone,
                                       @Field("student_email") String student_email,
                                       @Field("student_course") String student_course,
                                       @Field("institute_name") String intitute_name,
                                       @Field("institute_address") String intitute_address,
                                       @Field("term_details") String term_details,
                                       @Field("parent_name") String parent_name,
                                       @Field("parent_phone") String parent_phone,
                                       @Field("parent_email") String parent_email,
                                       @Field("fee_type") String fee_type,
                                       @Field("amount") String amount,
                                       @Field("account_name") String account_name,
                                       @Field("account_number") String account_number,
                                       @Field("bank_name") String bank_name,
                                       @Field("ifsc") String ifsc,
                                       @Field("dd_cheque_no") String dd_cheque_no,
                                       @Field("reason") String reason);

    /*Add Donate Item*/
    @Multipart
    @POST("askhelp")
    Call<AskHelpNowResBean> addAskHelpWithImage(@Part("user_id") RequestBody user_id,
                                        @Part("user_name") RequestBody user_name,
                                        @Part("mobile_no") RequestBody mobile_no,
                                        @Part("email") RequestBody email,
                                        @Part("state_id") RequestBody state_id,
                                        @Part("city_id") RequestBody city_id,
                                        @Part("address") RequestBody address,
                                        @Part("pincode") RequestBody pincode,
                                        @Part("category_id") RequestBody category_id,
                                        @Part("subcategory_id") RequestBody subcategory_id,
                                        @Part("description") RequestBody description,
                                        @Part("item_name") RequestBody item_name,
                                        @Part("isbn") RequestBody isbn,
                                        @Part("author") RequestBody author,
                                        @Part("book_category_id") RequestBody book_category_id,
                                        @Part("book_category") RequestBody book_category,
                                                @Part("first_name") RequestBody first_name,
                                                @Part("last_name") RequestBody last_name,
                                                @Part("student_level") RequestBody student_level,
                                                @Part("student_id") RequestBody student_id,
                                                @Part("student_phone") RequestBody student_phone,
                                                @Part("student_email") RequestBody student_email,
                                                @Part("student_course") RequestBody student_course,
                                                @Part("institute_name") RequestBody institute_name,
                                                @Part("institute_address") RequestBody institute_address,
                                                @Part("term_details") RequestBody term_details,
                                                @Part("parent_name") RequestBody parent_name,
                                                @Part("parent_phone") RequestBody parent_phone,
                                                @Part("parent_email") RequestBody parent_email,
                                                @Part("fee_type") RequestBody fee_type,

                                                @Part("amount") RequestBody amount,
                                                @Part("account_name") RequestBody account_name,
                                                @Part("account_number") RequestBody account_number,
                                                @Part("bank_name") RequestBody bank_name,
                                                @Part("ifsc") RequestBody ifsc,
                                                @Part("dd_cheque_no") RequestBody dd_cheque_no,
                                                @Part("reason") RequestBody reason,
                                        @Part MultipartBody.Part user_image);


    /*Notification Api */
    @FormUrlEncoded
    @POST("notification")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<NotificationResBean> getNotificationList(@Field("user_id") String user_id);

    /*ASkHelp Sub Category Api */
    @FormUrlEncoded
    @POST("subcategoryhelp")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<AskHelpSubCategoryResBean> getAskHelpSubCategories(@Field("user_id") String user_id,
                                                            @Field("categoryid") String category_id,
                                                            @Field("book_category") String book_category,
                                                            @Field("author_name") String author_name,
                                                            @Field("isbn") String isbn,
                                                            @Field("type") String type,
                                                            @Field("type1") String type1,
                                                            @Field("state_id") String state_id,
                                                            @Field("city_id") String city_id,
                                                            @Field("pincode") String pincode);

    /*MyHelpRequestList Api */
    @FormUrlEncoded
    @POST("myaskedhelp")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<MyHelpRequestResBean> getMyHelpRequestlist(@Field("user_id") String user_id);

    //AAAA9Ip0ckA:APA91bGduUopfvRG85JDvniU9g6Yl1_ElM50C1yaDVNMacNTUjB-Oz2XnRtDbbHt2rJT90f2WmEGBIQhCy0ptMLthb8GpPBmjQG5inHJsFwPghb_9ud1rsfgFlsMaricYHhykdfzKfop
    //FOR Firebase Chat notification
    @Headers({"Content-Type:application/json",
            "Authorization:key=AAAA2wFIdD8:APA91bELVy3mKEF5uPKbjI9AtiTT0YGu7QYV46UnPoggGR2mE3Xmr9bSojYpI6rtNtsPKoDx24hjOLgRpDUb-rx_29kFE8paBQ_BcVaEMdc_8JxRolP7vV_SiMmaGMo4O-VEADso_l5Z"
    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

    /*CreateThreadId Api */
    @FormUrlEncoded
    @POST("chat")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<ThreadIdResBean> createThreadID(@Field("sender_id") String sender_id,
                                         @Field("reciever_id") String receiver_id,
                                         @Field("item_id") String item_id,
                                         @Field("type") String type);

    /*DonateChatList Api */
    @FormUrlEncoded
    @POST("chat-list")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<ChatListResBean> getDonateChatList(@Field("user_id") String user_id,
                                            @Field("type") String type);

    /*Wishlist Api */
    @GET("edit-profile")
    Call<ResponseResBean> getTest(@Header("Authorization") String token);

    /*Category Api */
    @FormUrlEncoded
    @POST("recent-items")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<RecentItemsResBean> getRecentItem(@Field("type") String type,
                                           @Field("state_id") String state_id,
                                           @Field("city_id") String city_id,
                                           @Field("pincode") String pincode);

    /*Book Type Api */
    @FormUrlEncoded
    @POST("book-type")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<BookTypeResBean> getBookType(@Field("book_subcategory_id") String sub_category_id);

    /*Add Experience in Happy Section*/
    @Multipart
    @POST("add-happy-section")
    Call<HappySectionResBean> addExperience(@Part("user_id") RequestBody user_id,
                                            @Part("title") RequestBody title,
                                            @Part("experience_text") RequestBody experience,
                                            @Part MultipartBody.Part user_image);

    /*AddToWishList Api */
    @FormUrlEncoded
    @POST("like-product")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<SubmitLikeThumbResBean> submitLikeTHumb(@Field("product_id") String product_id,
                                                 @Field("user_id") String user_id,
                                                 @Field("type") String type);

    /*Putting support query Api */
    @FormUrlEncoded
    @POST("writetous")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<SupportQueryResBean> submitSupportQuery(@Field("user_id") String user_id,
                                                 @Field("title") String product_id,
                                                 @Field("description") String type);

    /*Prdouct Search Api */
    @FormUrlEncoded
    @POST("suggestion-search")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<SearchItemResBean> getSearchItems(@Field("user_id") String user_id,
                                              @Field("keyword") String keyword,
                                              @Field("type") String type);

    /*HappData Api */
    @FormUrlEncoded
    @POST("get-happy-section-data")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<HappyDataResBean> getHappyData(@Field("user_id") String user_id);

    /*Request Claim Api */
    @FormUrlEncoded
    @POST("askhelp-claim")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<ClaimResBean> claimHelp(@Field("user_id") String user_id,
                                    @Field("product_id") String product_id);

    /* Get MyHelpClaimItemList Api */
    @FormUrlEncoded
    @POST("my-request-claims")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<MyClaimItemListResBean> getMyHelpClaimItemList(@Field("user_id") String user_id);

}



