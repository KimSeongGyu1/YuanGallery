module.exports = {
  "outputDir": "/home/ksg/Desktop/gitHub/gallery/src/main/resources/static",
  "devServer": {
    "host": "localhost",
    "proxy": {
      "/api": {
        "target": "http://localhost:8080",
        "ws": true,
        "changeOrigin": true
      }
    }
  },
  "transpileDependencies": [
    "vuetify"
  ]
}