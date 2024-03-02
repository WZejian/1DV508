package cookbook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class PictureTest {
  @Test
  void illegalFormat_garbageFile_badIdDbFetch() {
    DbConnector.connect();
    File pic = new File("./tmp/safeFolder/TIFF.tiff");
    File notpic = new File("./README.md");
    Path placeholderPath = Paths.get("tmp/safeFolder/placeholder.jpeg");
    assertEquals(-2, Picture.imgUpload(pic.toPath()));
    assertEquals(-2, Picture.imgUpload(notpic.toPath()));
    assertEquals(placeholderPath.toString(), Picture.imgFetch(-1).toString());
  }
}
