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

import java.util.LinkedList;

import com.algorithmstudy.visualizer.client.model.ListBasedAlgorithm;
import com.algorithmstudy.visualizer.client.model.VisualizerTimer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * {@code SortingDisplayPanel} contains the code to render and manage the display of the
 * algorithm(s) being visualized.
 */
public class SortingDisplayPanel extends VerticalPanel {

  /**
   * {@code VisualizerIndex} is used to indicate how many algorithms should be displayed.
   */
  public static enum VisualizerIndex {
    // NOTE: The name VisualizerIndex is not a good name; figure out a better name.
    First, Second
  }

  /**
   * {@code NumberElements} encodes the possible number of elements that can be selected for
   * sorting.
   */
  private static enum NumberElements {
    Fifty(50, "50", 0), SeventyFive(75, "75", 1), OneHundred(100, "100", 2), OneHundredFifty(150,
        "150", 3), TwoHundred(200, "200", 4), ThreeHundred(300, "300", 5), FourHundred(400, "400",
        6);

    private int number;
    private String numberStr;
    private int index;

    private NumberElements(int number, String numberStr, int index) {
      this.number = number;
      this.numberStr = numberStr;
      this.index = index;
    }

    public int getNumber() {
      return number;
    }

    public String getNumberAsString() {
      return numberStr;
    }

    public int getIndex() {
      return index;
    }

    /**
     * Gets the correct instance of {@code NumberElements} for the specified index.
     * 
     * @param index
     *          The index for which to get an instance of {@code NumberElements}.
     * @return The correct instance of {@code NumberElements} for the specified index or null if the
     *         index does not correspond to an instance of {@code NumberElements}.
     */
    public static NumberElements getNumberElementsForIndex(int index) {
      if (Fifty.getIndex() == index) {
        return Fifty;
      } else if (SeventyFive.getIndex() == index) {
        return SeventyFive;
      } else if (OneHundred.getIndex() == index) {
        return OneHundred;
      } else if (OneHundredFifty.getIndex() == index) {
        return OneHundredFifty;
      } else if (TwoHundred.getIndex() == index) {
        return TwoHundred;
      } else if (ThreeHundred.getIndex() == index) {
        return ThreeHundred;
      } else if (FourHundred.getIndex() == index) {
        return FourHundred;
      }
      return null;
    }

  }

  private static final int DEFAULT_SPEED = 50;
  private static final NumberElements DEFAULT_NUM_ELEMENTS = NumberElements.TwoHundred;

  private SortControlsBar sortControls;

  private VisualizerIndex algorithmsDisplayed;

  private BarChartVisualizer visualizer1;
  private ListBasedAlgorithm alg1;
  private SortAlgorithmType alg1Type;

  private BarChartVisualizer visualizer2;
  private ListBasedAlgorithm alg2;
  private SortAlgorithmType alg2Type;

  private int[] valuesToSort;

  private VisualizerTimer timer;

  /**
   * Create a new {@code SortingDisplayPanel} instance.
   */
  public SortingDisplayPanel() {
    setWidth("100%");

    timer = new VisualizerTimerImpl();

    generateValuesToSort(DEFAULT_NUM_ELEMENTS);

    algorithmsDisplayed = VisualizerIndex.First;

    sortControls = new SortControlsBar(this, DEFAULT_NUM_ELEMENTS, algorithmsDisplayed);

    doReinitForOne(SortAlgorithmType.BubbleSort);
  }

  /**
   * Sets the algorithm type for the specified visualizer.
   * 
   * @param vi
   *          The index of the visualizer to set.
   * @param algorithm
   *          The algorithm to show in the visualizer.
   * @throws IllegalStateException
   *           Thrown if the specified {@code VisualizerIndex} is the second algorithm but only one
   *           algorithm is displayed.
   */
  protected void setVisualizerAlgorithm(VisualizerIndex vi, SortAlgorithmType algorithm) {
    if (VisualizerIndex.Second == vi && VisualizerIndex.First == algorithmsDisplayed) {
      throw new IllegalStateException("Can't set the second algorithm when only one algorithm is"
          + " displayed.");
    }

    timer.stop();

    ListBasedAlgorithm a = algorithm.getInstance();
    if (VisualizerIndex.First == vi) {
      alg1 = a;
      visualizer1.setAlgorithm(a);
      alg1Type = algorithm;
    } else {
      alg2 = a;
      visualizer2.setAlgorithm(a);
      alg2Type = algorithm;
    }

    reinit();
  }

  /**
   * Reinitializes the display panel using the existing number and type of algorithms that are
   * displayed.
   */
  public void reinit() {
    reinit(algorithmsDisplayed, alg1Type, alg2Type);
  }

  /**
   * Reinitializes the display panel using the specified number of algorithms to display.
   * 
   * @param vi
   *          The number of algorithms to display.
   */
  public void reinit(VisualizerIndex vi, SortAlgorithmType algorithm1, SortAlgorithmType algorithm2) {
    if (null == vi) {
      throw new NullPointerException("VisualizerIndex parameter required; can not be null.");
    }

    if (null == algorithm1) {
      throw new NullPointerException("At least one algorithm must be specified.");
    }

    if (VisualizerIndex.Second == vi && null == algorithm2) {
      throw new NullPointerException("Two algorithms must be specified when setting two "
          + "algorithms to be displayed.");
    }

    this.algorithmsDisplayed = vi;

    if (VisualizerIndex.First == algorithmsDisplayed) {
      doReinitForOne(algorithm1);
    } else {
      doReinitForTwo(algorithm1, algorithm2);
    }
  }

  private void doReinitForOne(SortAlgorithmType algorithm) {
    clearForReuse();

    sortControls.doReset();

    setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);

    sortControls.doSetNumElementsCB(VisualizerIndex.First);
    this.add(sortControls);

    setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

    visualizer1 = new BarChartVisualizer(valuesToSort, true);
    alg1 = algorithm.getInstance();
    alg1.setVisualizer(visualizer1);
    alg1.setListToSort(cloneValuesToSort());
    visualizer1.setAlgorithm(alg1);
    timer.addVisualizer(visualizer1);

    alg1Type = algorithm;

    visualizer2 = null;
    alg2 = null;
    alg2Type = null;

    this.add(visualizer1);
  }

  private void doReinitForTwo(SortAlgorithmType algorithm1, SortAlgorithmType algorithm2) {
    clearForReuse();

    sortControls.doReset();

    setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);

    sortControls.doSetNumElementsCB(VisualizerIndex.Second);
    this.add(sortControls);

    setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

    visualizer1 = new BarChartVisualizer(valuesToSort, false);
    alg1 = algorithm1.getInstance();
    alg1.setVisualizer(visualizer1);
    alg1.setListToSort(cloneValuesToSort());
    visualizer1.setAlgorithm(alg1);
    timer.addVisualizer(visualizer1);

    alg1Type = algorithm1;

    visualizer2 = new BarChartVisualizer(valuesToSort, false);
    alg2 = algorithm2.getInstance();
    alg2.setVisualizer(visualizer2);
    alg2.setListToSort(cloneValuesToSort());
    visualizer2.setAlgorithm(alg2);
    timer.addVisualizer(visualizer2);

    alg2Type = algorithm2;

    HorizontalPanel visPanel = new HorizontalPanel();
    visPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);

    visPanel.add(visualizer1);
    visPanel.add(visualizer2);

    this.add(visPanel);
  }

  /**
   * Create a copy of the {@code valuesToSort} array.
   * 
   * @return A copy of the {@code valuesToSort} array.
   */
  private int[] cloneValuesToSort() {
    int[] theCopy = new int[valuesToSort.length];
    for (int i = 0; i < valuesToSort.length; i++) {
      theCopy[i] = valuesToSort[i];
    }
    return theCopy;
  }

  /**
   * Generates the set of values that are to be sorted.
   * 
   * @param numValues
   *          The number of values that should be generated.
   */
  private void generateValuesToSort(NumberElements numValues) {
    // TODO: improve on the algorithm used to generate this randomized ordering
    valuesToSort = new int[numValues.getNumber()];
    LinkedList<Integer> l = new LinkedList<Integer>();
    for (int i = 1; i <= numValues.getNumber(); i++) {
      l.add(i);
    }

    for (int i = numValues.getNumber(); i > 0; i--) {
      int pos = (int) ((i + 1) * Math.random());
      if (l.size() == pos) {
        --pos;
      }
      Integer value = l.remove(pos);
      valuesToSort[numValues.getNumber() - i] = value;
    }
  }

  /**
   * Clear the widgets from this display panel.
   */
  private void clearForReuse() {
    stopTimer();
    timer.clearVisualizerList();

    for (int i = getWidgetCount() - 1; i >= 0; i--) {
      remove(i);
    }
  }

  /**
   * Starts the timer.
   */
  private void startTimer() {
    timer.start();
  }

  /**
   * Stops the timer.
   */
  private void stopTimer() {
    timer.stop();
  }

  /**
   * Set the multiplier for the timer.
   * 
   * @param multiplier
   *          The multiplier to set for the timer.
   */
  private void changeTimerMultiplier(int multiplier) {
    timer.setTickMultiplier(multiplier);
  }

  /**
   * A container to hold the sorting controls. The controls consist of the speed of execution, the
   * number of elements being put into the algorithm resetting the visualizations and randomizing
   * the order of the elements.
   */
  private class SortControlsBar extends HorizontalPanel {

    private SortingDisplayPanel parentPanel;

    private TextBox speedTA;
    private int lastSpeed;

    private ListBox numElementsSingleLB;
    private ListBox numElementsDoubleLB;
    private ChangeListener numElementsChangeListener;
    private NumberElements lastNumElements;

    private Button goButton;
    private Button stopButton;
    private Button resetButton;

    private Button randomize;

    public SortControlsBar(SortingDisplayPanel parent, NumberElements numElements,
        SortingDisplayPanel.VisualizerIndex numVisualizersDisplayed) {
      parentPanel = parent;

      setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);

      // Add the speed text box
      Label speedLabel = new Label("Speed");
      this.add(speedLabel);

      speedTA = new TextBox();
      speedTA.setText(Integer.toString(DEFAULT_SPEED));
      speedTA.setTitle("Sort Speed: 1 - 100");
      speedTA.setMaxLength(3);
      speedTA.setVisibleLength(3);
      speedTA.addChangeListener(new ChangeListener() {
        public void onChange(Widget w) {
          doChangeSpeed(w);
        }
      });
      this.add(speedTA);

      this.add(createSpacer());

      // Add the number of elements selection list box
      Label numElementsLabel = new Label("# Elements");
      this.add(numElementsLabel);

      numElementsSingleLB = new ListBox();
      numElementsSingleLB.addItem(NumberElements.Fifty.getNumberAsString());
      numElementsSingleLB.addItem(NumberElements.SeventyFive.getNumberAsString());
      numElementsSingleLB.addItem(NumberElements.OneHundred.getNumberAsString());
      numElementsSingleLB.addItem(NumberElements.OneHundredFifty.getNumberAsString());
      numElementsSingleLB.addItem(NumberElements.TwoHundred.getNumberAsString());
      numElementsSingleLB.addItem(NumberElements.ThreeHundred.getNumberAsString());
      numElementsSingleLB.addItem(NumberElements.FourHundred.getNumberAsString());
      numElementsSingleLB.setSelectedIndex(numElements.index);

      numElementsDoubleLB = new ListBox();
      numElementsDoubleLB.addItem(NumberElements.Fifty.getNumberAsString());
      numElementsDoubleLB.addItem(NumberElements.SeventyFive.getNumberAsString());
      numElementsDoubleLB.addItem(NumberElements.OneHundred.getNumberAsString());
      numElementsDoubleLB.addItem(NumberElements.OneHundredFifty.getNumberAsString());
      numElementsDoubleLB.addItem(NumberElements.TwoHundred.getNumberAsString());
      numElementsDoubleLB.setSelectedIndex(numElements.index);

      lastNumElements = numElements;

      numElementsChangeListener = new ChangeListener() {
        public void onChange(Widget w) {
          doChangeNumElements(w);
        }
      };

      if (SortingDisplayPanel.VisualizerIndex.Second == numVisualizersDisplayed) {
        numElementsDoubleLB.addChangeListener(numElementsChangeListener);
        numElementsSingleLB.setVisible(false);
      } else {
        numElementsSingleLB.addChangeListener(numElementsChangeListener);
        numElementsDoubleLB.setVisible(false);
      }
      this.add(numElementsSingleLB);
      this.add(numElementsDoubleLB);

      this.add(createSpacer());

      // Add the go, stop and reset buttons
      goButton = new Button("Go");
      goButton.setWidth("5em");
      goButton.addClickListener(new ClickListener() {
        public void onClick(Widget w) {
          doGo(w);
        }
      });
      this.add(goButton);

      stopButton = new Button("Stop");
      stopButton.setWidth("5em");
      stopButton.setVisible(false);
      stopButton.addClickListener(new ClickListener() {
        public void onClick(Widget w) {
          doStop(w);
        }
      });
      this.add(stopButton);

      resetButton = new Button("Reset");
      resetButton.setWidth("5em");
      resetButton.setVisible(false);
      resetButton.addClickListener(new ClickListener() {
        public void onClick(Widget w) {
          doReset(w);
        }
      });
      this.add(resetButton);

      this.add(createSpacer());

      // Add the randomize button
      randomize = new Button("Randomize");
      randomize.addClickListener(new ClickListener() {
        public void onClick(Widget w) {
          doRandomize(w);
        }
      });
      this.add(randomize);
    }

    private Panel createSpacer() {
      VerticalPanel p = new VerticalPanel();
      p.setSize("2em", ".5em");
      return p;
    }

    private void doChangeSpeed(Widget w) {
      if (speedTA != w) {
        return;
      }

      int newSpeed = VisualizerTimerImpl.MULTIPLIER_DEFAULT;
      try {
        newSpeed = Integer.parseInt(speedTA.getText());
      } catch (NumberFormatException e) {
        // TODO: warn user that the speed must be a number > 0 and < 101.
        // NOTE: That warning may not be necessary. The mouse over info and setting the value to a
        // value in the acceptable range may be sufficient.
        speedTA.setText(Integer.toString(lastSpeed));
        return;
      }

      if (VisualizerTimerImpl.MULTIPLIER_LOW > newSpeed) {
        newSpeed = VisualizerTimerImpl.MULTIPLIER_LOW;
        speedTA.setText(Integer.toString(VisualizerTimerImpl.MULTIPLIER_LOW));
      } else if (VisualizerTimerImpl.MULTIPLIER_HIGH < newSpeed) {
        newSpeed = VisualizerTimerImpl.MULTIPLIER_HIGH;
        speedTA.setText(Integer.toString(VisualizerTimerImpl.MULTIPLIER_HIGH));
      }

      parentPanel.changeTimerMultiplier(newSpeed);

      lastSpeed = newSpeed;
    }

    private void doChangeNumElements(Widget w) {
      if (numElementsSingleLB != w && numElementsDoubleLB != w) {
        return;
      }

      NumberElements newNumElements = null;
      if (numElementsSingleLB == w) {
        newNumElements = NumberElements.getNumberElementsForIndex(numElementsSingleLB
            .getSelectedIndex());
        numElementsDoubleLB.setSelectedIndex(newNumElements.getIndex());
      } else {
        newNumElements = NumberElements.getNumberElementsForIndex(numElementsDoubleLB
            .getSelectedIndex());
        numElementsSingleLB.setSelectedIndex(newNumElements.getIndex());
      }

      if (newNumElements == lastNumElements) {
        return;
      }

      lastNumElements = newNumElements;

      parentPanel.generateValuesToSort(lastNumElements);
      parentPanel.reinit();
    }

    private void doGo(Widget w) {
      if (goButton != w) {
        return;
      }
      doGo();
    }

    protected void doGo() {
      goButton.setVisible(false);
      stopButton.setVisible(true);
      resetButton.setVisible(false);

      parentPanel.startTimer();
    }

    private void doStop(Widget w) {
      if (stopButton != w) {
        return;
      }
      doStop();
    }

    protected void doStop() {
      goButton.setVisible(false);
      stopButton.setVisible(false);
      resetButton.setVisible(true);

      parentPanel.stopTimer();
    }

    private void doReset(Widget w) {
      if (resetButton != w) {
        return;
      }
      doReset();

      parentPanel.reinit();
    }

    protected void doReset() {
      goButton.setVisible(true);
      stopButton.setVisible(false);
      resetButton.setVisible(false);
    }

    private void doRandomize(Widget w) {
      if (randomize != w) {
        return;
      }

      parentPanel.generateValuesToSort(lastNumElements);
      parentPanel.reinit();
    }

    protected void doSetNumElementsCB(SortingDisplayPanel.VisualizerIndex vi) {
      if (SortingDisplayPanel.VisualizerIndex.First == vi) {
        numElementsDoubleLB.removeChangeListener(numElementsChangeListener);
        numElementsDoubleLB.setVisible(false);
        numElementsSingleLB.setVisible(true);
        numElementsSingleLB.addChangeListener(numElementsChangeListener);
      } else if (SortingDisplayPanel.VisualizerIndex.Second == vi) {
        numElementsSingleLB.removeChangeListener(numElementsChangeListener);
        numElementsSingleLB.setVisible(false);

        if (lastNumElements.getIndex() > SortingDisplayPanel.NumberElements.TwoHundred.getIndex()) {
          numElementsSingleLB.setSelectedIndex(SortingDisplayPanel.NumberElements.TwoHundred
              .getIndex());
          numElementsDoubleLB.setSelectedIndex(SortingDisplayPanel.NumberElements.TwoHundred
              .getIndex());
          lastNumElements = SortingDisplayPanel.NumberElements.TwoHundred;
          parentPanel.generateValuesToSort(lastNumElements);
        }

        numElementsDoubleLB.setVisible(true);
        numElementsDoubleLB.addChangeListener(numElementsChangeListener);
      }
    }
  }

}
