package com.swayam.sqliteroomsample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PublicKey;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 Created by akhil on 1/3/18.
 */

public class RestClient {

  private static String LOGIN_BASE_URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/";

  private static String USER_DETAILS_BASE_URL = "https://sample-d89f8.firebaseio.com/users/";

  private static String GET_ALL_USERS_BASE_URL = "https://sample-d89f8.firebaseio.com/";

  private static String REFRESH_TOKEN_BASE_URL = "https://securetoken.googleapis.com/v1/";


  public static Retrofit retrofit = null;

  static OkHttpClient.Builder  httpClient = new OkHttpClient.Builder();

  private final ApiInterface apiService;

  private final ApiInterface userApiService;

  private final ApiInterface refreshTokenService;

  private final ApiInterface getAllUsersService;

  /*public static Retrofit getClient(){

    httpClient.addInterceptor(new Interceptor() {
      @Override public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
            .header("Content-Type", "application/json")
            .method(original.method(), original.body())
            .build();

        return chain.proceed(request);
      }
    });

    OkHttpClient client  = httpClient.build();
    retrofit = new Retrofit.Builder()
                  .baseUrl(LOGIN_BASE_URL)
                  .addConverterFactory(GsonConverterFactory.create())
                  .client(client)
                  .build();
    return retrofit;
  }

  public static Retrofit getUserDetailsClient(){
    retrofit = new Retrofit.Builder()
                   .baseUrl(USER_DETAILS_BASE_URL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
    return retrofit;
  }*/

  public RestClient() {

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    // set your desired log level
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    httpClient.addInterceptor(new Interceptor() {
      @Override
      public Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
            .header("Content-Type", "application/json")
            .method(original.method(), original.body())
            .build();

        return chain.proceed(request);
      }
    });


    OkHttpClient.Builder httpRefreshClient = new OkHttpClient.Builder();
    httpRefreshClient.addInterceptor(new Interceptor() {
      @Override public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                 .header("Content-Type","application/x-www-form-urlencoded")
                 .method(original.method(),original.body())
                 .build();
        return chain.proceed(request);
      }
    });


    httpClient.addInterceptor(logging);
    httpRefreshClient.addInterceptor(logging);

    Retrofit LoginRetrofit = new Retrofit.Builder()
        .baseUrl(LOGIN_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build();

    Retrofit userRetrofit = new Retrofit.Builder()
        .baseUrl(USER_DETAILS_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build();


    Retrofit refreshTokenRetrofit = new Retrofit.Builder()
         .baseUrl(REFRESH_TOKEN_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpRefreshClient.build())
        .build();

    Retrofit getALlUsersRetrofit = new Retrofit.Builder()
          .baseUrl(GET_ALL_USERS_BASE_URL)
        .addConverterFactory(createGsonConverter())
        .client(httpClient.build())
        .build();


    apiService = LoginRetrofit.create(ApiInterface.class);
    userApiService = userRetrofit.create(ApiInterface.class);

    refreshTokenService = refreshTokenRetrofit.create(ApiInterface.class);
    getAllUsersService = getALlUsersRetrofit.create(ApiInterface.class);
  }

  public ApiInterface getApiService() {
    return apiService;
  }

  public ApiInterface getUserApiService() {
    return userApiService;
  }

  public ApiInterface getGetAllUsersService() {
    return getAllUsersService;
  }

  public ApiInterface getRefreshTokenService() {
    return refreshTokenService;
  }

  private Converter.Factory createGsonConverter() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(AllUserDetailsKeyModel.class, new UserDetailsDeserializer());
    Gson gson = gsonBuilder.create();
    return GsonConverterFactory.create(gson);
  }
}
