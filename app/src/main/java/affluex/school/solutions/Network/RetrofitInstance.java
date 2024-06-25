package affluex.school.solutions.Network;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
        private static Retrofit retrofit = null;
        private static final String BASE_URL = "https://school.afluex.com/";

        public static Retrofit getClient() {
            if (retrofit == null) {
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                        .readTimeout(290, TimeUnit.SECONDS) // Increase timeout for reading response
                        .connectTimeout(290, TimeUnit.SECONDS); // Increase timeout for establishing connection

                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
            }
            return retrofit;
        }
    }



