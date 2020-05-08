package project2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.pool.OracleDataSource;

/*
 * @author Munjal Shah
 */
public class GUI extends JFrame {
    
    private String username = "username";
    private String password = "password";

    private JPanel contentPane;
    private JTextArea menu;

    private OracleDataSource ds;
    private Connection con;

    
    public GUI() {

        try {
            ds = new oracle.jdbc.pool.OracleDataSource();
            ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:acad111");

            initUI();
            
        } catch (SQLException se) {
            System.out.println("\n*** SQLException caught ***\n" + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private void initUI() {

        createMenuBar();

        setTitle("CS 432/532 Project 2");
        setSize(800, 400);
        setLocation(1000, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        menu = new JTextArea();
        contentPane.add(menu, BorderLayout.CENTER);
    }
    

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu display = new JMenu("Display");
        menuBar.add(display);
        displayItems(display);

        JMenu function = new JMenu("Functions");
        menuBar.add(function);
        functionItems(function);
    }
    

    private void displayItems(JMenu display) {
        JMenuItem employees = new JMenuItem("Employees");
        employees.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.setText("");
                show_employees();
            }
        });
        display.add(employees);

        JMenuItem customers = new JMenuItem("Customers");
        customers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.setText("");
                show_customers();
            }
        });
        display.add(customers);

        JMenuItem products = new JMenuItem("Products");
        products.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.setText("");
                show_products();
            }
        });
        display.add(products);
        
        JMenuItem purchases = new JMenuItem("Purchases");
        purchases.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.setText("");
                show_purchases();
            }
        });
        display.add(purchases);

        JMenuItem suppliers = new JMenuItem("Suppliers");
        suppliers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.setText("");
                show_suppliers();
            }
        });
        display.add(suppliers);

        JMenuItem supply = new JMenuItem("Supply");
        supply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.setText("");
                show_supply();
            }
        });
        display.add(supply);

        JMenuItem logs = new JMenuItem("Logs");
        logs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.setText("");
                show_logs();
            }
        });
        display.add(logs);
    }
    

    private void functionItems(JMenu function) {
        JMenuItem addProducts = new JMenuItem("Add Products");
        addProducts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Products();
            }
        });
        function.add(addProducts);

        JMenuItem addPurchase = new JMenuItem("Add Purchase");
        addPurchase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Purchases();
            }
        });
        function.add(addPurchase);

        JMenuItem monthlySales = new JMenuItem("Monthly Sales");
        monthlySales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MonthlyReport();
            }
        });
        function.add(monthlySales);
    }
    
    
    private void show_employees() {

        try {
            con = ds.getConnection(username, password);
            CallableStatement cs = con.prepareCall("begin show.show_employees(?); END;");
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);
            String columns = "  " + "EID" + "\t" + "ENAME" + "\t" + "TELEPHONE#\n\n";
            menu.append(columns);
            while (rs.next()) {
                menu.append("  " + rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\n");
            }
            
            cs.close();
            con.close();

        } catch (SQLException se) {
            System.out.println("\n*** SQLException caught ***\n" + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private void show_customers() {

        try {
            con = ds.getConnection(username, password);
            CallableStatement cs = con.prepareCall("begin show.show_customers(?); END;");
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);
            String columns = "  " + "CID" + "\t" + "CNAME" + "\t" + "TELEPHONE#" + "\t" + "VISITS_MADE" + "\t" + "LAST_VISIT_DATE\n\n";
            menu.append(columns);
            while (rs.next()) {
                menu.append("  " + rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getDate(5) + "\n");
            }
            
            cs.close();
            con.close();

        } catch (SQLException se) {
            System.out.println("\n*** SQLException caught ***\n" + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private void show_products() {

        try {
            con = ds.getConnection(username, password);
            CallableStatement cs = con.prepareCall("begin show.show_products(?); END;");
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);
            String columns = "  " + "PID" + "\t" + "PNAME" + "\t" + "QOH" + "\t" + "QOH_THRESHOLD" + "\t" + "ORIGINAL_PRICE" + "\t" + "DISCNT_RATE\n\n";
            menu.append(columns);
            while (rs.next()) {
                menu.append("  " + rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\n");
            }
            
            cs.close();
            con.close();

        } catch (SQLException se) {
            System.out.println("\n*** SQLException caught ***\n" + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private void show_purchases() {

        try {
            con = ds.getConnection(username, password);
            CallableStatement cs = con.prepareCall("begin show.show_purchases(?); END;");
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);
            String columns = "  " + "PUR#" + "\t" + "EID" + "\t" + "PID" + "\t" + "CID" + "\t" + "QTY" + "\t" + "PTIME" + "\t" + "TOTAL_PRICE\n\n";
            menu.append(columns);
            while (rs.next()) {
                menu.append("  " + rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getDate(6) + "\t" + rs.getString(7)+ "\n");
            }
            
            cs.close();
            con.close();

        } catch (SQLException se) {
            System.out.println("\n*** SQLException caught ***\n" + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private void show_suppliers() {

        try {
            con = ds.getConnection(username, password);
            CallableStatement cs = con.prepareCall("begin show.show_suppliers(?); END;");
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);
            String columns = "  " + "SID" + "\t" + "SNAME" + "\t" + "CITY" + "\t" + "TELEPHONE#\n\n";
            menu.append(columns);
            while (rs.next()) {
                menu.append("  " + rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\n");
            }
            
            cs.close();
            con.close();

        } catch (SQLException se) {
            System.out.println("\n*** SQLException caught ***\n" + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private void show_supply() {

        try {
            con = ds.getConnection(username, password);
            CallableStatement cs = con.prepareCall("begin show.show_supply(?); END;");
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);
            String columns = "  " + "SUP#" + "\t" + "PID" + "\t" + "SID" + "\t" + "SDATE" + "\t" + "QUANTITY\n\n";
            menu.append(columns);
            while (rs.next()) {
                menu.append("  " + rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getDate(4) + "\t" + rs.getString(5) + "\n");
            }
            
            cs.close();
            con.close();

        } catch (SQLException se) {
            System.out.println("\n*** SQLException caught ***\n" + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private void show_logs() {

        try {
            con = ds.getConnection(username, password);
            CallableStatement cs = con.prepareCall("begin show.show_logs(?); END;");
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);
            String columns = "  " + "LOG#" + "\t" + "WHO" + "\t" + "TABLE_NAME" + "\t" + "OPERATION" + "\t" + "KEY_VALUE\n\n";
            menu.append(columns);
            while (rs.next()) {
                menu.append("  " + rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\n");
            }
            
            cs.close();
            con.close();

        } catch (SQLException se) {
            System.out.println("\n*** SQLException caught ***\n" + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
