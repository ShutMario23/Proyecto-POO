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
    private JLabel atendido_txt, nom_cliente_txt, dir_txt, tel_txt, corr_txt, fecha_txt;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton salir, factura;
    private Conexion db;
  	private Statement st;
    private ResultSet rs, rs2;
    private Integer id;
    private String idCliente, idEmpleado, idProducto;
    private String idrec, idcl, idemp, idprod, idcot;

    public Recibo (String title, String idCliente, String idEmpleado) {
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

        idcot = id.toString();
        idcl = idCliente;
        idemp = idEmpleado; 

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
        id_rec.setText("Id Recibo:       "+ id.toString()+ "");
        id_rec.setForeground(black);
        add(id_rec);

        fechaLabel = new JLabel("Fecha: ");
    	fechaLabel.setBounds(410, 45, 152, 15);
    	fechaLabel.setFont(new Font("Microsoft New Tai Lue", 0, 11));
		fechaLabel.setForeground(black);
        add(fechaLabel);

        fecha_txt = new JLabel(dia + "/" + mes + "/" + anio);
        fecha_txt.setBounds(455, 45, 152, 15);
    	fecha_txt.setFont(new Font("Microsoft New Tai Lue", 0, 11));
		fecha_txt.setForeground(black);
        add(fecha_txt);

        atendido = new JLabel("Atendido Por: ");
        atendido.setBounds(30, 115, 100, 15);
        atendido.setFont(new Font("Microsoft New Tai Lue", 1 , 11));
        atendido.setBackground(black);
        add(atendido);

        atendido_txt = new JLabel("Atendido Por: ");
        atendido_txt.setBounds(110, 115, 350, 15);
        atendido_txt.setFont(new Font("Microsoft New Tai Lue", 0 , 11));
        atendido_txt.setBackground(black);
        add(atendido_txt);

        nom_cliente = new JLabel("Receptor: ");
        nom_cliente.setBounds(30, 133, 70, 15);
        nom_cliente.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        nom_cliente.setForeground(black);
        add(nom_cliente);

        nom_cliente_txt = new JLabel();
        nom_cliente_txt.setBounds(90, 133, 180, 15);
        nom_cliente_txt.setFont( new Font("Microsoft New Tai Lue", 0, 11));
        nom_cliente_txt.setForeground(black);
        add(nom_cliente_txt);

        tel = new JLabel("Tel\u00E9fono: ");
        tel.setBounds(350, 133, 60, 15);
        tel.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        tel.setForeground(black);
        add(tel);

        tel_txt = new JLabel();
        tel_txt.setBounds(410, 133, 500, 15);
        tel_txt.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        tel_txt.setForeground(black);
        add(tel_txt);

        dir = new JLabel("Direcci\u00F3n: ");
        dir.setBounds(30, 151, 60, 15);
        dir.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        dir.setForeground(black);
        add(dir);

        dir_txt = new JLabel();
        dir_txt.setBounds(90, 151, 390, 15);
        dir_txt.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        dir_txt.setForeground(black);
        add(dir_txt);

        corr = new JLabel("Correo electr\u00F3nico: ");
        corr.setBounds(30, 169, 110, 15);
        corr.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        corr.setForeground(black);
        add(corr);

        corr_txt = new JLabel("Correo electr\u00F3nico: ");
        corr_txt.setBounds(140, 169, 390, 15);
        corr_txt.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        corr_txt.setForeground(black);
        add(corr_txt);

        head_tabla = new JLabel("Id     Nombre                                         Tipo             P. Unitario  Dise\u00F1o  Largo  Ancho  Cant    P. total");
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
        tabla.getColumnModel().getColumn(1).setMinWidth(160); //Nombre
        tabla.getColumnModel().getColumn(1).setMaxWidth(160);
        tabla.getColumnModel().getColumn(2).setMinWidth(60); //Tipo
        tabla.getColumnModel().getColumn(2).setMaxWidth(60);
        tabla.getColumnModel().getColumn(3).setMinWidth(55); //Precio unitario
        tabla.getColumnModel().getColumn(3).setMaxWidth(55);
        tabla.getColumnModel().getColumn(4).setMinWidth(40); //Diseño
        tabla.getColumnModel().getColumn(4).setMaxWidth(40);
        tabla.getColumnModel().getColumn(5).setMinWidth(30); //Largo
        tabla.getColumnModel().getColumn(5).setMaxWidth(30);
        tabla.getColumnModel().getColumn(6).setMinWidth(35); //Ancho
        tabla.getColumnModel().getColumn(6).setMaxWidth(35);
        tabla.getColumnModel().getColumn(7).setMinWidth(35); //Cantidad
        tabla.getColumnModel().getColumn(7).setMaxWidth(35);
        tabla.getColumnModel().getColumn(8).setMinWidth(55); //Precio total
        tabla.getColumnModel().getColumn(8).setMaxWidth(55);
        tabla.setRowHeight(15);
        tabla.getTableHeader().setFont(new Font("Microsoft New Tai Lue", 1, 10));
        tabla.getTableHeader().setForeground(white);
        tabla.getTableHeader().setBackground(blue);
        tabla.setBackground(white);
        tabla.setForeground(black);
        tabla.setFont(new Font("Microsoft New Tai Lue", 0, 10));

        //Conexión con Carrito
        String idProducto ="";
        String nomProducto = "";
        String tipoProducto = "";
        String diseProducto = "";
        String larProducto = "";
        String anchProducto = "";
        String cantProducto = "";
        Double preuProducto, pretProducto;

        try {
            rs = st.executeQuery("SELECT * FROM Carrito WHERE id_cot = '" + id +"'");
            while(rs.next()){

                idProducto = rs.getString("id_prod");
                diseProducto = rs.getString("dis_prod");
                larProducto = rs.getString("largo_prod");
                anchProducto = rs.getString("ancho_prod");
                cantProducto = rs.getString("cant_prod");
                pretProducto = rs.getDouble("subt_prod");

                rs2 = st.executeQuery("SELECT * FROM Producto WHERE id_prod = '" + idProducto + "'");
                rs2.next();

                idprod = idProducto;

                nomProducto = rs2.getString("nom_prod");
                tipoProducto = rs2.getString("tipo_prod");
                preuProducto = rs2.getDouble("prec_prod");

                Redondear(pretProducto, 2); 
                Redondear(preuProducto, 2);

                modelo.addRow(new String[]{idProducto, nomProducto, tipoProducto, preuProducto.toString(), diseProducto, larProducto, anchProducto, cantProducto, pretProducto.toString()});
                
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err);
        }

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
        sbt.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        sbt.setForeground(black);
        add(sbt);

        sbt_txt = new JLabel("0", SwingConstants.RIGHT);
        sbt_txt.setBounds(270, posy + 25, 80, 15);
        sbt_txt.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        sbt_txt.setForeground(black);
        add(sbt_txt);

        iva = new JLabel("IVA 16 \u0025: ");
        iva.setBounds(230, posy + 44, 60, 15);
        iva.setFont(new Font("Microsoft New Tai Lue", 0, 11));
        iva.setForeground(black);
        add(iva);

        iva_txt = new JLabel("0", SwingConstants.RIGHT);
        iva_txt.setBounds(270, posy + 44, 80, 15);
        iva_txt.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        iva_txt.setForeground(black);
        add(iva_txt);

        total = new JLabel("Total: ");
        total.setBounds(230, posy + 62, 60, 15);
        total.setFont(new Font("Microsoft New Tai Lue", 0 ,11));
        total.setForeground(black);
        add(total);

        total_txt = new JLabel("0", SwingConstants.RIGHT);
        total_txt.setBounds(270, posy + 62, 80, 15);
        total_txt.setFont(new Font("Microsoft New Tai Lue", 1, 11));
        total_txt.setForeground(black);
        add(total_txt);

        anti = new JLabel("Anticipo: ");
		anti.setBounds(390, posy + 25, 60, 15);
		anti.setFont(new Font("Microsoft New Tai Lue", 0, 11));
		anti.setForeground(black);
		add(anti);

        anti_txt = new JLabel("0", SwingConstants.RIGHT);
		anti_txt.setBounds(440, posy + 25, 80, 15);
		anti_txt.setFont(new Font("Microsoft New Tai Lue", 1, 11));
		anti_txt.setForeground(black);
		add(anti_txt);

		pend = new JLabel("Pendiente: ");
		pend.setBounds(390, posy + 44, 60, 15);
		pend.setFont(new Font("Microsoft New Tai Lue", 0, 11));
		pend.setForeground(black);
		add(pend);

		pend_txt = new JLabel("0", SwingConstants.RIGHT);
		pend_txt.setBounds(440, posy + 44, 80, 15);
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
        salir.setBounds(127, 650, 90, 25);
        salir.setFont(new Font("Microsoft New Tai Lue", 1, 14));
        salir.setBackground(blue);
        salir.setForeground(white);
        salir.addActionListener(this);
        salir.addFocusListener(this);
        salir.addMouseListener(this);
        add(salir);

        factura = new JButton("Factura");
        factura.setBounds(344, 650, 90, 25);
        factura.setFont(new Font("Microsoft New Tai Lue", 1, 14));
        factura.setBackground(blue);
        factura.setForeground(white);
        factura.addActionListener(this);
        factura.addFocusListener(this);
        factura.addMouseListener(this);
        add(factura);

        String nomEmpleado = "";
        String nomCliente = "";
        String telCliente = "";
		String dirCliente = "";
        String corrCliente = "";
        Double subRec, ivaRec, totRec, antiRec, pendRec;

        try {
            //Llamanod los datos del Empelado
            rs = st.executeQuery("SELECT nom_emp FROM Empleado WHERE id_emp = '" + idEmpleado + "'");
            rs.next();

            nomEmpleado = rs.getString("nom_emp");
            atendido_txt.setText(nomEmpleado);

            //Lamando los datos de Cotizacion
            rs = st.executeQuery("SELECT * FROM Cotizacion WHERE id_cot = '" + id + "'");
            rs.next();

            subRec = rs.getDouble("sub_cot");
            ivaRec = rs.getDouble("iva_cot");
            totRec = rs.getDouble("tot_cot");
            antiRec = rs.getDouble("ant_cot");
            pendRec = rs.getDouble("pend_cot");

            Redondear(subRec, 2); sbt_txt.setText(subRec.toString());
            Redondear(ivaRec, 2); iva_txt.setText(ivaRec.toString());
            Redondear(totRec, 2); total_txt.setText(totRec.toString());
            Redondear(antiRec, 2); anti_txt.setText(antiRec.toString());
            Redondear(pendRec, 2); pend_txt.setText(pendRec.toString());

            //Obtenemos los datos del cliente
            rs = st.executeQuery("SELECT * FROM Cliente WHERE id_cl = '" + idCliente + "'");
            rs.next();

            nomCliente = rs.getString("nom_cl");
            telCliente = rs.getString("tel_cl");
            dirCliente = rs.getString("dir_cl");
            corrCliente = rs.getString("corr_cl");

            nom_cliente_txt.setText(nomCliente);
            tel_txt.setText(telCliente);
            dir_txt.setText(dirCliente);
            corr_txt.setText(corrCliente);

        } catch(SQLException err) {
            JOptionPane.showMessageDialog(null, err.toString());
        }
    }

    public static double Redondear(double numero,int digitos) {
        int cifras=(int) Math.pow(10,digitos);
        return Math.rint(numero*cifras)/cifras;
    }

    //Botones
    @Override
    public void actionPerformed(ActionEvent evt){
        System.out.println("Se rompe1");
        if (evt.getSource() == this.salir){
            try {
                System.out.println("Se rompe2");
                System.out.println(id);
                System.out.println(idprod);
                System.out.println(idcl);
                System.out.println(idemp);
                System.out.println(idcot);
                String camposRecibo = "'" + id + "', '" + idprod + "', '" + idcl +  "', '" + idemp + "', '" + idcot + "'";
				st.executeUpdate("INSERT INTO Recibo (id_rec, id_prod, id_cl, id_emp, id_cot)" +
                " VALUES (" + camposRecibo + ")");
                db.desconectar();
                System.out.println("Se rompe");
                Menu menu = new Menu("Men\u00FA");
                menu.setVisible(true);
                this.setVisible(false);
            } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error" + e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (evt.getSource() == this.factura){
            try {
                System.out.println("Se rompe2");
                System.out.println(id);
                System.out.println(idprod);
                System.out.println(idcl);
                System.out.println(idemp);
                System.out.println(idcot);
                String camposRecibo = "'" + id + "', '" + idprod + "', '" + idcl +  "', '" + idemp + "', '" + idcot + "'";
				st.executeUpdate("INSERT INTO Recibo (id_rec, id_prod, id_cl, id_emp, id_cot)" +
                " VALUES (" + camposRecibo + ")");
                db.desconectar();
                System.out.println("Se rompe");
                System.out.println(id.toString() + idcl + idemp);
                Factura fac = new Factura("Factura", id.toString(), idcl.toString(), idemp.toString());
                fac.setVisible(true);
                this.setVisible(false);
            } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error" + e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

	//Focus
	@Override
    public void focusGained(FocusEvent evt) {
        if(evt.getSource() == this.salir){
            this.salir.setBackground(bluefocus);
            this.salir.setForeground(black);
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
        } else if (evt.getSource() == this. factura){
            this.factura.setBackground(blue);
            this.factura.setForeground(white);
        }
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
        this.salir.setBackground(blue);
        this.salir.setForeground(white);
        this.factura.setBackground(blue);
        this.factura.setForeground(white);
        if(evt.getSource() == this.salir){
            this.salir.setBackground(bluefocus);
            this.salir.setForeground(black);
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
