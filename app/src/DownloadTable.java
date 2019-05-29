import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;

public class DownloadTable extends JTable
{
   private static final int MIN = 0;
   private static final int MAX = 100;
   
   private ProgressRenderer renderer;
   private TableModel model;

   public DownloadTable(TableModel model)
   {
      super(model);

      this.renderer = new ProgressRenderer(MIN, MAX);
      setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      setDefaultRenderer(JProgressBar.class, this.renderer);
   }

   public static class ProgressRenderer extends JProgressBar implements TableCellRenderer
   {
      public ProgressRenderer(int min, int max)
      {
         super(min, max);
      }

      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
boolean hasFocus, int row, int column)
      {
         setValue((int) value);
         return this;
      }
   }

   public static void main(String[] args)
   {
      JFrame frame = new JFrame("Demo");
      frame.addWindowListener(new WindowAdapter() {
                  
         @Override
         public void windowClosing(WindowEvent e)
         {
            System.exit(1);
         }
      });

      DownloadTableModel model = new DownloadTableModel();
      model.addDownload(new Download("https://doc.lagout.org/programmation/Java/Data%20Structures%20and%20Algorithms%20in%20Java%20%286th%20ed.%29%20%5BGoodrich%2C%20Tamassia%20%26%20Goldwasser%202014-01-28%5D.pdf"));
      frame.add((JTable) new DownloadTable(model));
      frame.pack();
      frame.setVisible(true);
   }


}
