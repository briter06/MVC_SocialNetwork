// Part of the code was obtained from the official documentation for AngularJS
// https://material.angularjs.org/latest/demo/dialog

const app = angular.module('app', ['ngMaterial','index-module', 'home-module'])

app.controller('MainController', function($scope) {

    $scope.openMenu = function($mdMenu, ev) {
        $mdMenu.open(ev);
    };

    $scope.redirect = function(path){
        location.href = path
    }
});