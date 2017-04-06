package file;

import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import gui.GUI;

public class ProgressBarUp implements Runnable {
	
	private JProgressBar pBar;
	
	private JLabel label1;
	private JLabel lblMbs;
	private JLabel lblEstRTT;
	private FileSender fileSender;
	private GUI gui;
	
	public ProgressBarUp(JProgressBar pBar, JLabel label1, JLabel lblMbs, JLabel lblEstRTT, FileSender fileSender, GUI gui){
		this.pBar = pBar;
		this.pBar.setStringPainted(true);
		this.gui = gui;
		this.label1 = label1;
		this.fileSender = fileSender;
		this.lblMbs = lblMbs;
		this.lblEstRTT = lblEstRTT;
	}
	
	public void run(){
		this.pBar.setMaximum(100);
		long timeInit = System.currentTimeMillis();
		while(true){
			DecimalFormat formater = new DecimalFormat("#.###");
			long timeAfter = System.currentTimeMillis();
			label1.setText((int)this.fileSender.getRemainingTime() + " segundos");
			lblMbs.setText(formater.format(this.fileSender.getSpeed()) + "  MB/s");
			if((timeAfter - timeInit) >= 1000){
				lblEstRTT.setText("RTT Estimado: " + formater.format(this.fileSender.getEstimatedRTT()/1000000) + " milisegundos");
				timeInit = System.currentTimeMillis();
			}
			this.pBar.setValue((int)this.fileSender.getPercent());
			if(this.fileSender.getPercent() >= 100){
				break; 
			}
		}
		this.gui.returnFileSelector();
	}
}
