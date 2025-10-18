export function debounce<F extends (...args: any[]) => void>(fn: F, delay: number) {
    let timeout: ReturnType<typeof setTimeout>;
    return (...args: Parameters<F>) => {
        clearTimeout(timeout);
        timeout = setTimeout(() => fn(...args), delay);
    };
}