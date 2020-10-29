#!/usr/bin/env python3

#  Copyright 2020 Baptiste Candellier
#
#     Licensed under the Apache License, Version 2.0 (the "License");
#     you may not use this file except in compliance with the License.
#     You may obtain a copy of the License at
#
#         http://www.apache.org/licenses/LICENSE-2.0
#
#     Unless required by applicable law or agreed to in writing, software
#     distributed under the License is distributed on an "AS IS" BASIS,
#     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#     See the License for the specific language governing permissions and
#     limitations under the License.

import argparse
import re
import os
import sys


def get_script_path():
    return os.path.dirname(os.path.realpath(sys.argv[0]))


def main():
    parser = argparse.ArgumentParser(
        description='Generate the font map for Material Design Icons.')
    parser.add_argument('input', type=str,
                        help='the minified css file containing the mapping for the mdi font')
    args = parser.parse_args()

    regex = r'\.mdi-([a-z\-0-9]+)::before\{content:"\\([A-F0-9]{2,8})"\}'

    with open(args.input, 'r') as f_in:
        content = f_in.read()

    for res in re.finditer(regex, content):
        name = res.group(1)
        hex_char = res.group(2).rjust(8, "0")
        char = bytes.fromhex(hex_char)
        str_char = bytes.decode(char, 'utf-32-be')

        print(name + ' ' + str_char)


if __name__ == "__main__":
    main()
