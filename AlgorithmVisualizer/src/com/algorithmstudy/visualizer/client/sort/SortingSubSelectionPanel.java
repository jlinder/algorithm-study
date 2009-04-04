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
package com.algorithmstudy.visualizer.client.sort;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class SortingSubSelectionPanel extends HorizontalPanel {

  private SortingDisplayPanel displayPanel;

  private ListBox numberChoice;
  private int lastNumberChoice;

  private ListBox alg1Choice;
  private int lastAlg1Choice;

  private ListBox alg2Choice;
  private int lastAlg2Choice;

  public SortingSubSelectionPanel(SortingDisplayPanel displayPanel) {
    this.displayPanel = displayPanel;

    setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);

    // Set up the number choice
    numberChoice = new ListBox();
    numberChoice.addItem("Show One");
    numberChoice.addItem("Compare Two");
    this.add(numberChoice);

    numberChoice.addChangeListener(new ChangeListener() {
      public void onChange(Widget w) {
        numberChoiceChange(w);
      }
    });

    lastNumberChoice = 0;

    createAlgChoice1ListBox();
  }

  private void createAlgChoice1ListBox() {
    alg1Choice = createAlgChoiceListBox();
    this.add(alg1Choice);

    alg1Choice.addChangeListener(new ChangeListener() {
      public void onChange(Widget w) {
        alg1ChoiceChange(w);
      }
    });

    lastAlg1Choice = 0;
  }

  private void createAlgChoice2ListBox() {
    alg2Choice = createAlgChoiceListBox();
    alg2Choice.setSelectedIndex(1);
    this.add(alg2Choice);

    alg2Choice.addChangeListener(new ChangeListener() {
      public void onChange(Widget w) {
        alg2ChoiceChange(w);
      }
    });

    lastAlg2Choice = 1;
  }

  private ListBox createAlgChoiceListBox() {
    ListBox algChoice = new ListBox();
    algChoice.addItem(SortAlgorithmType.getTypeByIndex(0).getDescription());
    algChoice.addItem(SortAlgorithmType.getTypeByIndex(1).getDescription());
    algChoice.addItem(SortAlgorithmType.getTypeByIndex(2).getDescription());
    algChoice.addItem(SortAlgorithmType.getTypeByIndex(3).getDescription());
    return algChoice;
  }

  private void alg1ChoiceChange(Widget w) {
    if (!alg1Choice.equals(w)) {
      return;
    }

    if (alg1Choice.getSelectedIndex() == lastAlg1Choice) {
      return;
    }

    lastAlg1Choice = alg1Choice.getSelectedIndex();
    displayPanel.setVisualizerAlgorithm(SortingDisplayPanel.VisualizerIndex.First,
        SortAlgorithmType.getTypeByIndex(lastAlg1Choice));
  }

  private void alg2ChoiceChange(Widget w) {
    if (!alg2Choice.equals(w)) {
      return;
    }

    if (alg2Choice.getSelectedIndex() == lastAlg2Choice) {
      return;
    }

    lastAlg2Choice = alg2Choice.getSelectedIndex();
    displayPanel.setVisualizerAlgorithm(SortingDisplayPanel.VisualizerIndex.Second,
        SortAlgorithmType.getTypeByIndex(lastAlg2Choice));
  }

  /**
   * Called when the selected number of algorithms to display is changed.
   * 
   * @param w
   */
  private void numberChoiceChange(Widget w) {
    if (!numberChoice.equals(w)) {
      return;
    }

    int selected = numberChoice.getSelectedIndex();
    if (lastNumberChoice == selected) {
      return;
    }

    if (0 == selected) {
      clearForReuse();
      createAlgChoice1ListBox();

      displayPanel.reinit(SortingDisplayPanel.VisualizerIndex.First, SortAlgorithmType.BubbleSort,
          null);
    } else {
      clearForReuse();
      createAlgChoice1ListBox();
      createAlgChoice2ListBox();

      displayPanel.reinit(SortingDisplayPanel.VisualizerIndex.Second, SortAlgorithmType.BubbleSort,
          SortAlgorithmType.InsertionSort);
    }

    lastNumberChoice = selected;
  }

  private void clearForReuse() {
    for (int i = getWidgetCount() - 1; i > 0; i--) {
      remove(i);
    }
  }

}
