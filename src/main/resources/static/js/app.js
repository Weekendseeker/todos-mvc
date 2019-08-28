(function (){
    'use strict';

    angular
        .module('todomvc', ['ngRoute','ngResource'])
        .config(config);
        //.run(run);

    config.$inject = ['$routeProvider', '$locationProvider'];
    function config($routeProvider, $locationProvider) {
        $routeProvider.when('/', {
            controller: 'TodoCtrl',
            templateUrl: 'todos.html',
            controllerAs: 'vm',
            resolve: {
                store: function (todoStorage) {
                    return todoStorage.then(function (module) {
                        module.get();
                        return module;
                    });
                }
            }
        })
    }
//
// .when('/login', {
//         controller: 'LoginController',
//         templateUrl: 'login.html',
//         controllerAs: 'vm'
//     }).otherwise({ redirectTo: '/login' });
    // run.$inject = ['$rootScope', '$location', '$http', '$window'];
    // function run($rootScope, $location, $http, $window) {
    //     var userData = $window.sessionStorage.getItem('userData');
    //     if (userData) {
    //         $http.defaults.headers.common['Authorization']
    //             = 'Basic ' + JSON.parse(userData).authData;
    //     }
    //
    //     $rootScope
    //         .$on('$locationChangeStart', function (event, next, current) {
    //             var restrictedPage
    //                 = $.inArray($location.path(), ['/login']) === -1;
    //             var loggedIn
    //                 = $window.sessionStorage.getItem('userData');
    //             if (restrictedPage && !loggedIn) {
    //                 $location.path('/login');
    //             }
    //         });
    // }

})();