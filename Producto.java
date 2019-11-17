import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;

public class Producto extends JFrame implements ActionListener, KeyListener, FocusListener, MouseListener, WindowListener, ItemListener {

    private Color blue = new Color(0, 153, 153);
    private Color blue2 = new Color(2, 199, 199);
    private Color blue3 = new Color(0, 220, 220);
    private Color blue4 = new Color(0, 243, 243);
    private Color bluefocus = new Color(167, 255, 255);
    private Color white = new Color(255, 255, 255);
    private Color black = new Color(0, 0, 0);
    private Color gray = new Color(224, 224, 224);
    private JPanel mostrarResultados, agregarProducto;
    private JLabel tira, tira2, tira3, tira4, rights;
    private JLabel consulta, busqueda, por, ordenado, agreProducto, id_prod, nombre, tipo, precio, cantidad;
    private JTextField consulta_txt, id_txt, nombre_txt, tipo_txt, precio_txt, cantidad_txt;
    private JButton buscar, filtros, agregar, cancelar, eliminar, guardar, regresar;
    private JComboBox<String> por_cb, ordenado_cb, asce_desce;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JScrollPane scroll;
    private Conexion db;
    private Statement st;
    private ResultSet rs;
    private Integer id;

    public Producto(String title) {// constructor
        this.setLayout(null);
        this.setBounds(0, 0, 810, 650);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle(title);
        this.getContentPane().setBackground(new Color(255, 255, 255));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon(getClass().getResource("images/Logo.png")).getImage());
        this.addWindowListener(this);

        //Panel para mostrar
        mostrarResultados = new JPanel();
        mostrarResultados.setBounds(0, 0, 810, 650);
        mostrarResultados.setBackground(new Color(255, 255, 255));
        mostrarResultados.setLayout(null);
        mostrarResultados.setVisible(true);

        //Panel para agregar
        agregarProducto = new JPanel();
        agregarProducto.setBounds(0, 0, 810, 650);
        agregarProducto.setBackground(new Color(255, 255, 255));
        agregarProducto.setLayout(null);
        agregarProducto.setVisible(false);

        //iniciamos conexion a la db
		db = new Conexion();
		try {
			db.conectar();
			st = db.getConexion().createStatement();

			rs = st.executeQuery("SELECT MAX(id_prod) FROM Producto");
			rs.next();
			id = rs.getInt(1); //maxima id de Productos + 1
		} catch(SQLException err) {
			JOptionPane.showMessageDialog(null, "Error: " + err, "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        //Panel Mostrar
        tira = new JLabel();
        tira.setBounds(0, 0, 810, 20);
        tira.setBackground(new Color(0, 153, 153));
        tira.setOpaque(true);
        mostrarResultados.add(tira);

        tira2 = new JLabel();
        tira2.setBounds(0, 20, 810, 9);
        tira2.setBackground(new Color(2, 199, 199));
        tira2.setOpaque(true);
        mostrarResultados.add(tira2);

        tira3 = new JLabel();
        tira3.setBounds(0, 29, 810, 7);
        tira3.setBackground(new Color(0, 220, 220));
        tira3.setOpaque(true);
        mostrarResultados.add(tira3);

        tira4 = new JLabel();
        tira4.setBounds(0, 36, 810, 4);
        tira4.setBackground(new Color(0, 243, 243));
        tira4.setOpaque(true);
        mostrarResultados.add(tira4);

        rights = new JLabel("Cristaler\u00eda San Rom\u00e1n. \u00A9 Copyright 2019. Todos los derechos reservados.",
                SwingConstants.CENTER);
        rights.setBounds(0, 591, 810, 30);
        rights.setFont(new Font("Microsoft New Tai Lue", 2, 12));
        rights.setOpaque(true);
        rights.setBackground(new Color(0, 0, 0));
        rights.setForeground(new Color(255, 255, 255));
        mostrarResultados.add(rights);

        busqueda = new JLabel("B\u00FAsqueda de productos");
        busqueda.setBounds(50, 60, 300, 40);
        busqueda.setForeground(blue);
        busqueda.setFont(new Font("Microsoft New Tai Lue", 1, 20));
        mostrarResultados.add(busqueda);

        consulta = new JLabel("Consulta");
        consulta.setBounds(50, 120, 80, 25);
        consulta.setForeground(new Color(0, 0, 0));
        consulta.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        mostrarResultados.add(consulta);

        consulta_txt = new JTextField();
        consulta_txt.setBounds(152, 117, 200, 30);
        consulta_txt.setBackground(new Color(224, 224, 224));
        consulta_txt.setForeground(new Color(0, 0, 0));// color de la letra
        consulta_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));// normal, tamaño
        consulta_txt.addFocusListener(this);
        mostrarResultados.add(consulta_txt);

        por = new JLabel("por:");
        por.setBounds(374, 120, 40, 25);
        por.setForeground(new Color(0, 0, 0));
        por.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        mostrarResultados.add(por);

        por_cb = new JComboBox<>();
        por_cb.setBounds(436, 117, 200, 30);
        por_cb.setForeground(new Color(0, 0, 0));
        por_cb.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        por_cb.addFocusListener(this);
        mostrarResultados.add(por_cb);

        ordenado = new JLabel("Ordenado por:");
        ordenado.setBounds(50, 164, 150, 25);
        ordenado.setForeground(new Color(0, 0, 0));
        ordenado.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        mostrarResultados.add(ordenado);

        ordenado_cb = new JComboBox<>();
        ordenado_cb.setBounds(203, 163, 200, 30);
        ordenado_cb.setForeground(new Color(0, 0, 0));
        ordenado_cb.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        ordenado_cb.addFocusListener(this);
        mostrarResultados.add(ordenado_cb);

        asce_desce = new JComboBox<>();
        asce_desce.setBounds(436, 163, 200, 30);
        asce_desce.setForeground(new Color(0, 0, 0));
        asce_desce.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        asce_desce.addFocusListener(this);
        mostrarResultados.add(asce_desce);

        buscar = new JButton("Buscar");
        buscar.setBounds(660, 117, 100, 30); // los primeros dos son la posicion y los siguientes dos son el tamaño
        buscar.setBackground(new Color(0, 153, 153));
        buscar.setForeground(new Color(255, 255, 255));
        buscar.setFont(new Font("Microsoft New Tai Lue", 1, 16));// nombre de la fuente, tipo de letra, tamaño
        buscar.addActionListener(this);
        buscar.addFocusListener(this);
        buscar.addKeyListener(this);
        buscar.addMouseListener(this);
        mostrarResultados.add(buscar);

        filtros = new JButton("Limpiar");
        filtros.setBounds(660, 163, 100, 30); // los primeros dos son la posicion y los siguientes dos son el tamaño
        filtros.setBackground(new Color(0, 153, 153));
        filtros.setForeground(new Color(255, 255, 255));
        filtros.setFont(new Font("Microsoft New Tai Lue", 1, 16));// nombre de la fuente, tipo de letra, tamaño
        filtros.addActionListener(this);
        filtros.addMouseListener(this);
        mostrarResultados.add(filtros);

        cancelar = new JButton("Cancelar");
        cancelar.setBounds(127, 532, 100, 30);
        cancelar.setBackground(blue);
        cancelar.setForeground(white);
        cancelar.setFont(new Font("Microsoft New Tai lue", 1, 16));
        cancelar.addActionListener(this);
        cancelar.addMouseListener(this);
        mostrarResultados.add(cancelar);

        eliminar = new JButton("Eliminar");
        eliminar.setBounds(355, 532, 100, 30); // los primeros dos son la posicion y los siguientes dos son el tamaño
        eliminar.setBackground(new Color(0, 153, 153));
        eliminar.setForeground(new Color(255, 255, 255));
        eliminar.setFont(new Font("Microsoft New Tai Lue", 1, 16));// nombre de la fuente, tipo de letra, tamaño
        eliminar.addActionListener(this);
        eliminar.addMouseListener(this);
        mostrarResultados.add(eliminar);

        agregar = new JButton("Agregar");
        agregar.setBounds(582, 532, 100, 30); // los primeros dos son la posicion y los siguientes dos son el tamaño
        agregar.setBackground(new Color(0, 153, 153));
        agregar.setForeground(new Color(255, 255, 255));
        agregar.setFont(new Font("Microsoft New Tai Lue", 1, 16));// nombre de la fuente, tipo de letra, tamaño
        agregar.addActionListener(this);
        agregar.addMouseListener(this);
        mostrarResultados.add(agregar);

        String[] header = new String[] { "Id", "Nombre", "Precio", "Tipo", "Cantidad" };
        modelo = new DefaultTableModel(null, header);
        tabla = new JTable(modelo){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setResizingAllowed(false);
        scroll = new JScrollPane(tabla);
        scroll.setBounds(30, 207, 745, 300);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(25); //Id
		tabla.getColumnModel().getColumn(1).setPreferredWidth(200); //Nombre
		tabla.getColumnModel().getColumn(2).setPreferredWidth(70); //Precio
		tabla.getColumnModel().getColumn(3).setPreferredWidth(95); //Tipo
        tabla.getColumnModel().getColumn(4).setPreferredWidth(70); //Cantidad
        tabla.setRowHeight(25);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tabla.getTableHeader().setFont(new Font("Microsoft New Tai Lue", 1, 16)); 
		tabla.getTableHeader().setForeground(white); 
		tabla.getTableHeader().setBackground(blue); 
		tabla.setBackground(white);
		tabla.setForeground(black);
		tabla.setFont(new Font("Microsoft New Tai Lue", 0, 14));
        mostrarResultados.add(scroll);

        //Visualizar Paneles
        this.add(mostrarResultados);
        this.add(agregarProducto);

        //Panel Agregar
        tira = new JLabel();
        tira.setBounds(0, 0, 810, 20);
        tira.setBackground(new Color(0, 153, 153));
        tira.setOpaque(true);
        agregarProducto.add(tira);

        tira2 = new JLabel();
        tira2.setBounds(0, 20, 810, 9);
        tira2.setBackground(new Color(2, 199, 199));
        tira2.setOpaque(true);
        agregarProducto.add(tira2);

        tira3 = new JLabel();
        tira3.setBounds(0, 29, 810, 7);
        tira3.setBackground(new Color(0, 220, 220));
        tira3.setOpaque(true);
        agregarProducto.add(tira3);

        tira4 = new JLabel();
        tira4.setBounds(0, 36, 810, 4);
        tira4.setBackground(new Color(0, 243, 243));
        tira4.setOpaque(true);
        agregarProducto.add(tira4);

        rights = new JLabel("Cristaler\u00eda San Rom\u00e1n. \u00A9 Copyright 2019. Todos los derechos reservados.",
                SwingConstants.CENTER);
        rights.setBounds(0, 591, 810, 30);
        rights.setFont(new Font("Microsoft New Tai Lue", 2, 12));
        rights.setOpaque(true);
        rights.setBackground(new Color(0, 0, 0));
        rights.setForeground(new Color(255, 255, 255));
        agregarProducto.add(rights);

        agreProducto = new JLabel("Agregar producto");
        agreProducto.setBounds(100, 80, 300, 60);
        agreProducto.setForeground(blue);
        agreProducto.setFont(new Font("Microsoft New Tai Lue", 1, 20));
        agregarProducto.add(agreProducto);

        id_prod = new JLabel("Id Producto");
        id_prod.setBounds(100, 180, 150, 25);
        id_prod.setForeground(new Color(0, 0, 0));
        id_prod.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        agregarProducto.add(id_prod);

        id_txt = new JTextField("", SwingConstants.CENTER);
        id_txt.setBounds(230, 177, 100, 30);
        id_txt.setBackground(new Color(224, 224, 224));
        id_txt.setForeground(new Color(0, 0, 0));// color de la letra
        id_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));// normal, tamaño
        id_txt.addFocusListener(this);
        agregarProducto.add(id_txt);

        nombre = new JLabel("Nombre");
        nombre.setBounds(100, 250, 70, 25);
        nombre.setForeground(new Color(0, 0, 0));
        nombre.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        agregarProducto.add(nombre);

        nombre_txt = new JTextField();
        nombre_txt.setBounds(210, 247, 470, 30);
        nombre_txt.setBackground(new Color(224, 224, 224));
        nombre_txt.setForeground(new Color(0, 0, 0));// color de la letra
        nombre_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));// normal, tamaño
        nombre_txt.addFocusListener(this);
        agregarProducto.add(nombre_txt);

        tipo = new JLabel("Tipo");
        tipo.setBounds(100, 320, 45, 25);
        tipo.setForeground(new Color(0, 0, 0));
        tipo.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        agregarProducto.add(tipo);

        tipo_txt = new JTextField("");
        tipo_txt.setBounds(175, 317, 200, 30);
        tipo_txt.setBackground(new Color(224, 224, 224));
        tipo_txt.setForeground(new Color(0, 0, 0));// color de la letra
        tipo_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));// normal, tamaño
        tipo_txt.addFocusListener(this);
        agregarProducto.add(tipo_txt);

        precio = new JLabel("Precio $");
        precio.setBounds(420, 320, 80, 25);
        precio.setForeground(new Color(0, 0, 0));
        precio.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        agregarProducto.add(precio);

        precio_txt = new JTextField("", SwingConstants.RIGHT);
        precio_txt.setBounds(530, 317, 150, 30);
        precio_txt.setBackground(new Color(224, 224, 224));
        precio_txt.setForeground(new Color(0, 0, 0));// color de la letra
        precio_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));// normal, tamaño
        precio_txt.addFocusListener(this);
        agregarProducto.add(precio_txt);

        cantidad = new JLabel("Cantidad ");
        cantidad.setBounds(100, 390, 100, 25);
        cantidad.setForeground(new Color(0, 0, 0));
        cantidad.setFont(new Font("Microsoft New Tai Lue", 0, 18));
        agregarProducto.add(cantidad);

        cantidad_txt = new JTextField();
        cantidad_txt.setBounds(210, 387, 150, 30);
        cantidad_txt.setBackground(new Color(224, 224, 224));
        cantidad_txt.setForeground(new Color(0, 0, 0));// color de la letra
        cantidad_txt.setFont(new Font("Microsoft New Tai Lue", 0, 18));// normal, tamaño
        cantidad_txt.addFocusListener(this);
        agregarProducto.add(cantidad_txt);

        regresar = new JButton("Regresar");
        regresar.setBounds(203, 490, 100, 30); // los primeros dos son la posicion y los siguientes dos son el tamaño
        regresar.setBackground(new Color(0, 153, 153));
        regresar.setForeground(new Color(255, 255, 255));
        regresar.setFont(new Font("Microsoft New Tai Lue", 1, 16));// nombre de la fuente, tipo de letra, tamaño
        regresar.addActionListener(this);
        regresar.addMouseListener(this);
        regresar.addFocusListener(this);
        regresar.addKeyListener(this);
        agregarProducto.add(regresar);

        guardar = new JButton("Guardar");
        guardar.setBounds(506, 490, 100, 30); // los primeros dos son la posicion y los siguientes dos son el tamaño
        guardar.setBackground(new Color(0, 153, 153));
        guardar.setForeground(new Color(255, 255, 255));
        guardar.setFont(new Font("Microsoft New Tai Lue", 1, 16));// nombre de la fuente, tipo de letra, tamaño
        guardar.addActionListener(this);
        guardar.addMouseListener(this);
        guardar.addFocusListener(this);
        guardar.addKeyListener(this);
        agregarProducto.add(guardar);

    }

    //ActionListener
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == this.agregar) {
            this.mostrarResultados.setVisible(false);
            this.agregarProducto.setVisible(true);
        } else if (evt.getSource() == this.regresar) {
            this.agregarProducto.setVisible(false);
            this.mostrarResultados.setVisible(true);
        } else if (evt.getSource() == this.cancelar) {
            Menu m = new Menu("Men\u00FA");
            m.setVisible(true);
            this.setVisible(false);
        }
    }

    //KeyListener
    @Override
    public void keyTyped(KeyEvent evt) { // No se ocupa aun

    }

    @Override
    public void keyReleased(KeyEvent evt) { // No se ocupa aun

    }

    @Override
    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (evt.getSource() == this.regresar){
                this.agregarProducto.setVisible(false);
                this.mostrarResultados.setVisible(true);
            } else if (evt.getSource() == this.guardar){

            }
        }
    }

    //Focus Listener

    @Override
    public void focusGained(FocusEvent evt) {
        if (evt.getSource() == this.consulta_txt) {
            this.consulta_txt.setBackground(bluefocus);
        } else if (evt.getSource() == this.por_cb) {
            this.por_cb.setBackground(bluefocus);
        } else if (evt.getSource() == this.ordenado_cb) {
            this.ordenado_cb.setBackground(bluefocus);
        } else if (evt.getSource() == this.asce_desce) {
            this.asce_desce.setBackground(bluefocus);
        } else if (evt.getSource() == this.buscar) {
            this.buscar.setBackground(bluefocus);
            this.buscar.setForeground(black);
        } else if (evt.getSource() == this.filtros) {
            this.filtros.setBackground(bluefocus);
            this.filtros.setForeground(black);
        } else if (evt.getSource() == this.cancelar) {
            this.cancelar.setBackground(bluefocus);
            this.cancelar.setForeground(black);
        } else if (evt.getSource() == this.eliminar) {
            this.eliminar.setBackground(bluefocus);
            this.eliminar.setForeground(black);
        } else if (evt.getSource() == this.agregar) {
            this.agregar.setBackground(bluefocus);
            this.agregar.setForeground(black);
        } else if (evt.getSource() == this.id_txt) {
            this.id_txt.setBackground(bluefocus);
        } else if (evt.getSource() == this.nombre_txt) {
            this.nombre_txt.setBackground(bluefocus);
        } else if (evt.getSource() == this.tipo_txt) {
            this.tipo_txt.setBackground(bluefocus);
        } else if (evt.getSource() == this.precio_txt) {
            this.precio_txt.setBackground(bluefocus);
        } else if (evt.getSource() == this.cantidad_txt) {
            this.cantidad_txt.setBackground(bluefocus);
        } else if (evt.getSource() == this.regresar) {
            this.regresar.setBackground(bluefocus);
            this.regresar.setForeground(black);
        } else if (evt.getSource() == this.guardar) {
            this.guardar.setBackground(bluefocus);
            this.guardar.setForeground(black);
        }
    }

    @Override
    public void focusLost(FocusEvent evt) {
        if (evt.getSource() == this.consulta_txt) {
            this.consulta_txt.setBackground(gray);
        } else if (evt.getSource() == this.por_cb) {
            this.por_cb.setBackground(gray);
        } else if (evt.getSource() == this.ordenado_cb) {
            this.ordenado_cb.setBackground(gray);
        } else if (evt.getSource() == this.asce_desce) {
            this.asce_desce.setBackground(gray);
        } else if (evt.getSource() == this.buscar) {
            this.buscar.setBackground(blue);
            this.buscar.setForeground(white);
        } else if (evt.getSource() == this.filtros) {
            this.filtros.setBackground(blue);
            this.filtros.setForeground(white);
        } else if (evt.getSource() == this.cancelar) {
            this.cancelar.setBackground(blue);
            this.cancelar.setForeground(white);
        } else if (evt.getSource() == this.eliminar) {
            this.eliminar.setBackground(blue);
            this.eliminar.setForeground(white);
        } else if (evt.getSource() == this.agregar) {
            this.agregar.setBackground(blue);
            this.agregar.setForeground(white);
        } else if (evt.getSource() == this.id_txt) {
            this.id_txt.setBackground(gray);
        } else if (evt.getSource() == this.nombre_txt) {
            this.nombre_txt.setBackground(gray);
        } else if (evt.getSource() == this.tipo_txt) {
            this.tipo_txt.setBackground(gray);
        } else if (evt.getSource() == this.precio_txt) {
            this.precio_txt.setBackground(gray);
        } else if (evt.getSource() == this.cantidad_txt) {
            this.cantidad_txt.setBackground(gray);
        } else if (evt.getSource() == this.regresar) {
            this.regresar.setBackground(blue);
            this.regresar.setForeground(white);
        } else if (evt.getSource() == this.guardar) {
            this.guardar.setBackground(blue);
            this.guardar.setForeground(white);
        }
    }

    //MouseListener
	@Override
    public void mouseReleased(MouseEvent evt) {

    }

    @Override
    public void mousePressed(MouseEvent evt) {

    }

    @Override
    public void mouseExited(MouseEvent evt) {
        if (evt.getSource() == this.buscar) {
            this.buscar.setBackground(blue);
            this.buscar.setForeground(white);
        } else if (evt.getSource() == this.filtros) {
            this.filtros.setBackground(blue);
            this.filtros.setForeground(white);
        } else if (evt.getSource() == this.cancelar) {
            this.cancelar.setBackground(blue);
            this.cancelar.setForeground(white);
        } else if (evt.getSource() == this.eliminar) {
            this.eliminar.setBackground(blue);
            this.eliminar.setForeground(white);
        } else if (evt.getSource() == this.agregar) {
            this.agregar.setBackground(blue);
            this.agregar.setForeground(white);
        } else if (evt.getSource() == this.regresar) {
            this.regresar.setBackground(blue);
            this.regresar.setForeground(white);
        } else if (evt.getSource() == this.guardar) {
            this.guardar.setBackground(blue);
            this.guardar.setForeground(white);
        }
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
        this.buscar.setBackground(blue);
        this.buscar.setForeground(white);
        this.filtros.setBackground(blue);
        this.filtros.setForeground(white);
        this.cancelar.setBackground(blue);
        this.cancelar.setForeground(white);
        this.eliminar.setBackground(blue);
        this.eliminar.setForeground(white);
        this.agregar.setBackground(blue);
        this.agregar.setForeground(white);
        this.regresar.setBackground(blue);
        this.regresar.setForeground(white);
        this.guardar.setBackground(blue);
        this.guardar.setForeground(white);
		if (evt.getSource() == this.buscar) {
            this.buscar.setBackground(bluefocus);
            this.buscar.setForeground(black);
        } else if (evt.getSource() == this.filtros) {
            this.filtros.setBackground(bluefocus);
            this.filtros.setForeground(black);
        } else if (evt.getSource() == this.cancelar) {
            this.cancelar.setBackground(bluefocus);
            this.cancelar.setForeground(black);
        } else if (evt.getSource() == this.eliminar) {
            this.eliminar.setBackground(bluefocus);
            this.eliminar.setForeground(black);
        } else if (evt.getSource() == this.agregar) {
            this.agregar.setBackground(bluefocus);
            this.agregar.setForeground(black);
        } else if (evt.getSource() == this.regresar) {
            this.regresar.setBackground(bluefocus);
            this.regresar.setForeground(black);
        } else if (evt.getSource() == this.guardar) {
            this.guardar.setBackground(bluefocus);
            this.guardar.setForeground(black);
        }
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        
	}
	
	//WindowListener
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

	//ItemListener
	@Override
	public void itemStateChanged(ItemEvent evt) {
		
    }
}