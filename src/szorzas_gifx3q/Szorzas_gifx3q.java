package szorzas_gifx3q;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Adam Nemeth
 * @version 1.0
 *
 */
public class Szorzas_gifx3q extends JFrame implements PropertyChangeListener{

    JSpinner firNumberSpinner, secondNumberSpinner;
    JPanel szamolPanel;
    JPanel aboutPanel;
    JButton calculateButton;
    JTabbedPane tab;
    JLabel aboutText;
    Task task;
    JProgressBar progressBar;
    Integer firsttValue;
    Integer secondtValue;
    JTable table;
    DefaultTableModel model;
    
    class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {            
            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            while (progress < 100) {
                //Sleep for up to one second.
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignore) {}
                //Make random progress.
                progress += 1;
                setProgress(progress);
            }
            return null;
        } 
        
        @Override
        public void done() {
            JOptionPane.showMessageDialog(null, "Eredmény: " + firsttValue*secondtValue);
            addRow(firsttValue, secondtValue);
        }
    }
    
    public Szorzas_gifx3q () {
        super("Program");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        szamolPanel = new JPanel();
        aboutPanel = new JPanel(new BorderLayout());
        szamolPanel.setLayout(new FlowLayout());
        firNumberSpinner = new JSpinner();
        secondNumberSpinner = new JSpinner();        
        calculateButton = new JButton("Számol");
        tab = new JTabbedPane();
        aboutText = new JLabel("<html><center>Németh Ádám<br>nemethadam88@gmail.com</center></html>", SwingConstants.CENTER);
        table = new JTable();
        
        Object[][] data = {{1,1,1},{2,2,2},{3,3,3},{4,4,4}};        
        String[] columnNames = {"Első dobás","Második dobás","Szorzat"};
        model = new DefaultTableModel(data, columnNames);
        table.setModel(model);        
        
        progressBar = new JProgressBar(0, 100);        
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        
        calculateButton.addActionListener((ae) -> {
            firsttValue = (Integer)firNumberSpinner.getValue();
            secondtValue = (Integer)secondNumberSpinner.getValue();
            System.out.println(firsttValue*secondtValue);
            task = new Task();
            task.addPropertyChangeListener(this);
            task.execute();            
        });
        
        Dimension spinnerDimension = new Dimension(100, 30);
        firNumberSpinner.setPreferredSize(spinnerDimension);
        secondNumberSpinner.setPreferredSize(spinnerDimension);
        
        szamolPanel.add(firNumberSpinner);
        szamolPanel.add(secondNumberSpinner);        
        szamolPanel.add(calculateButton);
        szamolPanel.add(progressBar);
        //szamolPanel.add(table);
        szamolPanel.add(new JScrollPane(table));
        aboutPanel.add(aboutText, BorderLayout.CENTER);
        
        tab.addTab("Számolás", szamolPanel);
        tab.addTab("About", aboutPanel);        
        add(tab);
        //setSize(400, 300);
        pack();
        setVisible(true);
    }

    public void addRow(int first, int second){        
        ((DefaultTableModel) table.getModel()).addRow(new Object [] {first, second, first*second});
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
       if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);            
        }
    }    
    
    /**
     * Két nem negatív számot szoroz össze. Ha negatív számot kap akkor -1-et 
     * ad vissza.
     * @author Adam Nemeth
     * @version 1.0
     * @param first első szám
     * @param second második szám
     */
    public static int multiplyNaturals(int first, int second) {
        if (first >= 0 && second >= 0) {
            return first * second;
        } else {
            return -1;
        }
    }
    
    public static void main(String[] args) {
        new Szorzas_gifx3q();
    }
    
}
