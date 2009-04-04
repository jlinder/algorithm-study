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
 * Interface for a visualizer for list-based algorithms.
 */
public interface ListVisualizer extends Visualizer {

  /**
   * Swaps the elements at the indices {@code left} and {@code right}.
   * 
   * @param left
   *          The left index to swap.
   * @param right
   *          The right index to swap.
   * @throws IndexOutOfBoundsException
   *           Thrown if the left or right index is < 0 or > the number of bars in the chart.
   * @throws IllegalArgumentException
   *           Thrown if the left index >= the right index.
   */
  public void swap(int left, int right);

}
