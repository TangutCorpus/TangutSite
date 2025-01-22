/** @type {import('tailwindcss').Config} */
module.exports = {
  mode: "all",
  content: ["./src/**/*.{rs,html,css}", "./dist/**/*.html"],
  theme: {
    colors: {
      'zink-50': '#FAFAFA',
      'zink-100': '#F4F4F5',
      'zink-200': '#E4E4E7',
      'zink-500': '#71717A',
      'zink-800': '#27272A',
      "zink-950": '#09090B',
    },
    fontFamily: {
      sans: ["Inter", "sans-serif"],
    },
    extend: {},
  },
  plugins: [],
};
