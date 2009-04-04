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
 * The root interface for all visualizers.
 */
public interface Visualizer {

  /**
   * Sends a timer tick to the visualizer indicating it should make a move.
   * 
   * @return True if this visualizer has more moves to make, false if it has no more moves.
   */
  public boolean tick();

  /**
   * Sets the algorithm the visualizer will use.
   * 
   * @param a
   *          The algorithm performing the visualization.
   */
  public void setAlgorithm(Algorithm a);

}
