// tailwind.config.js
module.exports = {
  content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  darkMode: 'media', // or 'media' or 'class'
  theme: {
    extend: {colors: {
      'blueBlack':'#0d0d19',
      'lighterBlueBlack':'#0B1725',
      'darkerBlue': '#0d1d30',
      'darkBlue': '#102c47',
      'skyBlue': '#206191',
      'lightBlue': '#2bc0d3',
      'whiteBlue': '#aaced1',
      'inventoryBG': '#8b8b8b',
      'inventoryBorderDark':'#373737'
    },
  },
    },
    
  variants: {
    extend: {},
  },
  plugins: [],
}