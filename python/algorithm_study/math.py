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
This module implements some math functions.
"""


def gcd_euclid_iterative(a, b):
    """
    Calculate the greatest common divisor of the arguments using euclid's algorithm in an iterative approach.
    :param int a: Integer a. Must be > 0.
    :param int b: Integer b. Must be > 0.
    :return: The greatest common divisor.
    :rtype: int
    """
    if a < 1 or b < 1:
        raise ValueError
    if b > a:
        (b, a) = (a, b)
    while b != 0:
        (a, b) = (b, a % b)
    return a


def gcd_euclid_recursive(a, b):
    """
    Calculate the greatest common divisor of the arguments using euclid's algorithm in a recursive approach.
    :param int a: Integer a. Must be > 0.
    :param int b: Integer b. Must be > 0.
    :return: The greatest common divisor.
    :rtype: int
    """
    if a < 1 or b < 1:
        raise ValueError
    if b > a:
        (b, a) = (a, b)
    return _gcd_euclid_recursive(a, b)


def _gcd_euclid_recursive(a, b):
    if b == 0:
        return a
    return _gcd_euclid_recursive(b, a % b)
