angular.module('home-module', [])
    .controller('HomeController', function($scope) {
        $scope.formatDate = (stringDate) => {
            const date = new Date(stringDate)
            var options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }
            return date.toLocaleDateString(undefined, options)
        }
    });