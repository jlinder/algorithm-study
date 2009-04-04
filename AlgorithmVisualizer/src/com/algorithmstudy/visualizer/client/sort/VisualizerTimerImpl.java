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

import java.util.ArrayList;

import com.algorithmstudy.visualizer.client.model.Visualizer;
import com.algorithmstudy.visualizer.client.model.VisualizerTimer;
import com.google.gwt.user.client.Timer;

/**
 * A timer class that calls back to the list of visualizers.
 */
public class VisualizerTimerImpl implements VisualizerTimer {

  public static final int MULTIPLIER_DEFAULT = 50;
  public static final int MULTIPLIER_HIGH = 100;
  public static final int MULTIPLIER_LOW = 1;

  private static final int DELAY_BASE = 5;

  /** The list of visualizers. */
  private ArrayList<Visualizer> visualizers = new ArrayList<Visualizer>();

  private int tickMultiplier = MULTIPLIER_DEFAULT;

  private Timer theTimer;

  public void addVisualizer(Visualizer v) {
    visualizers.add(v);
  }

  public void clearVisualizerList() {
    visualizers.clear();
  }

  public void removeVisualizer(Visualizer v) {
    visualizers.remove(v);
  }

  public void setTickMultiplier(int tickMultiplier) {
    if (MULTIPLIER_LOW > tickMultiplier) {
      this.tickMultiplier = MULTIPLIER_LOW;
    } else if (MULTIPLIER_HIGH < tickMultiplier) {
      this.tickMultiplier = MULTIPLIER_HIGH;
    } else {
      this.tickMultiplier = tickMultiplier;
    }
  }

  public void start() {
    if (null != theTimer) {
      System.out.println("VisualizerTimer.start() called but timer is already running.");
      return;
    }

    theTimer = new Timer() {
      public void run() {
        sendTick();
      }
    };

    schedule();
  }

  public void stop() {
    if (null == theTimer) {
      System.out.println("VisualizerTimer.stopt() called but timer is not running.");
      return;
    }

    theTimer.cancel();
    theTimer = null;
  }

  /**
   * Schedule the timer.
   */
  private void schedule() {
    theTimer.schedule(DELAY_BASE * tickMultiplier);
  }

  /**
   * Callback to send a tick to all the visualizers.
   */
  private void sendTick() {
    boolean keepGoing = false;
    for (Visualizer v : visualizers) {
      if (v.tick()) {
        keepGoing = true;
      }
    }
    
    if (keepGoing) {
      schedule();
    }
  }

}
