import { throttle } from 'lodash';
import { useCallback, useEffect, useState } from 'react';

interface IMediaQuery {
  query?: string;
  throttleTime?: number;
}

// According to Ant Design definition (please refer to And Design Gird layout)
const customBreakpoints = {
  xs: 575,
  sm: 576,
  md: 768,
  lg: 992,
  xL: 1200,
  xxL: 1600,
  xxLPro: 1800, // Add three new parts
  xxLFullHD: 1920, // standard
  xxLProMax: 2200,
};

type IRange = keyof typeof customBreakpoints; // "xs" | "sm" | "md" ...

// The maximum breakpoint separation unit for Ant Design useBreakpoint() is 1600, and it is currently not possible to customize the breakpoint separation unit
// Customize the current screen media query based on the throttling function to determine if it matches
export const useMediaQueryMatch = ({
  query = '',
  throttleTime = 200,
}: IMediaQuery): boolean => {
  const [match, setMatch] = useState<boolean>(false);

  const handleResize = useCallback(
    throttle((): void => {
      const mediaQueryList = window.matchMedia(query!);
      setMatch(mediaQueryList.matches);
    }, throttleTime),

    [throttleTime],
  );

  useEffect(() => {
    window?.addEventListener('resize', handleResize);

    // Set initial value
    handleResize();

    return (): void => {
      // Attention: Remove event listening, otherwise it may cause memory leaks
      window?.removeEventListener('resize', handleResize);
    };
  }, [handleResize]);

  return match;
};

// Customize the current screen media to match the resolution based on the throttle function
export const useMediaQuerySize = ({ throttleTime = 200 }: IMediaQuery) => {
  const [range, setRange] = useState<IRange>('xxL');

  const handleResize = useCallback(
    throttle((): void => {
      for (const key in customBreakpoints) {
        // Properties on non prototype objects
        if (Object.prototype.hasOwnProperty.call(customBreakpoints, key)) {
          const typedKey = key as keyof typeof customBreakpoints;
          let query = `(min-width: ${customBreakpoints[typedKey]}px)`;
          if (typedKey === 'xs')
            query = `(max-width: ${customBreakpoints[typedKey]}px)`;
          if (window?.matchMedia(query)?.matches) {
            setRange(typedKey);
          }
        }
      }
    }, throttleTime),

    [throttleTime],
  );

  useEffect(() => {
    window?.addEventListener('resize', handleResize);

    // Set initial value
    handleResize();

    return (): void => {
      // Attention: Remove event listening, otherwise it may cause memory leaks
      window?.removeEventListener('resize', handleResize);
    };
  }, [handleResize]);

  return range;
};
