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
import unittest

from algorithm_study.dynamic_programming import knapsack_0_1


class KnapsackTest(unittest.TestCase):
    def setUp(self):
        self.weights = [1, 2, 4, 4, 10, 11, 17, 18, 18, 29, 41, 53]
        self.values = [3, 6, 3, 5, 2, 8, 21, 12, 15, 34, 32, 62]
        self.capacities = [5, 7, 16, 21, 28, 31, 37, 43, 53, 65, 79, 81]
        self.expected_values = [9, 14, 17, 30, 38, 40, 48, 51, 69, 79, 97, 100]
        self.expected_solutions = [
            [(2, 6), (1, 3)],
            [(4, 5), (2, 6), (1, 3)],
            [(11, 8), (2, 6), (1, 3)],
            [(17, 21), (2, 6), (1, 3)],
            [(17, 21), (4, 5), (4, 3), (2, 6), (1, 3)],
            [(29, 34), (2, 6)],
            [(29, 34), (4, 5), (2, 6), (1, 3)],
            [(29, 34), (11, 8), (2, 6), (1, 3)],
            [(29, 34), (17, 21), (4, 5), (2, 6), (1, 3)],
            [(53, 62), (4, 5), (4, 3), (2, 6), (1, 3)],
            [(53, 62), (17, 21), (4, 5), (2, 6), (1, 3)],
            [(53, 62), (17, 21), (4, 5), (4, 3), (2, 6), (1, 3)],
        ]

        self.test_data_groupings = [
            {
                "weights": [1, 2, 4, 4],
                "values": [3, 6, 3, 5],
                "capacities": [5, 7],
                "expected_values": [9, 14],
                "expected_solutions": [
                    [(2, 6), (1, 3)],
                    [(4, 5), (2, 6), (1, 3)],
                ],
            },
            {
                "weights": [1, 2, 4, 4, 10, 11],
                "values": [3, 6, 3, 5, 2, 8],
                "capacities": [16],
                "expected_values": [17],
                "expected_solutions": [[(11, 8), (2, 6), (1, 3)]],
            },
            {
                "weights": [1, 2, 4, 4, 10, 11, 17, 18, 18],
                "values": [3, 6, 3, 5, 2, 8, 21, 12, 15],
                "capacities": [21, 28],
                "expected_values": [30, 38],
                "expected_solutions": [
                    [(17, 21), (2, 6), (1, 3)],
                    [(17, 21), (4, 5), (4, 3), (2, 6), (1, 3)],
                ],
            },
            {
                "weights": [1, 2, 4, 4, 10, 11, 17, 18, 18, 29],
                "values": [3, 6, 3, 5, 2, 8, 21, 12, 15, 34],
                "capacities": [31, 37],
                "expected_values": [40, 48],
                "expected_solutions": [
                    [(29, 34), (2, 6)],
                    [(29, 34), (4, 5), (2, 6), (1, 3)],
                ],
            },
            {
                "weights": [1, 2, 4, 4, 10, 11, 17, 18, 18, 29, 41],
                "values": [3, 6, 3, 5, 2, 8, 21, 12, 15, 34, 32],
                "capacities": [43],
                "expected_values": [51],
                "expected_solutions": [[(29, 34), (11, 8), (2, 6), (1, 3)]],
            },
            {
                "weights": [1, 2, 4, 4, 10, 11, 17, 18, 18, 29, 41, 53],
                "values": [3, 6, 3, 5, 2, 8, 21, 12, 15, 34, 32, 62],
                "capacities": [53, 65, 79, 81],
                "expected_values": [69, 79, 97, 100],
                "expected_solutions": [
                    [(29, 34), (17, 21), (4, 5), (2, 6), (1, 3)],
                    [(53, 62), (4, 5), (4, 3), (2, 6), (1, 3)],
                    [(53, 62), (17, 21), (4, 5), (2, 6), (1, 3)],
                    [(53, 62), (17, 21), (4, 5), (4, 3), (2, 6), (1, 3)],
                ],
            },
        ]

    def test_knapsack_memoized(self):
        for x in range(len(self.expected_values)):
            self.assertEqual(
                self.expected_values[x],
                knapsack_0_1.knapsack_memoized(self.weights, self.values, self.capacities[x])
            )

    def test_knapsack_bottom_up(self):
        for x in range(len(self.expected_values)):
            self.assertEqual(
                self.expected_values[x],
                knapsack_0_1.knapsack_bottom_up(self.weights, self.values, self.capacities[x])
            )

    def _knapsack_checker(self, knapsack_class):
        for test_data in self.test_data_groupings:
            for capacity, expected_value, expected_solution in zip(
                test_data['capacities'],
                test_data['expected_values'],
                test_data['expected_solutions']
            ):
                knapsack = knapsack_class(test_data['weights'], test_data['values'], capacity)
                self.assertEqual(expected_value, knapsack.value(capacity))
                self.assertEqual(expected_solution, knapsack.solution(capacity))

                self.assertEqual(capacity, knapsack.greatest_knapsack_capacity())

    def test_knapsack_bottom_up_with_solutions(self):
        self._knapsack_checker(knapsack_0_1.KnapsackBottomUp)

        # This only works for the bottom up approach. It doesn't work for the memoized approach.
        knapsack = knapsack_0_1.KnapsackBottomUp(self.weights, self.values, self.capacities[-1])
        for index, capacity in zip(range(len(self.capacities)), self.capacities):
            self.assertEqual(self.expected_values[index], knapsack.value(capacity))
            self.assertEqual(self.expected_solutions[index], knapsack.solution(capacity))

        self.assertEqual(self.capacities[-1], knapsack.greatest_knapsack_capacity())

    def test_knapsack_memoized_with_solutions(self):
        self._knapsack_checker(knapsack_0_1.KnapsackMemoized)
