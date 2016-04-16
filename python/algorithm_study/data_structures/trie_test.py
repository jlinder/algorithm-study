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

from algorithm_study.data_structures import trie


class TrieTest(unittest.TestCase):
    def test_trie_no_values(self):
        dictionary = ['a', 'awe', 'bellow', 'belie', 'craft', 'zardoz']
        t = trie.Trie()
        for x in dictionary:
            t.insert(x)

        self.assertEquals((True, None), t.search('a'))
        self.assertEquals((True, None), t.search('awe'))
        self.assertEquals((True, None), t.search('bellow'))

        self.assertEquals((False, None), t.search('ad'))
        self.assertEquals((False, None), t.search('zardo'))
        self.assertEquals((False, None), t.search('zardozie'))
        self.assertEquals((False, None), t.search('mongoose'))

    def test_trie_set_values(self):
        dictionary = {
            'a': 'basic',
            'abba': 'awe',
            'bbbbbb': 'bellow',
            'lard': 'craft',
            'zardoz': 'god',
        }
        t = trie.Trie()
        for key, value in dictionary.items():
            t.insert(key, value)

        self.assertEquals((True, dictionary['a']), t.search('a'))
        self.assertEquals((True, dictionary['abba']), t.search('abba'))
        self.assertEquals((True, dictionary['lard']), t.search('lard'))

        self.assertEquals((False, None), t.search('ad'))
        self.assertEquals((False, None), t.search('zardo'))
        self.assertEquals((False, None), t.search('zardozie'))
        self.assertEquals((False, None), t.search('mongoose'))

    def test_trie_delete(self):
        dictionary = {
            'a': 'basic',
            'ab': 'double',
            'abba': 'awe',
            'ababab': None,
            'bbbbbb': 'bellow',
            'lard': 'craft',
            'ruda': None,
            'zardoz': 'god',
        }
        t = trie.Trie()
        for key, value in dictionary.items():
            if not value:
                t.insert(key)
            else:
                t.insert(key, value)

        self.assertEquals((True, dictionary['a']), t.search('a'))
        self.assertEquals((True, dictionary['ab']), t.search('ab'))
        self.assertEquals((True, dictionary['abba']), t.search('abba'))
        self.assertEquals((True, dictionary['ababab']), t.search('ababab'))
        self.assertEquals((True, dictionary['lard']), t.search('lard'))
        self.assertEquals((True, dictionary['ruda']), t.search('ruda'))

        self.assertEquals((False, None), t.search('r'))
        self.assertTrue(t.delete('ruda'))
        self.assertEquals((False, None), t.search('ruda'))
        self.assertEquals((False, None), t.search('r'))

        self.assertTrue(t.delete('ababab'))
        self.assertEquals((False, None), t.search('ababab'))
        self.assertEquals((True, dictionary['a']), t.search('a'))
        self.assertEquals((True, dictionary['abba']), t.search('abba'))

        self.assertTrue(t.delete('a'))
        self.assertEquals((False, None), t.search('ababab'))
        self.assertEquals((False, None), t.search('a'))
        self.assertEquals((True, dictionary['ab']), t.search('ab'))
        self.assertEquals((True, dictionary['abba']), t.search('abba'))

        self.assertTrue(t.delete('ab'))
        self.assertEquals((False, None), t.search('a'))
        self.assertEquals((False, None), t.search('ab'))
        self.assertEquals((True, dictionary['abba']), t.search('abba'))

        self.assertFalse(t.delete('a'))
        self.assertFalse(t.delete('abbab'))
        self.assertFalse(t.delete('x'))
