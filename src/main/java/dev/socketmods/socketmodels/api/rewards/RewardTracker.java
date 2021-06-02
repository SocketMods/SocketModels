package dev.socketmods.socketmodels.api.rewards;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;

import dev.socketmods.socketmodels.SocketModels;
import dev.socketmods.socketmodels.utils.Workers;
import dev.socketmods.socketmodels.utils.http.HTTP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraftforge.fml.loading.FMLPaths;

public class RewardTracker {

    //TODO: Point to a repository on SocketMods
    private static final String REMOTE_URL
        = "https://gist.githubusercontent.com/AterAnimAvis/ac0fa5803a4917602e1c3a82034ca0d0/raw/contributors.json";

    private static final Path CACHE_PATH = Paths.get("socketmods", SocketModels.MODID + "-rewards.json");

    private static final Map<String, Person> PEOPLE = new HashMap<>();
    private static final Person NULL = new Person("Nil", Util.NIL_UUID.toString(), Collections.emptySet());

    //------------------------------------------------------------------------------------------------------------------

    public static boolean hasPermission(PlayerEntity player, String permission) {
        return hasPermission(getPerson(player), permission);
    }

    public static boolean hasPermission(String uuid, String permission) {
        return hasPermission(getPerson(uuid), permission);
    }

    public static boolean hasPermission(Person person, String permission) {
        return person.permissions.contains(permission.toLowerCase(Locale.ROOT));
    }

    public static boolean hasPermission(PlayerEntity player, String... permission) {
        return hasPermission(getPerson(player), Arrays.asList(permission));
    }

    public static boolean hasPermission(String uuid, String... permission) {
        return hasPermission(getPerson(uuid), Arrays.asList(permission));
    }

    public static boolean hasPermission(PlayerEntity player, Collection<String> permission) {
        return hasPermission(getPerson(player), permission);
    }

    public static boolean hasPermission(String uuid, Collection<String> permission) {
        return hasPermission(getPerson(uuid), permission);
    }

    public static boolean hasPermission(Person person, Collection<String> permission) {
        return !Collections.disjoint(person.permissions, permission);
    }

    //------------------------------------------------------------------------------------------------------------------

    public static Collection<Person> getPeople() {
        return Collections.unmodifiableCollection(PEOPLE.values());
    }

    public static Person getPerson(PlayerEntity player) {
        return getPerson(player.getStringUUID());
    }

    public static Person getPerson(String uuid) {
        return PEOPLE.getOrDefault(uuid, NULL);
    }

    @Nullable
    public static Person getPersonOrNull(PlayerEntity player) {
        return getPersonOrNull(player.getStringUUID());
    }

    @Nullable
    public static Person getPersonOrNull(String uuid) {
        return PEOPLE.get(uuid);
    }

    //------------------------------------------------------------------------------------------------------------------

    private static final CompletableFuture<Void> loaded = CompletableFuture.runAsync(RewardTracker::load, Workers.DAEMON_SINGLE);

    public static CompletableFuture<Void> whenLoaded() {
        return loaded;
    }

    private static void load() {
        try {
            //TODO: Add fallback if local doesn't exist as well?
            HTTP.downloadEtag(FMLPaths.CONFIGDIR.get().resolve(CACHE_PATH), REMOTE_URL, RewardTracker::handle);
        } catch (Exception e) {
            SocketModels.LOGGER.warn("Could not load RewardTracker", e);
        }
    }

    private static void handle(InputStream stream) throws IOException {
        RemoteList list = RemoteList.parse(new InputStreamReader(stream));
        list.people.forEach(person -> PEOPLE.merge(person.uuid, person, Person::merge));
    }

}
