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
This module implements dynamic programming algorithms for solving the knapsack problem. It contains:

  - basic memoized and bottom-up versions that return the optimized maximum value for a knapsack of a given capacity
    and a set of weights / values.
  - memoized and bottom-up objects that save optimized maximum values and solutions for the knapsack problem. The
    bottom-up version can produce a maximum value and solution for any capacity smaller than the originally provided
    capacity. The memoized version only produces a maximum value and solution for the originally specified capacity.
"""
import sys

_MINIMUM_VALUE = -sys.maxsize


def knapsack_memoized(weights, values, capacity):
    """
    Implementation of the knapsack problem using memoization.

    :param list[int] weights: The list of weights of each item that could be placed in the knapsack.
    :param list[int] values: The list of values corresponding to the weight at the same index in the weights list.
                             Values must be >= 0.
    :param int capacity: The capacity of the knapsack.
    :return: The greatest value that can be achieved.
    :rtype: int
    """
    the_memo = [[_MINIMUM_VALUE for x in range(capacity + 1)] for y in range(len(weights) + 1)]
    return _knapsack_memoized(weights, values, capacity, len(weights), the_memo)


def _knapsack_memoized(weights, values, capacity, item_index, the_memo):
    """
    The recursive function for calculating the greatest value using memoization.

    :param list[int] weights: The list of weights of each item that could be placed in the knapsack.
    :param list[int] values: The list of values corresponding to the weight at the same index in the weights list.
                             Values must be >= 0.
    :param int capacity: The capacity of the knapsack.
    :param int item_index: The index in the weights/values lists that is being calculated. A one-based number.
    :param list[list[int]] the_memo: A memo that stores the calculated total weight and value for each index in
                                     the weights/values lists.
    :return: The greatest value that can be achieved.
    :rtype: int
    """
    if item_index == 0 or capacity == 0:
        return 0
    if the_memo[item_index][capacity] >= 0:
        return the_memo[item_index][capacity]
    if weights[item_index - 1] > capacity:
        value = _knapsack_memoized(weights, values, capacity, item_index - 1, the_memo)
    else:
        value = max(
            values[item_index - 1] + _knapsack_memoized(
                weights,
                values,
                capacity - weights[item_index - 1],
                item_index - 1,
                the_memo
            ),
            _knapsack_memoized(weights, values, capacity, item_index - 1, the_memo)
        )
    the_memo[item_index][capacity] = value
    return value


def knapsack_bottom_up(weights, values, capacity):
    """
    Implementation of the knapsack problem using the bottom-up approach.

    :param list[int] weights: The list of weights of each item that could be placed in the knapsack.
    :param list[int] values: The list of values corresponding to the weight at the same index in the weights list.
                             Values must be >= 0.
    :param int capacity: The capacity of the knapsack.
    :return: The greatest value that can be achieved.
    :rtype: int
    """
    if capacity == 0:
        return 0

    table = [[0 for x in range(capacity + 1)] for y in range(len(weights) + 1)]

    for item_index in range(len(weights) + 1):
        for c in range(capacity + 1):
            if item_index == 0 or c == 0:
                table[item_index][c] = 0
            elif weights[item_index - 1] <= c:
                table[item_index][c] = max(
                    values[item_index - 1] + table[item_index - 1][c - weights[item_index - 1]],
                    table[item_index - 1][c]
                )
            else:
                table[item_index][c] = table[item_index - 1][c]
    return table[len(weights)][capacity]


class KnapsackBase(object):
    def __init__(self, weights, values, capacity):
        """
        Initialize the Knapsack implementation with the values and solutions for all sub-problems for a knapsack of size
        capacity and the supplied weight/value pairings.

        :param list[int] weights: The list of weights of each item that could be placed in the knapsack.
        :param list[int] values: The list of values corresponding to the weight at the same index in the weights list.
                                 Values must be >= 0.
        :param int capacity: The capacity of the knapsack.
        """
        self.weights = weights
        self.values = values

        # A table containing the maximum value for each [weight_index][capacity] that is evaluated.
        self.maximum_values = None

        # A table containing information to track back to the elements that compose the maximum value. Each element
        # is an int in the set:
        #  -1 => was not computed
        #   0 => edge (no more back tracking needed)
        #   1 => go to the next smaller index in the weights array/list
        #   2 => the weight/value for this index is in the solution
        self.solutions = None

        self._generate_solution(capacity)

    def _generate_solution(self, capacity):
        """
        Calculate the values and solutions for all sub-problems to the knapsack 0-1 problem using the approach of the
        implementing sub-class.

        :param int capacity: The capacity of the knapsack.
        """
        raise NotImplemented

    def greatest_knapsack_capacity(self):
        """
        :return: The greatest capacity for which values and solutions have been calculated.
        :rtype: int
        """
        return len(self.solutions[0]) - 1

    def value(self, capacity):
        """
        :param int capacity: The capacity for which to get the value.
        :return: The maximum value achievable for the supplied capacity.
        :rtype: int
        """
        self._check_valid_capacity(capacity)
        return self.maximum_values[-1][capacity]

    def solution(self, capacity):
        """
        :param capacity: The capacity for which to get a solution.
        :return: A solution for how to maximize the value at the specified capacity.
        :rtype: list[(int, int)]
        """
        self._check_valid_capacity(capacity)

        item_index = len(self.solutions) - 1
        while self.weights[item_index - 1] > capacity:
            item_index -= 1

        solution = []
        while capacity > 0 and item_index > 0:
            if 0 == self.solutions[item_index][capacity]:
                break
            if 1 == self.solutions[item_index][capacity]:
                item_index -= 1
            elif 2 == self.solutions[item_index][capacity]:
                solution.append((self.weights[item_index - 1], self.values[item_index - 1]))
                item_index -= 1
                capacity -= self.weights[item_index]
        return solution

    def _check_valid_capacity(self, capacity):
        raise NotImplemented

    def __str__(self):
        return '\n'.join((
            ' '.join(('values:', repr(self.maximum_values))),
            ' '.join(('solutions:', repr(self.solutions))),
        ))


class KnapsackBottomUp(KnapsackBase):
    """
    Knapsack implementation using the bottom-up approach.
    """
    def _generate_solution(self, capacity):
        if capacity == 0:
            self.maximum_values = []
            self.solutions = []
            return

        self.maximum_values = [[0 for x in range(capacity + 1)] for y in range(len(self.weights) + 1)]
        self.solutions = [[-1 for x in range(capacity + 1)] for y in range(len(self.weights) + 1)]

        for item_index in range(len(self.weights) + 1):
            for c in range(capacity + 1):
                if item_index == 0 or c == 0:
                    self.maximum_values[item_index][c] = 0
                    self.solutions[item_index][c] = 0
                elif self.weights[item_index - 1] <= c:
                    jump_value = self.values[item_index - 1] + \
                                 self.maximum_values[item_index - 1][c - self.weights[item_index - 1]]
                    no_change_value = self.maximum_values[item_index - 1][c]
                    if no_change_value > jump_value:
                        self.maximum_values[item_index][c] = no_change_value
                        self.solutions[item_index][c] = 1
                    else:
                        self.maximum_values[item_index][c] = jump_value
                        self.solutions[item_index][c] = 2
                else:
                    self.maximum_values[item_index][c] = self.maximum_values[item_index - 1][c]
                    self.solutions[item_index][c] = 1

    def _check_valid_capacity(self, capacity):
        if capacity < 0 or capacity > len(self.solutions[0]):
            raise ValueError


class KnapsackMemoized(KnapsackBase):
    """
    Knapsack implementation using memoization.
    """
    def _generate_solution(self, capacity):
        self.maximum_values = [[_MINIMUM_VALUE for x in range(capacity + 1)] for y in range(len(self.weights) + 1)]
        self.solutions = [[-1 for x in range(capacity + 1)] for y in range(len(self.weights) + 1)]
        self._generate_solution_recurse(capacity, len(self.weights))

    def _generate_solution_recurse(self, capacity, item_index):
        if item_index == 0 or capacity == 0:
            self.maximum_values[item_index][capacity] = 0
            self.solutions[item_index][capacity] = 0
            return 0
        if self.maximum_values[item_index][capacity] >= 0:
            value = self.maximum_values[item_index][capacity]
            return value
        if self.weights[item_index - 1] > capacity:
            value = self._generate_solution_recurse(capacity, item_index - 1)
            solution = 1
        else:
            jump_value = self.values[item_index - 1] + self._generate_solution_recurse(
                capacity - self.weights[item_index - 1],
                item_index - 1
            )
            no_change_value = self._generate_solution_recurse(capacity, item_index - 1)
            if no_change_value > jump_value:
                value = no_change_value
                solution = 1
            else:
                value = jump_value
                solution = 2
        self.maximum_values[item_index][capacity] = value
        self.solutions[item_index][capacity] = solution
        return value

    def _check_valid_capacity(self, capacity):
        if capacity != len(self.solutions[0]) - 1:
            raise ValueError
