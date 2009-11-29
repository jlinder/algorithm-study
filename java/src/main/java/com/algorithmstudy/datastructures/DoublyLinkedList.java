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

/**
 * An implementation of a linked list with links in both directions. A doubly linked list can be
 * used as a stack but is particularly useful when used as a {@code Queue} or in cases where quick
 * access to the beginning and the end of the list are necessary.
 * 
 * Some notes:
 * <ul>
 * <li>This list implementation is not thread safe</li>
 * <li>The methods that need to find a specific index in the list have not been optimized in this
 * implementation. Thus, the entire list up to the specified location will be traversed for those
 * methods and, since the specified location may be at the end of the list, these methods run in
 * {@code O(n)} time. <br/>
 * <br/>
 * 
 * The only way the search time can be improved for these operations is to start the list traversal
 * from the end of the list when {@code i > ( size / 2 )}. The upper limit on the number of
 * operations would become {@code n/2}. Since {@code n/2} grows in the same order of magnitude as
 * {@code n}, the method would still be an {@code O(n)} operation.</li>
 * </ul>
 * 
 * @param <T>
 *          The type to store in a {@code DoublyLinkedList} instance.
 */
public class DoublyLinkedList<T> implements Stack<T> {

  private Node<T> head;
  private Node<T> tail;
  private int size;

  /**
   * Construct a {@code DoublyLinkedList} instance.
   */
  public DoublyLinkedList() {
    head = null;
    tail = null;
    size = 0;
  }

  /**
   * Returns the number of elements in the list.
   */
  public int size() {
    return size;
  }

  /**
   * Appends {@code value} to the end of the list. <br/>
   * <br/>
   * 
   * Note: a reference to the tail of the list is maintained. Thus, this is an {@code O(1)}
   * operation as the tail is known.
   * 
   * @param value
   *          The value to be appended to the end of the list.
   */
  public void append(T value) {
    Node<T> n = new Node<T>(value);
    if (0 == size) {
      head = n;
      tail = n;
    } else {
      tail.next = n;
      n.previous = tail;
      tail = n;
    }

    size++;
  }

  /**
   * Get the value at index {@code i} in the list. <br/>
   * <br/>
   * 
   * Note: this method has not been optimized. To get the value at index {@code i}, the entire list
   * up to the specified location is being traversed. Since the specified location may be at the end
   * of the list, this is an {@code O(n)} operation.
   * 
   * @param i
   *          The index at which the desired value resides. A zero (0) based number. It must follow
   *          this rule: {@code 0 <= i < list.size()} where {@code list.size()} is the size of the
   *          list. If {@code i} is not within those bounds, an {@code IndexOutOfBoundsException}
   *          will be thrown.
   * @return The value at index {@code i} in the list.
   * @throws IndexOutOfBoundsException
   *           if the index is not within the prescribed limits.
   */
  public T get(int i) throws IndexOutOfBoundsException {
    if (i >= size || 0 > i) {
      throw new IndexOutOfBoundsException("Index beyond bounds of the list");
    }

    Node<T> n = findNode(i);

    return n.value;
  }

  /**
   * Inserts {@code value} into the list at the index {@code i}. <br/>
   * <br/>
   * 
   * Note: this method has not been optimized. To insert the value at index {@code i}, the entire
   * list up to the specified location is traversed. Since the specified location may be at the end
   * of the list, this is an {@code O(n)} operation.
   * 
   * @param value
   *          The value to insert.
   * @param i
   *          The index in the list where the value should be inserted. A zero (0) based number. It
   *          must fall in the following range: {@code 0 <= i <= list.size()} where {@code
   *          list.size()} is the size of the list. If {@code i} is not within those bounds, an
   *          {@code IndexOutOfBoundsException} will be thrown.
   * @throws IndexOutOfBoundsException
   *           if the index is not within the prescribed limits.
   */
  public void insert(T value, int i) {
    if (0 > i || size < i) {
      throw new IndexOutOfBoundsException("Can't insert value at index=[" + i + "]");
    }

    Node<T> n = new Node<T>(value);

    if (0 == size) {
      head = n;
      size++;
      return;
    }

    if (0 == i) {
      n.next = head;
      head.previous = n;
      head = n;
      size++;
      return;
    }

    if (i == size) {
      n.previous = tail;
      tail.next = n;
      tail = n;
      size++;
      return;
    }

    Node<T> prev = findNode(i - 1);

    // make proper assignments on both sides, thus, 4 assignments are necessary.
    n.next = prev.next;
    n.next.previous = n;
    prev.next = n;
    n.previous = prev;
    size++;
  }

  /**
   * Push a value to the front of the list.<br/>
   * <br/>
   * 
   * Since the value is placed at the head of the list, insertion time is an {@code O(1)} operation
   * (contrasted with an {@code O(n)} operation if it were placed at the end of the list).
   * 
   * @param value
   *          The value to push to the front of the list.
   */
  public void push(T value) {
    if (0 == size) {
      head = new Node<T>(value);
    } else {
      Node<T> n = new Node<T>(value);
      n.next = head;
      head.previous = n;
      head = n;
    }

    size++;
  }

  /**
   * Pops a value from the front of the list. The element is removed from the list (as defined in
   * the {@code Stack} interface).
   * 
   * @return The value at the front of the list. If no values are in the list, then a {@code
   *         NoSuchElementException} is thrown.
   * @throws NoSuchElementException
   *           if the list is empty.
   */
  public T pop() {
    if (0 == size) {
      throw new NoSuchElementException("There are no values to pop.");
    }

    Node<T> n = head;
    head = n.next;
    if (null != head) {
      head.previous = null;
    }
    size--;
    return n.value;
  }

  /**
   * Takes a peek at the element at the head of the list. The element is left in the list (as
   * defined in the {@code Stack} interface). <br/>
   * <br/>
   * 
   * This is an {@code O(1)} operation.
   * 
   * @return The element at the head of the list. If no values are in the list, then a {@code
   *         NoSuchElementException} is thrown.
   * @throws NoSuchElementException
   *           if the list is empty.
   */
  public T peek() {
    if (0 == size) {
      throw new NoSuchElementException();
    }

    return head.value;
  }

  /**
   * Removes the element at index {@code i} in the list. <br/>
   * <br/>
   * 
   * Note: this method has been minimally optimized. If the index specifies the tail node, then the
   * tail will be removed. Otherwise, the entire list is searched from the head toward the tail to
   * find the correct node to remove. Since the specified location may be at {@code list.size() - 2}
   * (where {@code list.size()} is the size of the list, i.e., {@code n}), this is an {@code O(n)}
   * operation.
   * 
   * @param i
   *          The index for which the value should be removed. A zero (0) based number. It must
   *          follow this rule: {@code 0 <= i < list.size()} where {@code list.size()} is the size
   *          of the list. If {@code i} is not within those bounds, an {@code
   *          IndexOutOfBoundsException} will be thrown.
   * @return The value at index {@code i}.
   * @throws IndexOutOfBoundsException
   *           if the index is not within the prescribed limits.
   */
  public T remove(int i) throws IndexOutOfBoundsException {
    if (i >= size || 0 > i) {
      throw new IndexOutOfBoundsException("Index beyond bounds of the list");
    }

    if (0 == i) {
      Node<T> n = head;
      head = n.next;
      if (null != head) {
        head.previous = null;
      }
      size--;
      return n.value;
    }

    if (size - 1 == i) {
      Node<T> n = tail;
      tail = n.previous;
      tail.next = null;
      size--;
      return n.value;
    }

    Node<T> prev = findNode(i - 1);
    Node<T> n = prev.next;
    prev.next = n.next;
    prev.next.previous = prev;
    size--;
    return n.value;
  }

  /**
   * Find the node at position {@code index} in the list.
   * 
   * @param index
   *          The index for which a node should be found in the list. The value is zero (0) based
   *          and must be within the following limits: {@code 0 <= index < list.size()} where
   *          {@code list.size()} is the size of the list.
   * @return The node at position {@code index} in the list. Null if no nodes are in the list or
   *         index is greater than the size of the list.
   */
  private Node<T> findNode(int index) {
    if (0 == size || index > size || 0 > index) {
      return null;
    }

    Node<T> n = head;
    for (int i = 0; i < index; i++) {
      if (null == n.next) {
        return n;
      }
      n = n.next;
    }
    return n;
  }

  /**
   * A node object to hold the values at each index.
   * 
   * @param <E>
   *          The type to store in a {@code Node} instance.
   */
  private class Node<E> {
    private E value;
    private Node<E> next = null;
    private Node<E> previous = null;

    protected Node(E value) {
      this.value = value;
    }

  }

}
