package Control;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.filechooser.FileNameExtensionFilter;

import View.Frame;

public class ControllerTela {
	
	@SuppressWarnings("unused")
	private final Frame view;
	private BufferedImage bImage   = null;
	private File 		  file     = null;
	private File 		  filesave = null;
	
	public ControllerTela(Frame view) {
		this.view = view;
		
	}
	
	public void selecionar() {
		
		JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new FileNameExtensionFilter("Imagem", "jpg", "jpeg", "gif", "png"));
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.showDialog(jfc, "Selecione");
            if(jfc.getSelectedFile() != null) {
                JOptionPane.showMessageDialog(null, jfc.getSelectedFile(), "Imagem Selecionada!", 1);
                file = new File(jfc.getSelectedFile().getAbsolutePath());
            } 
	}
	
	public void salvar(JSpinner spinLargura, JSpinner spinAltura) {
		
		if(file != null) {
			try {
            	bImage = ImageIO.read(file);	
            	ImageIcon i = new ImageIcon(bImage.getScaledInstance(
            			(int) spinLargura.getValue(), (int)spinAltura.getValue(), 100));
            	BufferedImage bi = new BufferedImage(i.getIconWidth(), i.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            	
            	Graphics g = bi.createGraphics();
        		i.paintIcon(null, g, 0,0);
        		g.dispose();
        		
        		JFileChooser jfc = new JFileChooser();
        		jfc.setFileFilter(new FileNameExtensionFilter("Imagem", "jpg", "jpeg", "gif", "png"));
        		jfc.setAcceptAllFileFilterUsed(false);
        		
        		boolean a = true;
        		
        		while(a == true) {
                    int option = jfc.showSaveDialog(null);
                    
                    if(option == JFileChooser.APPROVE_OPTION) {
                        	
                        filesave = new File(jfc.getSelectedFile().getAbsolutePath() + ".png");

                		if(filesave.exists() != true) {
                			
                			ImageIO.write(bi, "png", filesave);
    						JOptionPane.showMessageDialog(null, "Salvo com sucesso!", "", 1);
                			a = false;
                			
                		}else {
                			int op = JOptionPane.showInternalConfirmDialog(null, "Já existe uma imagem com mesmo nome. \n"
                					+ "Deseja Sobreescrever a imagem mesmo assim ?",
                					"", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
                			
                			if(op == 0) {
                				ImageIO.write(bi, "png", filesave);
        						JOptionPane.showMessageDialog(null, "Salvo com sucesso!", "", 1);
                    			a = false;
                			}
                				
                		}
                    }else
                    	a = false;
                    
                    }
        		} catch (IOException e1) {
        			e1.printStackTrace();
        			
        		}
			}else
			JOptionPane.showMessageDialog(null, "selecione uma imagem!");
	}
	

}
