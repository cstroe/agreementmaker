package am.va.graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.embed.swing.JFXPanel;

import javax.swing.JMenuItem;

import am.ui.UICore;
import am.ui.UIMenu;
import am.ui.api.AMMenuItem;

public class VAMenuItem implements AMMenuItem, ActionListener {

	@Override
	public String getMenuLocation() {
		return UIMenu.MENU_MATCHERS + "/";
	}

	@Override
	public JMenuItem getMenuItem() {
		JMenuItem menuVisualAanalytics = new JMenuItem(
				"Show Visual Analytics Panel");
		menuVisualAanalytics.addActionListener(this);
		return menuVisualAanalytics;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Init data
		VAPanelLogic val = new VAPanelLogic();
		val.InitData();

		// show chart panel
		VAPanel vap = new VAPanel(val);
		vap.initButNotShow();

		//Init tab
		JFXPanel fxp = vap.getFxPanel();
		if (fxp instanceof VATab) {
			UICore.getUI().addTab((VATab) fxp);
		}
	}

}