class ConsoleErrorFilterPlugin {
  constructor(private keywords: string[]) {}

  apply(compiler: any) {
    compiler.hooks.emit.tapAsync(
      'ConsoleErrorFilterPlugin',
      (compilation: any, callback: any): void => {
        for (const filename in compilation.assets) {
          if (filename.endsWith('.js')) {
            const source = compilation.assets[filename].source();
            const filterCode = `
              (function() {
                const originalConsoleError = console.error;
                console.error = function(...args) {
                  const shouldFilter = ${JSON.stringify(
                    this.keywords,
                  )}.some(keyword =>
                    args.some(arg => typeof arg === 'string' && arg.includes(keyword))
                  );
                  if (!shouldFilter) {
                    originalConsoleError.apply(console, args);
                  }
                };
              })();
            `;
            const newSource = `${filterCode}\n${source}`;
            compilation.assets[filename] = {
              source: (): string => newSource,
              size: (): number => newSource.length,
            };
          }
        }
        callback();
      },
    );
  }
}

export default ConsoleErrorFilterPlugin;
