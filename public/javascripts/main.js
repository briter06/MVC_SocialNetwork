// Part of the code was obtained from the official documentation for AngularJS
// https://material.angularjs.org/latest/demo/dialog

const app = angular.module('app', ['ngMaterial','index-module'])
app.controller('MainController', function($scope, $mdDialog) {
        $scope.title = "MVC Social Network"
    });