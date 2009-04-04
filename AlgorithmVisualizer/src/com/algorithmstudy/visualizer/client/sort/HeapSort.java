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
 * Implementation of the heap sort algorithm that interfaces with a ListVisualizer implementation.
 */
public class HeapSort implements ListBasedAlgorithm {

  private int[] elements;
  private ListVisualizer v;

  private boolean canMakeMove = false;

  private boolean isBuildingMaxHeap = false;
  private MaxHeapifyIteration mhNext;
  private MaxHeapifyIteration current;

  private int sortIndex;

  public boolean canMakeMove() {
    return canMakeMove;
  }

  public void makeMove() {
    if (!canMakeMove) {
      throw new IllegalStateException("No more moves can be made!");
    }

    if (isBuildingMaxHeap) {
      MaxHeapifyIteration next = maxHeapify();

      if (null == next) {
        if (null == mhNext) {
          current = null;
          isBuildingMaxHeap = false;
        } else {
          current = mhNext;
          if (mhNext.index > 0) {
            mhNext = MaxHeapifyIteration.createIterationState(mhNext.index - 1, elements.length);
          } else {
            mhNext = null;
          }
        }
      } else {
        current = next;
      }

      return;
    }

    if (current != null) {
      current = maxHeapify();
      return;
    }

    if (sortIndex == 0) {
      canMakeMove = false;
      return;
    }
    
    exchange(0, sortIndex);
    current = MaxHeapifyIteration.createIterationState(0, sortIndex);
    current = maxHeapify();
    --sortIndex;
  }

  private MaxHeapifyIteration maxHeapify() {
    MaxHeapifyIteration next = null;

    if (current.left < current.size && elements[current.left] > elements[current.index]) {
      if (current.right < current.size && elements[current.right] > elements[current.left]) {
        exchange(current.index, current.right);
        next = MaxHeapifyIteration.createIterationState(current.right, current.size);
      } else {
        exchange(current.index, current.left);
        next = MaxHeapifyIteration.createIterationState(current.left, current.size);
      }
    } else if (current.right < current.size && elements[current.right] > elements[current.index]) {
      exchange(current.index, current.right);
      next = MaxHeapifyIteration.createIterationState(current.right, current.size);
    }

    return next;
  }

  private void exchange(int left, int right) {
    int tmp = elements[left];
    elements[left] = elements[right];
    elements[right] = tmp;
    v.swap(left, right);
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

    isBuildingMaxHeap = true;
    current = MaxHeapifyIteration.createIterationState(elements.length / 2, elements.length);
    mhNext = MaxHeapifyIteration.createIterationState(current.index - 1, elements.length);

    sortIndex = elements.length - 1;
  }

  public void setVisualizer(ListVisualizer v) {
    this.v = v;
  }

  private static class MaxHeapifyIteration {
    int index;
    int size;

    int left;
    int right;

    static MaxHeapifyIteration createIterationState(int index, int size) {
      MaxHeapifyIteration is = new MaxHeapifyIteration();
      is.index = index;
      is.size = size;
      is.left = index * 2;
      is.right = (index * 2) + 1;
      return is;
    }
  }

}
