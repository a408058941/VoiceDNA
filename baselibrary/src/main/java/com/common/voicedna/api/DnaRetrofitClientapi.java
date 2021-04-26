package com.common.voicedna.api;

import com.fd.baselibrary.api.RetrofitClientJsonObject;

public class DnaRetrofitClientapi extends RetrofitClientJsonObject {
    public static <T> T getAPIService(Class<T> service) {
        return getInstance().create(service);
    }

    public static DnaService getTestService() {
        return getAPIService(DnaService.class);
    }
}
