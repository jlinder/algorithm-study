/*
 * Copyright (C) 2009 The Algorithm Study Project
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2.0 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version
 * 2.0 along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 * or find it on the GNU website at:
 *
 *    http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
package com.algorithmstudy.visualizer.client.model;

/**
 * The base interface from which all algorithm implementations implement.
 */
public interface Algorithm {

  /**
   * Instruct the algorithm to make its next move.
   */
  public void makeMove();

  /**
   * Indicates if the algorithm can make another move.
   * 
   * @return True indicates the algorithm can make another move. False indicates the algorithm has
   *         no moves left.
   */
  public boolean canMakeMove();

}
