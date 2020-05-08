package project2;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.pool.OracleDataSource;

/*
 * @author Munjal Shah
 */
public class Purchases extends JFrame {
    
    private String username = "username";
    private String password = "password";
    
    private JPanel contentPane;
    private JTextField TFQuantity;
    private JComboBox<String> CBpid;
    private JComboBox<String> CBeid;
    private JComboBox<String> CBcid;
    
    private OracleDataSource ds;
    private Connection con;
    
    List<String> pid = new ArrayList<>();
    List<String> eid = new ArrayList<>();
    List<String> cid = new ArrayList<>();
    
    public Purchases() {
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

        setTitle("Add Purchase");
        setBounds(1200, 600, 350, 300);
        setVisible(true);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        
        
        // Product ID
        JLabel lblProductId = new JLabel("Product Id :");
        lblProductId.setBounds(50, 30, 75, 20);
        contentPane.add(lblProductId);
        
        CBpid = new JComboBox<String>();
        CBpid.setBounds(140, 30, 90, 20);
        contentPane.add(CBpid);
        try {
            getPids();
            for (int i = 0; i < pid.size(); i++) {
                CBpid.addItem(pid.get(i));
            }
        } catch (SQLException se) {
            System.out.println("\n*** SQLException caught ***\n" + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        //Employee ID
        JLabel lblEmployeeId = new JLabel("Employee Id :");
        lblEmployeeId.setBounds(50, 60, 75, 20);
        contentPane.add(lblEmployeeId);
        
        CBeid = new JComboBox<String>();
        CBeid.setBounds(140, 60, 90, 20);
        contentPane.add(CBeid);
        try {
            getEids();
            for (int i = 0; i < eid.size(); i++) {
                CBeid.addItem(eid.get(i));
            }
        } catch (SQLException se) {
            System.out.println("\n*** SQLException caught ***\n" + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        //Customer ID
        JLabel lblCustomerId = new JLabel("Customer Id :");
        lblCustomerId.setBounds(50, 90, 75, 20);
        contentPane.add(lblCustomerId);
        
        CBcid = new JComboBox<String>();
        CBcid.setBounds(140, 90, 90, 20);
        contentPane.add(CBcid);
        try {
            getCids();
            for (int i = 0; i < cid.size(); i++) {
                CBcid.addItem(cid.get(i));
            }
        } catch (SQLException se) {
            System.out.println("\n*** SQLException caught ***\n" + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        //Quantity
        JLabel lblQuantity = new JLabel("Quantity :");
        lblQuantity.setBounds(50, 120, 75, 20);
        contentPane.add(lblQuantity);

        TFQuantity = new JTextField();
        TFQuantity.setBounds(140, 120, 90, 20);
        contentPane.add(TFQuantity);

        
        //Add Button
        JButton btnAdd = new JButton("ADD");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                add_purchase();
            }
        });
        btnAdd.setBounds(100, 160, 90, 20);
        contentPane.add(btnAdd);
    }

    
    private void getPids() throws SQLException {
        con = ds.getConnection(username, password);
        Statement s = con.createStatement();

        ResultSet rs = s.executeQuery("select pid from products");
        while (rs.next()) {
            pid.add(rs.getString(1));
        }
        s.close();
        con.close();
    }
    
    
    private void getEids() throws SQLException {
        con = ds.getConnection(username, password);
        Statement s = con.createStatement();

        ResultSet rs = s.executeQuery("select eid from employees");
        while (rs.next()) {
            eid.add(rs.getString(1));
        }
        s.close();
        con.close();
    }
    
    
    private void getCids() throws SQLException {
        con = ds.getConnection(username, password);
        Statement s = con.createStatement();

        ResultSet rs = s.executeQuery("select cid from customers");
        while (rs.next()) {
            cid.add(rs.getString(1));
        }
        s.close();
        con.close();
    }
    
    
    private void add_purchase(){
        
        try {
            con = ds.getConnection(username, password);
            CallableStatement cs = con.prepareCall("begin ? := operations.add_purchase(?,?,?,?); end;");
            cs.registerOutParameter(1, OracleTypes.NUMBER);
            
            if (TFQuantity.getText().matches("^\\d+(\\.\\d+)?")) {
                
                cs.setString(2, CBpid.getSelectedItem().toString());
                cs.setString(3, CBeid.getSelectedItem().toString());
                cs.setString(4, CBcid.getSelectedItem().toString());
                cs.setInt(5, Integer.parseInt(TFQuantity.getText()));

                cs.execute();

                int result = cs.getInt(1);
                if (result == -123) {
                    JOptionPane.showMessageDialog(null, "Insufficient quantity in stock!");
                } else {
                    JOptionPane.showMessageDialog(null, "Purchase added successfully");
                    JOptionPane.showMessageDialog(null, "Now Product Quantity of " + CBpid.getSelectedItem().toString() + " = " + result);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Only integer value is accepted for Quantity!");

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
