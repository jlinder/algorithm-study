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


/**
 * {@code MaxBinaryHeap} implements a binary heap that tracks the maximum value in the heap at the
 * root of the heap's tree. This implementation is based on the maximum binary heap described in
 * chapter 6 of "Introduction to Algorithms, Second Edition," by Cormen et. al.
 */
public class MaxBinaryHeap {

  private static int DEFAULT_ARRAY_SIZE = 128;

  // The array containing the heap data
  private int[] heap;

  // The size of the heap
  private int heapSize = 0;

  /**
   * Construct a new {@code MaxBinaryHeap} using the default array size for the array backing the
   * heap.
   */
  public MaxBinaryHeap() {
    heap = new int[DEFAULT_ARRAY_SIZE];
  }

  /**
   * Construct a new {@code MaxBinaryHeap} backed by an array that starts at the specified size.
   * 
   * @param size
   *          The starting size of the array backing the heap.
   */
  public MaxBinaryHeap(int size) {
    if (size < 10) {
      size = 10;
    }

    heap = new int[size];
  }

  /**
   * Create a new {@code MaxBinaryHeap} instance containing the data in the provided {@code int[]}.
   * 
   * @param data
   *          A {@code int[]} containing the data to use as the starting set of data for this
   *          instance. All elements in this array are inserted into the heap. I.e., elements
   *          {@code data[0]} through {@code data[data.length-1]} are inserted into the heap.
   */
  public MaxBinaryHeap(int[] data) {
    if (null == data) {
      heap = new int[DEFAULT_ARRAY_SIZE];
      return;
    }

    int startSize = DEFAULT_ARRAY_SIZE;

    if (startSize < data.length) {
      startSize = data.length + (data.length / 2);
    }

    heap = new int[startSize];

    for (int i = 0; i < data.length; i++) {
      heap[i] = data[i];
    }

    heapSize = data.length;
    buildMaxHeap();
  }

  /**
   * Gets the size of the heap.
   * 
   * @return The size of the heap.
   */
  public int size() {
    return heapSize;
  }

  /**
   * Removes the maximum element from the heap. <br>
   * <br>
   * The {@code removeMax()} method takes {@code O(h)} time to complete where {@code h} is the
   * height of the heap (note, {@code h = n lg n} where {@code n} is the number of elements in the
   * heap). It takes {@code O(h)} time to complete because {@code maxHeapify()} must be called on
   * the root element in the heap once the maximum element is removed from the heap.
   * 
   * @return The maximum element in the heap.
   * @throws HeapUnderflowException
   *           Thrown if the number of elements in the heap is zero (i.e. the size of the heap is
   *           0).
   */
  public int removeMax() throws HeapUnderflowException {
    if (0 == heapSize) {
      throw new HeapUnderflowException("Heap empty.  Nothing to return");
    }

    int ret = heap[0];

    heap[0] = heap[heapSize - 1];
    --heapSize;
    maxHeapify(0);

    return ret;
  }

  /**
   * Returns the maximum value in the heap without removing it. The {@code max()} method takes
   * {@code O(1)} time to complete as it simply returns the element at the root of the heap.
   * 
   * @return The maximum value in the heap.
   * @throws HeapUnderflowException
   *           Thrown if the number of elements in the heap is zero (i.e. the size of the heap is
   *           0).
   */
  public int max() throws HeapUnderflowException {
    if (0 == heapSize) {
      throw new HeapUnderflowException("Heap empty.  Nothing to return");
    }

    return heap[0];
  }

  /**
   * Inserts a new value into the heap. The same value can be inserted into the heap multiple times
   * (i.e. {@code insert()} does not check if the heap already contains the value being inserted).   
   * <br>
   * <br>
   * If there is space in the heap for the new value, the run time of this method is
   * {@code O(lg(n))} as at most it will travel {@code h + 1} levels up the tree to insert the new
   * value. If the tree needs to be grown, the run time is {@code O(n)} because, to grow the size of
   * the array, a new array is created and the n values in the heap are copied to the new array.
   * 
   * @param toInsert
   *          The value to insert.
   */
  public void insert(int toInsert) {
    grow();

    int newLoc = heapSize;
    heap[newLoc] = toInsert;

    heapSize++;

    int p = parent(newLoc);
    while (0 != newLoc && heap[p] < heap[newLoc]) {
      exchange(p, newLoc);
      newLoc = p;
      p = parent(p);
    }
  }

  /**
   * Builds a max heap in the {@code int[]} backing this heap instance. <br>
   * <br>
   * A loose upper bound on the run time of this method is {@code O(n lg(n))} because the max run
   * time of {@code maxHeapify()} is {@code O(lg(n))} and {@code maxHeapify()} is called {@code n}
   * times. However, a tighter upper bound is {@code O(n)} (see "Introduction to Algorithms, Second
   * Edition" chapter 6, section 3).
   */
  private void buildMaxHeap() {
    for (int i = heapSize / 2; i >= 0; i--) {
      maxHeapify(i);
    }
  }

  /**
   * Creates a max heap at the specified index in the {@code int[]} backing this instance. <br>
   * <br>
   * The {@code maxHeapify()} method takes {@code O(h)} time to complete where {@code h} is the
   * height of the tree rooted at {@code i} (note, {@code h = n lg n} where {@code n} is the number
   * of elements in the tree rooted at {@code i}). It takes {@code O(h)} time to complete because
   * it recurses at a maximum down to its lowest leaf which is at a depth of {@code h}.
   * 
   * @param i
   *          The index in the {@code int[]} backing this instance for which to ensure it is a max
   *          heap.
   */
  private void maxHeapify(int i) {
    int l = left(i);
    int r = right(i);
    int largest = i;

    if (l < heapSize && heap[l] > heap[i]) {
      if (r < heapSize && heap[r] > heap[l]) {
        largest = r;
      } else {
        largest = l;
      }
    } else if (r < heapSize && heap[r] > heap[i]) {
      largest = r;
    }

    if (largest != i) {
      exchange(i, largest);
      maxHeapify(largest);
    }
  }

  /**
   * Exchange the elements at indices {@code i1} and {@code i2} in the {@code heap}.
   * 
   * @param i1
   *          One index which is to be switched.
   * @param i2
   *          The second index which is to be switched.
   */
  private void exchange(int i1, int i2) {
    int tmp = heap[i1];
    heap[i1] = heap[i2];
    heap[i2] = tmp;
  }

  /**
   * Grows the heap if there is no space left in the heap.
   */
  private void grow() {
    if (heap.length == heapSize) {
      int[] newHeap = new int[heap.length + (heap.length / 2)];
      for (int i = 0; i < heapSize; i++) {
        newHeap[i] = heap[i];
      }
      heap = newHeap;
    }
  }

  /**
   * The index of the left child of the element at index {@code i} in the backing {@code int[]}.
   * 
   * @param i
   *          The index of the element for which to get the left child's index.
   * @return The index of the left child of element {@code i} in the backing {@code int[]}. The
   *         returned value may be greater than the current size of the heap or the backing
   *         {@code int[]}.
   */
  private int left(int i) {
    return i * 2;
  }

  /**
   * The index of the right child of the element at index {@code i} in the backing {@code int[]}.
   * 
   * @param i
   *          The index of the element for which to get the right child's index.
   * @return The index of the right child of element {@code i} in the backing {@code int[]}. The
   *         returned value may be greater than the current size of the heap or the backing
   *         {@code int[]}.
   */
  private int right(int i) {
    return (i * 2) + 1;
  }

  /**
   * The index of the parent of the element at index {@code i} in the backing {@code int[]}.
   * 
   * @param i
   *          The index of the element for which to get the parent's index.
   * @return The index of the parent of element {@code i} in the backing {@code int[]}. If
   *         {@code i} is zero (the root element), zero will be returned.
   */
  private int parent(int i) {
    return i / 2;
  }

}
