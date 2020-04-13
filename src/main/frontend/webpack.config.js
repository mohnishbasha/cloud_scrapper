const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const OptimizeCssAssetsPlugin = require('optimize-css-assets-webpack-plugin');
const UglifyJsPlugin = require('uglifyjs-webpack-plugin');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

const pathsToClean = ['docs'];
const cleanOptions = {
  dry: false,
  verbose: true,
  watch: false,
  exclude: [],
  allowExternal: false,
  beforeEmit: false,
};

module.exports = {
  entry: ['babel-polyfill', './src'],
  resolve: {
    extensions: ['.js', '.jsx', '.ts', '.tsx', '.json'],
    modules: [path.resolve(__dirname, './src'), 'node_modules'],
  },
  output: {
    filename: 'main.[chunkhash].js',
    path: path.resolve(__dirname, '../resources/static/ui'),
    publicPath: '/ui',
  },
  mode: 'production',
  module: {
    rules: [
      {
        test: /\.(js|jsx)$/,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
        },
      },
      {
        test: /\.(sa|sc|c)ss$/,
        use: [
          MiniCssExtractPlugin.loader,
          'css-loader',
          'postcss-loader',
          'sass-loader',
        ],
      },
      {
        test: /\.(png|jpg|gif|svg|eot|ttf|woff|woff2|otf|json)$/,
        use: [
          {
            loader: 'url-loader',
            options: {
              fallback: 'file-loader',
              limit: 8192,
              context: path.resolve(__dirname, '../src'),
              name: '[path][name].[ext]',
            },
          },
        ],
      },
    ],
  },
  optimization: {
    /*  minimizer: [
        new UglifyJsPlugin({
          uglifyOptions: {
            mangle: {
              keep_fnames: true
            }
          }
        })
      ], */
    runtimeChunk: true,
    splitChunks: {
      cacheGroups: {
        default: false,
        commons: {
          test: /[\\/]node_modules[\\/]/,
          name: 'vendor_app',
          chunks: 'all',
          minChunks: 2,
        },
      },
    },
  },
  plugins: [
    //  new CleanWebpackPlugin(cleanOptions),
    new OptimizeCssAssetsPlugin(),
    new HtmlWebpackPlugin({
      template: './src/index.html',
      filename: 'index.html',
    }),
    new MiniCssExtractPlugin({
      filename: '[name].[hash].css',
      chunkFilename: '[id].[hash].css',
    }),
  ],
};
