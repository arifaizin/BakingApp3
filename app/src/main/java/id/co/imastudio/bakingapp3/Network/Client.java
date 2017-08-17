package id.co.imastudio.bakingapp3.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by idn on 8/12/2017.
 */

public class Client {
//    static Service service;
//
//    public static Service Retrieve() {
//
//        Gson gson = new GsonBuilder().create();
//
//        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
//
//
//        service = new Retrofit.Builder()
//                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .callFactory(httpClientBuilder.build())
//                .build().create(Service.class);
//
//
//        return service;
//    }

    private static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Service getInstanceRetrofit(){
        return getRetrofit().create(Service.class);
    }
}
