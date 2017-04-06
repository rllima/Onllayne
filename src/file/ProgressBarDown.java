package file;


import javax.swing.JLabel;
import javax.swing.JProgressBar;

import gui.GUI;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;

public class ProgressBarDown implements Runnable {
	
	private JProgressBar pBar;
	private GUI gui;
	private JLabel tempo;
	private int porcentagem;
	private FileReciev fileR;
	private JLabel rtt;
	JLabel vel;
	
	public ProgressBarDown(JProgressBar pBar, JLabel tempo, JLabel vel, JLabel rtt, FileReciev fileR, GUI gui){
		this.gui = gui;
		this.pBar = pBar;
		this.pBar.setStringPainted(true);
		this.tempo = tempo;
		this.porcentagem = 0;
		this.rtt = rtt;
		this.vel = vel;
		this.fileR = fileR;
	}

	public void run(){
		this.pBar.setMaximum(100);
		long init = System.currentTimeMillis();
		DecimalFormat df = new DecimalFormat("#.##");
		while(true){
			this.porcentagem = (int)fileR.getPercent();
			if(System.currentTimeMillis()-init>=1000) {init = System.currentTimeMillis();this.rtt.setText(df.format(this.fileR.getRTT()/1000000.0)+" milisegundos");}
			this.vel.setText(this.fileR.getVelocidade()+" MB/segundos");
			this.tempo.setText(fileR.getRemainingTime()+" segundos");
		    //this.label.setText(this.porcentagem+"%");
			this.pBar.setValue(this.porcentagem);
			if(this.porcentagem>=100) break;
		}
		if(!fileR.isCanceled())
			gui.downloadFinish(fileR.getPort());
	}
}