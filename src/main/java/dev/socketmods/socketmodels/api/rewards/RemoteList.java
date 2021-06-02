package dev.socketmods.socketmodels.api.rewards;

import java.io.IOException;
import java.io.Reader;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import dev.socketmods.socketmodels.utils.gson.Gsons;
import dev.socketmods.socketmodels.utils.gson.SingleToSetAdapter;

public class RemoteList {

    private static final Gson GSON = Gsons.lenientType(SingleToSetAdapter.FACTORY);

    public final int version;
    public final Set<Person> people;

    public RemoteList(int version, Set<Person> people) {
        this.version = version;
        this.people = Sets.filter(people, Objects::nonNull);
    }

    public static RemoteList parse(String data) throws IOException {
        try {
            return GSON.fromJson(data, RemoteList.class);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public static RemoteList parse(Reader reader) throws IOException {
        try {
            return GSON.fromJson(reader, RemoteList.class);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

}