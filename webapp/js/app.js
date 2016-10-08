'use strict';

var gwuCarpool = angular.module('gwuCarpool', ['ngRoute', 'carpoolCtrl']);

   // configure our routes
   gwuCarpool.config(['$routeProvider',
                      function($routeProvider) {
       $routeProvider

           // route for the home page
           .when('/', {
               templateUrl : 'view/home.html',
               controller  : 'mainController'
           })

           // route for the about page
           .when('/about', {
               templateUrl : 'view/about.html',
               controller  : 'aboutController'
           })

           .when('/login', {
               templateUrl : 'view/login.html',
               controller  : 'loginController'
           })
           
           .when('/signup', {
               templateUrl : 'view/signup.html',
               controller  : 'signupController'
           })
           
           .when('/account', {
               templateUrl : 'view/account.html',
               controller  : 'accountController'
           })
       
       	   .when('/driver_info', {
           templateUrl : 'view/driver_info.html',
           controller  : 'driverInfoController'
           })
       
           .when('/rider_info', {
           templateUrl : 'view/rider_info.html',
           controller  : 'riderInfoController'
           })
           
           .when('/event/:eventId', {
           templateUrl : 'view/event.html',
           controller  : 'eventController'
           })
       
   }]);

   


