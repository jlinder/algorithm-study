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

import java.util.EmptyStackException;

/**
 * An implementation of a linked list with links in one direction. A singly linked list is
 * particularly useful when implementing a stack or the elements that are commonly being accessed or
 * inserted are near the front of the list.
 * 
 * Some notes:
 * <ul>
 * <li>This list implementation is not thread safe</li>
 * <li>A reference to the head element is maintained but not a reference to the tail element; this
 * means some operations must run in {@code O(n)} time instead of {@code O(1)} time</li>
 * </ul>
 * 
 * @param <T>
 *          The type to store in a {@code SinglyLinkedList} instance.
 */
public class SinglyLinkedList<T> {

  private Node<T> head;
  private int size;

  /**
   * Construct a {@code SinglyLinkedList} instance.
   */
  public SinglyLinkedList() {
    head = null;
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
   * Note: a reference to the tail of the list is not maintained (only a reference to the head is
   * maintained), this method must search to the end of the list to find where to insert the new
   * value. Thus, this is an {@code O(n)} operation.
   * 
   * @param value
   *          The value to be appended to the end of the list.
   */
  public void append(T value) {
    Node<T> n = new Node<T>(value);
    Node<T> last = findNode(size - 1);
    if (null == last) {
      head = n;
    } else {
      last.next = n;
    }
    size++;
  }

  /**
   * Get the value at index {@code i} in the list. <br/>
   * <br/>
   * 
   * Note: since only a reference to the head of the list is maintained, the entire list up to the
   * specified location must be traversed before finding the specified node. Since the specified
   * location may be at the end of the list, this is an {@code O(n)} operation.
   * 
   * @param i
   *          The index at which the desired value resides. A zero (0) based number. It must follow
   *          this rule: {@code 0 <= i < list.size()} where {@code list.size()} is the size of the
   *          linked list; if {@code i} is not within those bounds, an {@code
   *          IndexOutOfBoundsException} will be thrown.
   * @return The value in index {@code i} in the list.
   * @throws IndexOutOfBoundsException
   *           Thrown if the index is not within the prescribed limits.
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
   * Note: the node at the specified location must be found before the new value can be inserted.
   * Since the specified location may be at the end of the list, this is an {@code O(n)} operation.
   * 
   * @param value
   *          The value to insert.
   * @param i
   *          The index in the list where the value should be inserted. A zero (0) based number. It
   *          must fall in the following range: {@code 0 <= i <= ListSize} where {@code ListSize} is
   *          the number of elements in the list.
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
      head = n;
      size++;
      return;
    }

    Node<T> prev = findNode(i - 1);

    n.next = prev.next;
    prev.next = n;
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
      head = n;
    }
    size++;
  }

  /**
   * Pops a value from the front of the list. The element is removed from the list.
   * 
   * @return The value at the front of the list. If no values are in the list, then an {@code
   *         EmptyStackException} is thrown.
   * @throws EmptyStackException
   *           Thrown if the list is empty.
   */
  public T pop() {
    if (0 == size) {
      throw new EmptyStackException();
    }

    Node<T> n = head;
    head = n.next;
    size--;
    return n.value;
  }

  /**
   * Removes the element at index {@code i} in the list. <br/>
   * <br/>
   * 
   * Note: If the element being removed is the last element in the list, all {@code n} elements in
   * the list will be traversed. If the element is at any other location in the list, fewer than
   * {@code n} elements will be traversed. Thus, this is an {@code O(n)} operation.
   * 
   * @param i
   *          The index for which the value should be removed. A zero (0) based number. It must
   *          follow the following rule: {@code 0 <= i < list.size()} where {@code list.size()} is
   *          the size of the linked list; if {@code i} is not within those bounds, an {@code
   *          IndexOutOfBoundsException} will be thrown.
   * @return The value at index {@code i}.
   * @throws IndexOutOfBoundsException
   *           Thrown if the index is not within the prescribed limits.
   */
  public T remove(int i) throws IndexOutOfBoundsException {
    if (i >= size || 0 > i) {
      throw new IndexOutOfBoundsException("Index beyond bounds of the list");
    }

    if (0 == i) {
      Node<T> n = head;
      head = n.next;
      size--;
      return n.value;
    }

    Node<T> prev = findNode(i - 1);
    Node<T> n = prev.next;
    prev.next = n.next;
    size--;
    return n.value;
  }

  /**
   * Find the node at position {@code index} in the list.
   * 
   * @param index
   *          The index for which a node should be found in the list. The value is zero (0) based
   *          and must be within the following limits: {@code 0 <= index < SizeOfList} where {@code
   *          SizeOfList} is the number of elements in the list.
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
    private Node<E> next;

    protected Node(E value) {
      this.value = value;
      next = null;
    }

  }

}
