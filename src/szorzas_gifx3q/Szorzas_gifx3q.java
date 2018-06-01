package szorzas_gifx3q;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
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
public class Szorzas_gifx3q extends JFrame implements PropertyChangeListener {

    private JSpinner firstNumberSpinner, secondNumberSpinner;
    private JPanel szamolPanel, vezerlokPanel, aboutPanel, adatbazisPanel, filePanel;
    private JButton calculateButton, dbbeButton, dbboButton, saveFileButton, loadFileButton;
    private JTabbedPane tab;
    private JLabel aboutText;
    private Task task;
    private JProgressBar progressBar;
    private Integer firstValue;
    private Integer secondValue;
    private JTable table;
    private DefaultTableModel model;
    private Connection con;
    private Statement stm;

    class Task extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() {
            int progress = 0;
            setProgress(0);
            while (progress < 100) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignore) {
                }
                progress += 1;
                setProgress(progress);
            }
            return null;
        }

        @Override
        public void done() {
            JOptionPane.showMessageDialog(null, "Eredmény: " + multiplyNaturals(firstValue, secondValue));
            addRowToTable(firstValue, secondValue);
            setProgress(0);
        }
    }

    public Szorzas_gifx3q() throws SQLException {
        super("Szorzás");
        setGUI();
    }

    public void setGUI() {

        szamolPanel = new JPanel();
        vezerlokPanel = new JPanel();
        aboutPanel = new JPanel(new BorderLayout());
        adatbazisPanel = new JPanel();
        filePanel = new JPanel();
        szamolPanel.setLayout(new BoxLayout(szamolPanel, BoxLayout.Y_AXIS));

        firstNumberSpinner = new JSpinner();
        secondNumberSpinner = new JSpinner();

        calculateButton = new JButton("Számol");
        dbbeButton = new JButton("DB-be nyom");
        dbboButton = new JButton("DB-bő' tőt");
        dbbeButton.setBackground(Color.yellow);
        dbboButton.setBackground(Color.yellow);
        dbbeButton.addActionListener((ae) -> {
            try {
                refreshDb();
            } catch (SQLException ex) {
                Logger.getLogger(Szorzas_gifx3q.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        dbboButton.addActionListener((ae) -> {
            try {
                loadDb();
            } catch (SQLException ex) {
                Logger.getLogger(Szorzas_gifx3q.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        saveFileButton = new JButton("Kimentés állományba");
        loadFileButton = new JButton("Betöltés állományból");
        saveFileButton.setBackground(Color.green);
        loadFileButton.setBackground(Color.green);

        saveFileButton.addActionListener((ae) -> {
            try {
                saveToFile();
            } catch (IOException ex) {
                Logger.getLogger(Szorzas_gifx3q.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        loadFileButton.addActionListener((ae) -> {
            loadFromFile();
        });
        
        tab = new JTabbedPane();
        aboutText = new JLabel("<html><center>Németh Ádám<br>nemethadam88@gmail.com</center></html>", SwingConstants.CENTER);
        table = new JTable();

        Object[][] data = {};
        String[] columnNames = {"Első dobás", "Második dobás", "Szorzat"};
        model = new DefaultTableModel(data, columnNames);
        table.setModel(model);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        calculateButton.addActionListener((ae) -> {
            firstValue = (Integer) firstNumberSpinner.getValue();
            secondValue = (Integer) secondNumberSpinner.getValue();
            task = new Task();
            task.addPropertyChangeListener(this);
            task.execute();
        });

        Dimension spinnerDimension = new Dimension(100, 30);
        firstNumberSpinner.setPreferredSize(spinnerDimension);
        secondNumberSpinner.setPreferredSize(spinnerDimension);

        vezerlokPanel.add(firstNumberSpinner);
        vezerlokPanel.add(secondNumberSpinner);
        vezerlokPanel.add(calculateButton);
        vezerlokPanel.add(progressBar);

        adatbazisPanel.add(dbbeButton);
        adatbazisPanel.add(dbboButton);

        filePanel.add(saveFileButton);
        filePanel.add(loadFileButton);

        szamolPanel.add(vezerlokPanel);
        szamolPanel.add(new JScrollPane(table));
        szamolPanel.add(adatbazisPanel);
        szamolPanel.add(filePanel);
        aboutPanel.add(aboutText, BorderLayout.CENTER);

        tab.addTab("Számolás", szamolPanel);
        tab.addTab("About", aboutPanel);

        add(tab);
        pack();
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void addRowToTable(int first, int second) {
        ((DefaultTableModel) table.getModel()).addRow(new Object[]{first, second, multiplyNaturals(first, second)});
    }

    public void loadDb() throws SQLException {
        String sql = "select * from szorzas.dump;";
        con = DriverManager.getConnection("jdbc:mysql://localhost:3307/szorzas?user=root&password=");
        stm = con.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        while (rs.next()) {
            model.addRow(new Object[]{rs.getInt("F"), rs.getInt("S"), rs.getInt("P")});
        }
    }

    public void refreshDb() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3307/?user=root&password=");
        stm = con.createStatement();
        String sql = "create database if not exists szorzas;";
        stm.execute(sql);
        sql = "create table if not exists szorzas.dump (F integer, S integer, P integer);";
        stm.execute(sql);
        sql = "delete from szorzas.dump;";
        stm.execute(sql);
        int rowCount = table.getModel().getRowCount();
        int columnCount = table.getModel().getColumnCount();

        for (int i = 0; i < rowCount; i++) {
            int firstValue = Integer.parseInt(table.getModel().getValueAt(i, 0).toString());
            int secondValue = Integer.parseInt(table.getModel().getValueAt(i, 1).toString());
            int multiValue = Integer.parseInt(table.getModel().getValueAt(i, 2).toString());
            sql = "insert into szorzas.dump (F, S, P) values (" + firstValue + ", " + secondValue + ", " + multiValue + ");";
            stm.execute(sql);
        }
    }

    public void saveToFile() throws IOException {
        FileOutputStream fileOut = new FileOutputStream("stuff.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(model);
        out.close();
    }

    public void loadFromFile() {
        try {
            FileInputStream fileIn = new FileInputStream("stuff.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            model = (DefaultTableModel) in.readObject();
            table.setModel(model);
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        }
    }

    /**
     * Két nem negatív számot szoroz össze. Ha negatív számot kap akkor -1-et ad
     * vissza.
     *
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

    public static void main(String[] args) throws SQLException {
        new Szorzas_gifx3q();
    }

}
