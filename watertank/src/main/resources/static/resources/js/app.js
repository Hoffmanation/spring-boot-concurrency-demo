var loginApp = angular.module("loginApp", [ 'ui.bootstrap' ])


loginApp.controller("loginAppController",function($scope, $http, $rootScope, $window,$location,$interval) {

					$scope.restUrl = $location.protocol() + '://'+ $location.host() +':'+  $location.port();

					$scope.registrationErrorMessage = [] ;
					$scope.printAllWatertanks = [] ;
					$scope.username = $window.localStorage.getItem('username') ;
					
					  $scope.records = [
						    "Alfreds Futterkiste",
						    "Berglunds snabbköp",
						    "Centro comercial Moctezuma",
						    "Ernst Handel",
						  ]
					
					//Registration
					$scope.register = function() {	
						$scope.userDetails = {
								username : $scope.username,
								password : $scope.password,
								confirmPassword : $scope.confirmPassword,
								email : $scope.email 
							}
						
						$http({
							method : 'POST',
							data: $scope.userDetails,
							url : $scope.restUrl + "/registration",
							headers : {
								'Content-Type' : 'application/json',
								'Accept' : 'application/json'
							}
						}).success(
								function(response, data, status, headers,config) {
									if(response.status==200){
										$scope.registrationErrorMessage = response.entity ;										
									}
									else if(response.status==201){
										$window.localStorage.setItem('username', response.entity.username);
										$window.location.href = '/index.html';
									}
							
								}).error(
								function(response, data, status, headers,config) {
									console.log(response, data, status, headers,config) ;
								});
					};
									
					//Login
					$scope.login = function() {
						$scope.userDetails = {
							email : $scope.email,
							password : $scope.password,
						}
						
						$http({
							method : 'POST',
							data : $scope.userDetails,
							url : $scope.restUrl + "/login",
							headers : {
								'Content-Type' : 'application/json',
								'Accept' : 'application/json'
							}
						}).success(
								function(response, data, status, headers,config) {
									if(response.entity instanceof Array){
										$scope.registrationErrorMessage = response.entity ;	
										return ;
									}
										
									$window.location.href = '/index.html';

								}).error(
								function(response, data, status, headers,config) {
									console.log(response, data, status, headers, config) ;
								});
					};
					
					
				
					//Get User Data On submition
					$scope.getAllWatertanks = function() {
						 setInterval(function(){ 

								$http({
									method : 'GET',
									data : $scope.blog,
									url : $scope.restUrl + "/getAllWatertanks",
									headers : {
										'Content-Type' : 'application/json',
										'Accept' : 'application/json'
									}
								}).success(function(response, data, status, headers, config) {
									$scope.printAllWatertanks  = response.entity ;	
									
								}).error(function(response, data, status, headers, config) {
									console.log(response, data, status, headers, config) ;
								})
								
								
								
								
						 	}, 500);
			
					};
					
					//Logout
					$scope.logout = function(id, code , topic) {
						$http({
							method : 'POST',
							data : $scope.blog,
							url : $scope.restUrl + "/logout",
							headers : {
								'Content-Type' : 'application/json',
								'Accept' : 'application/json'
							}
						}).success(function(response, data, status, headers, config) {
							if(response.status==200){ 
								$window.location.href = '/login.html';								
							}
							
						}).error(function(response, data, status, headers, config) {
							console.log(response, data, status, headers, config) ;
						});
					};

		
				});


