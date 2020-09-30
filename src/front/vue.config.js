const path = require("path");

module.exports = {
    outputDir: path.resolve(__dirname, "../" + "main/resources/static"),
    devServer: {
        host: "localhost",
        proxy: {
            "/api": {
                target: "http://localhost:8080",
                ws: true,
                changeOrigin: true
            }
        }
    }
};
