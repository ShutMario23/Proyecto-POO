import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Calendar;
import javax.swing.table.*;

public class Cotizacion extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener, WindowListener {

	private Color blue = new Color(0, 153, 153);
    private Color blue2 = new Color(2,199,199);
    private Color blue3= new Color(0, 220, 220);
    private Color blue4 = new Color(0, 243, 243);
    private Color bluefocus = new Color(167, 255, 255);
	private Color white = new Color(255, 255, 255);
	private Color black = new Color(0, 0, 0);
	private Color gray = new Color(224, 224, 224);
	private JLabel tira, tira2, tira3, tira4, rights;
	private JButton cancelar, agregar, guardar, borrar, agregarcot, regresar;
	private JLabel no_cot, fechaLabel, sbt, total, iva, id_cliente, nom_cliente, tel, dir, corr, emp;
	private JLabel prod, pre_uni, dim, x, cant, sbtotal, mas, menos, tipo, id_prod;
	private JTextField no_cotField, pre_uni_txt, dim_largo, dim_ancho, cant_txt, sbtotal_txt, id_prod_txt;
	private JTextField id_cliente_txt, nom_cliente_txt, tel_txt, dir_txt, corr_txt;
	private Conexion db;
	private Statement st;
	private ResultSet rs;
	private Integer id;
	private Calendar fecha;
	private String dia, mes, anio;
	private DefaultTableModel modelo;
	private JTable tabla;
	private JScrollPane scroll;
	private JPanel agregarCot, mostrarCot;
	private JComboBox<String> prod_com, tipo_prod, emp_txt;
	private char cop = 169;

	public Cotizacion(String title) {
		this.setLayout(null);;
		this.setResizable(false);
		this.setBounds(0, 0, 810, 650);
		this.setLocationRelativeTo(null);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(white);
		this.setIconImage(new ImageIcon(getClass().getResource("images/Logo.png")).getImage());
		this.addWindowListener(this);

		//Panel para agregar cotizacion
		agregarCot = new JPanel();
		agregarCot.setBackground(white);
		agregarCot.setBounds(0, 0, 810, 650);
		agregarCot.setLayout(null);
		agregarCot.setVisible(false);
		
		//Panel para mostrar detalles de cotizacion
		mostrarCot = new JPanel();
		mostrarCot.setBackground(white);
		mostrarCot.setBounds(0, 0, 810, 650);
		mostrarCot.setLayout(null);
		mostrarCot.setVisible(true);

		//obtenemos fecha actual
		fecha = Calendar.getInstance();
		dia = Integer.valueOf(fecha.get(Calendar.DATE)).toString();
		mes = Integer.valueOf(fecha.get(Calendar.MONTH) + 1).toString();
		anio = Integer.valueOf(fecha.get(Calendar.YEAR)).toString();

		//iniciamos conexion a la db
		db = new Conexion();
		try {
			db.conectar();
			st = db.getConexion().createStatement();
			rs = st.executeQuery("SELECT MAX(id_cot) FROM Cotizacion");
			rs.next();
			id = rs.getInt(1); //maxima id de cotizaciones
		} catch(SQLException err) {
			JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		panelMostrarCot();
		panelAgregarCot();

		this.add(mostrarCot);
		this.add(agregarCot);
	}

	public void panelMostrarCot() {
		no_cot = new JLabel("Id Cotizaci\u00F3n");
		no_cot.setBounds(30, 57, 140, 25);
		no_cot.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		no_cot.setForeground(blue);
		mostrarCot.add(no_cot);

		no_cotField = new JTextField();
		no_cotField.setBounds(159, 54, 50, 30);
		no_cotField.setBackground(white);
		no_cotField.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		no_cotField.setText(Integer.valueOf(id).toString());
		no_cotField.setHorizontalAlignment(JTextField.CENTER); 
		no_cotField.setEditable(false);
		no_cotField.setForeground(blue);
		no_cotField.addKeyListener(this);
		mostrarCot.add(no_cotField);

		fechaLabel = new JLabel("Fecha: " + dia + "/" + mes + "/" + anio);
		fechaLabel.setBounds(618, 54, 152, 25);
		fechaLabel.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		fechaLabel.setForeground(black);
		mostrarCot.add(fechaLabel);

		id_cliente = new JLabel("Id Cliente");
		id_cliente.setBounds(57, 97, 80, 25);
		id_cliente.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		id_cliente.setForeground(black);
		mostrarCot.add(id_cliente);

		id_cliente_txt = new JTextField();
		id_cliente_txt.setBounds(159, 95, 50, 30);
		id_cliente_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		id_cliente_txt.setBackground(gray);
		id_cliente_txt.setForeground(black);
		id_cliente_txt.addFocusListener(this);
		mostrarCot.add(id_cliente_txt);

		nom_cliente = new JLabel("Nombre");
		nom_cliente.setBounds(228, 97, 70, 25);
		nom_cliente.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		nom_cliente.setForeground(black);
		mostrarCot.add(nom_cliente);

		nom_cliente_txt = new JTextField();
		nom_cliente_txt.setBounds(310, 95, 461, 30);
		nom_cliente_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		nom_cliente_txt.setBackground(gray);
		nom_cliente_txt.setForeground(black);
		nom_cliente_txt.addFocusListener(this);
		mostrarCot.add(nom_cliente_txt);

		tel = new JLabel("T\u00E9lefono");
		tel.setBounds(57, 141, 75, 25);
		tel.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		tel.setForeground(black);
		mostrarCot.add(tel);

		tel_txt = new JTextField();
		tel_txt.setBounds(159, 139, 171, 30);
		tel_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		tel_txt.setBackground(gray);
		tel_txt.setForeground(black);
		tel_txt.addFocusListener(this);
		mostrarCot.add(tel_txt);

		dir = new JLabel("Direcci\u00F3n");
		dir.setBounds(352, 141, 80, 25);
		dir.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		dir.setForeground(black);
		mostrarCot.add(dir);

		dir_txt = new JTextField();
		dir_txt.setBounds(444, 139, 326, 30);
		dir_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		dir_txt.setBackground(gray);
		dir_txt.setForeground(black);
		dir_txt.addFocusListener(this);
		mostrarCot.add(dir_txt);

		corr = new JLabel("Correo");
		corr.setBounds(57, 184, 58, 25);
		corr.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		corr.setForeground(black);
		mostrarCot.add(corr);

		corr_txt = new JTextField();
		corr_txt.setBounds(159, 182, 279, 30);
		corr_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		corr_txt.setBackground(gray);
		corr_txt.setForeground(black);
		corr_txt.addFocusListener(this);
		mostrarCot.add(corr_txt);

		emp = new JLabel("Empleado");
		emp.setBounds(57, 225, 84, 25);
		emp.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		emp.setForeground(black);
		mostrarCot.add(emp);

		emp_txt = new JComboBox<>();
		emp_txt.addItem("");
		emp_txt.setBounds(159, 223, 461, 30);
        emp_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        emp_txt.setBackground(gray);
		emp_txt.setForeground(black);
		emp_txt.addFocusListener(this);
		mostrarCot.add(emp_txt);

		String[] campos = new String[]{"Id", "Nombre", "Tipo", "Precio unitario", "Largo", "Ancho", 
									   "Cantidad", "Precio total"};

		modelo = new DefaultTableModel(null, campos);
        tabla = new JTable(modelo) {
            @Override
            public boolean isCellEditable(int row, int column) {
            //all cells false
            return false;
            }
        };
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setResizingAllowed(false);
        scroll = new JScrollPane(tabla);
		scroll.setBounds(30, 275, 750, 180);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(30); //Id
		tabla.getColumnModel().getColumn(1).setPreferredWidth(160); //Nombre
		tabla.getColumnModel().getColumn(2).setPreferredWidth(80); //Tipo
		tabla.getColumnModel().getColumn(3).setPreferredWidth(120); //Precio unitario
		tabla.getColumnModel().getColumn(4).setPreferredWidth(80); //Largo
		tabla.getColumnModel().getColumn(5).setPreferredWidth(80); //Ancho
		tabla.getColumnModel().getColumn(6).setPreferredWidth(100); //Cantidad
		tabla.getColumnModel().getColumn(7).setPreferredWidth(100); //Precio total
		tabla.setRowHeight(25);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tabla.getTableHeader().setFont(new Font("Microsoft New Tai Lue", 1, 16)); 
		tabla.getTableHeader().setForeground(white); 
		tabla.getTableHeader().setBackground(blue); 
		tabla.setBackground(white);
		tabla.setForeground(black);
		tabla.setFont(new Font("Microsoft New Tai Lue", 0, 14));
		mostrarCot.add(scroll);

		modelo.addRow(new String[]{"1", "Cristal", "Barandal", "1200", "800", "600", "5", "1200"});
		modelo.addRow(new String[]{"2", "Cristal", "Barandal", "1200", "800", "600", "5", "1200"});
		
		sbt = new JLabel("Subtotal: \u0024 0");
		sbt.setBounds(599, 478, 150, 25);
		sbt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		sbt.setForeground(black);
		mostrarCot.add(sbt);

		iva = new JLabel("IVA: 16 \u0025");
		iva.setBounds(640, 508, 80, 25);
		iva.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		iva.setForeground(black);
		mostrarCot.add(iva);
		
		total = new JLabel("Total: \u0024 0");
		total.setBounds(627, 538, 150, 25);
		total.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		total.setForeground(black);
		mostrarCot.add(total);

		cancelar = new JButton("Cancelar");
		cancelar.setBounds(30, 533, 100, 30);
		cancelar.setBackground(blue);
		cancelar.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		cancelar.setForeground(white);
		cancelar.addActionListener(this);
		cancelar.addKeyListener(this);
		mostrarCot.add(cancelar);

		borrar = new JButton("Borrar");
		borrar.setBounds(170, 533, 100, 30);
		borrar.setBackground(blue);
		borrar.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		borrar.setForeground(white);
		borrar.addActionListener(this);
		borrar.addKeyListener(this);
		mostrarCot.add(borrar);

		agregar = new JButton("Agregar");
		agregar.setBounds(310, 533, 100, 30);
		agregar.setBackground(blue);
		agregar.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		agregar.setForeground(white);
		agregar.addActionListener(this);
		agregar.addKeyListener(this);
		mostrarCot.add(agregar);

		guardar = new JButton("Guardar");
		guardar.setBounds(450, 533, 100, 30);
		guardar.setBackground(blue);
		guardar.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		guardar.setForeground(white);
		guardar.addActionListener(this);
		guardar.addKeyListener(this);
		mostrarCot.add(guardar);

		rights = new JLabel("Cristaler\u00eda San Rom\u00e1n. " + cop + " Copyright 2019. Todos los derechos reservados.",SwingConstants.CENTER);
		rights.setBounds(0, 591, 810, 30);
		rights.setFont(new Font("Microsoft New Tai Lue", 2, 12));
		rights.setOpaque(true);
		rights.setBackground(black);
		rights.setForeground(white);
        mostrarCot.add(rights);
        
        tira = new JLabel();
        tira.setBounds(0,0,810,20);
        tira.setBackground(blue);
        tira.setOpaque(true);
        add(tira);

        tira2 = new JLabel();
        tira2.setBounds(0,20,810,9);
        tira2.setBackground(blue2);
        tira2.setOpaque(true);
        add(tira2);

        tira3 = new JLabel();
        tira3.setBounds(0,29,810,7);
        tira3.setBackground(blue3);
        tira3.setOpaque(true);
        add(tira3);

        tira4 = new JLabel();
        tira4.setBounds(0,36,810,4);
        tira4.setBackground(blue4);
        tira4.setOpaque(true);
        add(tira4);
	}

	public void panelAgregarCot() {
		fechaLabel = new JLabel("Fecha: " + dia + "/" + mes + "/" + anio);
		fechaLabel.setBounds(618, 91, 152, 25);
		fechaLabel.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		fechaLabel.setForeground(black);
		agregarCot.add(fechaLabel);

		id_prod = new JLabel("Id Producto");
		id_prod.setBounds(30, 144, 128, 25);
		id_prod.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		id_prod.setForeground(blue);
		agregarCot.add(id_prod);

		id_prod_txt = new JTextField();
		id_prod_txt.setText("");
		id_prod_txt.setBounds(159, 142, 50, 30);
		id_prod_txt.setBackground(white);
		id_prod_txt.setFont(new Font("Microsoft New Tai Lue", 1, 18));
		id_prod_txt.setForeground(blue);
		id_prod_txt.setHorizontalAlignment(JTextField.CENTER);
		id_prod_txt.setEditable(false);
		agregarCot.add(id_prod_txt);

		prod = new JLabel("Producto");
		prod.setBounds(57, 243, 77, 25);
		prod.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		prod.setForeground(black);
		agregarCot.add(prod);

		prod_com = new JComboBox<>();
		prod_com.addItem("");
		prod_com.setBounds(163, 241, 248, 30);
        prod_com.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        prod_com.setBackground(gray);
		prod_com.setForeground(black);
		prod_com.addFocusListener(this);
		agregarCot.add(prod_com);
		
		pre_uni = new JLabel("Precio unitario");
		pre_uni.setBounds(438, 243, 124, 25);
		pre_uni.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		pre_uni.setForeground(black);
		agregarCot.add(pre_uni);

		pre_uni_txt = new JTextField();
		pre_uni_txt.setText("\u0024 0");
		pre_uni_txt.setBounds(581, 241, 159, 30);
		pre_uni_txt.setBackground(white);
		pre_uni_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		pre_uni_txt.setForeground(black);
		pre_uni_txt.setEditable(false);
		agregarCot.add(pre_uni_txt);

		dim = new JLabel("Dimensiones");
		dim.setBounds(57, 310, 113, 25);
		dim.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		dim.setForeground(black);
		agregarCot.add(dim);

		dim_largo = new JTextField();
		dim_largo.setBounds(188, 308, 107, 30);
		dim_largo.setBackground(gray);
		dim_largo.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		dim_largo.setForeground(black);
		dim_largo.addFocusListener(this);
		agregarCot.add(dim_largo);

		x = new JLabel("X");
		x.setBounds(308, 310, 11, 25);
		x.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		x.setForeground(black);
		agregarCot.add(x);

		dim_ancho = new JTextField();
		dim_ancho.setBounds(332, 308, 107, 30);
		dim_ancho.setBackground(gray);
		dim_ancho.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		dim_ancho.setForeground(black);
		dim_ancho.addFocusListener(this);
		agregarCot.add(dim_ancho);

		tipo = new JLabel("Tipo");
		tipo.setBounds(462, 310, 37, 25);
		tipo.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		tipo.setForeground(black);
		agregarCot.add(tipo);

		tipo_prod = new JComboBox<>();
		tipo_prod.addItem("");
		tipo_prod.setBounds(517, 308, 223, 30);
        tipo_prod.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        tipo_prod.setBackground(gray);
		tipo_prod.setForeground(black);
		tipo_prod.addFocusListener(this);
		agregarCot.add(tipo_prod);

		cant = new JLabel("Cantidad");
		cant.setBounds(58, 378, 76, 25);
		cant.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		cant.setForeground(black);
		agregarCot.add(cant);

		cant_txt = new JTextField();
		cant_txt.setBounds(153, 376, 90, 30);
		cant_txt.setBackground(white);
		cant_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		cant_txt.setForeground(black);
		cant_txt.setText("0");
		cant_txt.setHorizontalAlignment(JTextField.CENTER);
		cant_txt.setEditable(false);
		agregarCot.add(cant_txt);

		ImageIcon mas_imagen = new ImageIcon("./images/mas.png");
		mas = new JLabel(mas_imagen);
		mas.setBounds(306, 376, 30, 30);
		mas.addMouseListener(this);
		mas.addFocusListener(this);
		agregarCot.add(mas);

		ImageIcon menos_imagen = new ImageIcon("./images/menos.png");
		menos = new JLabel(menos_imagen);
		menos.setBounds(258, 376, 30, 30);
		menos.addMouseListener(this);
		menos.addFocusListener(this);
		agregarCot.add(menos);

		sbtotal = new JLabel("Subtotal");
		sbtotal.setBounds(370, 378, 70, 25);
		sbtotal.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		sbtotal.setForeground(black);
		agregarCot.add(sbtotal);

		sbtotal_txt = new JTextField();
		sbtotal_txt.setBounds(462, 376, 133, 30);
		sbtotal_txt.setBackground(white);
		sbtotal_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));
		sbtotal_txt.setForeground(black);
		sbtotal_txt.setText("\u0024 0");
		sbtotal_txt.setEditable(false);
		agregarCot.add(sbtotal_txt);

		regresar = new JButton("Regresar");
		regresar.setBounds(494, 503, 100, 30);
		regresar.setBackground(blue);
		regresar.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		regresar.setForeground(white);
		regresar.addActionListener(this);
		regresar.addFocusListener(this);
		agregarCot.add(regresar);

		agregarcot = new JButton("Agregar");
		agregarcot.setBounds(644, 503, 100, 30);
		agregarcot.setBackground(blue);
		agregarcot.setFont(new Font("Microsoft New Tai Lue", 1, 16));
		agregarcot.setForeground(white);
		agregarcot.addFocusListener(this);
		agregarCot.add(agregarcot);

		rights = new JLabel("Cristaler\u00eda San Rom\u00e1n. " + cop + " Copyright 2019. Todos los derechos reservados.",SwingConstants.CENTER);
		rights.setBounds(0, 591, 810, 30);
		rights.setFont(new Font("Microsoft New Tai Lue", 2, 12));
		rights.setOpaque(true);
		rights.setBackground(black);
		rights.setForeground(white);
        agregarCot.add(rights);
        
        tira = new JLabel();
        tira.setBounds(0,0,810,20);
        tira.setBackground(blue);
        tira.setOpaque(true);
        agregarCot.add(tira);

        tira2 = new JLabel();
        tira2.setBounds(0,20,810,9);
        tira2.setBackground(blue2);
        tira2.setOpaque(true);
        agregarCot.add(tira2);

        tira3 = new JLabel();
        tira3.setBounds(0,29,810,7);
        tira3.setBackground(blue3);
        tira3.setOpaque(true);
        agregarCot.add(tira3);

        tira4 = new JLabel();
        tira4.setBounds(0,36,810,4);
        tira4.setBackground(blue4);
        tira4.setOpaque(true);
        agregarCot.add(tira4);
	}

	//botones
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == this.cancelar) {
			Menu m = new Menu("Men\u00FA");
			m.setVisible(true);
			this.setVisible(false);
			try {
				db.desconectar();
			} catch(SQLException err) {
				JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if(evt.getSource() == this.agregar) {
			mostrarCot.setVisible(false);
			agregarCot.setVisible(true);
		} else if(evt.getSource() == this.regresar) {
			mostrarCot.setVisible(true);
			agregarCot.setVisible(false);
		}
	}

	//teclado
	@Override
	public void keyTyped(KeyEvent evt) {
        if(evt.getSource() == this.no_cotField) {
			if(this.no_cotField.getText().length() >= 4) {
				evt.consume();
			}
		}
    }

	@Override
	public void keyReleased(KeyEvent evt) {
        
    }

	@Override
	public void keyPressed(KeyEvent evt) {
		if(evt.getSource() == this.cancelar) {
			Menu m = new Menu("Men\u00FA");
			m.setVisible(true);
			this.setVisible(false);
			try {
				db.desconectar();
			} catch(SQLException err) {
				JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	//Focus
	@Override
    public void focusGained(FocusEvent evt) {
		if(evt.getSource() == this.id_cliente_txt) {
			this.id_cliente_txt.setBackground(bluefocus);
		} else if (evt.getSource() == this.nom_cliente_txt) {
			this.nom_cliente_txt.setBackground(bluefocus);
		} else if (evt.getSource() == this. tel_txt) {
			this.tel_txt.setBackground(bluefocus);
		} else if (evt.getSource() == this. dir_txt) {
			this.dir_txt.setBackground(bluefocus);
		} else if (evt.getSource() == this.corr_txt) {
			this.corr_txt.setBackground(bluefocus);
		} else if (evt.getSource() == this.emp_txt) {
			this.emp_txt.setBackground(bluefocus);
		} else if (evt.getSource() == this.prod_com) {
			this.prod_com.setBackground(bluefocus);
		} else if (evt.getSource() == this.dim_largo) {
			this.dim_largo.setBackground(bluefocus);
		} else if (evt.getSource() == this.dim_ancho) {
			this.dim_ancho.setBackground(bluefocus);
		} else if (evt.getSource() == this.tipo_prod) {
			this.tipo_prod.setBackground(bluefocus);
		} else if (evt.getSource() == this.regresar) {
			this.regresar.setBackground(bluefocus);
		} else if (evt.getSource() == this.agregarcot) {
			this.agregarcot.setBackground(bluefocus);
		}
    }

    @Override
    public void focusLost(FocusEvent evt) {
		if(evt.getSource() == this.id_cliente_txt) {
			this.id_cliente_txt.setBackground(gray);
		} else if (evt.getSource() == this.nom_cliente_txt) {
			this.nom_cliente_txt.setBackground(gray);
		} else if (evt.getSource() == this. tel_txt) {
			this.tel_txt.setBackground(gray);
		} else if (evt.getSource() == this. dir_txt) {
			this.dir_txt.setBackground(gray);
		} else if (evt.getSource() == this.corr_txt) {
			this.corr_txt.setBackground(gray);
		} else if (evt.getSource() == this.emp_txt) {
			this.emp_txt.setBackground(gray);
		} else if (evt.getSource() == this.prod_com) {
			this.prod_com.setBackground(gray);
		} else if (evt.getSource() == this.dim_largo) {
			this.dim_largo.setBackground(gray);
		} else if (evt.getSource() == this.dim_ancho) {
			this.dim_ancho.setBackground(gray);
		} else if (evt.getSource() == this.tipo_prod) {
			this.tipo_prod.setBackground(gray);
		} else if (evt.getSource() == this.regresar) {
			this.regresar.setBackground(blue);
		} else if (evt.getSource() == this.agregarcot) {
			this.agregarcot.setBackground(blue);
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
        if(evt.getSource() == this.menos) {
			if(Integer.parseInt(this.cant_txt.getText()) > 0) {
				this.cant_txt.setEditable(true);
				Integer valor = Integer.parseInt(this.cant_txt.getText()) - 1;
				this.cant_txt.setText(valor.toString());
				this.cant_txt.setEditable(false);
			}
		} else if(evt.getSource() == this.mas) {
			this.cant_txt.setEditable(true);
			Integer valor = Integer.parseInt(this.cant_txt.getText()) + 1;
			this.cant_txt.setText(valor.toString());
			this.cant_txt.setEditable(false);
		}
		this.sbtotal_txt.setEditable(true);
		Integer precio = Integer.parseInt(this.cant_txt.getText()) * 100;
		this.sbtotal_txt.setText("\u0024 " + precio.toString());
		this.sbtotal_txt.setEditable(false);
	}
	
	//ventana
	@Override
	public void windowClosing(WindowEvent evt) {
		try {
			db.desconectar();
			System.out.println("Se ha desconectado de la base de datos.");
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