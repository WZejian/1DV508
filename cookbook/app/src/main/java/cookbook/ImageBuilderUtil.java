package cookbook;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Meichen Ji
 * This class is only used by shopping list, to import shopping list as a jpeg.
 * This class does not affect any other classes, you can ignore this.
 * still work in progress.
 */
public class ImageBuilderUtil {
  /**
   * This class is to generate an image, only used in shopping list.
   * 
   * @param path     the path of heading "my shopping list"
   * @param inlst    the arraylist of ingredient
   * @param savePath the folder that user want to store the image
   */
  public static String imageBuilder(String path, ArrayList<IngredientRec> inlst, String savePath) {
    final int width = 2268;
    final int height = inlst.size() * 215 + 600;
    FileInputStream fileInputStream = null;

    try {
      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      File file = new File(path);
      fileInputStream = new FileInputStream(file);
      BufferedImage image2 = ImageIO.read(fileInputStream);
      Graphics2D g2 = (Graphics2D) bi.createGraphics();
      Color background = new Color(255, 229, 118); // yellow background
      g2.setColor(background);
      g2.fillRect(0, 0, width, height);
      g2.drawImage(image2, 91, 100, 1757, 228, null); // heading

      g2.drawRect(0, 0, width - 1, height - 1);
      g2.setFont(new Font("Arial", Font.BOLD, 72));
      g2.setColor(Color.BLACK);
      LocalDateTime timenow = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      String timeline = "Time: " + timenow.format(formatter);
      g2.drawString(timeline, 150, 400); // time
      int count = 600;
      // every ingredient
      for (IngredientRec ingr : inlst) {
        g2.drawString(ingr.getName(), 150, count);
        g2.drawString(String.valueOf(ingr.getQuantity()), 1100, count);
        g2.drawString(ingr.getUnit(), 1800, count);
        count += 215;
      }
      DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
      String saveTo = savePath + "/shopping_list_" + timenow.format(formatter2) + ".jpeg";
      ImageIO.write(bi, "JPEG", new FileOutputStream(saveTo));
      return saveTo;
    } catch (Exception e) {
      e.printStackTrace();
      return "error!";
    } finally {
      if (fileInputStream != null) {
        try {
          fileInputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
