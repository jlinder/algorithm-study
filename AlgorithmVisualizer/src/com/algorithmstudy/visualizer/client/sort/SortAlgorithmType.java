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

/**
 * An enumeration of the types of sorting algorithms that a user can choose to visualize.
 */
public enum SortAlgorithmType {
  BubbleSort(0, "Bubble Sort"), InsertionSort(1, "Insertion Sort"), QuickSort(2, "Quick Sort"), HeapSort(
      3, "Heap Sort");

  private final int index;
  private final String description;

  private SortAlgorithmType(int index, String description) {
    this.index = index;
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public int getIndex() {
    return index;
  }

  /**
   * Factory function that creates an instance of the correct algorithm for the instance being asked
   * to create the algorithm instance.
   * 
   * @return A {@code ListBasedAlgorithm} instance that corresponds to the instance of
   *         {@code SortAlgorithmType} upon which the method is called.
   */
  public ListBasedAlgorithm getInstance() {
    if (this == BubbleSort) {
      return new BubbleSort();
    }
    if (this == InsertionSort) {
      return new InsertionSort();
    }
    if (this == QuickSort) {
      return new QuickSort();
    }
    if (this == HeapSort) {
      return new HeapSort();
    }
    return null;
  }

  /**
   * Gets the {@code SortAlgorithmType} that corresponds with the specified index.
   * 
   * @param i
   *          The index for which to get the corresponding {@code SortAlgorithmType}.
   * @return The {@code SortAlgorithmType} that corresponds with the specified index. If no
   *         {@code SortAlgorithmType} corresponds to the specified index, then null is returned.
   */
  public static SortAlgorithmType getTypeByIndex(int i) {
    if (BubbleSort.getIndex() == i) {
      return BubbleSort;
    } else if (InsertionSort.getIndex() == i) {
      return InsertionSort;
    } else if (QuickSort.getIndex() == i) {
      return QuickSort;
    } else if (HeapSort.getIndex() == i) {
      return HeapSort;
    }

    return null;
  }

}