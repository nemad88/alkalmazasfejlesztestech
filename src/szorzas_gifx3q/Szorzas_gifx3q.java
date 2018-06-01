package szorzas_gifx3q;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * 
 * @author Adam Nemeth
 * @version 1.0
 *
 */
public class Szorzas_gifx3q extends JFrame{

    JSpinner firNumberSpinner, secondNumberSpinner;
    JPanel szamolPanel;
    JPanel aboutPanel;
    JButton calculateButton;
    JTabbedPane tab;
    JLabel aboutText;
    
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
        
        
        calculateButton.addActionListener((ae) -> {
            Integer firsttValue = (Integer)firNumberSpinner.getValue();
            Integer secondtValue = (Integer)secondNumberSpinner.getValue();
            System.out.println(firsttValue*secondtValue);
            JOptionPane.showMessageDialog(null, "Eredmény: " + firsttValue*secondtValue);
            
        });
        
        Dimension spinnerDimension = new Dimension(100, 30);
        firNumberSpinner.setPreferredSize(spinnerDimension);
        secondNumberSpinner.setPreferredSize(spinnerDimension);
        
        szamolPanel.add(firNumberSpinner);
        szamolPanel.add(secondNumberSpinner);        
        szamolPanel.add(calculateButton);
        aboutPanel.add(aboutText, BorderLayout.CENTER);
        
        tab.addTab("Számolás", szamolPanel);
        tab.addTab("About", aboutPanel);
        
        add(tab);
        setSize(300, 300);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new Szorzas_gifx3q();
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
}
