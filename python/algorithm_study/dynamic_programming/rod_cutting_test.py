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

from algorithm_study.dynamic_programming import rod_cutting


class RodCuttingTest(unittest.TestCase):
    def setUp(self):
        self.prices = [0, 1, 3, 3, 3, 7, 8, 0, 14, 15, 18, 22, 38]
        self.expected_values = [1, 3, 4, 6, 7, 9, 10, 14, 15, 18, 22, 38]
        self.expected_solutions = [
            [1],
            [2],
            [1, 2],
            [2, 2],
            [1, 2, 2],
            [2, 2, 2],
            [1, 2, 2, 2],
            [8],
            [1, 8],
            [10],
            [11],
            [12],
        ]

    def test_cut_rod_memoized(self):
        for x in range(len(self.expected_values)):
            self.assertEqual(self.expected_values[x], rod_cutting.cut_rod_memoized(self.prices, x + 1))

    def test_cut_rod_bottom_up(self):
        for x in range(len(self.expected_values)):
            self.assertEqual(self.expected_values[x], rod_cutting.cut_rod_bottom_up(self.prices, x + 1))

    def _cut_rod_checker(self, cut_rod):
        cut_rod_str = (
            'values: [0, 1, 3, 4, 6, 7, 9, 10, 14, 15, 18, 22, 38]\n'
            'solutions: [0, 1, 2, 1, 2, 1, 2, 1, 8, 1, 10, 11, 12]'
        )
        self.assertEqual(cut_rod_str, str(cut_rod))

        for x in range(len(self.expected_values)):
            self.assertEqual(self.expected_values[x], cut_rod.value(x + 1))

        for x in range(len(self.expected_values)):
            self.assertEqual(self.expected_solutions[x], cut_rod.solution(x + 1))

        self.assertEqual(len(self.expected_values), cut_rod.maximum_rod_length())

    def test_cut_rod_bottom_up_with_solutions(self):
        cut_rod = rod_cutting.CutRodBottomUp(self.prices, len(self.expected_values))
        self._cut_rod_checker(cut_rod)

    def test_cut_rod_memoized_with_solutions(self):
        cut_rod = rod_cutting.CutRodMemoized(self.prices, len(self.expected_values))
        self._cut_rod_checker(cut_rod)
