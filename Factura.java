import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Calendar;
import javax.swing.table.*;

public class Factura extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener, WindowListener {

    private Color blue = new Color(0, 153, 153);
    private Color blue2 = new Color(2, 199, 199);
    private Color blue3= new Color(0, 220, 220);
    private Color blue4 = new Color(0, 243, 243);
    private Color bluefocus = new Color(167, 255, 255);
	private Color white = new Color(255, 255, 255);
	private Color black = new Color(0, 0, 0);
    private Color gray = new Color(224, 224, 224);
    
    public Factura(String title) {
        this.setLayout(null);
        this.setResizable(false);
        this.setBounds(0, 0, 561, 726); //X27
        this.setLocationRelativeTo(null);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(white);
        this.setIconImage(new ImageIcon(getClass().getResource("images/Logo.png")).getImage());
        this.addWindowListener(this);

    }

    //Botones
    @Override
    public void actionPerformed(ActionEvent evt){

    }

    //Teclado
    @Override
    public void keyTyped(KeyEvent evt){

    }

    @Override
	public void keyReleased(KeyEvent evt) {
        
    }

	@Override
	public void keyPressed(KeyEvent evt) {

	}

	//Focus
	@Override
    public void focusGained(FocusEvent evt) {

    }

    @Override
    public void focusLost(FocusEvent evt) {

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


}