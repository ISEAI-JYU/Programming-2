import json
import sys
import re
import io
import secrets

from util import preprocessor_start, process_chapters


CODE_BLOCK_PATTERN = re.compile(r"""
^(?P<indentation> *)([`~]{3,})[ \t]*(.*?)[ \t]*$    # Line start, Leading spaces (indentation), Opening code fence with optional language
\n(?P<code_block_contents>.*?)\n                    # Code block contents
^\1\2[ \t]*$                                        # Closing code fence with same indentation
""", flags=re.VERBOSE | re.MULTILINE | re.DOTALL)


def process_chapter(chapter):
    cur_contents = chapter['content']


if __name__ == '__main__':
    preprocessor_start()

    context, book = json.load(sys.stdin)

    process_chapters(book['sections'], process_chapter)

    print(json.dumps(book))