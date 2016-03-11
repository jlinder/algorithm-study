# Copyright (c) 2016, The Algorithm Study Project
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without modification,
# are permitted provided that the following conditions are met:
#
#     * Redistributions of source code must retain the above copyright notice,
#       this list of conditions and the following disclaimer.
#     * Redistributions in binary form must reproduce the above copyright notice,
#       this list of conditions and the following disclaimer in the documentation
#       and/or other materials provided with the distribution.
#     * Neither the name of the Algorithm Study Project, algorithmstudy.com, nor
#       the names of its contributors may be used to endorse or promote products
#       derived from this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
# ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
# WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
# DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
# ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
# (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
# LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
# ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
# (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
# SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
"""
This module implements dynamic programming algorithms for solving the fibonacci sequence. It contains:

  - basic memoized and bottom-up versions that return the value for a given index in the sequence
  - memoized and bottom-up objects that save the values at each step of the sequence and allow for retrieval of the
    value or solution at each index < the maximum index to which it was calculated.
"""
import sys

_MINIMUM_VALUE = -sys.maxsize


def fibonacci_memoized(index):
    """
    Implementation of the fibonacci sequence using memoization. The assumed values of the first two indices are 0 and 1.

    :param int index: The index in the sequence for which the value is desired. A zero-based number.
    :return: The value at position index in the sequence.
    :rtype: int
    """
    the_memo = [_MINIMUM_VALUE for x in range(index + 1)]
    return _fibonacci_memoized(index, the_memo)


def _fibonacci_memoized(index, the_memo):
    """
    The recursive function for calculating the value in the fibonacci sequence at "index" using memoization.

    :param int index: The index in the sequence for which the value is desired. A zero-based number.
    :param list the_memo: A memo where calculated values are stored for each index in the sequence.
    :return: The value at the specified index in the sequence.
    :rtype: int
    """
    if the_memo[index] >= 0:
        return the_memo[index]
    value = 0 if index == 0  else 1
    if index > 1:
        value = _fibonacci_memoized(index - 2, the_memo) + _fibonacci_memoized(index - 1, the_memo)
    the_memo[index] = value
    return value


def fibonacci_bottom_up(index):
    """
    Implementation of the fibonacci sequence using the bottom-up approach.

    :param int index: The index in the sequence for which the value is desired. A zero-based number.
    :return: The value at the specified index in the sequence.
    :rtype: int
    """
    if index == 0:
        return 0
    if index == 1:
        return 1
    results = [0, 1]
    for x in range(2, index + 1):
        value = results[x - 2] + results[x - 1]
        results.append(value)
    return results[index]


class FibonacciBase(object):
    def __init__(self, maximum_index):
        """
        Initialize the Fibonacci implementation with the values and solutions for all sub-problems of the sequence.

        :param maximum_index: The index in the sequence to which to calculate. A zero-based number. Must be >= 2.
        """
        self.values = None
        self._generate_solution(maximum_index)

    def _generate_solution(self, maximum_index):
        """
        Calculate the values and solutions for all sub-problems to the fibonacci sequence up to the specified
        maximum_index using the approach of the implementing sub-class.

        :param maximum_index: The index in the sequence to which to calculate. A zero-based number. Must be >= 2.
        """
        raise NotImplemented

    def maximum_index(self):
        """
        :return: The maximum index for which solutions have been calculated.
        :rtype: int
        """
        return len(self.values) - 1

    def value(self, index):
        """
        :param int index: The index for which to get a value. The base case indices of 0 and 1 are not valid.
        :return: The value at the specified index in the sequence.
        :rtype: int
        """
        self._check_valid_index(index)
        return self.values[index]

    def solution(self, index):
        """
        :param index: The index for which to get a solution. The base case indices of 0 and 1 are not valid.
        :return: A solution for how to arrive at the value for the specified index.
        :rtype: list[int]
        """
        self._check_valid_index(index)
        solution = [self.values[index - 2], self.values[index - 1]]
        return solution

    def _check_valid_index(self, index):
        if index < 2 or index > len(self.values):
            raise ValueError

    def __str__(self):
        return ' '.join(('values:', repr(self.values)))


class FibonacciBottomUp(FibonacciBase):
    """
    Fibonacci implementation using the bottom-up approach.
    """
    def _generate_solution(self, index):
        if index < 2:
            raise ValueError
        self.values = [0, 1]
        for x in range(2, index + 1):
            value = self.values[x - 2] + self.values[x - 1]
            self.values.append(value)


class FibonacciMemoized(FibonacciBase):
    """
    Fibonacci implementation using memoization.
    """
    def _generate_solution(self, index):
        if index < 2:
            raise ValueError
        self.values = [0, 1]
        self.values.extend([_MINIMUM_VALUE for x in range(2, index + 1)])
        self._generate_solution_recurse(index)

    def _generate_solution_recurse(self, index):
        if self.values[index] >= 0:
            return self.values[index]
        value = self._generate_solution_recurse(index - 2) + self._generate_solution_recurse(index - 1)
        self.values[index] = value
        return value
