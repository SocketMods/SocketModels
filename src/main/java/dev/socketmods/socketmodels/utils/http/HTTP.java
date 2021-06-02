package dev.socketmods.socketmodels.utils.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import org.apache.commons.io.IOUtils;
import dev.socketmods.socketmodels.utils.functional.IOConsumer;

public class HTTP {

    public static void downloadEtag(Path path, String url, IOConsumer<InputStream> consumer) throws IOException {
        String local  = ETag.load(path).orElse(null);
        String server = ETag.fromRemote(url).orElse(null);

        // If we have a local copy and the server doesn't use the local
        //  TODO: Additional handling if server exists but doesn't provide ETag ?
        // Or if it's the same as the server
        if (local != null) {
            if (server == null || Objects.equals(local, server)) {
                try (InputStream stream = Files.newInputStream(path)) {
                    consumer.accept(stream);
                }
                return;
            }
        }

        // Otherwise Download
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        download(url, (input) -> IOUtils.copy(input, data));

        try {
            consumer.accept(new ByteArrayInputStream(data.toByteArray()));
        } catch (IOException e) {
            // If the remote is ill formatted then try the local copy
            if (local != null)
                try (InputStream stream = Files.newInputStream(path)) {
                    consumer.accept(stream);
                }

            throw e;
        }

        // Save the remote
        copyTo(path).accept(new ByteArrayInputStream(data.toByteArray()));
        ETag.save(path, server);
    }


    public static void download(String url, IOConsumer<InputStream> consumer) throws IOException {
        try (InputStream input = new URL(url).openStream()) {
            consumer.accept(input);
        }
    }

    public static IOConsumer<InputStream> copyTo(Path path) {
        return (input) -> {
            Files.createDirectories(path.getParent());

            try (OutputStream output = Files.newOutputStream(path)) {
                IOUtils.copy(input, output);
            }
        };
    }


}
