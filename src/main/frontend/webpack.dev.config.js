const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const OptimizeCssAssetsPlugin = require('optimize-css-assets-webpack-plugin');
const UglifyJsPlugin = require('uglifyjs-webpack-plugin');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const webpack = require('webpack');


module.exports = {
  mode: 'development',
  entry: ['babel-polyfill', './src/index.jsx'],
  resolve: {
    extensions: ['.js', '.jsx', '.ts', '.tsx', '.json'],
    modules: [path.resolve(__dirname, './src'), 'node_modules'],
  },
  output: {
    filename: 'dev.js',
    path: path.resolve(__dirname, '../resources/static/ui'),
    publicPath: '/',
  },
  module: {
    rules: [
      {
        test: /\.(js|jsx)$/,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
        },
      },
    ],
  },
  optimization: {
    minimize: false,
    minimizer: [
      new UglifyJsPlugin({
        uglifyOptions: {
          mangle: {
            keep_fnames: true,
          },
          compress: { drop_debugger: false },
        },
      }),
    ],
    runtimeChunk: false,
    namedModules: true,
  },
  devServer: {
    historyApiFallback: true,
  },
  devtool: 'inline-source-map',
  plugins: [

    new HtmlWebpackPlugin({
      template: './src/index.html',
      filename: 'index.html',
    }),
  ],
};
