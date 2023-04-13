module.exports = {
  ci: {
    collect: {
      url: ['http://127.0.0.1:4173/'],
      startServerCommand: 'npm run preview',
      settings: {
        chromeFlags: "--no-sandbox",
    },
    },
    // assert: {
    //   preset: 'lighthouse:recommended',
    // },
    upload: {
      target: 'lhci',
      serverBaseUrl: '#####',
      token: '#####',
    },
  },
};