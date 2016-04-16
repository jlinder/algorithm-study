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
This module implements the trie data structure.
"""


class Node(object):
    def __init__(self, value=None, leaf=False):
        """
        Initialize a node.

        :param object value: The value of the node. Default: None
        :param bool leaf: If the node is a leaf node, then True, else False. Default: False
        """
        self.value = value
        self.children = {}
        self.leaf = leaf


class Trie(object):
    def __init__(self):
        """
        Construct a new instance of Trie.
        """
        self.root = Node()

    def insert(self, key, value=None):
        """
        Insert a key, possibly paired with a non-boolean value. If the key already exists, it will overwrite the
        existing value with the new value.

        :param str key: The key to insert.
        :param object|bool|int|str value: A desired value. Default: None.
        """
        node = self.root
        for index in range(len(key)):
            if key[index] not in node.children:
                node.children[key[index]] = Node()
            node = node.children[key[index]]
        node.value = value
        node.leaf = True

    def search(self, key):
        """
        Find the key in the Trie.

        :param str key: The key to search for.
        :return: A tuple with element 0 indicating if the key exists in the trie and element 1 as the value.
        :rtype: tuple[bool,object|bool|int|str]
        """
        node = self.root
        for index in range(len(key)):
            if key[index] not in node.children:
                return False, None
            node = node.children[key[index]]
        return (True, node.value) if node.leaf else (False, None)

    def delete(self, key):
        """
        Delete a key from the trie.

        :param key: The key to delete from the trie.
        :return: True if the key was deleted. False if it wasn't found
        :rtype: bool
        """
        result = self._delete(self.root, key, 0)
        return result[0]

    def _delete(self, node, key, offset):
        """
        Recursively descend the trie searching for the key and, if the key is found, delete the nodes from the trie that
        can be safely deleted.

        :param Node node: The current node in the tree.
        :param str key: The key being deleted.
        :param int offset: The offset in the key (a.k.a. the leve in the tree).
        :return: A tuple where element 0 indicates if the key was found and element 1 indicates if the node at below
        checked index was deleted.
        :rtype: tuple[bool,bool]
        """
        if offset == len(key) - 1:
            if key[offset] not in node.children:
                return False, False
            child = node.children[key[offset]]
            if not child.leaf:
                return False, False
            if child.children:
                child.leaf = False
                child.value = None
                return True, False
            else:
                del node.children[key[offset]]
                return True, True
        if key[offset] in node.children:
            result = self._delete(node.children[key[offset]], key, offset + 1)
            if result[1] and not node.children[key[offset]].leaf:
                del node.children[key[offset]]
                return result
            return result[0], False
        else:
            return False, False
