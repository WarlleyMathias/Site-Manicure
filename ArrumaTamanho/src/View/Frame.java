package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Control.ControllerTela;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	private JPanel 		  contentPane;
	
	private final ControllerTela control;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try { 
		     UIManager . setLookAndFeel ( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel" ); 
		    } 
		catch ( Exception e )  
		{
		      e.printStackTrace();
		    }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
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
	@SuppressWarnings("deprecation")
	public Frame() {
		
		control = new ControllerTela(this);
		
		setTitle("XSize");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Frame.class.getResource("/View/Images/x.png")));
		setResizable(false);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 228, 249);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		
		JLabel lblAltura = new JLabel("Altura:");
		lblAltura.setForeground(new Color(0, 0, 0));
		lblAltura.setBounds(10, 11, 85, 30);
		
		JLabel lblLargura = new JLabel("Largura:");
		lblLargura.setForeground(new Color(0, 0, 0));
		lblLargura.setBounds(117, 11, 85, 30);
		
		JButton btnSelecionarImagem = new JButton("Selecionar Imagem");
		btnSelecionarImagem.setForeground(new Color(0, 0, 0));
		btnSelecionarImagem.setBounds(10, 128, 192, 30);
		
		btnSelecionarImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				control.selecionar();
	                
			}
		});
		
		contentPane.setLayout(null);
		contentPane.add(lblAltura);
		contentPane.add(lblLargura);
		contentPane.add(btnSelecionarImagem);
		
		JSpinner spinAltura = new JSpinner();
		spinAltura.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinAltura.setBounds(10, 52, 85, 30);
		contentPane.add(spinAltura);
		
		JSpinner spinLargura = new JSpinner();
		spinLargura.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinLargura.setBounds(117, 52, 85, 30);
		contentPane.add(spinLargura);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setForeground(new Color(0, 0, 0));
		
		btnSalvar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				control.salvar(spinLargura, spinAltura);
				
			}
		});
		
		btnSalvar.setBounds(10, 169, 192, 30);
		contentPane.add(btnSalvar);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Taxa de Propor\u00E7\u00E3o");
		chckbxNewCheckBox.setForeground(new Color(0, 0, 0));
		chckbxNewCheckBox.setOpaque(false);
		chckbxNewCheckBox.setBounds(10, 98, 192, 23);
		contentPane.add(chckbxNewCheckBox);
		
		chckbxNewCheckBox.addActionListener(e -> {
			spinLargura.setValue(spinAltura.getValue());
		});
		
		spinAltura.addChangeListener(e -> {
			if(chckbxNewCheckBox.isSelected() == true)
		    spinLargura.setValue(spinAltura.getValue());
		});
		spinLargura.addChangeListener(e -> {
			if(chckbxNewCheckBox.isSelected() == true) 
		    spinAltura.setValue(spinLargura.getValue());
		});
	}
}
