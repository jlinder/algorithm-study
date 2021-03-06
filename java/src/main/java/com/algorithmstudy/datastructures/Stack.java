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
   *           if the stack is empty.
   */
  public T pop() throws NoSuchElementException;

  /**
   * Takes a peek at the element at the top of the stack. The element is returned by the method but
   * not removed from the stack.
   * 
   * @return The element at the top of the stack.
   * @throws NoSuchElementException
   *           if the stack is empty.
   */
  public T peek() throws NoSuchElementException;

  /**
   * Returns the number of elements in the stack.
   */
  public int size();

}
