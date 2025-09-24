/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        "./index.html",
        "./src/**/*.{vue,js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            fontFamily: {
                tangut: ['TangutYinchuan', 'serif'], // fallback to serif
            },
        },
    },
    plugins: [],
}
