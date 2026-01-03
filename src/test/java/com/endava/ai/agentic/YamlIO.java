package com.endava.ai.agentic;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class YamlIO {

    private YamlIO() { }

    public static <T> T readResource(String resourcePath, Class<T> type) {
        try (InputStream in = resolveResource(resourcePath)) {
            LoaderOptions options = new LoaderOptions();
            Constructor constructor = new Constructor(type, options);
            Yaml yaml = new Yaml(constructor);
            return yaml.load(in);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to load YAML resource: " + resourcePath, e
            );
        }
    }

    private static InputStream resolveResource(String resourcePath) {
        // 1) Context ClassLoader
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl != null) {
            InputStream in = cl.getResourceAsStream(resourcePath);
            if (in != null) return in;
        }

        // 2) Class-relative
        InputStream in = YamlIO.class.getResourceAsStream("/" + resourcePath);
        if (in != null) return in;

        // 3) System ClassLoader
        in = ClassLoader.getSystemResourceAsStream(resourcePath);
        if (in != null) return in;

        throw new IllegalStateException(
                "YAML resource not found on classpath (context, class, system loaders): "
                        + resourcePath
        );
    }
    @SuppressWarnings("unused")
    public static void write(Path path, Object value) {
        try {
            Files.createDirectories(path.getParent());
            Yaml yaml = new Yaml();
            try (OutputStream out = Files.newOutputStream(path)) {
                out.write(yaml.dump(value).getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to write YAML: " + path, e
            );
        }
    }
}
