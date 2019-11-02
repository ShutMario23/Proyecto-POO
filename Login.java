import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener, WindowListener {

	private JButton singIn;
	private JLabel user, empresa, rights, password, name;
	private JTextField userField;
	private JPasswordField passwordField;
	//private Color rojochido = new Color(200, 50, 55);
	private Color blue = new Color(0, 153, 153);
	private Color white = new Color(255, 255, 255);
	private Color black = new Color(0,0,0);
	private Color gray = new Color(224, 224, 224);
	private String patchLogo = "images/Logo.png";
	private String patchEmpresa = "images/San-Roman-Logo.png";
	private String patchAlert = "images/alert.png";
	private String patchOk = "images/ok.png";
	private ImageIcon alert = new ImageIcon(patchAlert);
	private ImageIcon ok = new ImageIcon(patchOk);
	private char cop = 169;
	private CustomFont cf = new CustomFont();
	private Conexion db;
	private Statement st;
	private ResultSet rs;
	private Boolean exist = false;

	public Login(String title) {
		this.setLayout(null);
		this.setBounds(0, 0, 630, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(white);
		this.setIconImage(new ImageIcon(getClass().getResource(patchLogo)).getImage());

		//Iniciamos la conexion a la db
		db = new Conexion();
		try {
			db.conectar();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error" + e, "Error", JOptionPane.ERROR_MESSAGE);
		}

		ImageIcon empresa_imagen = new ImageIcon(patchEmpresa);

		empresa = new JLabel(empresa_imagen);
		empresa.setBounds(90, 90, 450, 157);
		add(empresa);

		user = new JLabel("USUARIO");
		user.setBounds(120, 250, 100, 30);
		user.setFont(new Font("Microsoft New Tai Lue", 1, 14));
		user.setForeground(blue);
		add(user);

		password = new JLabel("CONTRASE\u00D1A");
		password.setBounds(120, 300, 100, 30);
		password.setFont(new Font("Microsoft New Tai Lue", 1, 14));
		password.setForeground(blue);
		add(password);

		userField = new JTextField();
		userField.setBounds(260, 250, 250, 30);
		userField.setBackground(gray);
		userField.setFont(new Font("Microsoft New Tai Lue", 0, 14));
		userField.setForeground(black);
		userField.addKeyListener(this);
		add(userField);

		passwordField = new JPasswordField();
		passwordField.setBounds(260, 300, 250, 30);
		passwordField.setBackground(gray);
		passwordField.setForeground(black);
		passwordField.addKeyListener(this);
		add(passwordField);

		rights = new JLabel("Cristaler\u00eda San Rom\u00e1n. " + cop + " Copyright 2019. Todos los derechos reservados.",SwingConstants.CENTER);
		rights.setBounds(0, 441, 630, 30);
		rights.setFont(new Font("Microsoft New Tai Lue", 2, 12));
		rights.setOpaque(true);
		rights.setBackground(black);
		rights.setForeground(white);
		add(rights);

		name = new JLabel("S I L I C A", SwingConstants.CENTER);
		name.setBounds(0, 0, 630, 80);
		name.setFont(cf.MyFont(1, 50f));
		name.setOpaque(true);
		name.setBackground(blue);
		name.setForeground(black);
		add(name);

		singIn = new JButton("Ingresar");
		singIn.setBounds(265, 380, 100, 30);
		singIn.setBackground(blue);
		singIn.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		singIn.setForeground(white);
		singIn.addActionListener(this);
		singIn.addKeyListener(this);
		add(singIn);
	}
	// Botones
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == singIn) {
			String user1 = new String(userField.getText().trim());
			String password1 = new String(passwordField.getPassword());
			if(user1.isEmpty() || password1.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Debe llenar todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					st = db.getConexion().createStatement();
					rs = st.executeQuery("SELECT nomm_usu, contra_usu FROM Usuario");
					while (rs.next()) {
						if (rs.getString("nom_usu").equals(user1) && rs.getString("contra_usu").equals(password1)){
							exist = true;
							break;
						}
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
				}
				if (exist) {
					try {
						db.desconectar();
						JOptionPane.showMessageDialog(null, "Bienvenido " + user1 + ".", "Bienvenida", 0, ok);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Ussuario o contrase\u00F1a incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	//Teclado


	@Override
	public void keyTyped(KeyEvent evt) { //No se ocupa aun
        
    }

	@Override
	public void keyReleased(KeyEvent evt) { //No se ocupa aun
        
    }

	@Override
	public void keyPressed(KeyEvent evt) {
		if(evt.getKeyCode() == 10) {
			if(evt.getSource() == singIn) {
				String user1 = new String(userField.getText().trim());
				String password1 = new String(passwordField.getPassword());
				if(user1.isEmpty() || password1.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Debe llenar todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						st = db.getConexion().createStatement();
						rs = st.executeQuery("SELECT nomm_usu, contra_usu FROM Usuario");
						while (rs.next()) {
							if (rs.getString("nom_usu").equals(user1) && rs.getString("contra_usu").equals(password1)){
								exist = true;
								break;
							}
						}
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
					}
					if (exist) {
						try {
							db.desconectar();
							JOptionPane.showMessageDialog(null, "Bienvenido " + user1 + ".", "Bienvenida", 0, ok);
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
						}
						
					} else {
						JOptionPane.showMessageDialog(null, "Ussuario o contrase\u00F1a incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}

	//Focus
	@Override
    public void focusGained(FocusEvent evt) {
		if(evt.getSource() == this.userField) {
			this.userField.setBackground(blue);
			this.userField.setForeground(white);
		} else if(evt.getSource() == this.passwordField) {
			this.passwordField.setBackground(blue);
			this.passwordField.setForeground(white);
		} else if(evt.getSource() == this.singIn) {
			this.singIn.setBackground(white);
			this.singIn.setForeground(blue);
		}
    }

	@Override
    public void focusLost(FocusEvent evt) {
		if(evt.getSource() == this.userField) {
			this.userField.setBackground(gray);
			this.userField.setForeground(blue);
		} else if(evt.getSource() == this.passwordField) {
			this.passwordField.setBackground(gray);
			this.passwordField.setForeground(blue);
		} else if(evt.getSource() == this.singIn) {
			this.singIn.setBackground(blue);
			this.singIn.setForeground(white);
		}
	}

	//mouse
	@Override
    public void mouseReleased(MouseEvent evt) {

    }

    @Override
    public void mousePressed(MouseEvent evt) {

    }

    @Override
    public void mouseExited(MouseEvent evt) {
        
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
        
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        
	}
	
	//ventana
	@Override
	public void windowClosing(WindowEvent evt) {
		try {
			db.desconectar();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent evt) {

	}

	@Override
	public void windowActivated(WindowEvent evt) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent evt) {
		
	}

	@Override
	public void windowIconified(WindowEvent evt) {
		
	}

	@Override
	public void windowClosed(WindowEvent evt) {
		
	}

	@Override
	public void windowOpened(WindowEvent evt) {
		
	}

	public static void main(String args[]) {
		Login login = new Login("Ingresar");
		login.setVisible(true);
	}
}