package tela2;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cadastro extends JFrame {
	
	private JPanel contentPane;
	private final static String url = "jdbc:mysql://localhost:3306/impacta";

	private final static String username = "root";
	private final static String password = "Imp@ct@";

	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	
	private JTextField txtCodigo = new JTextField();
	private JTextField txtNome = new JTextField();
	private JTextField txtTelefone = new JTextField();
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cadastro frame = new Cadastro();
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
	public Cadastro() {
		setTitle("Cadastro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCdigo = new JLabel("C\u00F3digo:");
		lblCdigo.setBounds(36, 47, 46, 14);
		contentPane.add(lblCdigo);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(36, 97, 46, 14);
		contentPane.add(lblNome);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(36, 162, 46, 14);
		contentPane.add(lblTelefone);
		
//		txtCodigo = new JTextField();
		txtCodigo.setBounds(136, 44, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);
		
//		txtNome = new JTextField();
		txtNome.setBounds(136, 94, 86, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
//		txtTelefone = new JTextField();
		txtTelefone.setBounds(136, 159, 86, 20);
		contentPane.add(txtTelefone);
		txtTelefone.setColumns(10);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openDB();
				cadastrar();
				closeDB();
				
				
				
			}
		});
		btnCadastrar.setBounds(53, 216, 91, 23);
		contentPane.add(btnCadastrar);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
		btnSair.setBounds(194, 216, 91, 23);
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

	public void cadastrar() {
		String query;

		try {
			query = "INSERT INTO alunos(codigo, nome, telefone) VALUES (" 
		+ txtCodigo.getText() +", '" + txtNome.getText() + "','"+ txtTelefone.getText() + "')";
			
			//System.out.println(query); - Testar a String
			 stmt.executeUpdate (query);
//			while (rs.next()) {
//				txtNome.setText(rs.getString("nome"));
//				txtTelefone.setText(rs.getString("telefone"));
			
			JOptionPane.showMessageDialog(null, "Cadastrado com sucesso! Ufa  " +"\nCodigo: " + txtCodigo.getText() + "\nNome: " + txtNome.getText() + "\nTelefone: " + txtTelefone.getText());

			limpar();
		} catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException i){
			JOptionPane.showMessageDialog(null, "Esse registro já existe.");
			limpar();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("problemas...");
			// setNome(null);
			// setTelefone(null);
		}
	}
	public void limpar(){
		txtCodigo.setText("");
		txtNome.setText("");
		txtTelefone.setText("");
		txtCodigo.requestFocus();
	}
}
