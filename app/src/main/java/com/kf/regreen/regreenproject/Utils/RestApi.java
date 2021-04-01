package com.kf.regreen.regreenproject.Utils;

import android.net.Uri;

public class RestApi {

    //http://www.regreen.co.in/admin/webservice/api/Action_Name
    // OLD URL : http://callabhi.com/regreen/
    public static String MAIN_URL = "http://www.regreen.co.in/admin/webservice/";
    public static String IMAGE_URL = "http://www.regreen.co.in/web/";


    // METHODS
    public static class METHODS {
        public static final String USER_REGISTER = "api/register";
        public static final String USER_PROFILE_UPDATE = "api/profileUpdate";

        public static final String USER_LOGIN = "api/login";
        public static final String SOCIAL_USER_LOGIN = "api/socialLogin";
        public static final String FORGOTPASSWORD = "api/forgotPassword";
        public static final String CHANGEPASSWORD = "api/changePassword";
        public static final String QR_CODE = "api/checkDiscount";
        public static final String CHECK_WELCOME_DISCOUNT = "api/checkWelcomeDiscount";
        public static final String MAINTENANCE_SCR = "users/splash";
        public static final String ARTICLE_LIST = "api/getArticleList";
        public static final String RESEND_OTP = "api/resendOTP";
        public static final String VERIFY_USER = "api/verifyOTP";
        public static final String NURSERY_LIST = "api/getNurseryList";
        public static final String NURSERY_PLANTS_LIST = "api/getPlantCategoryList";
        public static final String HOME_QUOTE_MESSAGE = "api/getTodayQuote";
        public static final String EXPERTLIST = "api/getExpertsList";
        public static final String USERLIST = "api/getExpertUserList";
        public static final String SAVEANSWER = "api/expertAnswer";
        public static final String ADDTOFAQ = "api/addFaq";
        public static final String ADDTOSPAM = "api/addToSpam";
        public static final String EXPERTFAQLIST = "api/getExpertFaqList";
        public static final String PLANTS_CATEGORIES = "api/getNursertCategory";
        public static final String PLANTS_LIST = "api/getPlantsByCategory";
        public static final String EXPERT_QUESTION = "api/expertQuestion";
        public static final String ADD_BILL = "api/addBill";
        public static final String GET_BILL = "api/getBills";
        public static final String UPVOTE = "api/likeUnlike";
    }

    // PARAMETERS
    public static class PARAMETERS {

        public static final int ANSWER_REQUESTCODE = 123;
        public static final String STATUS = "status";
        public static final int STATUS_PASS = 1;
        public static final int STATUS_FAIL = 0;
        public static final String MESSAGE = "message";
        public static final String DISCOUNT = "discount";
        public static final String RESULT = "result";


        public static final String ID = "id";
        public static final String PHONE_NO = "phone_no";
        public static final String PASSWORD = "password";
        public static final String USER_TYPE = "user_type";
        public static final String DEVICE_TYPE = "device_type";
        public static final String DEVICE_TOKEN = "device_token";
        public static final String SOCIAL_PROVIDER_ID = "sm_social_provider_id";
        public static final String SOCIAL_PROVIDER = "sm_social_provider";


        public static final String REG_OS = "os";

        public static final String REG_USER_FIRST_NAME = "first_name";
        public static final String REG_USER_LAST_NAME = "last_name";
        public static final String REG_USER_GENDER = "sex";
        public static final String REG_USER_NAME = "user_name";
        public static final String REG_USER_EMAIL = "email";
        public static final String REG_USER_PWD = "password";
        public static final String REG_USER_PHONE = "phone_no";
        public static final String REG_USER_DESIGNATION = "e_designation";
        public static final String REG_USER_AREAEXPERTS = "e_area_experts";
        public static final String REG_USER_PROFILEPIC = "profile_pic";
        public static final String REG_LATITUDE = "lattitude";
        public static final String REG_LONGITUDE = "longitude";
        public static final String REG_DEVICE_UNIQUE_KEY = "device_id";
        public static final String REG_PUSH_KEY = "push_key";
        public static final String REG_COUNTRY_CODE = "country_code";
        public static final String REG_CITY = "city";
        public static final String REG_USER_OS = "os";
        public static final String REG_USER_TYPE = "user_type";
        public static final String REG_USER_PROFILE_PIC = "image";
        public static final String REG_USER_OLD_PWD = "old_password";
        public static final String REG_USER_NEW_PWD = "new_password";


        //For QR code
        public static final String SCAN_QR_CODE = "code";
        public static final String REG_USER_ID = "user_id";

        //For resend OTP
        public static final String OTP = "otp";
        public static final String USER_ID = "user_id";

        //For article list
        public static final String ART_CODE = "code";
        public static final String ART_DRAW = "draw";
        public static final String ART_REC_TOTAL = "recordsTotal";
        public static final String ART_REC_FILTER = "recordsFiltered";
        public static final String ART_SEARCH = "search";
        public static final String ART_ID = "id";
        public static final String ART_DESC = "description";
        public static final String ART_IMG_URL = "image_url";
        public static final String ART_URL = "url";
        public static final String ART_TITLE = "title";
        public static final String ART_IS_ACTIVE = "is_active";
        public static final String ART_USER_ID = "user_id";
        public static final String ART_CREATED_AT = "created_at";
        public static final String ART_USER_FULLNAME = "user_fullname";
        public static final String ART_USER_PROFILE_PIC = "user_profile_pic";
        public static final String ART_REWARD_POINTS = "reward_points";
        public static final String ART_SUBMITTED_BY = "submitted_by";
        public static final String ART_SOURCE_BY = "source";

        //For nursery list
        public static final String BANNER = "banner";
        public static final String NURSERY_ID = "nursery_id";
        public static final String NUR_CODE = "code";
        public static final String NUR_DRAW = "draw";
        public static final String NUR_REC_TOTAL = "recordsTotal";
        public static final String NUR_REC_FILTER = "recordsFiltered";
        public static final String NUR_SEARCH = "search";
        public static final String NUR_ID = "id";
        public static final String NUR_NAME = "name";
        public static final String NUR_CONT_NUM = "contact_no";
        public static final String NUR_ADD = "address";
        public static final String NUR_IMG_URL = "image_url";
        public static final String NUR_IS_ACTIVE = "is_active";
        public static final String NUR_USER_LATITUDE = "latitude";
        public static final String NUR_USER_LONGITUDE = "longitude";
        public static final String NUR_OWNER_NAME = "owner_name";
        public static final String NUR_IS_MEM = "is_member";



        public static final String EXPERT_NAME_FAQ = "expert_name";
        public static final String EXPERT_NAME = "q_name";
        public static final String EXPERT_DESC = "q_desc";
        public static final String EXPERT_ID = "q_e_id";
        public static final String EXPERT_USER_ID = "q_u_id";

        public static final String USER_EQ_ID = "eq_id";
        public static final String USER_EQ_E_ID = "eq_e_id";
        public static final String USER_EQ_U_ID = "eq_u_id";
        public static final String USER_EQ_NAME = "eq_name";
        public static final String USER_EQ_DESC = "eq_desc";
        public static final String USER_EQ_ANSWER = "eq_answer";
        public static final String USER_EQ_STATUS = "eq_status";
        public static final String USER_EQ_TYPE = "type";
        public static final String USER_EQ_DATE = "eq_created";
        public static final String USER_PROFILE_PIC = "profile_pic";
        public static final String USER_QUESTION_PICS = "question_pic";
        public static final String USER_QUESTION_PIC = "eqi_image";


        //For Plant List
        public static final String PLANT_REC_TOTAL = "recordsTotal";
        public static final String PLANT_ID = "id";
        public static final String PLANT_NAME = "name";
        public static final String PLANT_IS_ACTIVE = "is_active";
        public static final String PLANT_DESC = "description";
        public static final String PLANT_QR_CODE = "qrcode";
        public static final String PLANT_IMG_URL = "image_url";
        public static final String PLANT_CAT = "category";
        public static final String PLANT_CAT_ID = "categoryid";
        public static final String PLANT_CAT_ID1 = "category_id";

        public static final String PLANT_LOCAL_NAME = "local_name";
        public static final String PLANT_EXTRA_COMMENT = "extra_comment";
        public static final String PLANT_ENGLISH_NAME = "english_name";
        public static final String PLANT_BOTANICAL_NAME = "botanical_name";
        public static final String PLANT_HABIT = "habit";
        public static final String PLANT_FAMILY = "family";
        public static final String PLANT_WATERING = "watering";
        public static final String PLANT_LOCATION = "location";
        public static final String PLANT_USE = "plant_use";


        //For Plant Categories
        public static final String PLCAT_REC_TOTAL = "recordsTotal";
        public static final String PLCAT_ID = "id";
        public static final String PLCAT_NAME = "name";
        public static final String PLCAT_IS_ACTIVE = "is_active";
        public static final String PLCAT_IMAGE_URL = "image";
        public static final String PLCAT_DATE = "created_at";

        //Add Bill
        public static final String BILL_AMT = "amount";
        public static final String NURSERY_NAME = "n_name";
        public static final String BILL_IMAGE = "image";

        //Get Bill
        public static final String GBILL_CODE = "code";
        public static final String GBILL_DRAW = "draw";
        public static final String GBILL_RECTOTAL = "recordsTotal";
        public static final String GBILL_RECFILTER = "recordsFiltered";
        public static final String GBILL_SEARCH = "search";
        public static final String GBILL_ID = "id";
        public static final String GBILL_IMGURL = "image_url";
        public static final String GBILL_AMT = "amount";
        public static final String GBILL_NURNAME = "nurname";
        public static final String GBILL_ISCONF = "is_confirm";
        public static final String GBILL_USERFN = "user_fullname";
        public static final String GBILL_REWARDS = "reward_points";
        public static final String GBILL_USER_IS_NEW = "user_is_new";


        //for Upvote
        public static final String ARTICLE_ID = "article_id";
        public static final String USER_ID_UPVOTE = "user_id";

        public static final String SI_LATITIUDE = "lat";
        public static final String SI_LONGITUDE = "long";
        public static final String SI_OS_TYPE = "os";
        public static final String SI_DEVICE_RESOLUTION = "device_res";
        public static final String SI_USER_ID = "user_id";
        public static final String SI_DEVICE_UNIQUE_ID = "device_unique_key";
        public static final String SI_TIMEZONE = "time_zone";
        public static final String SI_DENSITY = "density";
        public static final String SI_PUSH_KEY = "push_key";
        public static final String SI_UPDATE_DATE = "update_date";
        public static final String SI_APP_VER = "app_version";
        public static final String SI_PHONE_NUMBER = "user_phone_no";

        public static final String COMMON_DEVICE_RESOLUTION = "device_res";
        public static final String COMMON_INTERNET_TYPE = "internet_type";
        public static final String COMMON_INTERNET_SPEED = "internet_speed";
        public static final String COMMON_LIMIT = "limit";
        public static final String COMMON_START = "offset";
        public static final String COMMON_TIMEZONE = "time_zone";
        public static final String COMMON_DENSITY = "density";

        public static final String CAT_ID = "main_category_id";
        public static final String SUB_CAT_ID = "sub_category_id";

        public static final String SEARCH_TEXT = "keyword";

        public static final String AF_TOWHOM_ID = "to_whom_id";
        public static final String AF_TONE_ID = "tone_id";
        public static final String AF_CANVAS_ID = "orientation_id";
        public static final String AF_SUBCAT_ID = "sub_category_id";

        //HOLI KUNDAN
        public static final String SCH_CARD_NAME = "card_name";

        public static final String OAUTH_ACCESS_TOKAN = "access_token";
        public static final String OAUTH_REFRESH_TOKAN = "refresh_token";
        public static final String OAUTH_SCOPE = "scope";
        public static final String DASH_CAT_LAST_UPDATED_DATE = "last_updated_dt";

    }

    public final class ErrorCode {

        public final static String ERROR_CODE = "errorCode";

        public final static String ERROR_MESSAGE = "errorMessage";
        public final static String MESSAGE = "message";

        public final static String DATA = "data";

        public final static int SUCCESS = 1;

        public final static int FAILED = 0;

        public final static int NULL = 901;

        public final static int REQUEST_TIMEOUT = 902;

        public final static int TRY_AGAIN = 903;

    }

    public static String createURI(String methodName) {
        Uri.Builder urlBuilder = Uri.parse(MAIN_URL + "index.php" + methodName).buildUpon();
        return urlBuilder.build().toString();
    }


}
