// Pay attention to: @antv/g6 Dependency must be installed using npm
import * as G6 from '@antv/g6';
import { isEmpty } from 'lodash';
import React, { useEffect, useRef } from 'react';

const G6Graph: React.FC = () => {
  // DRAW P
  const mountNodeRef: React.MutableRefObject<HTMLDivElement | null> =
    useRef(null);

  useEffect(() => {
    const data = {
      // Combos: [{ id: 'combo-1' }], // The combination data has not been used yet and can be ignored
      nodes: [
        // Node example data
        {
          id: 'node1',
          // Combos: 'combo-1',
          label: 'Node 1',
        },
        {
          id: 'node2',
          // Combos: 'combo-1',
          label: 'Node 2',
        },
        {
          id: 'node3',
          // Combos: 'combo-1',
          label: 'Node 3',
        },
        {
          id: 'node4',
          // Combos: 'combo-1',
          label: 'Node 4',
        },
        {
          id: 'node5',
          // Combos: 'combo-1',
          label: 'Node 5',
        },
        {
          id: 'node6',
          // Combos: 'combo-1',
          label: 'Node 6',
        },
      ],
      // Edge data
      edges: [
        { source: 'node1', target: 'node2' },
        { source: 'node1', target: 'node3' },
        { source: 'node1', target: 'node4' },
        { source: 'node2', target: 'node5' },
        { source: 'node2', target: 'node6' },
      ],
    };

    if (!isEmpty(G6) && !isEmpty(mountNodeRef.current)) {
      // G6Instance
      const graph: G6.Graph = new G6.Graph({
        container: mountNodeRef.current!, // Canvas Container
        width: 600,
        height: 460,
        data: data,
        autoResize: true, // Automatically adjust canvas size
        background: '#FFF', // Canvas background color
        autoFit: {
          // Does it automatically adapt
          type: 'center',
        },
        animation: true, // Enable or disable global animation
        layout: {
          nodeSize: 42,
          type: 'mds', // Temporarily use this type
          linkDistance: 130,
          center: [300, 300],
        },
        node: {
          // Node attributeN
          type: 'circle', // Node Type
          style: {
            size: [42, 42], // Node size, quick setting of node width and height
            fill: '#7e3feb', // Node fill color
            fillOpacity: 1,
            labelPlacement: 'center',
            labelText: (d) => d.id,
            labelFontSize: 12,
            labelTextDecorationStyle: 'solid',
            labelFill: '#FFF',
            lineWidth: 0,
            cursor: 'pointer',
            port: true, // Do you want to display the connection pile
            halo: false, // Whether to display node halo
          },
        },
        edge: {
          // Edge attribute
          // Type: 'quadratic',
          style: {
            endArrow: true,
            endArrowSize: 6,
            stroke: '#377df7', // Edge fill color
            strokeOpacity: '0.8',
            lineWidth: 1.5,
            loop: false, // Do you want to enable self looping edges
            halo: false, // Whether to display node halo
          },
        },
        behaviors: ['zoom-canvas', 'drag-canvas', 'drag-element'],
        zoomRange: [0.5, 2],
      });

      // Image rendering
      graph?.render();

      return (): void => {
        graph?.destroy();
      };
    }
  }, []);

  return <div ref={mountNodeRef} />;
};

export default G6Graph;
