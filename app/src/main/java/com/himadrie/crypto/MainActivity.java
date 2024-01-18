package com.himadrie.crypto;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ListView lvCrypto;
    ArrayList<String> crName;
    ArrayList<String> crID;
    ArrayList<String> crImage;
    ArrayList<String> crSymbol;
    ArrayList<Double> crPrice;
    ArrayList<Double> crChange;
    List<CryptoCurrency> marketData;

    CryptoCurrency cc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cc = new CryptoCurrency();
        crName = new ArrayList<>();
        crImage = new ArrayList<>();
        crID = new ArrayList<>();
        crSymbol = new ArrayList<>();
        crPrice = new ArrayList<>();
        crChange = new ArrayList<>();

        lvCrypto = findViewById(R.id.lvCrypto);
        fetchMarketData();


    }

    private void fetchMarketData() {
        CoinGeckoApi api = ApiClient.getApiClient().create(CoinGeckoApi.class);
        Call<List<CryptoCurrency>> call = api.getMarketData("usd", "market_cap_desc", 100, 1, false);

        call.enqueue(new Callback<List<CryptoCurrency>>() {
            @Override
            public void onResponse(Call<List<CryptoCurrency>> call, Response<List<CryptoCurrency>> response) {
                if (response.isSuccessful()) {
                    marketData = response.body();
                    // Update the RecyclerView with the fetched data

                    Log.i("MArketSize", String.valueOf(marketData.get(2).getName()));
                    for(int i=0;i<marketData.size(); i++){
                        crName.add(marketData.get(i).getName());
                        crID.add(marketData.get(i).getId());
                        crPrice.add(marketData.get(i).getCurrent_price());
                        crChange.add(marketData.get(i).getPrice_change_percentage_24h());
                        crImage.add(marketData.get(i).getImage());
                        crSymbol.add(marketData.get(i).getSymbol());
                    }

                    Log.i("Image", marketData.get(0).getImage());
                    CryptoAdapter cadt = new CryptoAdapter();
                    lvCrypto.setAdapter(cadt);
                }
            }

            @Override
            public void onFailure(Call<List<CryptoCurrency>> call, Throwable t) {
                // Handle the error
            }
        });
    }

    class CryptoAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return marketData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.crypto_layout, null);

            TextView name = convertView.findViewById(R.id.tvName);
            TextView id = convertView.findViewById(R.id.tvID);
            TextView price = convertView.findViewById(R.id.tvPrice);
            TextView change = convertView.findViewById(R.id.tvChange);
            TextView symbol = convertView.findViewById(R.id.tvSymbol);
            ImageView img = convertView.findViewById(R.id.imgCrypto);

//            Bitmap bitmap;
//            try {
//                bitmap = BitmapFactory.decodeStream((InputStream)new URL("https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1696501400").getContent());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }

            name.setText(crName.get(position));
            id.setText(crID.get(position));
            change.setText(crChange.get(position).toString());
            price.setText(crPrice.get(position).toString());
            symbol.setText(crSymbol.get(position));
//            img.setImageBitmap(bitmap);
            Picasso.get().load(crImage.get(position)).into(img);

            return convertView;
        }
    }
}
