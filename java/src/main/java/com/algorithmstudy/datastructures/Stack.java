package com.algorithmstudy.datastructures;

import java.util.NoSuchElementException;

/**
 * A simple stack interface.
 * 
 * @param <T>
 *          The type to store in the {@code Stack}.
 */
public interface Stack<T> {

  /**
   * Push an element onto the stack.
   * 
   * @param element
   *          The element to push onto the stack.
   */
  public void push(T element);

  /**
   * Pop an element off the stack. The element that is popped off the stack is removed from the
   * stack.
   * 
   * @return The element popped off the stack.
   * @throws NoSuchElementException
   *           Thrown if the stack is empty.
   */
  public T pop() throws NoSuchElementException;

  /**
   * Takes a peek at the element at the top of the stack. The element is returned by the method but
   * not removed from the stack.
   * 
   * @return The element at the top of the stack.
   * @throws NoSuchElementException
   *           Thrown if the stack is empty.
   */
  public T peek() throws NoSuchElementException;

  /**
   * Returns the number of elements in the stack.
   */
  public int size();

}
