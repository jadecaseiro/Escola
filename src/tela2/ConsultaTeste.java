package tela2;

//import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.sql.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class ConsultaTeste extends JFrame {

	private JPanel contentPane;

	private final static String url = "jdbc:mysql://localhost:3306/impacta";

	private final static String username = "root";
	private final static String password = "Imp@ct@";

	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	JLabel lblNome = new JLabel("");
	JLabel lblTelefone = new JLabel("");
	JTextField txtCodigo = new JTextField();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaTeste frame = new ConsultaTeste();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConsultaTeste() {
		setTitle("Consulta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 344, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCod = new JLabel("C\u00F3digo:");
		lblCod.setFont(new Font("Courier New", Font.PLAIN, 12));
		lblCod.setBounds(56, 40, 46, 14);
		contentPane.add(lblCod);

		JLabel lblNo = new JLabel("Nome:");
		lblNo.setFont(new Font("Courier New", Font.PLAIN, 12));
		lblNo.setBounds(56, 93, 60, 14);
		contentPane.add(lblNo);

		JLabel lblTel = new JLabel("Telefone:");
		lblTel.setFont(new Font("Courier New", Font.PLAIN, 12));
		lblTel.setBounds(56, 147, 60, 14);
		contentPane.add(lblTel);

		// txtCodigo = new JTextField();
		txtCodigo.setBounds(150, 37, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		// lblNome = new JLabel("");
		lblNome.setBounds(150, 93, 84, 14);
		contentPane.add(lblNome);

		// lblTelefone = new JLabel("");
		lblTelefone.setBounds(150, 147, 86, 14);
		contentPane.add(lblTelefone);

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setFont(new Font("Courier New", Font.PLAIN, 12));
		btnConsultar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openDB();
				mostrar();
				closeDB();
			}
		});
		btnConsultar.setBounds(51, 195, 91, 23);
		contentPane.add(btnConsultar);

		JButton btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Courier New", Font.PLAIN, 12));
		btnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
		btnSair.setBounds(152, 195, 91, 23);
		contentPane.add(btnSair);
	}

	public void openDB() {
		try {
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			System.out.println("\nConexão estabelecida com sucesso!\n");
		} catch (SQLException e) {
			System.out.println("\nNão foi possível estabelecer conexão " + e
					+ "\n");
			System.exit(1);
		}
	}

	public void closeDB() {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("\nNão foi possível fechar conexão " + e + "\n");
			System.exit(1);
		}
	}

	public void mostrar() {
		String query;

		try {
			query = "SELECT * FROM alunos  where codigo = '"
					+ txtCodigo.getText() + "'";

			System.out.println("\nMostrando dados.\n");

			System.out.println(txtCodigo.getText());

			rs = stmt.executeQuery (query);
			if (!rs.next()){
				JOptionPane.showMessageDialog(null,"Registro não existe");
				limpar();
			}
			
			else {
				lblNome.setText(rs.getString("nome"));
				lblTelefone.setText(rs.getString("telefone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("problemas...");
			// setNome(null);
			// setTelefone(null);
		}
	}
	public void limpar(){
		txtCodigo.setText("");
		lblNome.setText("");
		lblTelefone.setText("");
		txtCodigo.requestFocus();
	}
}
