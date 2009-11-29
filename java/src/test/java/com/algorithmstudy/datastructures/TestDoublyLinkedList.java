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

import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.junit.Test;

public class TestDoublyLinkedList extends TestCase {

  @Test
  public void testDoublyLinkedListAsQueue() {
    DoublyLinkedList<Integer> l = new DoublyLinkedList<Integer>();

    checkDequeueError(l);

    assertEquals(0, l.size());

    l.enqueue(23);
    assertEquals(1, l.size());

    assertEquals((Integer) 23, l.dequeue());
    assertEquals(0, l.size());

    l.enqueue(23);
    l.enqueue(24);
    l.enqueue(25);
    assertEquals(3, l.size());

    assertEquals((Integer) 23, l.dequeue());
    assertEquals(2, l.size());

    assertEquals((Integer) 24, l.dequeue());
    assertEquals(1, l.size());

    assertEquals((Integer) 25, l.dequeue());
    assertEquals(0, l.size());

    checkDequeueError(l);

  }

  @Test
  public void testDoublyLinkedListAsStack() {
    DoublyLinkedList<Integer> l = new DoublyLinkedList<Integer>();

    checkPopError(l);
    checkPeekError(l);

    assertEquals(0, l.size());

    l.push(23);
    assertEquals(1, l.size());

    assertEquals((Integer) 23, l.pop());
    assertEquals(0, l.size());

    l.push(23);
    l.push(24);
    l.push(25);
    assertEquals(3, l.size());

    assertEquals((Integer) 25, l.peek());
    assertEquals((Integer) 25, l.pop());
    assertEquals(2, l.size());

    assertEquals((Integer) 24, l.peek());
    assertEquals((Integer) 24, l.pop());
    assertEquals(1, l.size());

    assertEquals((Integer) 23, l.peek());
    assertEquals((Integer) 23, l.pop());
    assertEquals(0, l.size());

    checkPopError(l);
    checkPeekError(l);

  }

  @Test
  public void testDoublyLinkedList() {

    DoublyLinkedList<Integer> l = new DoublyLinkedList<Integer>();

    assertEquals(0, l.size());
    checkGetError(l, 0);
    checkGetError(l, -1);
    checkRemoveError(l, 0);
    checkRemoveError(l, -1);
    checkInsertError(l, -1, 20);
    checkInsertError(l, 1, 20);

    l.insert(-232, 0);
    assertEquals((Integer) (-232), l.peek());
    l.remove(0);
    // [ ]

    l.append(324);
    assertEquals(1, l.size());
    assertEquals((Integer) 324, l.get(0));
    assertEquals((Integer) 324, l.remove(0));
    assertEquals(0, l.size());
    // [ 324 ]

    l.append(4);
    assertEquals(1, l.size());
    assertEquals((Integer) 4, l.get(0));
    // [ 4 ]

    l.append(5);
    assertEquals(2, l.size());
    assertEquals((Integer) 4, l.get(0));
    assertEquals((Integer) 5, l.get(1));
    // [ 4, 5 ]

    l.append(2);
    assertEquals(3, l.size());
    assertEquals((Integer) 4, l.get(0));
    assertEquals((Integer) 5, l.get(1));
    assertEquals((Integer) 2, l.get(2));
    // [ 4, 5, 2 ]

    l.insert(80, 2);
    // [ 4, 5, 80, 2 ]

    assertEquals((Integer) 80, l.get(2));
    assertEquals((Integer) 2, l.get(3));
    assertEquals((Integer) 2, l.remove(l.size() - 1));
    // [ 4, 5, 80 ]

    checkInsertError(l, -5234, -1);
    l.insert(-35, 0);
    l.insert(472, 4);
    l.insert(29908, 4);
    checkInsertError(l, -54, 100);
    // [ -35, 4, 5, 80, 29908, 472 ]

    assertEquals((Integer) 4, l.get(1));
    assertEquals((Integer) 472, l.get(5));

    l.push(9);
    // [ 9, -35, 4, 5, 80, 29908, 472 ]

    checkGetError(l, 7);
    assertEquals((Integer) 9, l.get(0));
    assertEquals((Integer) 9, l.pop());
    assertEquals((Integer) (-35), l.remove(0));
    assertEquals((Integer) 472, l.remove(4));
    assertEquals((Integer) 4, l.pop());
    assertEquals((Integer) 5, l.pop());
    assertEquals((Integer) 80, l.pop());
    assertEquals((Integer) 29908, l.pop());
    // [ ]

    checkGetError(l, 0);
    checkGetError(l, 1);
    checkGetError(l, -1);
    checkRemoveError(l, 0);
    checkRemoveError(l, 1);
    checkRemoveError(l, -1);
  }

  private void checkGetError(DoublyLinkedList<Integer> l, int index) {
    try {
      l.get(index);
      fail();
    } catch (IndexOutOfBoundsException e) {
    }
  }

  private void checkInsertError(DoublyLinkedList<Integer> l, Integer value, int index) {
    try {
      l.insert(value, index);
      fail();
    } catch (IndexOutOfBoundsException e) {
    }
  }

  private void checkPeekError(Stack<Integer> l) {
    try {
      l.peek();
      fail();
    } catch (NoSuchElementException e) {
    }
  }

  private void checkDequeueError(Queue<Integer> l) {
    try {
      l.dequeue();
      fail();
    } catch (NoSuchElementException e) {
    }
  }

  private void checkPopError(Stack<Integer> l) {
    try {
      l.pop();
      fail();
    } catch (NoSuchElementException e) {
    }
  }

  private void checkRemoveError(DoublyLinkedList<Integer> l, int index) {
    try {
      l.remove(index);
      fail();
    } catch (IndexOutOfBoundsException e) {
    }
  }

}
