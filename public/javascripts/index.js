angular.module('index-module', [])
    .controller('IndexController', function($scope) {
        $scope.minLength = 5
        $scope.maxLength = 30
    });