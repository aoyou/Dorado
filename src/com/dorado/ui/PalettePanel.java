package com.dorado.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;

import com.dorado.image.Palette;
import com.dorado.tool.ToolDirector;
import com.dorado.util.GraphicsUtil;
import com.forana.layout.BlockLayout;

/**
 * Panel for displaying the palette to the user.
 */
public class PalettePanel extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 150;
	private static final int BUTTON_SIZE = 24;
	
	private Palette palette;

	public PalettePanel(ToolDirector director, Palette palette) {
		setMinimumSize(new Dimension(WIDTH, 0));
		setPreferredSize(new Dimension(WIDTH, 0));
		setMaximumSize(new Dimension(WIDTH, Integer.MAX_VALUE));
		getViewport().setBackground(UIConstants.PANEL_COLOR);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		setBorder(null);
		
		this.palette = palette;
		
		populatePanel(director);
	}
	
	private void populatePanel(ToolDirector director) {
		JPanel panel = new JPanel();
		panel.setBackground(UIConstants.PANEL_COLOR);
		panel.setLayout(new BlockLayout());
		
		ButtonGroup group = new ButtonGroup();
		for (Map.Entry<Integer, Color> entry : this.palette.getAllColors()) {
			AbstractButton button = new ColorButton(director, entry.getKey(), entry.getValue());
			group.add(button);
			panel.add(button, BlockLayout.INLINE);
		}
		
		setViewportView(panel);
	}
	
	private class ColorButton extends JToggleButton implements ActionListener {
		private static final long serialVersionUID = 1L;
		
		private int index;
		private Color color;
		private ToolDirector director;
		
		public ColorButton(ToolDirector director, int index, Color color) {
			this.director = director;
			this.index = index;
			this.color = color;
			setText(color.toString());
			setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
			setBorder(null);
			
			addActionListener(this);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			if (color.getAlpha() < 255) {
				GraphicsUtil.tileImage(g, 0, 0, BUTTON_SIZE, BUTTON_SIZE, UIConstants.TRANSPARENT_TILE, this);
			}
			
			g.setColor(color);
			g.fillRect(0, 0, BUTTON_SIZE, BUTTON_SIZE);
			
			if (isSelected()) {
				g.setColor(Color.BLACK);
				g.drawRect(0, 0, BUTTON_SIZE - 1, BUTTON_SIZE - 1);
				g.setColor(Color.WHITE);
				g.drawRect(1, 1, BUTTON_SIZE - 3, BUTTON_SIZE - 3);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			director.setColor(index);
		}
	}
}
