import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame implements ActionListener, KeyListener {

	private Color white = new Color(255, 255, 255);
    private Color color = new Color(50, 203, 185);
    private String patchLogo = "images/Logo.png";
	// private JButton prod, prov, em, cot;
	private JButton prod;
    private String patchBarcode = "images/barcode-logo.png";
    //private String patchuser = "images/.user-logo.png";
    //private String patchprovider = "images/provider-logo.png";
    //private String patchshop = "iamges/shop-logo.png";
    private ImageIcon prod_img = new ImageIcon(patchBarcode);
    //private ImageIcon prov_img = new ImageIcon(patchprovider);
    //private ImageIcon em_img = new ImageIcon(patchuser);
    //private ImageIcon cot_img = new ImageIcon(patchshop);
    //private JMenuBar mb;
    //private JMenu opciones;
	//private JMenuItem cerrar;

    public Menu (String title) {
        this.setLayout(null);
        this.setBounds(0, 0, 700, 700);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane();
        this.setBackground(white);
        this.setIconImage(new ImageIcon(getClass().getResource(patchLogo)).getImage());

        //mb = new JMenuBar();
        //mb.setBackground(color);
        //this.setJMenuBar(mb);

       /* opciones = new JMenu("Opciones");
        opciones.setFont(new Font("Tahoma", 1, 14));
        opciones.setForeground(blanco);
        opciones.setBackground(color);
        mb.add(opciones);

        cerrar = new JMenuItem("Cerrar sesi\u00F3n");
        cerrar.setFont(new Font("Tahoma", 1, 14));
        cerrar.setForeground(color);
        opciones.add(cerrar);
        cerrar.addActionListener(this);
*/
		prod = new JButton();
		prod.setBounds(100, 100, 100, 80);
		prod.setBackground(white);
		prod.setFont(new Font("Tahoma", 1, 14));
		prod.setForeground(color);
		prod.setIcon(prod_img);
		prod.setHorizontalTextPosition( SwingConstants.CENTER );
        prod.setVerticalTextPosition( SwingConstants.BOTTOM );
        prod.setBorder(null);
		prod.addActionListener(this);
		prod.addKeyListener(this);
		add(prod);
    }

    public void keyTyped(KeyEvent evt) { //No se ocupa aun
        
    }

	public void keyReleased(KeyEvent evt) { //No se ocupa aun
        
	}

	public void actionPerformed(ActionEvent evt) {
		//if(evt.getSource() == cerrar) {
		//	Login login = new Login("Ingresar");
		//	login.setVisible(true);
		//	this.setVisible(false);
		
        //} else 
        if(evt.getSource() == prod) {

		} 
	}

	public void keyPressed(KeyEvent evt) {
		
	}


}