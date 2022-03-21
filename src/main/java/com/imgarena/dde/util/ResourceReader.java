package com.imgarena.dde.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

public class ResourceReader {
  public static String readFileToString(String path) throws IOException {
    return FileUtils.readFileToString(ResourceUtils.getFile(path), StandardCharsets.UTF_8);
  }
}
