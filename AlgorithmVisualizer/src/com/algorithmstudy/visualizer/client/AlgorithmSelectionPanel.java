/*
 * Copyright (C) 2009 The Algorithm Study Project
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2.0 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version
 * 2.0 along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 * or find it on the GNU website at:
 *
 *    http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
package com.algorithmstudy.visualizer.client;

import com.algorithmstudy.visualizer.client.sort.SortingDisplayPanel;
import com.algorithmstudy.visualizer.client.sort.SortingSubSelectionPanel;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Implements and manages the bar containing the algorithm selection options and manages the
 * creation/deletion of the panel showing the current algorithm being visualized.
 */
public class AlgorithmSelectionPanel extends HorizontalPanel {

  private HorizontalPanel selectBoxPanel;
  private VerticalPanel contentsPanel;
  private ListBox algorithmChoice;
  private int lastAlgorithmChoice;

  public AlgorithmSelectionPanel(VerticalPanel contentsPanel) {
    this.contentsPanel = contentsPanel;

    selectBoxPanel = new HorizontalPanel();
    add(selectBoxPanel);

    algorithmChoice = new ListBox();
    algorithmChoice.addItem("Select Algorithm Class ...");
    algorithmChoice.addItem("Sorting");
    selectBoxPanel.add(algorithmChoice);

    algorithmChoice.addChangeListener(new ChangeListener() {
      public void onChange(Widget w) {
        algorithmChoiceChange(w);
      }
    });

    lastAlgorithmChoice = 0;
  }

  private void algorithmChoiceChange(Widget w) {
    if (!algorithmChoice.equals(w)) {
      return;
    }

    int selected = algorithmChoice.getSelectedIndex();

    if (selected == lastAlgorithmChoice) {
      return;
    }

    switch (selected) {
    case 0:
      clearSelection();
      lastAlgorithmChoice = 0;
      break;
    case 1:
      clearSelection();
      setupSorting();
      lastAlgorithmChoice = 1;
      break;
    }
  }

  private void setupSorting() {
    SortingDisplayPanel displayPanel = new SortingDisplayPanel();
    contentsPanel.add(displayPanel);

    SortingSubSelectionPanel subSelectPanel = new SortingSubSelectionPanel(displayPanel);
    selectBoxPanel.add(subSelectPanel);
  }

  private void clearSelection() {
    // Remove the sub algorithm selection options
    for (int i = selectBoxPanel.getWidgetCount() - 1; i > 0; i--) {
      selectBoxPanel.remove(i);
    }

    // Remove the displayed visualization panels
    for (int i = contentsPanel.getWidgetCount() - 1; i >= 0; i--) {
      contentsPanel.remove(i);
    }
  }

}
