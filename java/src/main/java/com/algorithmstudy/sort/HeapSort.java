/*
 * Copyright (c) 2009, The Algorithm Study Project
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice,
 *       this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the Algorithm Study Project, algorithmstudy.com, nor
 *       the names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package com.algorithmstudy.sort;

/**
 * {@code HeapSort} contains implementations of the heap sort algorithm. The binary heap sort
 * algorithm:
 * <ul>
 * <li>runs in {@code O(n lg(n))} time</li>
 * <li>has memory use of {@code O(n)} as it sorts in place</li>
 * </ul>
 */
public class HeapSort {

  /**
   * Sort an {@code int[]} using the heap sort algorithm based on a binary heap. {@code heapSort()}
   * calls {@code buildMaxHeap()} once and calls {@code maxHeapify() n} times. The resultant run
   * time is {@code O(n + n lg(n))} which simplifies to {@code O(n lg(n))}.
   * 
   * @param a
   *          The {@code int[]} to sort.
   */
  public static void heapSort(int[] a) {
    if (null == a) {
      throw new NullPointerException("Can't sort a null array!");
    }

    if (a.length < 2) {
      return;
    }

    buildMaxHeap(a);

    for (int i = a.length - 1; i > 0; i--) {
      exchange(a, 0, i);
      maxHeapify(a, 0, i);
    }
  }

  /**
   * Builds a max heap in the {@code int[]} backing this heap instance. A loose upper bound on the
   * run time of this method is {@code O(n lg(n))} because the max run time of {@code maxHeapify()}
   * is {@code O(lg(n))} and {@code maxHeapify()} is called {@code n} times. However, a tighter
   * upper bound is {@code O(n)} (see "Introduction to Algorithms, Second Edition" chapter 6,
   * section 3).
   * 
   * @param a
   *          The array to build into a max heap.
   */
  private static void buildMaxHeap(int[] a) {
    for (int i = a.length / 2; i >= 0; i--) {
      maxHeapify(a, i, a.length);
    }
  }

  /**
   * Creates a max heap at the specified index in {@code int[] a}. The {@code maxHeapify()} method
   * takes {@code O(h)} time to complete where {@code h} is the height of the tree rooted at
   * {@code i} (note, {@code h = n lg n} where {@code n} is the number of elements in the tree
   * rooted at {@code i}). It takes {@code O(h)} time to complete because it recurses at a maximum
   * down to its lowest leaf which is at a depth of {@code h}.
   * 
   * @param a
   *          The {@code int[]} in which to max heapify an element.
   * @param i
   *          The index in {@code a} for which to ensure it is a max heap.
   * @param size
   *          The size of the heap that is to be {@code maxHeapified()}.
   */
  private static void maxHeapify(int[] a, int i, int size) {
    int l = left(i);
    int r = right(i);

    if (l < size && a[l] > a[i]) {
      if (r < size && a[r] > a[l]) {
        exchange(a, i, r);
        maxHeapify(a, r, size);
      } else {
        exchange(a, i, l);
        maxHeapify(a, l, size);
      }
    } else if (r < size && a[r] > a[i]) {
      exchange(a, i, r);
      maxHeapify(a, r, size);
    }

  }

  /**
   * Exchange the elements at indices {@code i1} and {@code i2} in {@code a}.
   * 
   * @param a
   *          The array in which to exchange two items.
   * @param i1
   *          One index which is to be switched.
   * @param i2
   *          The second index which is to be switched.
   */
  private static void exchange(int[] a, int i1, int i2) {
    int tmp = a[i1];
    a[i1] = a[i2];
    a[i2] = tmp;
  }

  /**
   * The index of the left child of the element at index {@code i} in a binary heap array.
   * 
   * @param i
   *          The index of the element for which to get the left child's index.
   * @return The index of the left child of element {@code i} in a binary heap array. The returned
   *         value may be greater than the size of the heap or array.
   */
  private static int left(int i) {
    return i * 2;
  }

  /**
   * The index of the right child of the element at index {@code i} in a binary heap array.
   * 
   * @param i
   *          The index of the element for which to get the right child's index.
   * @return The index of the right child of element {@code i} in a binary heap array. The returned
   *         value may be greater than the size of the heap or array.
   */
  private static int right(int i) {
    return (i * 2) + 1;
  }

}
