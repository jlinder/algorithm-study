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
package com.algorithmstudy.strings;

/**
 * Reverse the order of the words in a string. So, the first word becomes the last word and the last
 * word becomes the first word; the second word becomes the second-to-last word and the
 * second-to-last word becomes the second word; etc.
 */
public class ReverseWordOrder {

  /**
   * This method reverses the order of the words in a string in place in a {@code char[]}. A new
   * {@code char[]} is created from the source string and is then the reversal is done in the
   * {@code char[]}. A new {@code String} is then created from the {@code char[]} after reversal.
   * This algorithm takes {@code O(n)} time and {@code O(n)} space to complete.
   * 
   * @param s
   *          The {@code String} for which the order of its words is to be reversed.
   * @return The reversed {@code String}. If {@code s} is null, then null is returned.
   */
  public static String reverseWordOrderInPlace(String s) {
    if (null == s) {
      return null;
    }

    char[] c = s.toCharArray();

    reverseWordOrderInPlace(c);

    return new String(c);
  }

  /**
   * This method reverses the order of the words in a {@code char[]} in place. This algorithm takes
   * {@code O(n)} time and {@code O(n)} space to complete.
   * 
   * @param c
   *          The {@code char[]} for which to reverse the order of its words.
   */
  public static void reverseWordOrderInPlace(char[] c) {
    String s;
    reverseInPlace(c, 0, c.length);

    s = new String(c);

    int l = 0;
    int space = s.indexOf(' ');
    while (space >= 0) {
      reverseInPlace(c, l, space);
      l = space + 1;
      space = s.indexOf(' ', space + 1);
    }

    reverseInPlace(c, l, s.length());
  }

  private static void reverseInPlace(char[] c, int l, int r) {
    int diff = r - l;
    for (int i = 0; i < diff / 2; i++) {
      char tmp = c[l + i];
      c[l + i] = c[r - i - 1];
      c[r - i - 1] = tmp;
    }
  }

  /**
   * This method reverses the order of the words in the string by copying the words in the string
   * into a buffer in the reverse order and then create a new string from the contents of the
   * buffer. This algorithm takes {@code O(n)} time and {@code O(n)} space to complete.
   * 
   * @param s
   *          The {@code String} for which the order of its words is to be reversed.
   * @return The reversed {@code String}. If {@code s} is null, then null is returned.
   */
  public static String reverseWordOrderInBuffer(String s) {
    if (null == s) {
      return null;
    }

    StringBuilder sb = new StringBuilder(s.length());

    int space = s.lastIndexOf(' ');
    int r = s.length();
    while (0 <= space) {
      sb.append(s.substring(space + 1, r)).append(" ");
      r = space;
      space = s.lastIndexOf(' ', r - 1);
    }

    sb.append(s.substring(0, r));

    return sb.toString();
  }

}
