export function getRadicals(): string[] {
    const start = 0x18800
    const end = 0x18AFF
    const arr: string[] = []
    for (let cp = start; cp <= end; cp++) {
        arr.push(String.fromCodePoint(cp))
    }
    return arr
}
