var yeastsControllers = angular.module('yeastsControllers', []);

yeastsControllers.controller('YeastsListController', ['$scope', '$http',
    function($scope, $http) {
        $http.get('/api/yeast').success(function(data) {
            $scope.yeasts = data;
        });
    }
]);
