(function javaCodeHighlights() {
    const MARKER_REGEX = /[ \t]*\/\/\s*HIGHLIGHT_([A-Z0-9]+)_(BEGIN|END)\s*(?:\r?\n)?/g;
    const COLOR_MAP = {
        GREEN: 'var(--code-highlight-green)',
        YELLOW: 'var(--code-highlight-yellow)',
        RED: 'var(--code-highlight-red)',
        BLUE: 'var(--code-highlight-blue)',
    };

    function wrapRange(range, color) {
        if (range.collapsed) {
            range.detach?.();
            return;
        }

        const span = document.createElement('span');
        span.className = 'code-highlight-inline';
        span.style.backgroundColor = COLOR_MAP[color] || COLOR_MAP.GREEN;
        span.dataset.highlightColor = color;

        try {
            range.surroundContents(span);
        } catch (err) {
            // If surroundContents fails due to partially selected nodes,
            // fall back to extracting the contents manually.
            const contents = range.extractContents();
            span.appendChild(contents);
            range.insertNode(span);
        }
        range.detach?.();
    }

    function moveForwardPastBreaks(textNodes, indexMap, node, offset) {
        let idx = indexMap.get(node);
        let currentNode = node;
        let currentOffset = offset;

        while (currentNode) {
            const text = currentNode.nodeValue || '';
            while (currentOffset < text.length) {
                const ch = text[currentOffset];
                if (ch === '\n' || ch === '\r' || ch === ' ' || ch === '\t') {
                    currentOffset += 1;
                } else {
                    return { node: currentNode, offset: currentOffset };
                }
            }
            idx += 1;
            currentNode = textNodes[idx];
            currentOffset = 0;
        }

        return { node: currentNode || node, offset: currentOffset };
    }

    function moveBackwardPastBreaks(textNodes, indexMap, node, offset) {
        let idx = indexMap.get(node);
        let currentNode = node;
        let currentOffset = offset;

        while (currentNode) {
            const text = currentNode.nodeValue || '';
            while (currentOffset > 0) {
                const ch = text[currentOffset - 1];
                if (ch === '\n' || ch === '\r' || ch === ' ' || ch === '\t') {
                    currentOffset -= 1;
                } else {
                    return { node: currentNode, offset: currentOffset };
                }
            }
            idx -= 1;
            currentNode = textNodes[idx];
            if (!currentNode) {
                break;
            }
            currentOffset = currentNode.nodeValue ? currentNode.nodeValue.length : 0;
        }

        return {
            node: currentNode || node,
            offset: currentNode ? currentOffset : 0,
        };
    }

    function findLineStart(textNodes, indexMap, node, offset) {
        let idx = indexMap.get(node);
        let currentNode = node;
        let currentOffset = offset;
        const fallbackNode = textNodes[0] || node;

        while (currentNode) {
            const text = currentNode.nodeValue || '';
            let i = Math.min(currentOffset, text.length);
            while (i > 0) {
                const ch = text[i - 1];
                if (ch === '\n' || ch === '\r') {
                    return { node: currentNode, offset: i };
                }
                i -= 1;
            }
            idx -= 1;
            currentNode = textNodes[idx];
            if (!currentNode) {
                break;
            }
            currentOffset = currentNode.nodeValue ? currentNode.nodeValue.length : 0;
        }

        return { node: fallbackNode, offset: 0 };
    }

    function findLineEnd(textNodes, indexMap, node, offset) {
        let idx = indexMap.get(node);
        let currentNode = node;
        let currentOffset = offset;
        const fallbackNode = textNodes[textNodes.length - 1] || node;

        while (currentNode) {
            const text = currentNode.nodeValue || '';
            let i = currentOffset;
            while (i < text.length) {
                const ch = text[i];
                if (ch === '\n' || ch === '\r') {
                    return { node: currentNode, offset: i };
                }
                i += 1;
            }
            idx += 1;
            currentNode = textNodes[idx];
            currentOffset = 0;
        }

        const fallbackOffset = fallbackNode && fallbackNode.nodeValue ? fallbackNode.nodeValue.length : 0;
        return { node: fallbackNode, offset: fallbackOffset };
    }

    function removeLeadingWhitespace(textNodes, indexMap, node, offset) {
        let idx = indexMap.get(node);
        let currentNode = node;
        let currentOffset = offset;

        while (currentNode) {
            const text = currentNode.nodeValue || '';
            while (currentOffset > 0) {
                const ch = text[currentOffset - 1];
                if (ch === ' ' || ch === '\t') {
                    currentNode.deleteData(currentOffset - 1, 1);
                    currentOffset -= 1;
                } else if (ch === '\n' || ch === '\r') {
                    return;
                } else {
                    return;
                }
            }
            idx -= 1;
            currentNode = textNodes[idx];
            if (!currentNode) {
                break;
            }
            currentOffset = currentNode.nodeValue ? currentNode.nodeValue.length : 0;
        }
    }

    function removeFollowingLineBreaks(textNodes, indexMap, node, offset) {
        let idx = indexMap.get(node);
        let currentNode = node;
        let currentOffset = offset;

        while (currentNode) {
            const text = currentNode.nodeValue || '';
            if (currentOffset >= text.length) {
                idx += 1;
                currentNode = textNodes[idx];
                currentOffset = 0;
                continue;
            }

            const ch = text[currentOffset];
            if (ch === '\r' || ch === '\n') {
                currentNode.deleteData(currentOffset, 1);
                continue;
            }
            break;
        }
    }

    function processCodeBlock(code) {
        const walker = document.createTreeWalker(code, NodeFilter.SHOW_TEXT, null);
        const textNodes = [];
        let node;
        while ((node = walker.nextNode())) {
            textNodes.push(node);
        }

        const indexMap = new Map(textNodes.map((textNode, idx) => [textNode, idx]));

        let activeRange = null;
        let activeColor = null;

        textNodes.forEach(textNode => {
            let text = textNode.nodeValue;
            if (!text || !text.includes('HIGHLIGHT_')) {
                return;
            }

            MARKER_REGEX.lastIndex = 0;

            let match;
            while ((match = MARKER_REGEX.exec(text)) !== null) {
                const matchStart = match.index;
                const matchLength = match[0].length;
                const color = match[1].toUpperCase();
                const type = match[2];

                removeLeadingWhitespace(textNodes, indexMap, textNode, matchStart);
                textNode.deleteData(matchStart, matchLength);
                text = textNode.nodeValue || '';
                removeFollowingLineBreaks(
                    textNodes,
                    indexMap,
                    textNode,
                    Math.min(matchStart, textNode.nodeValue ? textNode.nodeValue.length : 0)
                );

                if (type === 'BEGIN') {
                    const range = document.createRange();
                    const { node: startNode, offset: startOffset } = moveForwardPastBreaks(
                        textNodes,
                        indexMap,
                        textNode,
                        Math.min(matchStart, textNode.nodeValue.length)
                    );
                    range.setStart(startNode || textNode, startOffset);
                    activeRange = range;
                    activeColor = color;
                } else if (activeRange) {
                    const { node: endNode, offset: endOffset } = moveBackwardPastBreaks(
                        textNodes,
                        indexMap,
                        textNode,
                        Math.min(matchStart, textNode.nodeValue.length)
                    );
                    activeRange.setEnd(endNode || textNode, endOffset);
                    const startBoundary = findLineStart(
                        textNodes,
                        indexMap,
                        activeRange.startContainer,
                        activeRange.startOffset
                    );
                    const endBoundary = findLineEnd(
                        textNodes,
                        indexMap,
                        activeRange.endContainer,
                        activeRange.endOffset
                    );
                    activeRange.setStart(startBoundary.node || activeRange.startContainer, startBoundary.offset);
                    activeRange.setEnd(endBoundary.node || activeRange.endContainer, endBoundary.offset);
                    wrapRange(activeRange, activeColor);
                    activeRange = null;
                    activeColor = null;
                }

                MARKER_REGEX.lastIndex = matchStart;
            }
        });
    }

    document
        .querySelectorAll('pre > code.language-java')
        .forEach(processCodeBlock);
})();
