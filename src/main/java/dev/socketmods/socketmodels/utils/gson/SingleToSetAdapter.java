package dev.socketmods.socketmodels.utils.gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class SingleToSetAdapter<E> extends TypeAdapter<Set<Object>> {
    final TypeAdapter<Set<Object>> delegateAdapter;
    final TypeAdapter<Object> elementAdapter;

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings("unchecked")
        @Override
        @Nullable
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (type.getRawType() != Set.class) return null;

            Type elementType = ((ParameterizedType) type.getType()).getActualTypeArguments()[0];
            TypeAdapter<Set<Object>> delegateAdapter = (TypeAdapter<Set<Object>>) gson.getDelegateAdapter(this, type);
            TypeAdapter<Object> elementAdapter = (TypeAdapter<Object>) gson.getAdapter(TypeToken.get(elementType));
            return (TypeAdapter<T>) new SingleToSetAdapter<>(delegateAdapter, elementAdapter);
        }
    };

    SingleToSetAdapter(TypeAdapter<Set<Object>> delegateAdapter,
        TypeAdapter<Object> elementAdapter) {
        this.delegateAdapter = delegateAdapter;
        this.elementAdapter = elementAdapter;
    }

    @Override
    public Set<Object> read(JsonReader reader) throws IOException {
        if (reader.peek() != JsonToken.BEGIN_ARRAY)
            return Collections.singleton(elementAdapter.read(reader));

        return delegateAdapter.read(reader);
    }

    @Override
    public void write(JsonWriter writer, Set<Object> value) throws IOException {
        if (value.size() == 1) {
            elementAdapter.write(writer, value.toArray()[0]);
        } else {
            delegateAdapter.write(writer, value);
        }
    }

}
