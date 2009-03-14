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
package com.algorithmstudy.datastructures;

import org.junit.Test;

import com.algorithmstudy.datastructures.HeapUnderflowException;
import com.algorithmstudy.datastructures.MaxBinaryHeap;

import junit.framework.TestCase;

public class TestMaxBinaryHeap extends TestCase {

  @Test
  public void testBasicMaxBinaryHeapFunctionality() throws HeapUnderflowException {

    MaxBinaryHeap h = new MaxBinaryHeap();
    checkMaxException(h);
    checkRemoveMaxException(h);

    assertEquals(0, h.size());

    h.insert(3);
    assertEquals(1, h.size());
    assertEquals(3, h.max());
    assertEquals(3, h.max());
    assertEquals(3, h.removeMax());
    assertEquals(0, h.size());
    checkMaxException(h);

    h.insert(3);
    h.insert(10);
    h.insert(4);
    h.insert(18);
    h.insert(-5203);
    h.insert(3);
    h.insert(-2);
    h.insert(623);
    h.insert(Integer.MAX_VALUE);

    assertEquals(9, h.size());
    assertEquals(Integer.MAX_VALUE, h.max());
    assertEquals(Integer.MAX_VALUE, h.removeMax());
    assertEquals(8, h.size());

    h.insert(20);
    h.insert(235);
    assertEquals(10, h.size());

    assertEquals(623, h.removeMax());
    assertEquals(235, h.removeMax());
    assertEquals(20, h.removeMax());
    assertEquals(18, h.removeMax());
    assertEquals(10, h.max());
    assertEquals(6, h.size());
    assertEquals(10, h.removeMax());
    assertEquals(4, h.removeMax());
    assertEquals(3, h.removeMax());
    assertEquals(3, h.max());
    assertEquals(3, h.removeMax());
    assertEquals(-2, h.removeMax());
    assertEquals(-5203, h.removeMax());
    assertEquals(0, h.size());

    checkMaxException(h);
    checkRemoveMaxException(h);
  }

  @Test
  public void testMaxBinaryHeapSettingSizeConstructor() throws HeapUnderflowException {

    MaxBinaryHeap h = new MaxBinaryHeap(8);
    checkMaxException(h);
    checkRemoveMaxException(h);

    assertEquals(0, h.size());

    h.insert(3);
    h.insert(10);
    h.insert(4);
    h.insert(18);
    h.insert(-5203);
    h.insert(3);
    h.insert(-2);
    h.insert(623);
    h.insert(Integer.MAX_VALUE);
    h.insert(20);
    h.insert(235);
    h.insert(234);
    h.insert(236);
    h.insert(-238520);

    assertEquals(14, h.size());
    assertEquals(Integer.MAX_VALUE, h.max());
    assertEquals(Integer.MAX_VALUE, h.removeMax());
    assertEquals(623, h.removeMax());
    assertEquals(12, h.size());

    assertEquals(236, h.removeMax());
    assertEquals(235, h.removeMax());
    assertEquals(234, h.removeMax());
    assertEquals(20, h.removeMax());
    assertEquals(18, h.removeMax());
    assertEquals(10, h.max());
    assertEquals(7, h.size());

    assertEquals(10, h.removeMax());
    assertEquals(4, h.removeMax());
    assertEquals(3, h.removeMax());
    assertEquals(3, h.max());

    assertEquals(3, h.removeMax());
    assertEquals(-2, h.removeMax());
    assertEquals(-5203, h.removeMax());
    assertEquals(1, h.size());

    assertEquals(-238520, h.removeMax());
    assertEquals(0, h.size());

    checkMaxException(h);
    checkRemoveMaxException(h);
  }

  @Test
  public void testMaxBinaryHeapWithStarterArray() throws HeapUnderflowException {
    MaxBinaryHeap h = new MaxBinaryHeap(new int[] { 23, 951, 502, -35, -6, 8, 1, -4432, 723 });

    assertEquals(9, h.size());
    assertEquals(951, h.max());

    h.insert(8828);
    h.insert(5123);
    h.insert(-5262);
    h.insert(645);
    h.insert(125);
    h.insert(2);

    assertEquals(15, h.size());

    assertEquals(8828, h.max());

    assertEquals(8828, h.removeMax());
    assertEquals(5123, h.removeMax());
    assertEquals(951, h.removeMax());
    assertEquals(723, h.removeMax());
    assertEquals(645, h.removeMax());
    assertEquals(502, h.removeMax());
    assertEquals(125, h.removeMax());
    assertEquals(23, h.removeMax());
    assertEquals(8, h.removeMax());
    assertEquals(2, h.removeMax());
    assertEquals(1, h.removeMax());
    assertEquals(-6, h.removeMax());
    assertEquals(-35, h.removeMax());
    assertEquals(-4432, h.removeMax());
    assertEquals(-5262, h.removeMax());

    assertEquals(0, h.size());

    checkMaxException(h);
    checkRemoveMaxException(h);
  }

  private void checkMaxException(MaxBinaryHeap h) {
    try {
      h.max();
      fail("Excpected exception");
    } catch (HeapUnderflowException e) {
    }
  }

  private void checkRemoveMaxException(MaxBinaryHeap h) {
    try {
      h.removeMax();
      fail("Excpected exception");
    } catch (HeapUnderflowException e) {
    }
  }
}
