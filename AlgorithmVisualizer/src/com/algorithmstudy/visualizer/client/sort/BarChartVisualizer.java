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

import com.algorithmstudy.visualizer.client.model.Algorithm;
import com.algorithmstudy.visualizer.client.model.ListVisualizer;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 */
public class BarChartVisualizer extends VerticalPanel implements ListVisualizer {

  private static final String STYLE_BAR_CHART = "bar-chart-visualizer";
  private static final String STYLE_CHART_SINGLE = "chart-single-";
  private static final String STYLE_CHART_DOUBLE = "chart-double-";
  private static final String STYLE_INVISIBLE_BAR = "invisible-bar";
  private static final String STYLE_VISIBLE_BAR = "visible-bar";
  private static final String STYLE_MOVES_LABEL = "moves";
  private static final String STYLE_MOVES_LABEL_FINISHED = "moves-finished";

  private static final String MOVES_BASE = "Moves: ";

  private Label movesLabel;
  private int numberOfMoves;

  private Chart chart;

  private Algorithm algorithm;

  public BarChartVisualizer(int[] elements, boolean isSingle) {
    setStyleName(STYLE_BAR_CHART);

    HorizontalPanel timePanel = new HorizontalPanel();
    timePanel.setWidth("100%");
    timePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);

    movesLabel = new Label();
    movesLabel.setStyleName(STYLE_MOVES_LABEL);
    movesLabel.setText(MOVES_BASE + "0");
    movesLabel.setWidth("8em");
    numberOfMoves = 0;

    timePanel.add(movesLabel);

    this.add(timePanel);

    chart = new Chart(elements, isSingle);
    this.add(chart);
  }

  public void setAlgorithm(Algorithm a) {
    this.algorithm = a;
  }

  public boolean tick() {
    if (algorithm.canMakeMove()) {
      algorithm.makeMove();
      ++numberOfMoves;
      movesLabel.setText(MOVES_BASE + Integer.toString(numberOfMoves));
      if (!algorithm.canMakeMove()) {
        movesLabel.setStyleName(STYLE_MOVES_LABEL_FINISHED);
      }
      return algorithm.canMakeMove();
    } else {
      return false;
    }
  }

  public void swap(int left, int right) {
    chart.swap(left, right);
  }

  private static class Chart extends HorizontalPanel {

    private int numBars;

    Chart(int[] barHeights, boolean isSingle) {
      numBars = barHeights.length;

      if (isSingle) {
        setStyleName(STYLE_CHART_SINGLE + Integer.toString(barHeights.length));
      } else {
        setStyleName(STYLE_CHART_DOUBLE + Integer.toString(barHeights.length));
      }
      setVerticalAlignment(HorizontalPanel.ALIGN_BOTTOM);

      // generate the elements
      populate(barHeights);
    }

    /**
     * Populates the chart with a new set of bars.
     * 
     * @param barHeights
     *          The heights of the bars with which to populate the chart.
     */
    void setNewBarHeights(int[] barHeights) {
      clear();
      numBars = barHeights.length;
      populate(barHeights);
    }

    /**
     * Swaps the left and the right indices.
     * 
     * @param left
     * @param right
     * @throws IndexOutOfBoundsException
     *           Thrown if the left or right index is < 0 or > the number of bars in the chart.
     * @throws IllegalArgumentException
     *           Thrown if the left index >= the right index.
     */
    void swap(int left, int right) {
      if (0 > left || left > numBars) {
        throw new IndexOutOfBoundsException("The left index is out of bounds.  left index=[" + left
            + "]");
      }

      if (0 > right || right > numBars) {
        throw new IndexOutOfBoundsException("The right index is out of bounds.  right index=["
            + right + "]");
      }

      if (left >= right) {
        throw new IllegalArgumentException("Left must be < right");
      }

      int leftLocation = (left * 2) + 1;
      int rightLocation = (right * 2) + 1;
      Widget oldLeft = getWidget(leftLocation);
      Widget oldRight = getWidget(rightLocation);

      remove(rightLocation);
      remove(leftLocation);
      insert(oldRight, leftLocation);
      insert(oldLeft, rightLocation);
    }

    private void populate(int[] elements) {
      float multiplier = 1;
      if (elements.length == 50) {
        multiplier = 8;
      } else if (elements.length == 75) {
        multiplier = 5.33f;
      } else if (elements.length == 100) {
        multiplier = 4;
      } else if (elements.length == 150) {
        multiplier = 2.67f;
      } else if (elements.length == 200) {
        multiplier = 2;
      } else if (elements.length == 300) {
        multiplier = 1.33f;
      }

      for (int i = 0; i < elements.length; i++) {
        this.add(createSpacerBar());
        this.add(createBar((int) (elements[i] * multiplier)));
      }
      this.add(createSpacerBar());
    }

    private Label createSpacerBar() {
      Label bar = new Label();
      bar.setHeight("1px");
      bar.setStyleName(STYLE_INVISIBLE_BAR);
      return bar;
    }

    private Label createBar(int height) {
      Label bar = new Label();
      bar.setHeight(Integer.toString(height) + "px");
      bar.setStyleName(STYLE_VISIBLE_BAR);
      return bar;
    }

  }

}
