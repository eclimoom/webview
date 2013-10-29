/**
 * Use for [FILEName].
 * Author: hejj
 * Date: 13-10-9
 * Time: 上午11:11
 * 业务进展报告 & 放款数据报告.
 */
'use strict';
var imgApp = angular.module('imgApp', []).controller('ImagesController', function ($scope, $http) {
    $scope.loaded = true;
    $scope.showImage = function (str) {
        //Android.showToast(toast);
        Android.showImg(str);
    }
});