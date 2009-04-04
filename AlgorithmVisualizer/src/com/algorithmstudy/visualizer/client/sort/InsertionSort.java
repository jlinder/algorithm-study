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

import com.algorithmstudy.visualizer.client.model.ListBasedAlgorithm;
import com.algorithmstudy.visualizer.client.model.ListVisualizer;

/**
 * Implementation of the insertion sort algorithm that interfaces with a ListVisualizer
 * implementation.
 */
public class InsertionSort implements ListBasedAlgorithm {

  private int[] elements;
  private ListVisualizer v;

  private boolean canMakeMove = false;

  private int rightPtr;
  private int movingPtr;

  public boolean canMakeMove() {
    return canMakeMove;
  }

  public void makeMove() {
    if (!canMakeMove) {
      throw new IllegalStateException("No more moves can be made!");
    }

    if (elements[movingPtr - 1] <= elements[movingPtr]) {
      wrapAroundToNextElement();
      return;
    }

    int tmp = elements[movingPtr - 1];
    elements[movingPtr - 1] = elements[movingPtr];
    elements[movingPtr] = tmp;

    v.swap(movingPtr - 1, movingPtr);

    if (1 >= movingPtr) {
      wrapAroundToNextElement();
    } else {
      movingPtr--;
    }
  }

  /**
   * Increase the insertion point to the next element.
   */
  private void wrapAroundToNextElement() {
    ++rightPtr;
    movingPtr = rightPtr;
    if (elements.length == rightPtr) {
      canMakeMove = false;
    }
  }

  public void setListToSort(int[] elements) {
    if (null == elements) {
      throw new NullPointerException("A list of elements is required");
    }
    if (elements.length == 0) {
      throw new IllegalArgumentException("One or more elements is required, zero is not allowed");
    }
    this.elements = elements;
    canMakeMove = true;
    rightPtr = 1;
    movingPtr = rightPtr;
  }

  public void setVisualizer(ListVisualizer v) {
    this.v = v;
  }

}
