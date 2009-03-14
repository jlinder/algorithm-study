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

import junit.framework.TestCase;

import org.junit.Test;

import com.algorithmstudy.strings.ReverseWordOrder;

public class TestReverseWordOrder extends TestCase {

  @Test
  public void testReverseInPlaceString() {
    String r = ReverseWordOrder.reverseWordOrderInPlace("");
    assertEquals("", r);

    r = ReverseWordOrder.reverseWordOrderInPlace("b");
    assertEquals("b", r);

    r = ReverseWordOrder.reverseWordOrderInPlace("single");
    assertEquals("single", r);

    r = ReverseWordOrder.reverseWordOrderInPlace("one word, two word");
    assertEquals("word two word, one", r);

    r = ReverseWordOrder.reverseWordOrderInPlace("one word, two   word");
    assertEquals("word   two word, one", r);
  }

  @Test
  public void testReverseInPlaceCharArray() {
    char[] c = new char[0];
    ReverseWordOrder.reverseWordOrderInPlace(c);
    assertEquals("", new String(c));

    c = "b".toCharArray();
    ReverseWordOrder.reverseWordOrderInPlace(c);
    assertEquals("b", new String(c));

    c = "single".toCharArray();
    ReverseWordOrder.reverseWordOrderInPlace(c);
    assertEquals("single", new String(c));

    c = "one word, two word".toCharArray();
    ReverseWordOrder.reverseWordOrderInPlace(c);
    assertEquals("word two word, one", new String(c));

    c = "one word, two   word".toCharArray();
    ReverseWordOrder.reverseWordOrderInPlace(c);
    assertEquals("word   two word, one", new String(c));
  }

  @Test
  public void testReverseInBuffer() {
    String r = ReverseWordOrder.reverseWordOrderInBuffer("");
    assertEquals("", r);

    r = ReverseWordOrder.reverseWordOrderInBuffer("b");
    assertEquals("b", r);

    r = ReverseWordOrder.reverseWordOrderInBuffer("single");
    assertEquals("single", r);

    r = ReverseWordOrder.reverseWordOrderInBuffer("one word, two word");
    assertEquals("word two word, one", r);

    r = ReverseWordOrder.reverseWordOrderInBuffer("one word, two   word");
    assertEquals("word   two word, one", r);
  }

}
