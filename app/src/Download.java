import javax.swing.*;

import java.io.*;
import java.net.*;

public class Download extends Thread
{
   private static final int BUFFER_SIZE = 1024;

   private int size;
   private int downloaded;
   private byte[] buffer;
   private HttpURLConnection http;
   private URL url;
   private String filename;
   private File file;

   public Download(URL url, String filename)
   {
      super();

      try
      {
         this.url = url;
         this.http = (HttpURLConnection) this.url.openConnection();
      }
      catch (Exception e)
      {
         System.err.println("Error");
         System.exit(1);
      }
      
      this.filename = filename;
      this.size = this.http.getContentLength();
      this.downloaded = 0;
      this.buffer = new byte[BUFFER_SIZE];

      start();
   }

   public Download(String url, String filename)
   {
      super();

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
      
      this.filename = filename;
      this.size = this.http.getContentLength();
      this.downloaded = 0;
      this.buffer = new byte[BUFFER_SIZE];

      start();
   }

   @Override
   public void run()
   {
      try
      {
         download(this.filename);
      }
      catch (Exception e)
      {
         System.err.println("Error");
         
         return;
      }
   }

   public int getDownloadSize()
   {
      return size;
   }

   public void pauseDownload()
   {
    
   }

   public void resumeDownload()
   {
    
   }

   public void cancelDownload()
   {
      file.delete();
      interrupt();
   }

   private void download(String filename) throws IOException
   {
      BufferedInputStream is = new BufferedInputStream(http.getInputStream());
      file = new File(filename);
      file.createNewFile();
      RandomAccessFile raf = new RandomAccessFile(file, "w");

      int bytesRead;
      while ((bytesRead = is.read(buffer, 0, BUFFER_SIZE)) != -1)
      {
         raf.seek(downloaded);
         raf.write(buffer, 0, bytesRead);
         downloaded += bytesRead;
      }

      is.close();
      raf.close();
      http.disconnect();
   }

   public int getDownloadProgress()
   {
      System.out.println(downloaded);
      return ((downloaded * 100)/size);
   }

   public String getURL()
   {
      return url.toString();
   }

   public static void main(String[] args)
   {
      Download download = new Download("https://doc.lagout.org/programmation/Java/Data%20Structures%20and%20Algorithms%20in%20Java%20%286th%20ed.%29%20%5BGoodrich%2C%20Tamassia%20%26%20Goldwasser%202014-01-28%5D.pdf", "demo.pdf");
      
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
         percent = download.getDownloadProgress();
         bar.setValue(percent);
      }
   }
}
