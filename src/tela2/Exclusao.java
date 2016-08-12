package tela2;

import java.awt.BorderLayout;
import java.awt.Component;
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

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import java.awt.Font;

public class Exclusao extends JFrame {

	private JPanel contentPane;

	private JTextField txtCodigo = new JTextField("");
	private JLabel lblNome1 = new JLabel("");
	private final static String url = "jdbc:mysql://localhost:3306/impacta";

	private final static String username = "root";
	private final static String password = "Imp@ct@";

	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Exclusao frame = new Exclusao();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Exclusao() {
		setResizable(false);
		setTitle("Exclus\u00E3o");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 377, 263);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCdigo = new JLabel("C\u00F3digo:");
		lblCdigo.setFont(new Font("Courier New", Font.PLAIN, 12));
		lblCdigo.setBounds(51, 48, 67, 14);
		contentPane.add(lblCdigo);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Courier New", Font.PLAIN, 12));
		lblNome.setBounds(51, 112, 46, 14);
		contentPane.add(lblNome);

		txtCodigo = new JTextField();
		txtCodigo.setFont(new Font("Cordia New", Font.PLAIN, 12));
		txtCodigo.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				
				if (txtCodigo.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"O Campo deve ser preenchido");
					txtCodigo.requestFocus();
				}
				openDB();
				consultar();
				closeDB();
				
				
				
			}
		});
//		txtCodigo.addFocusListener(new FocusAdapter() {
//			@Override
//			public void focusLost(FocusEvent e) {
//				if (txtCodigo.getText().isEmpty()) {
//					JOptionPane.showMessageDialog(null,
//							"O Campo deve ser preenchido");
//					txtCodigo.requestFocus();
//				}
//				openDB();
//				consultar();
//				closeDB();
//			}
//		});
		txtCodigo.setBounds(192, 45, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);
		lblNome1.setFont(new Font("Courier New", Font.PLAIN, 12));

		// JLabel lblNome1 = new JLabel("");
		lblNome1.setBounds(151, 112, 183, 14);
		contentPane.add(lblNome1);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setFont(new Font("Courier New", Font.PLAIN, 12));
		btnExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int dialogresult = JOptionPane.showConfirmDialog(null,
						"Gostaria de apagar o registro: " + txtCodigo.getText()
								+ " ?");

				System.out.println(dialogresult);
				if (dialogresult == 0) {
					openDB();
					excluirDB();
					closeDB();
					lblNome1.setText("");
					

				} else {
					limpar();
				}

			}
		});
		btnExcluir.setBounds(65, 171, 91, 23);
		contentPane.add(btnExcluir);

		JButton btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Courier New", Font.PLAIN, 12));
		btnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);

			}
		});
		btnSair.setBounds(225, 171, 91, 23);
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

	public void consultar() {
		String query;

		try {
			query = "SELECT * FROM alunos  where codigo = '"
					+ txtCodigo.getText() + "'";

			System.out.println("\nMostrando dados.\n");

			System.out.println(txtCodigo.getText());

			rs = stmt.executeQuery(query);
			System.out.println(txtCodigo.getText());
			
			if (!rs.next() ) {
				lblNome1.setText("O registro não foi encontrado");
				
			}else{
				lblNome1.setText(rs.getString("nome"));
			}
			
//			if (!rs.next() ) {
//				lblNome1.setText("O registro não foi encontrado");
//				
//			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("problemas...");
			System.exit(1);
			// setNome(null);
			// setTelefone(null);
		}
	}

	public void limpar() {
		txtCodigo.setText("");
		lblNome1.setText(null);
		txtCodigo.requestFocus();
	}

	public void excluirDB() {
		String query;

		try {
			query = "DELETE FROM alunos  WHERE codigo = '"
					+ txtCodigo.getText() + "'";

			System.out.println("\nMostrando dados.\n");

			System.out.println(txtCodigo.getText());

			stmt.executeUpdate(query);
			JOptionPane
					.showMessageDialog(null, "Registro excluido com sucesso");
			// while (rs.next()) {
			// lblNome1.setText(rs.getString("nome"));
			// }
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("problemas...");
			// setNome(null);
			// setTelefone(null);
		}

	}

}
