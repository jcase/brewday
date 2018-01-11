var brewDayApp = angular.module('brewDay', [
    'ngRoute',
    'hopsControllers',
    'yeastsControllers'
]);

brewDayApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/hops', {
        templateUrl: 'templates/hop-list.html',
        controller: 'HopsListController'
      }).
      when('/hops/:hopId', {
        templateUrl: 'templates/hop-detail.html',
        controller: 'HopsDataController'
      }).
      when('/yeasts', {
        templateUrl: 'templates/yeast-list.html',
        controller: 'YeastsListController'
      }).
      otherwise({
        templateUrl: 'templates/main.html'
      });
  }]);