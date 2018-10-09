import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

//Button panel which includes 5 buttons we need
	public class BPanel extends JPanel {
		JButton load;
		JButton run;
		JButton interrupt;
		JButton clear;
		JButton quit;

		BPanel() {
			this.setLayout(new GridLayout(1, 5));
			load = new JButton("load");
			run = new JButton("run");
			interrupt = new JButton("interrupt");
			clear = new JButton("clear");
			quit = new JButton("quit");
			this.add(load);
			this.add(run);
			this.add(interrupt);
			this.add(clear);
			this.add(quit);
		}
	}
