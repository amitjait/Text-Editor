import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener {


    JTextArea textArea;
    JScrollPane scrollPane;
    JPanel utilityPanel;
    JLabel fontLabel; 
    JSpinner fontSizeSpinner;
    JButton fontColorButton;
    JComboBox fontBox;
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, themeMenu;
    JMenuItem openItem, saveItem, exitItem, newitem, cutItem, pasteItem, copyItem, selectAllItem, darkThemeItem, lightThemeItem, cutomeThemeItem; 
    
    TextEditor(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Text Editor");
        this.setSize(1100, 700);
        this.setLocationRelativeTo(null); 
        this.setLayout(new FlowLayout()); 
        this.setResizable(false);
        
        // this.pack();
        
        textArea = new JTextArea();
        // textArea.setPreferredSize(new Dimension(450, 450));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));


        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(1080, 590));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        fontLabel = new JLabel("Font: ");


        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                // TODO Auto-generated method stub
                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int)fontSizeSpinner.getValue()));

            }             
        });

        fontColorButton = new JButton("Color");
        fontColorButton.addActionListener((ActionListener) this );


        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(); //    Fonts array 

        fontBox = new JComboBox<>(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");

        utilityPanel = new JPanel();
        utilityPanel.add(fontLabel);
        utilityPanel.add(fontSizeSpinner);
        utilityPanel.add(fontColorButton);
        utilityPanel.add(fontBox);

        // ---MenuBar----

        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        newitem = new JMenuItem("New File");
        exitItem = new JMenuItem("Exit");


        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(newitem);
        fileMenu.add(exitItem);


        // ------Edit menu-------

        editMenu = new JMenu("Edit");
        cutItem = new JMenuItem("Cut");
        pasteItem = new JMenuItem("Paste");
        selectAllItem = new JMenuItem("Select All");
        copyItem = new JMenuItem("Copy");

        editMenu.add(cutItem);
        editMenu.add(pasteItem);
        editMenu.add(selectAllItem);
        editMenu.add(copyItem);


        // ------Edit menu-------

        // -----Theme menu -----

        themeMenu = new JMenu("Theme");
        darkThemeItem = new JMenuItem("Dark Theme");
        lightThemeItem = new JMenuItem("Light Theme");
        cutomeThemeItem = new JMenuItem("Custome theme");

        themeMenu.add(darkThemeItem);
        themeMenu.add(lightThemeItem);
        themeMenu.add(cutomeThemeItem);

        // ----Theme Menu------


        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(themeMenu);



        // ---MenuBar----
        

        this.setJMenuBar(menuBar);
        this.add(utilityPanel);
        // this.add(fontLabel);
        // this.add(fontSizeSpinner);
        // this.add(fontColorButton);
        // this.add(fontBox);
        

        
        // this.add(textArea);
        this.add(scrollPane);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == fontColorButton){
            JColorChooser colorChooser = new JColorChooser();

            Color color = colorChooser.showDialog(null, "Choose a Color" , Color.black);

            textArea.setForeground(color);
        }

        if(e.getSource() == fontBox){
            textArea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
        }

        if(e.getSource() == openItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            FileNameExtensionFilter filter = new FileNameExtensionFilter("text file", "txt");
            fileChooser.setFileFilter(filter);

            int response = fileChooser.showOpenDialog(null);

            if(response == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;

                try {
                    fileIn = new Scanner(file);
                    if(file.isFile()){
                        while(fileIn.hasNextLine()){
                            String line  = fileIn.nextLine() + "\n";
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                finally{
                    fileIn.close();
                }
            }
        }
        if(e.getSource() == saveItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int response = fileChooser.showSaveDialog(null);

            if(response == JFileChooser.APPROVE_OPTION){
                File file;
                PrintWriter fileOut = null;
                
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                try {
                    fileOut = new PrintWriter(file);
                    fileOut.print(textArea.getText());
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                finally{
                    fileOut.close();
                }
            }
        }

        if(e.getSource() == exitItem){
            System.exit(0);
        }
        
    }
   
}
