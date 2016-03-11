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

from algorithm_study.dynamic_programming import fibonacci


class FibonacciTest(unittest.TestCase):
    def setUp(self):
        self.expected_values = [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89]
        self.expected_solutions = [
            [],
            [],
            [0, 1],
            [1, 1],
            [1, 2],
            [2, 3],
            [3, 5],
            [5, 8],
            [8, 13],
            [13, 21],
            [21, 34],
            [34, 55],
        ]

    def test_fibonacci_memoized(self):
        for x in range(len(self.expected_values)):
            self.assertEqual(self.expected_values[x], fibonacci.fibonacci_memoized(x))

    def test_fibonacci_bottom_up(self):
        for x in range(len(self.expected_values)):
            self.assertEqual(self.expected_values[x], fibonacci.fibonacci_bottom_up(x))

    def _fibonacci_checker(self, fib):
        fibonacci_str = 'values: [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89]'
        self.assertEqual(fibonacci_str, str(fib))

        for x in range(2, len(self.expected_values)):
            self.assertEqual(self.expected_values[x], fib.value(x))

        for x in range(2, len(self.expected_values)):
            self.assertEqual(self.expected_solutions[x], fib.solution(x))

        self.assertEqual(len(self.expected_values) - 1, fib.maximum_index())

    def test_fibonacci_bottom_up_with_solutions(self):
        fib = fibonacci.FibonacciBottomUp(len(self.expected_values) - 1)
        self._fibonacci_checker(fib)

    def test_fibonacci_memoized_with_solutions(self):
        fib = fibonacci.FibonacciMemoized(len(self.expected_values) - 1)
        self._fibonacci_checker(fib)
