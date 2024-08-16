package utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataMiner {

  public static String refactor(String rawData) throws Exception {

    if (!rawData.contains(":")) {
      return rawData;
    }

    String key = rawData.split(":")[0];
    String value = rawData.split(":")[1];

    switch (key.toLowerCase()) {
      case HardCodes.FILE_KEYWORD:
        return getFileContent(value);
      default:
        throw new Exception("Unable to refactor, " + key + " is an invalid key");
    }
  }

  private static String getFileContent(String filePath) throws Exception {

    InputStream in = DataMiner.class
        .getResourceAsStream(filePath);
    if (in != null) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));

      byte[] replacement = org.apache.commons.io.IOUtils.toString(reader).getBytes();

      return new String(replacement);
    } else {
      throw new Exception("File not found:" + filePath);
    }

  }

}
