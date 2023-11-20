angular.module('app', ['index-module'])
    .controller('MainController', function($scope) {
        $scope.title = "MVC Social Network"
        $scope.openLoginModal = () => {
            console.log("Login modal!")
        }
    });