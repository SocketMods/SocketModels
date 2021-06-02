package dev.socketmods.socketmodels.utils.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

public class Gsons {

    public static GsonBuilder lenient() {
        return new GsonBuilder().setLenient();
    }

    public static Gson lenientType(TypeAdapterFactory factory) {
        return lenient().registerTypeAdapterFactory(factory).create();
    }

}
