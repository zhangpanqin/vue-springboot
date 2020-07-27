
const path = require('path');
const defaultSettings = require('./settings.js');

function resolve(dir) {
    return path.join(__dirname, dir);
}

// 页面 title
const name = defaultSettings.title || 'page title';


const vueConfig = {
    // BASE_URL
    publicPath: process.env.VUE_APP_DEPLOY_PATH,
    lintOnSave: process.env.NODE_ENV === 'development',
    // 生产环境不需要 source map
    productionSourceMap: false,
    devServer: {
        open: true,
        overlay: {
            warnings: false,
            errors: true,
        },
    },
    pluginOptions: {
        lintStyleOnBuild: process.env.NODE_ENV === 'development',
        stylelint: {
            fix: process.env.NODE_ENV === 'development',
        },
    },
    configureWebpack: {
        name,
        resolve: {
            alias: {
                '@': resolve('src'),
            },
        },
    },
    chainWebpack: (config) => {
        config.plugins.delete('preload');
        config.plugins.delete('prefetch');
        // 设置 public 目录别名
        config.resolve.alias.set('#', resolve('public'));
        // set preserveWhitespace
        config.module
            .rule('vue')
            .use('vue-loader')
            .loader('vue-loader')
            .tap((options) => {
                options.compilerOptions.preserveWhitespace = true;
                return options;
            })
            .end();
        config
            .when(process.env.NODE_ENV === 'development',
                (config2) => config2.devtool('cheap-source-map'));

        config
            .when(process.env.NODE_ENV !== 'development',
                (config3) => {
                    config3
                        .plugin('ScriptExtHtmlWebpackPlugin')
                        .after('html')
                        .use('script-ext-html-webpack-plugin', [{
                            inline: /runtime\..*\.js$/,
                        }])
                        .end();
                    config3
                        .optimization.splitChunks({
                            chunks: 'all',
                            cacheGroups: {
                                libs: {
                                    name: 'chunk-libs',
                                    test: /[\\/]node_modules[\\/]/,
                                    priority: 10,
                                    chunks: 'initial', // only package third parties that are initially dependent
                                },
                                elementUI: {
                                    name: 'chunk-elementUI', // split elementUI into a single package
                                    priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
                                    test: /[\\/]node_modules[\\/]_?element-ui(.*)/, // in order to adapt to cnpm
                                },
                                commons: {
                                    name: 'chunk-commons',
                                    test: resolve('src/components'),
                                    minChunks: 3,
                                    priority: 5,
                                    reuseExistingChunk: true,
                                },
                            },
                        });
                    config3.optimization.runtimeChunk('single');
                });
    },
};
if (process.env.NODE_ENV === 'development') {
    vueConfig.devServer.proxy = {
        [process.env.VUE_APP_BASE_API]: {
            target: process.env.VUE_APP_TARGET_API,
            changeOrigin: true,
            pathRewrite: {
                [`^${process.env.VUE_APP_BASE_API}`]: '',
            },
        },
    };
}
console.log(process.env.BASE_URL);
module.exports = vueConfig;
