var hopsControllers = angular.module('hopsControllers', []);

hopsControllers.controller('HopsListController', ['$scope', '$http',
    function($scope, $http) {
        $scope.hopName = "";
        $scope.hopDescription = "";

        $http.get('/api/hops').success(function(data) {
            $scope.hops = data;
        });

        $scope.$on('hopCreation', function(event, hopData) {
            $scope.hops.push(hopData);
        });
    }
]);

hopsControllers.controller('HopsDataController', ['$scope', '$routeParams', '$http',
    function($scope, $routeParams, $http) {
        $scope.hopId = $routeParams.hopId;
        $http.get('/api/hops/' + $scope.hopId).success(function(data) {
            $scope.hop = data;
        });
    }
]);

hopsControllers.controller('HopsCreationController', ['$scope', '$http',
    function($scope, $http) {
        $scope.formData = {};

        $scope.createHop = function() {
            var newHop = {};
            newHop.name = $scope.formData.hopName;
            newHop.description = $scope.formData.hopDescription;
            $scope.formData = {};
            $http.post('/api/hops', newHop)
                .success(function(data, status, header, config) {
                    $scope.$emit('hopCreation', data);
                })
                .error(function(data, status, header, config) {
                    alert('Creation failed');
                });
        }
    }
]);
// and other controllers here.