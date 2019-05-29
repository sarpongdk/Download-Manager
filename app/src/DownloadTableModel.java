import javax.swing.table.*;
import javax.swing.*;

import java.util.ArrayList;

public class DownloadTableModel extends AbstractTableModel
{
   private static final String[] colNames = {"URL", "Size", "Progress"};
   private static final Class[] colClasses = {String.class, Integer.class, JProgressBar.class};

   private ArrayList<Download> downloads;

   public DownloadTableModel()
   {
      super();

      downloads = new ArrayList<>();
   }

   @Override
   public String getColumnName(int column)
   {
      return colNames[column];
   }

   @Override
   public Class getColumnClass(int column)
   {
      return colClasses[column];
   }

   @Override
   public int getRowCount()
   {
      return downloads.size();
   }
   
   @Override
   public int getColumnCount()
   {
      return colNames.length;
   }
   
   @Override
   public Object getValueAt(int row, int column)
   {
      Download download = downloads.get(row);
      Object val = "";

      switch (column)
      {
         case 0:
            val = download.getURL();
            break;

         case 1:
            val = download.getDownloadSize();
            break;

         case 2:
            val = download.getDownloadProgress();
            break;
      }

      return val;
   }

   public Download getDownload(int row)
   {
      return downloads.get(row);
   }

   public void addDownload(Download download)
   {
      downloads.add(download);

      fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
   }
   
   public void removeDownload(int row)
   {
      downloads.remove(row);

      fireTableRowsDeleted(row, row);
   }
}
