import javax.swing.*;

import java.io.*;
import java.net.*;

public class Download implements Runnable
{
   private static final int BUFFER_SIZE = 1024;

   private int size;
   private int downloaded;
   private byte[] buffer;
   private HttpURLConnection http;
   private URL url;

   public Download(String url)
   {
      try
      {
         this.url = new URL(url);
         this.http = (HttpURLConnection) this.url.openConnection();
      }
      catch (Exception e)
      {
         System.err.println("Error");
         System.exit(1);
      }
      
      this.size = this.http.getContentLength();
      this.downloaded = 0;
      this.buffer = new byte[BUFFER_SIZE];

      Thread thread = new Thread(this);
      thread.start();
   }

   @Override
   public void run()
   {
      try
      {
         download();
      }
      catch (Exception e)
      {
         System.err.println("Error");
         System.exit(1);
      }
   }

   public int getFileSize()
   {
      return size;
   }

   private void download() throws IOException
   {
      BufferedInputStream is = new BufferedInputStream(http.getInputStream());
      FileOutputStream fs = new FileOutputStream("demo.pdf");

      int bytesRead;
      while ((bytesRead = is.read(buffer, 0, BUFFER_SIZE)) != -1)
      {
         downloaded += bytesRead;
         fs.write(buffer, 0, bytesRead);
      }

      is.close();
      fs.close();
      http.disconnect();
   }

   public int getDownloadPercent()
   {
      System.out.println(downloaded);
      return ((downloaded * 100)/size);
   }

   public static void main(String[] args)
   {
      Download download = new Download("https://doc.lagout.org/programmation/Java/Data%20Structures%20and%20Algorithms%20in%20Java%20%286th%20ed.%29%20%5BGoodrich%2C%20Tamassia%20%26%20Goldwasser%202014-01-28%5D.pdf");
      
      JProgressBar bar = new JProgressBar(0, 100);
      bar.setStringPainted(true);

      JFrame frame = new JFrame("Test");
      frame.add(bar);
      frame.pack();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);

      int percent = 0;
      while (percent < 100)
      {
         percent = download.getDownloadPercent();
         bar.setValue(percent);
      }
   }
}
