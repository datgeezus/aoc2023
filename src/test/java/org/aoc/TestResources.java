package org.aoc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public interface TestResources {

    default String loadResource(String path) {
        try(var inStream = getClass().getClassLoader().getResourceAsStream(path);) {
            return new String(inStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();;
        }
        return "";
    }
}
