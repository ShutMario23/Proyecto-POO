import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener, KeyListener {

	private JButton singIn;
	private JLabel user, empresa, rights, password, name;
	private JTextField userField;
	private JPasswordField passwordField;
	//private Color rojochido = new Color(200, 50, 55);
	private Color blue = new Color(0, 153, 153);
	private Color white = new Color(255, 255, 255);
	private Color black = new Color(0,0,0);
	private Color gray = new Color(224, 224, 224);
	//private Color blueline = new Color(32,205,200);
	private String patchLogo = "images/Logo.png";
	private String patchEmpresa = "images/San-Roman-Logo.png";
	private String patchAlert = "images/alert.png";
	private String patchOk = "images/ok.png";
	private ImageIcon alert = new ImageIcon(patchAlert);
	private ImageIcon ok = new ImageIcon(patchOk);
	private char cop = 169;
	private CustomFont cf = new CustomFont();

	public Login(String title) {
		this.setLayout(null);
		this.setBounds(0, 0, 630, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(white);
		this.setIconImage(new ImageIcon(getClass().getResource(patchLogo)).getImage());

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

	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == singIn) {
			String user1 = new String(userField.getText().trim());
			String password1 = new String(passwordField.getPassword());
			if(user1.isEmpty() || password1.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Debe llenar todos los campos.");
			} else if(user1.equals("Hola") && password1.equals("Hola")) {
				JOptionPane.showMessageDialog(null, "           Bienvenido " + user1 + ".", "Entrar", 0, ok);
				Menu m = new Menu("Men\u00FA");
				m.setVisible(true);
				this.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(null, "Usuario o contrase\u00f1a incorrectos.", "Error", 0, alert);
			}
		}
	}

	//Se declaran por que luego me dice que me falta definir estos metodos abstractos del awt xd
	public void keyTyped(KeyEvent evt) { //No se ocupa aun
        
    }

	public void keyReleased(KeyEvent evt) { //No se ocupa aun
        
    }

	public void keyPressed(KeyEvent evt) {
		if(evt.getKeyCode() == 10) {
			String user1 = new String(userField.getText().trim());
			String password1 = new String(passwordField.getPassword());
			if(user1.isEmpty() || password1.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Debe llenar todos los campos.");
			} else if(user1.equals("Hola") && password1.equals("Hola")) {
				JOptionPane.showMessageDialog(null, "           Bienvenido " + user1 + ".", "Entrar", 0 , ok);
				Menu m = new Menu("Men\u00FA");
				m.setVisible(true);
				this.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(null, "Usuario o contrase\u00f1a incorrectos.", "Error", 0, alert);
			}
		}
	}

	public static void main(String args[]) {
		Login login = new Login("Ingresar");
		login.setVisible(true);
	}
}