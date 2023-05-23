package br.com.sne.sistema.gui.util.components;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.border.Border;

public class FlashingTab extends JTabbedPane {
	private int _tabIndex;
	private Color _foreground;
	private Color _savedForeground;
	private Border _border;
	
	private Timer timer = new Timer(1000, new ActionListener() {
		private boolean on = false;
		public void actionPerformed(ActionEvent e) {
			flash(on);
			on = !on;
		}
	});

	public void flash(int tabIndex) {
		_tabIndex = tabIndex;
		_savedForeground = getForeground();
		_border = getBorder();
		_foreground = Color.red;
		timer.start();
	}

	private void flash(boolean on) {
		if (on) {
			setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red));
			if (_foreground != null) {
				setForegroundAt(_tabIndex, _foreground);
			}
		} else {
			if (_savedForeground != null) {
				setForegroundAt(_tabIndex, _savedForeground);
			}
			setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.yellow));
		}
		repaint();
	}

	public void clearFlashing() {
		timer.stop();
		setForegroundAt(_tabIndex, _savedForeground);
		setBorder(BorderFactory.createEmptyBorder());
		repaint();
	}
}