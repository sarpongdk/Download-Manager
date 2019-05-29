import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.net.*;

public class DownloadManagerGUI extends JFrame
{
   public static final int TEXTFIELD_SIZE = 40;
   public static final int WIDTH = 640;
   public static final int HEIGHT = 480;

   private JPanel textPanel, tablePanel, buttonsPanel;
   private JTextField textField;
   private JButton addDownload, cancel, pause, resume;
   private JMenuBar menuBar;
   private DownloadTable table;
   private DownloadTableModel tableModel;

   public DownloadManagerGUI()
   {
      initVariable();
      initMenu();
      initListeners();
      initPanels();
      initFrame();
   }

   private void initVariable()
   {
      textPanel = new JPanel();
      tablePanel = new JPanel();
      buttonsPanel = new JPanel();
      textField = new JTextField(TEXTFIELD_SIZE);
      addDownload = new JButton("Add Download");
      cancel = new JButton("Cancel");
      pause = new JButton("Pause");
      resume = new JButton("Resume");
      menuBar = new JMenuBar();

      tableModel = new DownloadTableModel();
      table = new DownloadTable(tableModel);
   }

   private void initListeners()
   {
      addDownload.addActionListener(new ActionListener() {
      
         @Override
         public void actionPerformed(ActionEvent e)
         {
            String urlString = textField.getText();
            

            try
            {
               URL url = new URL(urlString);
               String urlPath = url.getPath();
               tableModel.addDownload(new Download(url, urlPath));
               addDownload.setText("");
            }
            catch (Exception ex)
            {
               actionExit();
            }

         }
      });
   }


   private void initPanels()
   {
      textPanel.add(textField);
      textPanel.add(addDownload);

      JScrollPane tablePane = new JScrollPane(table);
      tablePanel.add(tablePane);

      buttonsPanel.add(pause);
      buttonsPanel.add(resume);
      buttonsPanel.add(cancel);
   }

   private void initFrame()
   {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setPreferredSize(new Dimension(WIDTH, HEIGHT));
      setTitle("Download Manager");

      setJMenuBar(menuBar);

      setLayout(new BorderLayout());
      add(textPanel, BorderLayout.NORTH);
      add(tablePanel, BorderLayout.CENTER);
      add(buttonsPanel, BorderLayout.SOUTH);

      pack();
      setVisible(true);
   }

   private void initMenu()
   {
      JMenu fileMenu = new JMenu("File");
      fileMenu.setMnemonic(KeyEvent.VK_F);
      JMenuItem fileExitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
      
      fileExitMenuItem.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            actionExit();
         }
      });

      fileMenu.add(fileExitMenuItem);
      menuBar.add(fileMenu);
   }

   private void actionExit()
   {
      System.exit(1);
   }

   public static void main(String[] args)
   {
      DownloadManagerGUI gui = new DownloadManagerGUI();
   }
}
