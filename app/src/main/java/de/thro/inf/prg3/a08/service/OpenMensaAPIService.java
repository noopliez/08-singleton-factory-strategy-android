package de.thro.inf.prg3.a08.service;

import de.thro.inf.prg3.a08.api.OpenMensaAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenMensaAPIService {
    private static OpenMensaAPIService instance;
    private OpenMensaAPI mensaApiInstance;

    private OpenMensaAPIService() { }

    public static OpenMensaAPIService getInstance() {
        return instance != null ? instance : new OpenMensaAPIService();
    }

    public OpenMensaAPI getApi() {
        if (mensaApiInstance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://openmensa.org/api/v2/")
                    .build();

            mensaApiInstance = retrofit.create(OpenMensaAPI.class);
        }

        return mensaApiInstance;
    }
}
