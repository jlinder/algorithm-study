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
 * {@code QuickSort} contains implementations of the quick sort algorithm. The average running time
 * of quick sort is {@code O(n lg(n))} time but in certain cases (such as where the array/list is
 * already sorted) the run time is {@code O(n*n)}. <br>
 * <br>
 * For further discussion of the quick sort algorithm, see chapter 7 of "Introduction to Algorithms"
 * by Cormen et. al.
 */
public class QuickSort {

  /**
   * Sort an {@code int[]} using the standard quick sort algorithm.
   * 
   * @param toSort
   *          The {@code int[]} to sort.
   */
  public static void quickSort(int[] toSort) {
    if (null == toSort) {
      throw new NullPointerException("Can't sort null.");
    }
    quickSortR(toSort, 0, toSort.length - 1);
  }

  private static void quickSortR(int[] a, int l, int r) {
    if (r - l > 0) {
      int p = partition(a, l, r);
      quickSortR(a, l, p);
      quickSortR(a, p + 1, r);
    }
  }

  private static int partition(int[] a, int l, int r) {
    int x = a[r];
    int lp = l - 1;
    for (int rp = l; rp < r; rp++) {
      if (a[rp] <= x) {
        lp++;
        int tmp = a[rp];
        a[rp] = a[lp];
        a[lp] = tmp;
      }
    }
    a[r] = a[lp + 1];
    a[lp + 1] = x;
    return lp;
  }

  /**
   * Sort an {@code int[]} using a quick sort algorithm similar to the standard quick sort algorithm
   * but which chooses a value at random during partitioning around which to partition the array
   * segment. <br>
   * <br>
   * Chapter 7, sections 3 and 4 of "Introduction to Algorithms" by Cormen et. al. provides
   * discussion of this version of the quick sort algorithm and its comparison to the standard
   * method for selecting the partitioning value.
   * 
   * @param toSort
   *          The {@code int[]} to sort.
   */
  public static void randomizedQuickSort(int[] toSort) {
    if (null == toSort) {
      throw new NullPointerException("Can't sort null.");
    }
    randomizedQuickSortR(toSort, 0, toSort.length - 1);
  }

  private static void randomizedQuickSortR(int[] a, int l, int r) {
    if (r - l > 0) {
      int p = randomizedPartition(a, l, r);
      randomizedQuickSortR(a, l, p);
      randomizedQuickSortR(a, p + 1, r);
    }
  }

  private static int randomizedPartition(int[] a, int l, int r) {
    exchange(a, randomize(l, r), r);

    int x = a[r];
    int lp = l - 1;

    for (int rp = l; rp < r; rp++) {
      if (a[rp] <= x) {
        lp++;
        int tmp = a[rp];
        a[rp] = a[lp];
        a[lp] = tmp;
      }
    }
    a[r] = a[lp + 1];
    a[lp + 1] = x;
    return lp;
  }

  /**
   * Choose a random number between {@code low} and {@code high} inclusive.
   * 
   * @param low
   *          The low number in the range from which to choose.
   * @param high
   *          The high number in the range from which to choose.
   * @return A number between {@code low} and {@code high} inclusive.
   */
  private static int randomize(int low, int high) {
    int p = (int) (((high - low + 1) * Math.random()));
    if (p > high - low) {
      p = high - low;
    }
    return low + p;
  }

  /**
   * Sort an {@code int[]} using the quick sort algorithm based on the partitioning scheme used with
   * the original version of the quick sort algorithm as introduced by C. A. R. Hoare. <br>
   * <br>
   * Chapter 7, sections 3 and 4 of "Introduction to Algorithms" by Cormen et. al. provides
   * discussion of this version of the quick sort algorithm and its comparison to the standard
   * method for selecting the partitioning value.
   * 
   * @param toSort
   *          The {@code int[]} to sort.
   */
  public static void hoareQuickSort(int[] toSort) {
    if (null == toSort) {
      throw new NullPointerException("Can't sort null.");
    }
    hoareQuickSortR(toSort, 0, toSort.length - 1);
  }

  private static void hoareQuickSortR(int[] a, int l, int r) {
    if (r - l > 0) {
      int p = hoarePartition(a, l, r);
      hoareQuickSortR(a, l, p);
      hoareQuickSortR(a, p + 1, r);
    }
  }

  private static int hoarePartition(int[] a, int p, int r) {
    int x = a[p];
    int i = p - 1;
    int j = r + 1;

    while (true) {
      do {
        j--;
      } while (a[j] > x);

      do {
        i++;
      } while (a[i] < x);

      if (i < j) {
        exchange(a, i, j);
      } else {
        return j;
      }
    }
  }

  /**
   * Exchange the elements at indices {@code i1} and {@code i2} in the {@code int[] a}.
   * 
   * @param a
   *          The array in which to exchange the elements.
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

}
