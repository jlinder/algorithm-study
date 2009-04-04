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
package com.algorithmstudy.visualizer.client.model;

/**
 * Defines a timer to use when running a visualizer. The timer will periodically call the
 * {@code tick()} method of all the visualizers in its list of visualizers.
 */
public interface VisualizerTimer {

  /**
   * Add a visualizer to the list of visualizers.
   * 
   * @param v
   *          The visualizer to add.
   */
  public void addVisualizer(Visualizer v);

  /**
   * Remove the specified visualizer from the list of visualizers.
   * 
   * @param v
   *          The visualizer to remove.
   */
  public void removeVisualizer(Visualizer v);

  /**
   * Clears all visualizers from the visualizer callback list.
   */
  public void clearVisualizerList();

  /**
   * Indicates the multiplier to use for making the callbacks.
   * 
   * @param multiplier
   */
  public void setTickMultiplier(int multiplier);

  /**
   * Start the timer.
   */
  public void start();

  /**
   * Stop the timer.
   */
  public void stop();
}
