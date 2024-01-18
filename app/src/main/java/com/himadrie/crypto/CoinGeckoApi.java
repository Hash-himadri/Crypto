package com.himadrie.crypto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface CoinGeckoApi {

    @GET("coins/markets")
    Call<List<CryptoCurrency>> getMarketData(@Query("vs_currency") String vsCurrency, @Query("order") String order, @Query("per_page") int perPage, @Query("page") int page, @Query("sparkline") boolean sparkline);
}