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

public interface ListBasedAlgorithm extends Algorithm {

  /**
   * Sets the visualizer to call back to when making a move.
   * 
   * @param v
   *          The visualizer.
   */
  public void setVisualizer(ListVisualizer v);

  /**
   * Sets the list to sort. Assume the algorithm will sort the exact array that is provided.
   * 
   * @param elements
   *          The elements to sort.
   */
  public void setListToSort(int[] elements);

}
