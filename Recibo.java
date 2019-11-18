import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Calendar;
import javax.swing.table.*;

public class Recibo extends JFrame implements ActionListener, FocusListener, MouseListener, WindowListener {

    private Color blue = new Color(0, 153, 153);
    private Color blue2 = new Color(2, 199, 199);
    private Color blue3= new Color(0, 220, 220);
    private Color blue4 = new Color(0, 243, 243);
    private Color bluefocus = new Color(167, 255, 255);
	private Color white = new Color(255, 255, 255);
    private Color black = new Color(0, 0, 0);
    private Color gray = new Color(224, 224, 224);
    private Calendar fecha;
    private String dia, mes, anio;
    private JLabel logo, dir_em, tel_em, id_rec, fechaLabel, atendido, nom_cliente, dir, tel, corr, head_tabla;
    private JLabel sbt, iva, total, sbt_txt, iva_txt, total_txt, anti, anti_txt, pend, pend_txt, firma, linea_f, firma_c, linea_c;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton salir, guardar, factura;
    private Conexion db;
  	private Statement st;
    private ResultSet rs;
    private Integer id;

    public Recibo (String title) {
        this.setLayout(null);
        this.setResizable(false);
        this.setBounds(0, 0, 561, 726); //X27
        this.setLocationRelativeTo(null);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(white);
        this.setIconImage(new ImageIcon(getClass().getResource("images/Logo.png")).getImage());
        this.addWindowListener(this);

        //Iniciamos la conexion a la db
    	db = new Conexion();
    	try {
    		db.conectar();
            st = db.getConexion().createStatement();
            rs = st.executeQuery("SELECT MAX(id_cot) FROM Cotizacion");
            rs.next();
            id = rs.getInt(1); //Maxima id de Recibo
    	} catch (SQLException e) {
    		JOptionPane.showMessageDialog(null, "Error" + e, "Error", JOptionPane.ERROR_MESSAGE);
    	}

        //obtenemos fecha actual
    	fecha = Calendar.getInstance();
    	dia = Integer.valueOf(fecha.get(Calendar.DATE)).toString();
		mes = Integer.valueOf(fecha.get(Calendar.MONTH) + 1).toString();
    	anio = Integer.valueOf(fecha.get(Calendar.YEAR)).toString();

        ImageIcon logo_image = new ImageIcon("./images/logo-fac.png");
        logo = new JLabel(logo_image);
        logo.setBounds(30, 20, 150, 89);
        this.add(logo);

        dir_em = new JLabel("<html><body>San Feipe No. 2599 Col.San Jorge Monterrey, N.L.</body></html>");
        dir_em.setBounds(195, 40, 180, 32);
        dir_em.setFont(new Font("Microsoft New Tai Lue", 1, 11 ));
        dir_em.setForeground(blue);
        add(dir_em);

        tel_em = new JLabel("<html><body><b>Tels: </b>818 708-4664, 83-11-2331</body></html>");
        tel_em.setBounds(195, 71, 180, 30);
        tel_em.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        tel_em.setForeground(black);
        add(tel_em);

        id_rec = new JLabel("Id Recibo: 0");
        id_rec.setBounds(410, 30, 100, 15);
        id_rec.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        id_rec.setText("Id Recibo: "+ id.toString()+ "");
        id_rec.setForeground(black);
        add(id_rec);

        fechaLabel = new JLabel("Fecha:     " + dia + "/" + mes + "/" + anio);
    	fechaLabel.setBounds(410, 45, 152, 15);
    	fechaLabel.setFont(new Font("Microsoft New Tai Lue", 0, 11));
		fechaLabel.setForeground(black);
        add(fechaLabel);

        atendido = new JLabel("<html><b>Atendido Por: </b></html>");
        atendido.setBounds(30, 115, 350, 15);
        atendido.setFont(new Font("Microsoft New Tai Lue", 0 , 11));
        atendido.setBackground(black);
        add(atendido);

        nom_cliente = new JLabel("<html><b>Receptor: </b></html>");
        nom_cliente.setBounds(30, 133, 300, 15);
        nom_cliente.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        nom_cliente.setForeground(black);
        add(nom_cliente);

        tel = new JLabel("<html><b>Tel\u00E9fono: </b></html>");
        tel.setBounds(350, 133, 100, 15);
        tel.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        tel.setForeground(black);
        add(tel);

        dir = new JLabel("<html><b>Direcci\u00F3n: </b></html>");
        dir.setBounds(30, 151, 500, 15);
        dir.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        dir.setForeground(black);
        add(dir);

        corr = new JLabel("<html><b>Correo electr\u00F3nico: </b></html>");
        corr.setBounds(30, 169, 300, 15);
        corr.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        corr.setForeground(black);
        add(corr);

        head_tabla = new JLabel("Id     Nombre                     Tipo                P. Unitario   Dise\u00F1o      Largo     Ancho    Cantidad   Precio total");
        head_tabla.setBounds(31, 191, 500, 15);
        head_tabla.setFont(new Font("Microsoft New Tai Lue", 1 ,10));
        head_tabla.setForeground(black);
        add(head_tabla);

        String[] campos = new String[]{"Id", "Nombre", "Tipo", "P. Unitario", "Dise\u00F1o", "Largo", "Ancho",
            "Cantidad", "Precio total"};

        modelo = new DefaultTableModel(null, campos);
        tabla = new JTable(modelo){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setResizingAllowed(false);
        tabla.getColumnModel().getColumn(0).setMinWidth(25); //Id
        tabla.getColumnModel().getColumn(0).setMaxWidth(25);
        tabla.getColumnModel().getColumn(1).setMinWidth(100); //Nombre
        tabla.getColumnModel().getColumn(1).setMaxWidth(100);
        tabla.getColumnModel().getColumn(2).setMinWidth(70); //Tipo
        tabla.getColumnModel().getColumn(2).setMaxWidth(70);
        tabla.getColumnModel().getColumn(3).setMinWidth(60); //Precio unitario
        tabla.getColumnModel().getColumn(3).setMaxWidth(60);
        tabla.getColumnModel().getColumn(4).setMinWidth(50); //Diseño
        tabla.getColumnModel().getColumn(4).setMaxWidth(50);
        tabla.getColumnModel().getColumn(5).setMinWidth(40); //Largo
        tabla.getColumnModel().getColumn(5).setMaxWidth(40);
        tabla.getColumnModel().getColumn(6).setMinWidth(40); //Ancho
        tabla.getColumnModel().getColumn(6).setMaxWidth(40);
        tabla.getColumnModel().getColumn(7).setMinWidth(50); //Cantidad
        tabla.getColumnModel().getColumn(7).setMaxWidth(50);
        tabla.getColumnModel().getColumn(8).setMinWidth(60); //Precio total
        tabla.getColumnModel().getColumn(8).setMaxWidth(60);
        tabla.setRowHeight(15);
        tabla.getTableHeader().setFont(new Font("Microsoft New Tai Lue", 1, 10));
        tabla.getTableHeader().setForeground(white);
        tabla.getTableHeader().setBackground(blue);
        tabla.setBackground(white);
        tabla.setForeground(black);
        tabla.setFont(new Font("Microsoft New Tai Lue", 0, 10));

        //Haz la connexion, y llena la tabla aqui
        modelo.addRow(new String[]{"100", "Cristal", "Barandal", "1200", "UMT", "800", "600", "5", "1200"});
        modelo.addRow(new String[]{"2", "Cristal", "Barandal", "800", "UML", "50", "900", "10", "20000"});
        modelo.addRow(new String[]{"100", "Cristal", "Barandal", "1200", "UMT", "800", "600", "5", "1200"});
        modelo.addRow(new String[]{"2", "Cristal", "Barandal", "800", "UML", "50", "900", "10", "20000"});
        modelo.addRow(new String[]{"100", "Cristal", "Barandal", "1200", "UMT", "800", "600", "5", "1200"});
        modelo.addRow(new String[]{"2", "Cristal", "Barandal", "800", "UML", "50", "900", "10", "20000"});
        modelo.addRow(new String[]{"100", "Cristal", "Barandal", "1200", "UMT", "800", "600", "5", "1200"});
        modelo.addRow(new String[]{"2", "Cristal", "Barandal", "800", "UML", "50", "900", "10", "20000"});
        modelo.addRow(new String[]{"100", "Cristal", "Barandal", "1200", "UMT", "800", "600", "5", "1200"});
        modelo.addRow(new String[]{"2", "Cristal", "Barandal", "800", "UML", "50", "900", "10", "20000"});
        modelo.addRow(new String[]{"100", "Cristal", "Barandal", "1200", "UMT", "800", "600", "5", "1200"});
        modelo.addRow(new String[]{"2", "Cristal", "Barandal", "800", "UML", "50", "900", "10", "20000"});
        modelo.addRow(new String[]{"100", "Cristal", "Barandal", "1200", "UMT", "800", "600", "5", "1200"});
        modelo.addRow(new String[]{"2", "Cristal", "Barandal", "800", "UML", "50", "900", "10", "20000"});
        modelo.addRow(new String[]{"100", "Cristal", "Barandal", "1200", "UMT", "800", "600", "5", "1200"});
        modelo.addRow(new String[]{"2", "Cristal", "Barandal", "800", "UML", "50", "900", "10", "20000"});
        modelo.addRow(new String[]{"100", "Cristal", "Barandal", "1200", "UMT", "800", "600", "5", "1200"});
        modelo.addRow(new String[]{"2", "Cristal", "Barandal", "800", "UML", "50", "900", "10", "20000"});
        modelo.addRow(new String[]{"100", "Cristal", "Barandal", "1200", "UMT", "800", "600", "5", "1200"});
        modelo.addRow(new String[]{"2", "Cristal", "Barandal", "800", "UML", "50", "900", "10", "20000"});

        int rowcount;
        rowcount = tabla.getRowCount()*15;

        tabla.setBounds(30, 206, 495, rowcount);
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        add(tabla);

        //abr x2
        int posy;
        posy = rowcount + 206;

        sbt = new JLabel("Subtotal: ");
        sbt.setBounds(230, posy + 25, 60, 15);
        sbt.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        sbt.setForeground(black);
        add(sbt);

        sbt_txt = new JLabel("0", SwingConstants.RIGHT);
        sbt_txt.setBounds(290, posy + 25, 50, 15);
        sbt_txt.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        sbt_txt.setForeground(black);
        add(sbt_txt);

        iva = new JLabel("IVA 16 \u0025: ");
        iva.setBounds(230, posy + 44, 60, 15);
        iva.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        iva.setForeground(black);
        add(iva);

        iva_txt = new JLabel("0", SwingConstants.RIGHT);
        iva_txt.setBounds(290, posy + 44, 50, 15);
        iva_txt.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        iva_txt.setForeground(black);
        add(iva_txt);

        total = new JLabel("Total: ");
        total.setBounds(230, posy + 62, 60, 15);
        total.setFont(new Font("Microsoft New Tai Lue", 1,11));
        total.setForeground(black);
        add(total);

        total_txt = new JLabel("0", SwingConstants.RIGHT);
        total_txt.setBounds(290, posy + 62, 50, 15);
        total_txt.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        total_txt.setForeground(black);
        add(total_txt);

        anti = new JLabel("Anticipo: ");
		anti.setBounds(400, posy + 25, 60, 15);
		anti.setFont(new Font("Microsoft New Tai Lue", 1, 11));
		anti.setForeground(black);
		add(anti);

        anti_txt = new JLabel("0", SwingConstants.RIGHT);
		anti_txt.setBounds(470, posy + 25, 50, 15);
		anti_txt.setFont(new Font("Microsoft New Tai Lue", 1, 11));
		anti_txt.setForeground(black);
		add(anti_txt);

		pend = new JLabel("Pendiente: ");
		pend.setBounds(400, posy + 44, 60, 15);
		pend.setFont(new Font("Microsoft New Tai Lue", 1, 11));
		pend.setForeground(black);
		add(pend);

		pend_txt = new JLabel("0", SwingConstants.RIGHT);
		pend_txt.setBounds(470, posy + 44, 50, 15);
		pend_txt.setFont(new Font("Microsoft New Tai Lue", 1, 11));
		pend_txt.setForeground(black);
        add(pend_txt);

        firma_c = new JLabel("FIRMA DEL CLIENTE", SwingConstants.CENTER);
        firma_c.setBounds(87, 615, 150, 15);
        firma_c.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        firma_c.setForeground(black);
        add(firma_c);

        linea_c = new JLabel();
        linea_c.setBounds(87, 613, 150, 1);
        linea_c.setOpaque(true);
        linea_c.setBackground(black);
        add(linea_c);

        firma = new JLabel("AUTORIZADO", SwingConstants.CENTER);
        firma.setBounds(324, 615, 150, 15);
        firma.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        firma.setForeground(black);
        add(firma);

        linea_f = new JLabel();
        linea_f.setBounds(324, 613, 150, 1);
        linea_f.setOpaque(true);
        linea_f.setBackground(black);
        add(linea_f);

        salir = new JButton("Salir");
        salir.setBounds(72, 650, 90, 25);
        salir.setFont(new Font("Microsoft New Tai Lue", 1, 14));
        salir.setBackground(blue);
        salir.setForeground(white);
        salir.addActionListener(this);
        salir.addFocusListener(this);
        salir.addMouseListener(this);
        add(salir);

        guardar = new JButton("Guardar");
        guardar.setBounds(232, 650, 90, 25);
        guardar.setFont(new Font("Microsoft New Tai Lue", 1, 14));
        guardar.setBackground(blue);
        guardar.setForeground(white);
        guardar.addActionListener(this);
        guardar.addFocusListener(this);
        guardar.addMouseListener(this);
        add(guardar);

        factura = new JButton("Factura");
        factura.setBounds(394, 650, 90, 25);
        factura.setFont(new Font("Microsoft New Tai Lue", 1, 14));
        factura.setBackground(blue);
        factura.setForeground(white);
        factura.addActionListener(this);
        factura.addFocusListener(this);
        factura.addMouseListener(this);
        add(factura);

        String idRec = id_rec.getText();
        String idCl ="";
        String nomCliente = "";
        String telCliente = "";
		String dirCliente = "";
		String corrCliente = "";
        String idEmpleado = "";
        String subRec = "";
        String ivaRec = "";
        String totRec = "";
        String antiRec = "";
        String pendRec = "";

        //Llamando los datos de la DB
        try {
            //Lamando los datos de Cotizacion
            rs = st.executeQuery("SELECT * FROM Cotizacion WHERE id_cot = '" + idRec + "'");
            rs.next();

            idCl = rs.getString("id_cl");
            idEmpleado = rs.getString("id_emp");
            subRec = rs.getString("sub_cot");
            // ivaRec = rs.getString("iva_cot");
            // totRec = rs.getString("tot_cot");
            // antiRec = rs.getString("ant_cot");
            // pendRec = rs.getString("pend_cot");

            sbt_txt.setText(subRec.toString());
            // total_txt.setText(totRec.toString());
            // iva_txt.setText(ivaRec.toString());
            // anti_txt.setText(antiRec.toString());
            // pend_txt.setText(pendRec.toString());


            //Obtenemos los datos del cliente
            // rs = st.executeQuery("SELECT MAX(id_cl), id_emp, nom_cl, tel_cl, dir_cl, corr_cl FROM Cliente");
            
            // nom_cliente.setText(nomCliente);

        } catch(SQLException err) {
            JOptionPane.showMessageDialog(null, err.toString());
        }
    }

    //Botones
    @Override
    public void actionPerformed(ActionEvent evt){
        if (evt.getSource() == salir){
            Menu menu = new Menu("Men\u00FA");
            menu.setVisible(true);
            this.setVisible(false);
        } else if (evt.getSource() == factura){
            Factura f1 = new Factura("Factura");
            f1.setVisible(true);
            this.setVisible(false);
        }
    }

	//Focus
	@Override
    public void focusGained(FocusEvent evt) {
        if(evt.getSource() == this.salir){
            this.salir.setBackground(bluefocus);
            this.salir.setForeground(black);
        } else if (evt.getSource() == this.guardar){
            this.guardar.setBackground(bluefocus);
            this.guardar.setForeground(black);
        } else if (evt.getSource() == this.factura){
            this.factura.setBackground(bluefocus);
            this.factura.setForeground(black);
        }
    }

    @Override
    public void focusLost(FocusEvent evt) {
        if(evt.getSource() == this.salir){
            this.salir.setBackground(blue);
            this.salir.setForeground(white);
        } else if (evt.getSource() == this.guardar){
            this.guardar.setBackground(blue);
            this.guardar.setForeground(white);
        } else if (evt.getSource() == this. factura){
            this.factura.setBackground(blue);
            this.factura.setForeground(white);
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
        if(evt.getSource() == this.salir){
            this.salir.setBackground(blue);
            this.salir.setForeground(white);
        } else if (evt.getSource() == this.guardar){
            this.guardar.setBackground(blue);
            this.guardar.setForeground(white);
        } else if (evt.getSource() == this. factura){
            this.factura.setBackground(blue);
            this.factura.setForeground(white);
        }
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
        this.salir.setBackground(blue);
        this.salir.setForeground(white);
        this.guardar.setBackground(blue);
        this.guardar.setForeground(white);
        this.factura.setBackground(blue);
        this.factura.setForeground(white);
        if(evt.getSource() == this.salir){
            this.salir.setBackground(bluefocus);
            this.salir.setForeground(black);
        } else if (evt.getSource() == this.guardar){
            this.guardar.setBackground(bluefocus);
            this.guardar.setForeground(black);
        } else if (evt.getSource() == this.factura){
            this.factura.setBackground(bluefocus);
            this.factura.setForeground(black);
        }
    }

    @Override
    public void mouseClicked(MouseEvent evt) {

	}

    //WindowListener
    @Override
    public void windowClosing(WindowEvent evt) {
        try {
            //Actualizamos el estado de sesion de usuario en la db
            String usuario = "";
            rs = st.executeQuery("SELECT nom_usu, sesion_act FROM Usuario");
            while(rs.next()) {
                if(rs.getString("sesion_act").equals("s")) {
                usuario = rs.getString("nom_usu");
                st.executeUpdate("UPDATE Usuario SET sesion_act = 'n' WHERE nom_usu = '" + usuario + "'");
                }
            }
            db.desconectar();
            System.out.println("Se ha desconectado el usuario: " + usuario);
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
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


}
