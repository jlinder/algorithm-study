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
 * {@code MergeSort} contains implementations of the merge sort algorithm. The algorithm is
 * guaranteed to run in {@code O(n lg(n))} time.<br>
 * <br>
 * For further discussion of the merge sort algorithm, see chapter 2, section 3 of "Introduction to
 * Algorithms" by Cormen et. al.
 */
public class MergeSort {

  /**
   * Sort an int[] using the merge sort algorithm in {@code O(n lg(n))} time.
   * 
   * @param toSort
   *          The array to sort.
   */
  public static void mergeSort(int[] toSort) {
    if (null == toSort) {
      throw new NullPointerException("Can't sort null input.");
    }
    if (toSort.length == 0) {
      return;
    }
    mergeSortR(toSort, 0, toSort.length);
  }

  /**
   * Recursively divide the sorting problem in half and then merge each half together.
   * 
   * @param toSort
   *          The array to sort.
   * @param l
   *          The index of the left most element in the array to sort in this iteration.
   * @param r
   *          One more than the index of the right most element in the array to sort in this
   *          iteration.
   */
  private static void mergeSortR(int[] toSort, int l, int r) {

    if (r - l == 1) {
      return;
    }
    int m = ((r - l) / 2) + l;
    mergeSortR(toSort, l, m);
    mergeSortR(toSort, m, r);
    merge(toSort, l, m, r);
  }

  /**
   * Merge two adjoined partitions in an array into a single sorted partition. <br>
   * <br>
   * The {@code merge()} method runs in {@code O(n)} time where {@code n} is the number of elements
   * being merged (i.e., {@code r - l}). The amount of memory used is also {@code O(n)}.
   * 
   * @param a
   *          The array containing the partitions to merge.
   * @param l
   *          The index in the array of the beginning of the left partition.
   * @param m
   *          The index in the array of the beginning of the right partition. This value is one
   *          greater than the index of the last element in the left partition.
   * @param r
   *          One greater than the index of the last element in the right partition.
   */
  private static void merge(int[] a, int l, int m, int r) {
    int[] lCopy = new int[m - l];
    int[] rCopy = new int[r - m];
    for (int i = l; i < m; i++) {
      lCopy[i - l] = a[i];
    }
    for (int i = m; i < r; i++) {
      rCopy[i - m] = a[i];
    }

    int lp = 0;
    int rp = 0;
    int s = l;
    for (; s < r; s++) {
      if (rCopy.length == rp || (lp < lCopy.length && lCopy[lp] < rCopy[rp])) {
        a[s] = lCopy[lp++];
      } else {
        a[s] = rCopy[rp++];
      }
    }
  }

}
