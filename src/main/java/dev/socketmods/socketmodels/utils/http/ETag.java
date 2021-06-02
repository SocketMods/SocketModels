package dev.socketmods.socketmodels.utils.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Optional;
import javax.annotation.Nullable;

import dev.socketmods.socketmodels.SocketModels;

public class ETag {

    public static Optional<String> fromRemote(String url) {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("HEAD");
            con.getResponseCode();

            return con.getHeaderFields().getOrDefault("ETag", Collections.emptyList()).stream().findFirst();
        } catch (Exception e) {
            SocketModels.LOGGER.warn("Could not load Etag from " + url, e);
        }

        return Optional.empty();
    }

    public static void save(Path path, @Nullable String eTag) {
        if (eTag == null) return;

        try {
            Files.write(location(path), Collections.singleton(eTag), StandardCharsets.UTF_8);
        } catch (IOException e) {
            SocketModels.LOGGER.warn("Could not save Etag for " + path, e);
        }
    }

    public static Optional<String> load(Path path) {
        Path location = location(path);

        try {
            if (Files.exists(location)) return Files.readAllLines(location).stream().findFirst();
        } catch (IOException e) {
            SocketModels.LOGGER.warn("Could not load Etag for " + path, e);
        }

        return Optional.empty();
    }

    private static Path location(Path path) {
        return path.getParent().resolve(path.getFileName() + ".etag");
    }

}
