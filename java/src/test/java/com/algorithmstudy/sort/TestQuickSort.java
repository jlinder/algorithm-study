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

import org.junit.Test;

import com.algorithmstudy.sort.QuickSort;

import junit.framework.TestCase;

public class TestQuickSort extends TestCase {

  @Test
  public void testQuickSort() {
    int[] s = { 0 };
    QuickSort.quickSort(s);
    verifySorted(s);

    s = new int[] { 0, 1 };
    QuickSort.quickSort(s);
    verifySorted(s);

    s = new int[] { 1, 0 };
    QuickSort.quickSort(s);
    verifySorted(s);

    s = new int[] { 5, 5 };
    QuickSort.quickSort(s);
    verifySorted(s);

    s = new int[] { 5, 4, 3, 2, 1 };
    QuickSort.quickSort(s);
    verifySorted(s);

    s = new int[] { 10, 8, 9, 7, 3, 4, 5, 6, 2, 1, 0 };
    QuickSort.quickSort(s);
    verifySorted(s);

    s = new int[] { 10, 10, 8, 9, 7, 3, 4, 5, 6, 2, 1, 0 };
    QuickSort.quickSort(s);
    verifySorted(s);
  }

  @Test
  public void testRandomizedQuickSort() {
    int[] s = { 0 };
    QuickSort.randomizedQuickSort(s);
    verifySorted(s);

    s = new int[] { 0, 1 };
    QuickSort.randomizedQuickSort(s);
    verifySorted(s);

    s = new int[] { 1, 0 };
    QuickSort.randomizedQuickSort(s);
    verifySorted(s);

    s = new int[] { 5, 5 };
    QuickSort.randomizedQuickSort(s);
    verifySorted(s);

    s = new int[] { 5, 4, 3, 2, 1 };
    QuickSort.randomizedQuickSort(s);
    verifySorted(s);

    s = new int[] { 10, 8, 9, 7, 3, 4, 5, 6, 2, 1, 0 };
    QuickSort.randomizedQuickSort(s);
    verifySorted(s);

    s = new int[] { 10, 10, 8, 9, 7, 3, 4, 5, 6, 2, 1, 0 };
    QuickSort.randomizedQuickSort(s);
    verifySorted(s);
  }

  @Test
  public void testHoareQuickSort() {
    int[] s = { 0 };
    QuickSort.hoareQuickSort(s);
    verifySorted(s);

    s = new int[] { 0, 1 };
    QuickSort.hoareQuickSort(s);
    verifySorted(s);

    s = new int[] { 1, 0 };
    QuickSort.hoareQuickSort(s);
    verifySorted(s);

    s = new int[] { 5, 5 };
    QuickSort.hoareQuickSort(s);
    verifySorted(s);

    s = new int[] { 5, 4, 3, 2, 1 };
    QuickSort.hoareQuickSort(s);
    verifySorted(s);

    s = new int[] { 10, 8, 9, 7, 3, 4, 5, 6, 2, 1, 0 };
    QuickSort.hoareQuickSort(s);
    verifySorted(s);

    s = new int[] { 10, 10, 8, 9, 7, 3, 4, 5, 6, 2, 1, 0 };
    QuickSort.hoareQuickSort(s);
    verifySorted(s);
  }

  private void verifySorted(int[] s) {
    for (int i = 1; i < s.length; i++) {
      assertTrue("elements are not sorted.  index=[" + i + "]", s[i - 1] <= s[i]);
    }
  }
}
