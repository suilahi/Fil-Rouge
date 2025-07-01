// tailwind.config.js
/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}", // Adjust this path if your components are elsewhere
  ],
  theme: {
    extend: {
      colors: {
        'primary-orange': '#ed5637', // Example primary accent color
        'dark-background': '#1a1a1a', // Example for dark sections like footer
        'light-gray': '#f5f5f5',     // Example for lighter backgrounds
        'dark-text': '#333333',      // Example for dark text
        'light-text': '#ffffff',     // Example for light text
        'header-bg-light': '#ffffff', // Header background when not fixed
        'header-bg-dark': '#212121',  // Header background when fixed/scrolled
        'search-border': '#666666',   // Border color for search input
        'header-link-hover': '#ed5637', // Nav link hover color
      },
      spacing: {
        '18': '4.5rem', // For the header's top/bottom padding 70px -> ~4.375rem
        '15': '3.75rem', // For some internal spacing 60px -> ~3.75rem
        '13': '3.25rem', // For 52px
      },
      height: {
        '18': '4.5rem', // For header fixed height 70px
      }
    },
  },
  plugins: [],
}
