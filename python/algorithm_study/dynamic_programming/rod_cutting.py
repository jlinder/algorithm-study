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
This module implements dynamic programming algorithms for solving the rod cutting problem. It contains:

  - basic memoized and bottom-up versions that return the optimized maximum value for a given rod length
    and set of prices
  - memoized and bottom-up objects that save optimized maximum values and solutions for rod lengths < the
    specified rod length
"""
import sys

_MINIMUM_VALUE = -sys.maxsize


def cut_rod_memoized(prices, length):
    """
    Implementation of the rod cutting problem using memoization.

    :param list prices: The list of prices for a rod of length x foreach x in prices.
    :param int length: The length of the rod in whole numbers.
    :return: The greatest value that can be achieved.
    :rtype: int
    """
    the_memo = [_MINIMUM_VALUE for x in range(length + 1)]
    return _cut_rod_memoized(prices, length, the_memo)


def _cut_rod_memoized(prices, length, the_memo):
    """
    The recursive function for calculating the greatest value using memoization.

    :param list prices: The list of prices.
    :param int length: The length of the rod.
    :param list the_memo: A memo where calculated values are stored for each rod length.
    :return: the greatest value possible for the rod of length length.
    :rtype: int
    """
    if the_memo[length] >= 0:
        return the_memo[length]
    value = 0
    if length > 0:
        value = _MINIMUM_VALUE
        for x in range(1, length + 1):
            value = max(value, prices[x] + _cut_rod_memoized(prices, length - x, the_memo))
    the_memo[length] = value
    return value


def cut_rod_bottom_up(prices, length):
    """
    Implementation of the rod cutting problem using the bottom-up approach.

    :param prices: The list of prices.
    :param length: The length of the rod.
    :return: The greatest achievable value for the rod of length length.
    :rtype: int
    """
    if length == 0:
        return 0
    results = [0]
    for x in range(1, length + 1):
        value = _MINIMUM_VALUE
        for i in range(1, x + 1):
            value = max(value, prices[i] + results[x - i])
        results.append(value)
    return results[length]


class CutRodBase(object):
    def __init__(self, prices, length):
        """
        Initialize the CutRod implementation with the values and solutions for all sub-problems for a rod of length
        length.

        :param prices: The list of prices.
        :param length: The length of the rod.
        """
        self.values = None
        self.solutions = None
        self._generate_solution(prices, length)

    def _generate_solution(self, prices, length):
        """
        Calculate the values and solutions for all sub-problems to the rod cutting problem for a rod of length length
        using the approach of the implementing sub-class.

        :param prices: The list of prices.
        :param length: The length of the rod.
        """
        raise NotImplemented

    def maximum_rod_length(self):
        """
        :return: The maximum rod length for which prices are known
        :rtype: int
        """
        return len(self.solutions) - 1

    def value(self, length):
        """
        :param int length: The length for which to get a value.
        :return: The value that can be had from a rod of length length.
        :rtype: int
        """
        self._check_valid_length(length)
        return self.values[length]

    def solution(self, length):
        """
        :param length: The length of rod for which to get a solution.
        :return: A solution for how to maximize the value of a rod of length length.
        :rtype: list[int]
        """
        self._check_valid_length(length)
        solution = []
        while length > 0:
            solution.append(self.solutions[length])
            length = length - self.solutions[length]
        return solution

    def _check_valid_length(self, length):
        if length < 0 or length > len(self.solutions):
            raise ValueError

    def __str__(self):
        return '\n'.join((
            ' '.join(('values:', repr(self.values))),
            ' '.join(('solutions:', repr(self.solutions))),
        ))


class CutRodBottomUp(CutRodBase):
    """
    CutRod implementation using the bottom-up approach.
    """
    def _generate_solution(self, prices, length):
        self.values = [0]
        self.solutions = [0]
        for x in range(1, length + 1):
            value = _MINIMUM_VALUE
            solution = 0
            for i in range(1, x + 1):
                if value < prices[i] + self.values[x - i]:
                    value = prices[i] + self.values[x - i]
                    solution = i
            self.values.append(value)
            self.solutions.append(solution)


class CutRodMemoized(CutRodBase):
    """
    CutRod implementation using memoization.
    """
    def _generate_solution(self, prices, length):
        self.values = [_MINIMUM_VALUE for x in range(length + 1)]
        self.solutions = [_MINIMUM_VALUE for x in range(length + 1)]
        self._generate_solution_recurse(prices, length)

    def _generate_solution_recurse(self, prices, length):
        if self.values[length] >= 0:
            return self.values[length]
        value = 0
        solution = 0
        if length > 0:
            value = _MINIMUM_VALUE
            solution = _MINIMUM_VALUE
            for index in range(1, length + 1):
                possible_max_value = prices[index] + self._generate_solution_recurse(prices, length - index)
                if value < possible_max_value:
                    value = possible_max_value
                    solution = index
        self.values[length] = value
        self.solutions[length] = solution
        return value
