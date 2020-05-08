package project2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.pool.OracleDataSource;

/*
 * @author Munjal Shah
 */
public class MonthlyReport extends JFrame {

    private String username = "username";
    private String password = "password";

    private JPanel contentPane;
    private JTextArea textArea;
    private JScrollPane scroll;
    private JComboBox<String> CBpid;

    private OracleDataSource ds;
    private Connection con;

    ArrayList<String> pid = new ArrayList<>();

    public MonthlyReport() {

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

        setTitle("Monthly report of Product");
        setBounds(1200, 600, 500, 350);
        setVisible(true);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(10, 49, 424, 212);
        contentPane.add(panel);
        panel.setLayout(new BorderLayout(0, 0));

        JLabel lblEmployeeId = new JLabel("Product ID : ");
        lblEmployeeId.setBounds(98, 24, 82, 14);
        contentPane.add(lblEmployeeId);
        CBpid = new JComboBox();
        try {
            getPids();
            for (int i = 0; i < pid.size(); i++) {

                CBpid.addItem(pid.get(i));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        CBpid.setBounds(171, 21, 118, 20);
        contentPane.add(CBpid);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                report();
            }
        });
        btnSubmit.setBounds(299, 20, 89, 23);
        contentPane.add(btnSubmit);

        textArea = new JTextArea();

        scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.add(scroll);

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

    private void report() {
        try {
            con = ds.getConnection(username, password);
            CallableStatement cs = con.prepareCall("begin ? := monthlySales.report_monthly_sales(?); end;");

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.setString(2, CBpid.getSelectedItem().toString());

            cs.execute();
            ResultSet rs = (ResultSet) cs.getObject(1);
            String column_data = "pname" + "\t" + "month" + "\t" + "Year" + "\t" + "quantity" + "\t" + "total_amount" + "\t" + "average_sale_price\t\n\n";
            textArea.append(column_data);
            while (rs.next()) {
                textArea.append(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\n");
            }
            cs.close();
            con.close();
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Something went Wrong!!");
            System.out.println("\n*** SQLException caught ***\n" + se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
