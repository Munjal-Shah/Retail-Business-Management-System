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
import oracle.jdbc.pool.OracleDataSource;

/*
 * @author Munjal Shah
 */
public class Products extends JFrame {

    private String username = "username";
    private String password = "password";
    List<String> pid = new ArrayList<>();
    List<String> sid = new ArrayList<>();

    private JPanel contentPane;
    private JTextField TFPid;
    private JTextField TFPname;
    private JTextField TFQoh;
    private JTextField TFQohThreshold;
    private JTextField TFOriginalPrice;
    private JTextField TFDiscountRate;
    private JComboBox<String> CBsid;

    private OracleDataSource ds;
    private Connection con;

    public Products() {
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

        setTitle("Add Product");
        setBounds(1200, 600, 350, 350);
        setVisible(true);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Product ID
        JLabel lblProductId = new JLabel("Product Id: ");
        lblProductId.setBounds(50, 30, 95, 20);
        contentPane.add(lblProductId);

        TFPid = new JTextField();
        TFPid.setBounds(160, 30, 90, 20);
        contentPane.add(TFPid);

        //Product Name
        JLabel lblProductName = new JLabel("Product Name: ");
        lblProductName.setBounds(50, 60, 95, 20);
        contentPane.add(lblProductName);

        TFPname = new JTextField();
        TFPname.setBounds(160, 60, 90, 20);
        contentPane.add(TFPname);

        //Qoh
        JLabel lblQoh = new JLabel("Qoh: ");
        lblQoh.setBounds(50, 90, 95, 20);
        contentPane.add(lblQoh);

        TFQoh = new JTextField();
        TFQoh.setBounds(160, 90, 90, 20);
        contentPane.add(TFQoh);

        //Qoh Threshold
        JLabel lblQohThreshold = new JLabel("Qoh Threshold: ");
        lblQohThreshold.setBounds(50, 120, 95, 20);
        contentPane.add(lblQohThreshold);

        TFQohThreshold = new JTextField();
        TFQohThreshold.setBounds(160, 120, 90, 20);
        contentPane.add(TFQohThreshold);

        //Original Price
        JLabel lblOriginalPrice = new JLabel("Original Price: ");
        lblOriginalPrice.setBounds(50, 150, 95, 20);
        contentPane.add(lblOriginalPrice);

        TFOriginalPrice = new JTextField();
        TFOriginalPrice.setBounds(160, 150, 90, 20);
        contentPane.add(TFOriginalPrice);

        //Discount Rate
        JLabel lblDiscountRate = new JLabel("Discount Rate: ");
        lblDiscountRate.setBounds(50, 180, 95, 20);
        contentPane.add(lblDiscountRate);

        TFDiscountRate = new JTextField();
        TFDiscountRate.setBounds(160, 180, 90, 20);
        contentPane.add(TFDiscountRate);
        
        //Supplier Id
        JLabel lblSupplierid = new JLabel("Supplier Id: ");
        lblSupplierid.setBounds(50, 210, 95, 20);
        contentPane.add(lblSupplierid);
        
        CBsid = new JComboBox<String>();
        CBsid.setBounds(160, 210, 90, 20);
        contentPane.add(CBsid);
        try {
            getSids();
            for (int i = 0; i < sid.size(); i++) {
                CBsid.addItem(sid.get(i));
            }
        } catch (SQLException se) {
            System.out.println("\n*** SQLException caught ***\n" + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Add Button
        JButton btnAdd = new JButton("ADD");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                add_products();
            }
        });
        btnAdd.setBounds(110, 250, 90, 20);
        contentPane.add(btnAdd);
    }

    private void add_products() {

        try {
            con = ds.getConnection(username, password);
            CallableStatement cs = con.prepareCall("{call operations.add_product(?,?,?,?,?,?,?)}");

            String match = "^\\d+(\\.\\d+)?";

            if ((TFPid.getText().charAt(0) == 'p' || TFPid.getText().charAt(0) == 'P') && (TFPid.getText().length() == 4)) {
                getPids();
                if (!pid.contains(TFPid.getText())) {
                    if (TFQoh.getText().matches(match)) {
                        if (TFQohThreshold.getText().matches(match)) {
                            if (TFOriginalPrice.getText().matches(match)) {
                                if (TFDiscountRate.getText().matches(match)) {
                                    if (Float.parseFloat(TFDiscountRate.getText()) > 0.2 && Float.parseFloat(TFDiscountRate.getText()) < 0.8) {

                                        cs.setString(1, TFPid.getText().toString());
                                        cs.setString(2, TFPname.getText().toString());
                                        cs.setInt(3, Integer.parseInt(TFQoh.getText()));
                                        cs.setInt(4, Integer.parseInt(TFQohThreshold.getText()));
                                        cs.setInt(5, Integer.parseInt(TFOriginalPrice.getText()));
                                        cs.setFloat(6, Float.parseFloat(TFDiscountRate.getText()));
                                        cs.setString(7, CBsid.getSelectedItem().toString());

                                        cs.execute();
                                        JOptionPane.showMessageDialog(null, "Product Added!!!");

                                    } else {
                                        JOptionPane.showMessageDialog(null, "Value of Discount Rate should be between 0.2 & 0.8!");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Only integer value is accepted for Discount Rate!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Only integer value is accepted for Original Price!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Only integer value is accepted for Qoh Threshold!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Only integer value is accepted for Qoh!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Pid exist enter different pid!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Id Must start with p or Incorrect Length!");
            }

            cs.close();
            con.close();

        } catch (SQLException se) {
            System.out.println("\n*** SQLException caught ***\n" + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    
    private void getSids() throws SQLException {
        con = ds.getConnection(username, password);
        Statement s = con.createStatement();

        ResultSet rs = s.executeQuery("select sid from suppliers");
        while (rs.next()) {
            sid.add(rs.getString(1));
        }
        s.close();
        con.close();
    }

}
