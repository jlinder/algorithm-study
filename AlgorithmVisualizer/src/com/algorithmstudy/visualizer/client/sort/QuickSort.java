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
 * Implementation of the quick sort algorithm that interfaces with a ListVisualizer implementation.
 */
public class QuickSort implements ListBasedAlgorithm {

  private int[] elements;
  private ListVisualizer v;

  private boolean canMakeMove = false;

  private IterationStack stack;
  private IterationState current;

  public boolean canMakeMove() {
    return canMakeMove;
  }

  public void makeMove() {
    if (!canMakeMove) {
      throw new IllegalStateException("No more moves can be made!");
    }

    if (current.rp >= current.right) {
      // Move x to the correct place
      elements[current.right] = elements[current.lp + 1];
      elements[current.lp + 1] = current.x;
      if (current.lp + 1 != current.right) {
        v.swap(current.lp + 1, current.right);
      }

      // Create right and stack it.
      if (current.right - (current.lp + 1) > 0) {
        IterationState is = IterationState.createIterationState(current.lp + 1, current.right,
            elements);
        IterationStack iStack = new IterationStack();
        iStack.value = is;
        if (null == stack) {
          stack = iStack;
        } else {
          iStack.next = stack;
          stack = iStack;
        }
      }

      // Create left and use it, take from the stack, or quick sort is done
      if (current.lp - current.left > 0) {
        current = IterationState.createIterationState(current.left, current.lp, elements);
      } else if (null != stack) {
        current = stack.value;
        stack = stack.next;
      } else {
        current = null;
        canMakeMove = false;
      }

      return;
    }

    // Still partitioning this iteration
    if (elements[current.rp] <= current.x) {
      current.lp++;
      int tmp = elements[current.rp];
      elements[current.rp] = elements[current.lp];
      elements[current.lp] = tmp;

      if (current.lp != current.rp) {
        v.swap(current.lp, current.rp);
      }
    }

    current.rp++;
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

    stack = null;
    current = IterationState.createIterationState(0, elements.length - 1, elements);
  }

  public void setVisualizer(ListVisualizer v) {
    this.v = v;
  }

  private static class IterationStack {
    volatile IterationState value;
    volatile IterationStack next;
  }

  private static class IterationState {
    volatile int left;
    volatile int right;

    volatile int lp;
    volatile int rp;

    volatile int x;

    static IterationState createIterationState(int left, int right, int[] elements) {
      IterationState is = new IterationState();
      is.left = left;
      is.right = right;
      is.x = elements[right];
      is.lp = left - 1;
      is.rp = left;
      return is;
    }
  }

}
