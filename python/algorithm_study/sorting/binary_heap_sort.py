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
This module implements the sorting algorithm "heap sort".
"""


def binary_heap_sort(array):
    """
    Sort an array, in place, using a binary heap.

    :param list array: The list of elements to sort.
    """
    for index in reversed(range(len(array) / 2 - 1)):
        _binary_heapify(array, index, len(array))

    for index in reversed(range(len(array))):
        array[0], array[index] = array[index], array[0]
        _binary_heapify(array, 0, index)


def _binary_heapify(array, index, number_of_elements):
    """
    Recursive function to turn a list into a binary heap.

    :param list array: The list of elements being heapified
    :param int index: The node being compared with its children
    :param int number_of_elements: The number of elements in the list.
    """
    largest = index
    left = index * 2 + 1
    right = index * 2 + 2

    if left < number_of_elements and array[left] > array[largest]:
        largest = left

    if right < number_of_elements and array[right] > array[largest]:
        largest = right

    if largest != index:
        array[index], array[largest] = array[largest], array[index]
        _binary_heapify(array, largest, number_of_elements)
