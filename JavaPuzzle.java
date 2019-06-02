
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author abhin This is a program to implement the java puzzle game
 */
public class JavaPuzzle {
	private JFrame jf;
	private JPanel pa;
	private JPanel pan;
	private JPanel u;
	private JTextArea t;
	private JLabel temp;
	private int count;
	private int rows = 10;
	private int columns = 10;
	private int blocks = 100;
	private static ArrayList<SubI> imags = new ArrayList<SubI>();
	private BufferedImage[] imgsa;
	private JButton jb1;

	/**
	 * @param args String arguments Main method
	 */
	public static void main(String args[]) {
		JavaPuzzle obj = new JavaPuzzle();
		obj.execute();
	}

	private String chooseim() {
		JFileChooser f = new JFileChooser();
		int result = f.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File fi = f.getSelectedFile();
			return fi.getAbsolutePath();
		} else {
			return null;
		}

	}

	private BufferedImage resize(BufferedImage img, int newW, int newH) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		return dimg;
	}

	private BufferedImage[] getIm() {
		File file = new File(chooseim());
		FileInputStream fi = null;
		try {
			fi = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			System.out.println("Failed to load image");
			System.exit(0);
		}
		BufferedImage image = null;
		try {
			image = ImageIO.read(fi);
		} catch (IOException e) {
			System.out.println("Failed to load image");
			System.exit(0);
		}
		image = resize(image, 800, 800);
		u = new JPanel();
		Graphics2D gr = image.createGraphics();
		gr.drawImage(image, 0, 0, null);
		JLabel orig = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage(image.getSource())));
		u.add(orig);
		u.setPreferredSize(new Dimension(800, 800));
		int chunkw = 80;
		int chunkh = 80;
		int c = 0;
		BufferedImage imgs[] = new BufferedImage[blocks];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				imgs[c] = new BufferedImage(chunkw, chunkh, image.getType());
				Graphics2D g = imgs[c++].createGraphics();
				g.drawImage(image, 0, 0, chunkw, chunkh, chunkw * j, chunkh * i, chunkw * j + chunkw,
						chunkh * i + chunkh, null);
				g.dispose();
				SubI a = new SubI();
				a.setLoc((10 * i) + j);
				imags.add(a);
			}
		}

		return imgs;
	}

	private void setimage() {
		pan = new JPanel();
		pan.setPreferredSize(new Dimension(800, 800));
		imgsa = getIm();
		pan.setLayout(new GridLayout(rows, columns, 0, 0));
		for (int i = 0; i < imgsa.length; i++) {
			temp = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage(imgsa[i].getSource())));
			temp.addMouseListener(new DragDropListener());
			temp.addMouseMotionListener(new DragDropListener());
			pan.add(temp);
			imags.get(i).setLab(temp);
		}
		Collections.shuffle(imags);
		for (int i = 0; i < imgsa.length; i++) {
			pan.add(imags.get(i).getLab());
		}
	}

	private void setbuttons() {
		pa = new JPanel();
		JButton jb = new JButton("Load Another Image");
		jb.addActionListener(new LoadListener());
		jb1 = new JButton("Show Original Image");
		jb1.addActionListener(new ShowListener());
		JButton jb2 = new JButton("Exit");
		jb2.addActionListener(new ExitListener());
		pa.add(jb);
		pa.add(jb1);
		pa.add(jb2);
	}

	private void execute() {
		jf = new JFrame("Puzzle Image");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// defining the text area
		t = new JTextArea(10, 5);
		JScrollPane scrollPane = new JScrollPane(t);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		t.setEditable(false);
		t.setText("Game Started!");
		t.setLineWrap(true);
		// defining the image panel
		setimage();
		// adding the buttons to a panel
		setbuttons();
		// adding the panels to the frame
		jf.getContentPane().add(BorderLayout.NORTH, pan);
		jf.add(BorderLayout.SOUTH, pa);
		jf.getContentPane().add(BorderLayout.CENTER, scrollPane);
		jf.pack();
		jf.setVisible(true);
	}

	/**
	 * @author abhin The user defined class which implements the overridden mouse
	 *         listener methods
	 */
	class DragDropListener extends MouseAdapter implements MouseListener, MouseMotionListener {
		int initx;
		int inity;
		JLabel j;
		JLabel u;

		/**
		 * Constructor
		 */
		DragDropListener() {
			super();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
		 * 
		 * @override
		 */
		public void mousePressed(MouseEvent e) {
			j = (JLabel) e.getSource();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
		 * 
		 * @override
		 */
		public void mouseDragged(MouseEvent e) {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
		 * 
		 * @override
		 */
		public void mouseReleased(MouseEvent e) {
			initx = (j.getLocation().x);
			inity = (j.getLocation().y);
			int finx = e.getXOnScreen() - (e.getXOnScreen() % 80);
			int finy = e.getYOnScreen() - (e.getYOnScreen() % 80) - 80;
			int inloc = 10 * (inity / 80) + (initx / 80);
			int finloc = (10 * (finy / 80) + (finx / 80));
			if (finloc != imags.get(finloc).getLoc()) {
				u = imags.get(finloc).getLab();
				u.setLocation(initx, inity);
				j.setLocation(finx, finy);
				Collections.swap(imags, finloc, inloc);
				if (finloc == imags.get(finloc).getLoc()) {
					t.setText(t.getText() + "\nImage Block in Correct Position!");
				}
				if (inloc == imags.get(inloc).getLoc()) {
					t.setText(t.getText() + "\nImage Block in Correct Position!");
				}
			} else
				System.out.println("\nImage Block in Correct Position");

		}
	}

	/**
	 * @author abhin load listener button
	 */
	class LoadListener implements ActionListener {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 * 
		 * @override
		 */
		public void actionPerformed(ActionEvent a) {
			jf.setVisible(false);
			try {
				execute();
			} catch (Exception e) {
				System.out.println("Failed to load new image");
			}

		}
	}

	/**
	 * @author abhin Show listener button
	 */
	class ShowListener implements ActionListener {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 * 
		 * @override
		 */
		public void actionPerformed(ActionEvent a) {
			count++;
			if (count % 2 == 0) {
				jf.remove(u);
				jf.getContentPane().add(BorderLayout.NORTH, pan);
				jb1.setText("Show Original Image");
				jf.validate();
				jf.repaint();
			} else {
				pan.revalidate();
				jf.remove(pan);
				jf.getContentPane().add(BorderLayout.NORTH, u);
				jb1.setText("Hide Original Image");
				jf.validate();
				jf.repaint();
			}

		}
	}

	/**
	 * @author abhin exit listener
	 */
	class ExitListener implements ActionListener {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 * 
		 * @override
		 */
		public void actionPerformed(ActionEvent a) {
			System.exit(0);
		}
	}
}
